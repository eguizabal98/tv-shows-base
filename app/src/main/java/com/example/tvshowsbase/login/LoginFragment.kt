package com.example.tvshowsbase.login

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.domain.model.InternalErrorCodes
import com.example.domain.model.WorkState
import com.example.tvshowsbase.R
import com.example.tvshowsbase.databinding.LoginFragmentBinding
import com.example.tvshowsbase.databindingutils.visible
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: LoginFragmentBinding
    @Inject lateinit var sharedPreferences: SharedPreferences
    private val args: LoginFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.loginWebView.canGoBack()) {
                        binding.loginWebView.goBack()
                    } else {
                        isEnabled = false
                        activity?.onBackPressed()
                    }
                }
            }
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        webViewConfiguration()
        createViewModelObservers()
        createBindingFragmentObservers()
        checkCredentials()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewConfiguration() {
        if (args.logOut) {
            binding.loginWebView.clearCache(true)
            binding.loginWebView.loadUrl(WEB_VIEW_URL_LOGOUT)
            binding.loginWebView.clearHistory()
        }
        binding.loginWebView.webViewClient = webClient
        binding.loginWebView.settings.javaScriptEnabled = true
    }

    private fun createBindingFragmentObservers() {
        binding.loginButton.setOnClickListener {
            viewModel.getAuthToken()
        }
    }

    private fun createViewModelObservers() {
        viewModel.authTokeState.observe(
            viewLifecycleOwner,
            { response ->
                handleResponse(
                    response = response,
                    successAction = {
                        viewModel.tokenTemp = it
                        binding.loginWebView.loadUrl("$WEB_VIEW_URL${viewModel.tokenTemp}")
                        updateUIChangeToWebView()
                    }
                )
            }
        )

        viewModel.sessionIdState.observe(
            viewLifecycleOwner,
            { response ->
                handleResponse(
                    response = response,
                    successAction = {
                        sharedPreferences.edit {
                            putString(SESSION_KEY, it)
                            apply()
                        }
                        checkCredentials()
                    }
                )
            }
        )

        viewModel.accountIdState.observe(
            viewLifecycleOwner,
            { response ->
                handleResponse(
                    response = response,
                    successAction = {
                        sharedPreferences.edit {
                            putInt(ACCOUNT_KEY, it)
                            apply()
                        }
                        checkCredentials()
                    }
                )
            }
        )
    }

    private val webClient = object : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            var condition = true
            url?.let {
                allowStrings.forEach {
                    if (url.contains(it)) {
                        condition = false
                    }
                }
            }
            return if (condition) {
                view?.loadUrl("$WEB_VIEW_URL${viewModel.tokenTemp}")
                true
            } else {
                false
            }
        }

        override fun onPageFinished(view: WebView?, url: String) {
            super.onPageFinished(view, url)
            if (url.contains(ALLOW_CHECK)) {
                viewModel.getSessionId(viewModel.tokenTemp)
            }
            if (url.contains(DENY_CHECK)) {
                view?.loadUrl(WEB_VIEW_URL_LOGOUT)
                view?.clearCache(true)
                view?.clearHistory()
                updateUIChangeFromWebView()
            }
        }
    }

    private fun checkCredentials() {
        val session = sharedPreferences.getString(SESSION_KEY, null)
        val account = sharedPreferences.getInt(ACCOUNT_KEY, 0)

        if (!session.isNullOrEmpty()) {
            viewModel.getAccountId(session)
        }

        if (!session.isNullOrEmpty() && account != 0) {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToTvShowsFragment())
        }
    }

    private fun <T : Any> handleResponse(response: WorkState<T>, successAction: (T) -> Unit) {
        when (response) {
            WorkState.Loading -> {
                starProgressBar()
            }
            is WorkState.Success -> {
                stopProgressBar()
                successAction(response.value)
            }
            is WorkState.Failure -> {
                stopProgressBar()
                showErrorMessage(response.error)
            }
            else -> {
            }
        }
    }

    private fun showErrorMessage(response: InternalErrorCodes) {
        when (response) {
            InternalErrorCodes.NO_INTERNET_ACCESS -> showMessage(getString(R.string.no_internet_access))
            InternalErrorCodes.BAD_CREDENTIALS -> showMessage(getString(R.string.error_provider))
            InternalErrorCodes.NOT_FOUND -> showMessage(getString(R.string.error_provider))
            InternalErrorCodes.NOT_SPECIFIC -> showMessage(getString(R.string.try_again))
            InternalErrorCodes.NOT_DB_ENTRY -> showMessage(getString(R.string.no_local_available))
        }
    }

    private fun updateUIChangeToWebView() {
        stopProgressBar()
        binding.loginText.visibility = View.GONE
        binding.loginButton.visibility = View.GONE
        binding.loginWebView.visibility = View.VISIBLE
    }

    private fun updateUIChangeFromWebView() {
        binding.loginWebView.visibility = View.GONE
        binding.loginText.visibility = View.VISIBLE
        binding.loginButton.visibility = View.VISIBLE
    }

    private fun starProgressBar() {
        binding.authProgressBar.visible(true)
    }

    private fun stopProgressBar() {
        binding.authProgressBar.visible(false)
    }

    private fun showMessage(message: String) {
        Snackbar.make(binding.loginConstrain, message, Snackbar.LENGTH_SHORT).show()
    }

    companion object {
        const val ALLOW_CHECK = "/allow"
        const val DENY_CHECK = "/deny"
        const val SESSION_KEY = "sessionId"
        const val ACCOUNT_KEY = "accountId"
        const val WEB_VIEW_URL = "https://www.themoviedb.org/authenticate/"
        const val WEB_VIEW_URL_LOGOUT = "https://www.themoviedb.org/logout"
        const val WEB_VIEW_URL_LOGIN = "login"
    }

    private val allowStrings = listOf(
        WEB_VIEW_URL, WEB_VIEW_URL_LOGOUT, WEB_VIEW_URL_LOGIN, ALLOW_CHECK
    )
}
