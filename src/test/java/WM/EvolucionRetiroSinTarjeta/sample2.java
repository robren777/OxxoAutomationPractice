package WM.EvolucionRetiroSinTarjeta;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class sample2 {
	
	WebDriver driver;

	@BeforeClass
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\GTIM\\Evolucion RST\\repos\\evolucion-rst\\Config\\Drivers\\Chrome\\Windows\\119.exe");
		driver = new ChromeDriver();
	}
	
	@Test
	public void test() {
		driver.get("https://www.google.com.mx/");
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}
