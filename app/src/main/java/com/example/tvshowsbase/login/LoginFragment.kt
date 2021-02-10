package com.example.tvshowsbase.login

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.domain.model.WorkState
import com.example.tvshowsbase.R
import com.example.tvshowsbase.databinding.LoginFragmentBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModel()
    private lateinit var binding: LoginFragmentBinding
    private val sharedPreferences: SharedPreferences by inject()
    private val args: LoginFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
        webViewConfiguration()
        createViewModelObservers()
        createBindingFragmentObservers()

        checkCredentials()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewConfiguration() {
        if (args.logOut) {
            Log.d("Login", "ClearCache")
            binding.loginWebView.clearCache(true)
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
        viewModel.authTokeState.observe(viewLifecycleOwner, { response ->
            when (response) {
                WorkState.Loading -> {
                    starProgressBar()
                }
                is WorkState.Success -> {
                    viewModel.tokenTemp = response.value
                    binding.loginWebView.loadUrl("$WEB_VIEW_URL${viewModel.tokenTemp}")
                    updateUIChangeToWebView()
                }
                is WorkState.Failure -> {
                    stopProgressBar()
                    showSnackBar(response.message)
                }
            }
        })

        viewModel.sessionIdState.observe(viewLifecycleOwner, { response ->
            when (response) {
                WorkState.Loading -> {
                    starProgressBar()
                }
                is WorkState.Success -> {
                    stopProgressBar()
                    Log.d("LoginFragment", "Session ID: ${response.value}")

                    sharedPreferences.edit {
                        putString(SESSION_KEY, response.value)
                        apply()
                    }
                    checkCredentials()
                }
                is WorkState.Failure -> {
                    stopProgressBar()
                    showSnackBar(response.message)
                }
            }
        })

        viewModel.accountIdState.observe(viewLifecycleOwner, { response ->
            when (response) {
                WorkState.Loading -> {
                    starProgressBar()
                }
                is WorkState.Success -> {
                    stopProgressBar()
                    Log.d("LoginFragment", "Account ID: ${response.value}")

                    sharedPreferences.edit {
                        putInt(ACCOUNT_KEY, response.value)
                        apply()
                    }
                    checkCredentials()
                }
                is WorkState.Failure -> {
                    stopProgressBar()
                    showSnackBar(response.message)
                }
            }
        })
    }

    private val webClient = object : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            url?.let { view?.loadUrl(it) }
            return false
        }

        override fun onPageFinished(view: WebView?, url: String) {
            super.onPageFinished(view, url)
            if (url.contains(ALLOW_CHECK)) {
                viewModel.getSessionId(viewModel.tokenTemp)
            }
        }
    }

    private fun checkCredentials() {
        val session = sharedPreferences.getString(SESSION_KEY, "")
        val account = sharedPreferences.getInt(ACCOUNT_KEY, 0)

        if (session?.isNotEmpty() == true) {
            Log.d("Login", "else")
            viewModel.getAccountId(session)
        }

        if (session != "" && account != 0) {
            Log.d("Login", "check2")
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToTvShowsFragment())
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.loginConstrain, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun starProgressBar() {
        binding.authProgressBar.visibility = View.VISIBLE
    }

    private fun stopProgressBar() {
        binding.authProgressBar.visibility = View.GONE
    }

    private fun updateUIChangeToWebView() {
        binding.authProgressBar.visibility = View.GONE
        binding.loginText.visibility = View.GONE
        binding.loginButton.visibility = View.GONE
        binding.loginWebView.visibility = View.VISIBLE
    }

    companion object {
        const val ALLOW_CHECK = "/allow"
        const val WEB_VIEW_URL = "https://www.themoviedb.org/authenticate/"
        const val SESSION_KEY = "sessionId"
        const val ACCOUNT_KEY = "accountId"
    }
}