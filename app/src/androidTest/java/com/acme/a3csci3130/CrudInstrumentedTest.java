package com.acme.a3csci3130;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class CrudInstrumentedTest {
    @Rule
    public final ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    /**
     * Test for create CRUD operation
     * @throws Exception
     */
    @Test
    public void testCreate() throws Exception {
        onView(withId(R.id.submitButton))
                .perform(click());

        onView(withId(R.id.businessNum))
                .perform(click())
                .perform(typeText("123456789"));

        pressBack();

        onView(withId(R.id.name))
                .perform(click())
                .perform(typeText("Hello World Inc"));

        pressBack();

        onView(withId(R.id.primaryBusiness))
                .perform(click());

        onData(hasToString(startsWith("Fish Monger")))
                .perform(click());

        onView(withId(R.id.submitButton))
                .perform(click());
    }

    /**
     * Test for read CRUD operation
     * @throws Exception
     */
    @Test
    public void testRead() throws Exception {
        Thread.sleep(10000);
        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).perform(click());
    }

    /**
     * Test for update CRUD operations
     * @throws Exception
     */
    @Test
    public void testUpdate() throws Exception {
        Thread.sleep(10000);
        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).perform(click());

        onView(withId(R.id.province))
                .perform(click());

        onData(hasToString(startsWith("NS")))
                .perform(click());

        onView(withId(R.id.updateButton))
                .perform(click());
    }

    /**
     * Test for delete CRUD operation
     * @throws Exception
     */
    @Test
    public void testDelete() throws Exception {
        Thread.sleep(5000);
        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).perform(click());

        onView(withId(R.id.deleteButton))
                .perform(click());
    }
}
