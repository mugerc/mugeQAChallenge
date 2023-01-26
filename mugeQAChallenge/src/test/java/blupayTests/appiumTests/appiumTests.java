package blupayTests.appiumTests;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

public class appiumTests {
	public static final String UserName = "appiumtester_yHvQVI";
	public static final String AccessKey = "ovWxpHg7gaUStb4Z73Lb";

	public static final String URL = "https://" + UserName + ":" + AccessKey + "@hub-cloud.browserstack.com/wd/hub";
	public IOSDriver driver;

	@BeforeTest
	public void setUp() throws InterruptedException {
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability("project", "iOS Tests for Login");
		desiredCapabilities.setCapability("build", "browserstack-build-muge");
		desiredCapabilities.setCapability("platformName", "iOS");
		desiredCapabilities.setCapability("deviceName", "iPhone 14 Pro Max");
		desiredCapabilities.setCapability("os_version","16");
		desiredCapabilities.setCapability("appPackage", "com.bluepay.roket");
		desiredCapabilities.setCapability("appActivity", "com.bluepay.roket.MainActivity");
		desiredCapabilities.setCapability("app","bs://bd0914f5f0a60b3b53d03510da242aca4435e3ed");
		desiredCapabilities.setCapability("autoAcceptAlerts", true);

		try {
			driver = new IOSDriver(new URL(URL),desiredCapabilities);
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);

		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void loginTest() throws Exception {
		MobileElement loginButton = (MobileElement) driver.findElement(MobileBy.id("com.bluepay.roket:id/user_login_Button"));
		loginButton.click();
		driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		MobileElement loginWithCredentialsButton = (MobileElement) driver.findElement(MobileBy.id("com.bluepay.roket:id/login_action_Button"));
		loginWithCredentialsButton.click();
		String welcomeText = loginWithCredentialsButton.getText();
		Assert.assertEquals(welcomeText, "Merhaba, Çağrı");
	}

	@AfterTest
	public void teardown(){
		driver.quit();
	}
}
