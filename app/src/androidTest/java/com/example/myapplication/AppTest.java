package com.example.myapplication;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingPolicies;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.Matchers.anything;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
public class AppTest {
    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityActivityScenario = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Before
    public void Setup() throws Exception{
    }

    @org.junit.Test
    public void testBookmarkSwipeAction() throws Exception{
        TimeUnit.SECONDS.sleep(2);

        // go bookmark
        Espresso.onView(withId(R.id.bookmarksFragment)).perform(click());

        // swipe right
        Espresso.onView(withId(R.id.bookmark_recycle_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeRight()));

        // start tracker
        Espresso.onView(withId(R.id.start_stop_btn)).perform(click());
        TimeUnit.SECONDS.sleep(1);
        Espresso.onView(withId(R.id.start_stop_btn)).perform(click());
    }
}
