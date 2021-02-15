package com.example.data

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.domain.model.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

val mockProfile = Profile(
    10067198,
    "Elias Eguizabal Mendoza",
    "eguizabal98",
    "https://image.tmdb.org/t/p/w400/magjele3tUZXYOKZrI1jayu0qXy.jpg"
)

val mockDetail = ShowDetails(
    85271,
    "Jac Schaeffer",
    true,
    "WandaVision",
    "https://image.tmdb.org/t/p/w400/lOr9NKxh4vMweufMOUDJjJhCRHW.jpg",
    Season(
        "2021-01-15",
        "Season 1",
        "https://image.tmdb.org/t/p/w400/glKDfE6btIRcVB5zrjspRIs4r52.jpg",
        1,
        null
    ),
    "Wanda Maximoff and Vision—two super-powered beings living idealized suburban lives—begin to suspect that everything is not as it seems.",
    "https://image.tmdb.org/t/p/w400/glKDfE6btIRcVB5zrjspRIs4r52.jpg",
    8.4
)

val mockCast = listOf<Cast>(
    Cast(
        "Elizabeth Olsen",
        "Wanda Maximoff",
        "https://image.tmdb.org/t/p/w400/wIU675y4dofIDVuhaNWPizJNtep.jpg"
    ),
    Cast(
        "Paul Bettany",
        "Vision",
        "https://image.tmdb.org/t/p/w400/m5fPZeWSd9PjKTgqpnfc5TscAfO.jpg"
    ),
    Cast(
        "Kathryn Hahn",
        "Agnes",
        "https://image.tmdb.org/t/p/w400/9sVllAKfEls3SJD3GoPm2JEZoa5.jpg"
    ),
    Cast(
        "Teyonah Parris",
        "Monica Rambeau / Geraldine",
        "https://image.tmdb.org/t/p/w400/k3U24YNgdaINZEQ4PftebnN5cpU.jpg"
    ),
    Cast(
        "Randall Park",
        "Jimmy Woo",
        "https://image.tmdb.org/t/p/w400/1QJ4cBQZoOaLR8Hc3V2NgBLvB0f.jpg"
    ),
    Cast(
        "Kat Dennings",
        "Darcy Lewis",
        "https://image.tmdb.org/t/p/w400/rrfyo9z1wW5nY9ZsFlj1Ozfj9g2.jpg"
    )
)

val mockFavorites = listOf<TvShow>(
    TvShow(
        1404,
        "Chuck",
        8.2,
        "2007-09-24",
        null,
        null,
        "Chuck is an American action-comedy/spy-drama television series created by Josh Schwartz and Chris Fedak. The series is about an \"average computer-whiz-next-door\" named Chuck, played by Zachary Levi, who receives an encoded e-mail from an old college friend now working for the Central Intelligence Agency; the message embeds the only remaining copy of a software program containing the United States' greatest spy secrets into Chuck's brain.",
        0
    )
)

val mockSeason = listOf<Season>(
    Season(
        "2021-01-15",
        "Season 1",
        "https://image.tmdb.org/t/p/w400/glKDfE6btIRcVB5zrjspRIs4r52.jpg",
        1,
        listOf(
            Episode(
                1830976,
                "2021-01-15",
                "Filmed Before a Live Studio Audience",
                "https://image.tmdb.org/t/p/w400/oNCzeCXFanVEWNpzRzyffhLLfZs.jpg"
            ),
            Episode(
                2293605,
                "2021-01-15",
                "Don't Touch That Dial",
                "https://image.tmdb.org/t/p/w400/3KZQbfi6SE5rTA5ysh8FvOMBuUN.jpg"
            )
        )
    )
)

val mockToken = "277635d446f24a2614387c3c6fcaa0006c5ba62c"
val mockSessionId = "752f3e6f9ae0ce07a73a3fc62ece4c7ac31d69a4"
val mockaccountId = 10067198
val mockShowId = 85271


@VisibleForTesting(otherwise = VisibleForTesting.NONE)
fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    this.observeForever(observer)

    try {
        afterObserve.invoke()

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }

    } finally {
        this.removeObserver(observer)
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}