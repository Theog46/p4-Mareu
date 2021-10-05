package com.example.maru;

import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.maru.utils.DeleteViewAction;
import com.example.maru.utils.RecyclerViewItemCountAssertion;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.PickerActions.setDate;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private MainActivity mActivity;

     @Rule
     public final ActivityTestRule<MainActivity> mActivityRules =
             new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRules.getActivity();
        assertThat(mActivity, notNullValue());
    }

     @Test
    public void listMeetingNotEmpty() {
        onView(withId(R.id.recycler_view_list)).check(matches(hasMinimumChildCount(3)));
     }

     @Test
    public void createMeetingWithSuccess() {
         onView(withId(R.id.meetingAdd)).perform(click());
         onView(withId(R.id.subjectInput)).perform(click());
         onView(withId(R.id.subjectInput)).perform(typeText("Meeting Subject"));
         onView(withId(R.id.placeInput)).perform(click());
         onView(withId(R.id.placeInput)).perform(typeText("Meeting Place")).perform(closeSoftKeyboard());
         onView(withId(R.id.timeButton)).perform(click());
         onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(20, 45));
         onView(withText("OK")).perform(click());
         onView(withId(R.id.emailsInput)).perform(scrollTo()).perform(click());
         onView(withId(R.id.emailsInput)).perform(typeText("john.smith@amazon.com, michael.jordan@amazon.fr")).perform(closeSoftKeyboard());
         onView(withId(R.id.submitButton)).perform(click());
         
     }

    @Test
    public void deleteMeetingWithSuccess() {
         onView(withId(R.id.recycler_view_list)).check(new RecyclerViewItemCountAssertion(3));
         onView(withId(R.id.recycler_view_list)).perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
         onView(withId(R.id.recycler_view_list)).check(new RecyclerViewItemCountAssertion(2));

    }

    @Test
    public void resetListOfMeetingsWithSuccess() {
        onView(withId(R.id.recycler_view_list)).check(new RecyclerViewItemCountAssertion(3));
        onView(withId(R.id.recycler_view_list)).perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.menu_reset)).perform(click());
        onView(withId(R.id.recycler_view_list)).check(new RecyclerViewItemCountAssertion(3));


    }

    @Test
    public void filterMeetingsByDateWithSuccess() {
        onView(withId(R.id.recycler_view_list)).check(new RecyclerViewItemCountAssertion(3));
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.menu_filter)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(setDate(2021, 7, 17));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.recycler_view_list)).check(new RecyclerViewItemCountAssertion(2));

    }


}