package com.example.data

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data.di.configureNetworkModuleForTest
import com.example.data.di.databaseTestModule
import com.example.data.di.mockWebServerTest
import com.example.data.di.repositoryModule
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
open class BaseTestData : KoinTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mMockServerInstance: MockWebServer
    private var mShouldStart = false

    val sessionId = "752f3e6f9ae0ce07a73a3fc62ece4c7ac31d69a4"

    @Before
    open fun setUp() {
        startMockServer(true)
        configureDi()
    }

    fun mockNetworkResponseWithFileContent(fileName: String, responseCode: Int) =
        mMockServerInstance.enqueue(
            MockResponse()
                .setResponseCode(responseCode)
                .setBody(getJson(fileName))
        )

    private fun getJson(path: String): String {
        val uri = javaClass.classLoader!!.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }

    private fun startMockServer(shouldStart: Boolean) {
        if (shouldStart) {
            mShouldStart = shouldStart
            mMockServerInstance = MockWebServer()
            mMockServerInstance.start()
        }
    }

    private fun getMockWebServerUrl() = mMockServerInstance.url("/").toString()

    private fun stopMockServer() {
        if (mShouldStart) {
            mMockServerInstance.shutdown()
        }
    }

    @After
    open fun tearDown() {
        stopMockServer()
        stopKoin()
    }

    private fun configureDi() {
        startKoin {
            modules(
                listOf(
                    databaseTestModule,
                    configureNetworkModuleForTest(getMockWebServerUrl()),
                    repositoryModule,
                    mockWebServerTest
                )
            )
        }
    }
}
