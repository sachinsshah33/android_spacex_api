package extension.domain.spacex.main

import androidx.paging.ExperimentalPagingApi
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import extension.domain.spacex.R
import extension.domain.spacex.ui.main.MainActivity
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalPagingApi
@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @Test
    fun test_isActivityInView() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.main_view)).check(matches(isDisplayed()))
    }

    @Test
    fun test_visibility_recyclerViewAndSpinnerInitiallyVisible() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.launchRecycler)).check(matches(isDisplayed()))
        onView(withId(R.id.spinner)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

}