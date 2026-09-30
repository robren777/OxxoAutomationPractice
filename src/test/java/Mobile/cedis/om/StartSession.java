package Mobile.cedis.om;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class StartSession {
    public static void main(String[] args) throws MalformedURLException, Exception {
        URL appiumUrl = new URL("http://0.0.0.0:4723");

        // Capabilities para el dispositivo que se usará
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("automationName", "UiAutomator2");
        //caps.setCapability("appPackage", "com.femco.oxxo.operaciones.mitienda.debug");
        //caps.setCapability("appActivity", "com.femco.oxxo.operaciones.mitienda.ui.login.LoginActivity");
        //caps.setCapability("avd", "Zebra Technologies TC52");
        //caps.setCapability("avdLaunchTimeout", 180000);
        //caps.setCapability("udid", "23206524200662");
        
        AppiumDriver driver = new AndroidDriver(appiumUrl, caps);
        
        // Obtener el sessionId
        String sessionId = driver.getSessionId().toString();
        System.out.println("Session ID: " + sessionId);
        
        
        driver.findElement(AppiumBy.accessibilityId("OXXO al día")).click();
        Thread.sleep(2000);
        driver.findElement(AppiumBy.id("com.femco.oxxo.operaciones.mitienda.debug:id/etIpAddress")).sendKeys("10.75.6.168");
        Thread.sleep(1000);
        driver.findElement(AppiumBy.id("com.femco.oxxo.operaciones.mitienda.debug:id/etPuerto")).sendKeys("53030");
        Thread.sleep(1000);
        driver.findElement(AppiumBy.id("com.femco.oxxo.operaciones.mitienda.debug:id/btnSaveIP")).click();
        Thread.sleep(1000);
        driver.findElement(AppiumBy.id("android:id/button1")).click();


        //si
        
    }
}
