package cases.s3_register_as_driver.s4_vehicle_information.s4_trunk;

import android.graphics.drawable.BitmapDrawable;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rideaustin.BaseUITest;
import com.rideaustin.R;
import com.rideaustin.RaActivityRule;
import com.rideaustin.RequestType;
import com.rideaustin.RiderMockResponseFactory;
import com.rideaustin.TestCases;
import com.rideaustin.ui.drawer.NavigationDrawerActivity;
import com.rideaustin.utils.NavigationUtils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import cases.s3_register_as_driver.RegisterAsDriverUtils;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.rideaustin.utils.CommonNavigationUtils.cancelChoosePhoto;
import static com.rideaustin.utils.CommonNavigationUtils.cancelTakePhoto;
import static com.rideaustin.utils.CommonNavigationUtils.choosePhoto;
import static com.rideaustin.utils.CommonNavigationUtils.takePhoto;
import static com.rideaustin.utils.DeviceTestUtils.search;
import static com.rideaustin.utils.MatcherUtils.hasDrawable;
import static com.rideaustin.utils.MatcherUtils.navigationIcon;
import static com.rideaustin.utils.ViewActionUtils.waitFor;
import static com.rideaustin.utils.ViewActionUtils.waitForCompletelyDisplayed;
import static com.rideaustin.utils.ViewActionUtils.waitForDisplayed;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Reference: RA-12282
 * Created by Sergey Petrov on 25/07/2017.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TrunkSuite extends BaseUITest {
    @Rule
    public ActivityTestRule<NavigationDrawerActivity> activityRule = new RaActivityRule<>(NavigationDrawerActivity.class, false, false);

    @Override
    public void setUp() {
        super.setUp();
        Intents.init();
        initMockResponseFactory(RiderMockResponseFactory.class);
        mockRequests(RequestType.GLOBAL_APP_INFO_200_GET,
                RequestType.LOGIN_SUCCESS_200_POST,
                RequestType.TOKENS_200_POST,
                RequestType.RIDER_DATA_NO_RIDE_200_GET,
                RequestType.DRIVER_TYPES_200_GET,
                RequestType.ACDR_EMPTY_200_GET,
                RequestType.SURGE_AREA_EMPTY_200_GET,
                RequestType.CONFIG_RIDER_200_GET,
                RequestType.EVENTS_EMPTY_200_GET,
                RequestType.CONFIG_ZIPCODES_200_GET,
                RequestType.CONFIG_DRIVER_REGISTRATION_200_GET,
                RequestType.SUPPORT_MESSAGE_200_POST);
    }

    @Override
    public void tearDown() {
        super.tearDown();
        Intents.release();
    }

    /**
     * C1930430: Edit Picture - Open Trunk - Cancel
     * C1930433: Vehicle Information - Open trunk photo taken by camera - Cancel
     * C1930436: Vehicle Information - Open trunk photo taken from library - Cancel
     * C1930438: Cannot proceed without adding Open trunk photo
     * C1930431: Back button goes to the Vehicle Information(Inside photo)
     * C1930437: Can request help from Vehicle Information - Open trunk photo screen
     * C1930409: Lost Internet connection
     * C1930432: Vehicle Information - Open trunk photo taken by camera
     * C1930434: Vehicle Information - Open trunk photo taken by camera - Retake
     */
    @Test
    @TestCases({"C1930430", "C1930433", "C1930436",
            "C1930438", "C1930431", "C1930437",
            "C1930409", "C1930432", "C1930434"})
    public void shouldTakeInsidePhoto() throws IOException {
        NavigationUtils.startActivity(activityRule);
        NavigationUtils.throughLogin("valid@email.com", "password");

        // wait for map
        waitForDisplayed(R.id.mapContainer);

        // go to driver registration
        onView(navigationIcon()).perform(click());
        onView(withId(R.id.navigationView)).perform(NavigationViewActions.navigateTo(R.id.navDriveWithRideApp));

        RegisterAsDriverUtils.throughInitial();
        RegisterAsDriverUtils.throughDriverPhoto(false);
        RegisterAsDriverUtils.throughDriverLicense(true);
        RegisterAsDriverUtils.skipTnc();
        RegisterAsDriverUtils.throughFrontPhoto(false);
        RegisterAsDriverUtils.throughBackPhoto(true);
        RegisterAsDriverUtils.throughInsidePhoto(false);

        // check title
        onView(allOf(withId(R.id.toolbarTitle), withText(R.string.title_driver_vehicle_information))).check(matches(isDisplayed()));
        // check details text
        onView(allOf(withId(R.id.text_detail), withText(R.string.car_photo_trunk))).check(matches(isDisplayed()));

        // check "Next" is disabled
        waitForDisplayed(R.id.next);
        onView(withId(R.id.next)).check(matches(not(isEnabled())));

        //------------------------------------------------------------------------------------------
        // C1930430: Edit Picture - Open Trunk - Cancel
        //------------------------------------------------------------------------------------------

        // open bottom sheet
        waitForDisplayed(R.id.openTakePhotoControl);
        onView(withId(R.id.openTakePhotoControl)).perform(click());
        waitForCompletelyDisplayed(R.id.takePhotoContainer);
        onView(withId(R.id.cancel)).check(matches(isDisplayed())).perform(click());

        //------------------------------------------------------------------------------------------
        // C1930433: Vehicle Information - Open trunk photo taken by camera - Cancel
        //------------------------------------------------------------------------------------------

        cancelTakePhoto(R.id.openTakePhotoControl);

        //------------------------------------------------------------------------------------------
        // C1930436: Vehicle Information - Open trunk photo taken from library - Cancel
        //------------------------------------------------------------------------------------------

        cancelChoosePhoto(R.id.openTakePhotoControl);

        //------------------------------------------------------------------------------------------
        // C1930438: Cannot proceed without adding Open trunk photo
        //------------------------------------------------------------------------------------------

        // check "Next" is disabled
        waitForDisplayed(R.id.next);
        onView(withId(R.id.next)).check(matches(not(isEnabled()))).perform(click());

        //------------------------------------------------------------------------------------------
        // C1930431: Back button goes to the Vehicle Information(Inside photo)
        //------------------------------------------------------------------------------------------

        Espresso.pressBack();

        onView(allOf(withId(R.id.toolbarTitle), withText(R.string.title_driver_vehicle_information))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.text_detail), withText(R.string.car_photo_inside))).check(matches(isDisplayed()));
        onView(withId(R.id.next)).perform(click());

        //------------------------------------------------------------------------------------------
        // C1930437: Can request help from Vehicle Information - Open trunk photo screen
        // C1930409: Lost Internet connection
        //------------------------------------------------------------------------------------------

        RegisterAsDriverUtils.checkNeedHelp(this);

        // waitForDisplayed() does not work, probably because it starts waiting in dialog's root
        // this is alternative way of verifying UI elements on screen
        search().id(R.id.toolbarTitle).text(R.string.title_driver_vehicle_information).exists(3000);

        //------------------------------------------------------------------------------------------
        // C1930432: Vehicle Information - Open trunk photo taken by camera
        // C1930434: Vehicle Information - Open trunk photo taken by camera - Retake
        //------------------------------------------------------------------------------------------

        takePhoto(R.id.openTakePhotoControl);
        choosePhoto(R.id.openTakePhotoControl);

        // wait until car image loaded
        waitFor(allOf(withId(R.id.car_detail), isDisplayed(), hasDrawable(instanceOf(BitmapDrawable.class))), "Car image should be set", 1000);

        // go next
        onView(withId(R.id.next)).perform(click());

        // check title
        onView(allOf(withId(R.id.toolbarTitle), withText(R.string.title_driver_vehicle_information))).check(matches(isDisplayed()));
        onView(withText("2001 or Newer")).check(matches(isDisplayed()));
    }
}
