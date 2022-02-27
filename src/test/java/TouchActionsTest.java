import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TouchActionsTest {
    public AndroidDriver<MobileElement> driver;
    public WebDriverWait wait;
    //Elements By

    By swipe = MobileBy.
            AndroidUIAutomator("new UiSelector().text(\"SWIPE\")");
    By swipeVertical = MobileBy.
            AndroidUIAutomator("new UiSelector().text(\"SWIPEABLE (VERTICAL)\")");


    @BeforeMethod
    public void setup() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "Pixel 4 API 30");
        caps.setCapability("udid", "emulator-5554"); //DeviceId from "adb devices" command
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "11.0");
        caps.setCapability("skipUnlock", "true");
        caps.setCapability("appPackage", "com.h6ah4i.android.example.advrecyclerview");
        caps.setCapability("appActivity", "com.h6ah4i.android.example.advrecyclerview.launcher.MainActivity");
        caps.setCapability("noReset", "false");
        driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void horizontalSwipe() throws InterruptedException {
        Activity activity = new Activity
                ("com.h6ah4i.android.example.advrecyclerview", "com.h6ah4i.android.example.advrecyclerview.launcher.MainActivity");
        driver.startActivity(activity);

        wait.until(ExpectedConditions.visibilityOfElementLocated(swipe)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(swipeVertical)).click();

        AndroidElement f5 = (AndroidElement) driver.findElement(MobileBy.
                AndroidUIAutomator("new UiSelector().text(\"5 - F\")"));
        AndroidElement A0 = (AndroidElement) driver.findElement(MobileBy.
                AndroidUIAutomator("new UiSelector().text(\"0 - A\")"));

        driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true).instance(1))" +
                        ".scrollIntoView(new UiSelector().text(\"5 - F\"))"));

        int midOfYCoordinate = f5.getLocation().y + (f5.getSize().height / 2);
        int firstElementXCoordinate = A0.getLocation().x;
        int thirdElementYCoordinate = f5.getLocation().x;


        TouchAction touch = new TouchAction(driver)
                .press(PointOption.point(thirdElementYCoordinate, midOfYCoordinate))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                .moveTo(PointOption.point(firstElementXCoordinate, midOfYCoordinate))
                .release();
        touch.perform();
        driver.navigate().back();

    }

    @Test
    public void verticalSwipeUpAndDown() throws InterruptedException {
        Activity activity = new Activity("com.h6ah4i.android.example.advrecyclerview", "com.h6ah4i.android.example.advrecyclerview.launcher.MainActivity");
        driver.startActivity(activity);
        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.visibilityOfElementLocated(swipe)).click();
        AndroidElement pressButton = (AndroidElement) driver.findElement(MobileBy.
                AndroidUIAutomator("new UiSelector().text(\"SWIPE ON LONG PRESS\")"));
        Point xcordinate = pressButton.getCenter();

        TouchAction touch = new TouchAction(driver)
                .press(PointOption.point(xcordinate))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3)))
                .release();
        touch.perform();

        Dimension dimension = driver.manage().window().getSize();
        int startX = (int) (dimension.width * 0.5);
        int startY = (int) (dimension.height * 0.9);

        int endX = (int) (dimension.width * 0.5);
        int endY = (int) (dimension.height * 0.2);


        TouchAction touch2 = new TouchAction(driver)
                .press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .moveTo(PointOption.point(endX, endY))
                .release();


        //Thread.sleep(3000);

        dimension = driver.manage().window().getSize();
        startX = (int) (dimension.width * 0.5);
        startY = (int) (dimension.height * 0.2);

        endX = (int) (dimension.width * 0.5);
        endY = (int) (dimension.height * 0.9);


        TouchAction touch1 = new TouchAction(driver)
                .press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .moveTo(PointOption.point(endX, endY))
                .release();


        MultiTouchAction multiTouchAction = new MultiTouchAction(driver).add(touch2).add(touch1);
        multiTouchAction.perform();

    }

    @AfterMethod
    public void teardown() {
        //driver.quit();
    }
}