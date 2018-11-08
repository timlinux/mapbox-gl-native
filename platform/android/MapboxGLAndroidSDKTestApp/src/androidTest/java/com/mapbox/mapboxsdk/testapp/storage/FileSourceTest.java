package com.mapbox.mapboxsdk.testapp.storage;

import android.os.Looper;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import android.view.View;

import com.mapbox.mapboxsdk.storage.FileSource;
import com.mapbox.mapboxsdk.testapp.R;
import com.mapbox.mapboxsdk.testapp.action.WaitAction;
import com.mapbox.mapboxsdk.testapp.activity.FeatureOverviewActivity;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.mapbox.mapboxsdk.testapp.action.OrientationChangeAction.orientationLandscape;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class FileSourceTest {

  @Rule
  public ActivityTestRule<FeatureOverviewActivity> rule = new ActivityTestRule<>(FeatureOverviewActivity.class);

  private FileSource fileSource;

  @Before
  public void setUp() throws Exception {
    onView(withId(R.id.recyclerView)).perform(new FileSourceCreator());
  }

  @Test
  public void testDefault() throws Exception {
    assertFalse("FileSource should not be active", fileSource.isActivated());
  }

  @Test
  public void testActivateDeactivate() throws Exception {
    assertFalse("1) FileSource should not be active", fileSource.isActivated());
    onView(withId(R.id.recyclerView)).perform(new FileSourceActivator(true));
    assertTrue("2) FileSource should be active", fileSource.isActivated());
    onView(withId(R.id.recyclerView)).perform(new FileSourceActivator(false));
    assertFalse("3) FileSource should not be active", fileSource.isActivated());
  }

  @Test
  public void testOpenCloseMapView() throws Exception {
    assertFalse("1) FileSource should not be active", fileSource.isActivated());
    onView(withText("Simple Map")).perform(click());
    onView(withId(R.id.mapView)).perform(new WaitAction());
    assertTrue("2) FileSource should be active", fileSource.isActivated());
    onView(withId(R.id.mapView)).perform(new WaitAction());
    pressBack();
    assertFalse("3) FileSource should not be active", fileSource.isActivated());
  }

  @Test
  @Ignore
  public void testRotateMapView() throws Exception {
    assertFalse("1) FileSource should not be active", fileSource.isActivated());
    onView(withText("Simple Map")).perform(click());
    onView(withId(R.id.mapView)).perform(new WaitAction());
    onView(isRoot()).perform(orientationLandscape());
    onView(withId(R.id.mapView)).perform(new WaitAction());
    assertTrue("2) FileSource should be active", fileSource.isActivated());
    onView(withId(R.id.mapView)).perform(new WaitAction());
    pressBack();
    assertFalse("3) FileSource should not be active", fileSource.isActivated());
  }

  private class FileSourceCreator implements ViewAction {
    @Override
    public Matcher<View> getConstraints() {
      return isDisplayed();
    }

    @Override
    public String getDescription() {
      return "Creates the filesource instance on the UI thread";
    }

    @Override
    public void perform(UiController uiController, View view) {
      assertTrue(Looper.myLooper() == Looper.getMainLooper());
      fileSource = FileSource.getInstance(rule.getActivity());
    }
  }

  private class FileSourceActivator implements ViewAction {

    private boolean activate;

    FileSourceActivator(boolean activate) {
      this.activate = activate;
    }

    @Override
    public Matcher<View> getConstraints() {
      return isDisplayed();
    }

    @Override
    public String getDescription() {
      return "Creates the filesource instance on the UI thread";
    }

    @Override
    public void perform(UiController uiController, View view) {
      assertTrue(Looper.myLooper() == Looper.getMainLooper());
      if (activate) {
        fileSource.activate();
      } else {
        fileSource.deactivate();
      }
    }
  }
}