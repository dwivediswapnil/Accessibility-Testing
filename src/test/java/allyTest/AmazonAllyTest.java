package allyTest;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.deque.axe.AXE;

import io.github.sridharbandi.AxeRunner;

public class AmazonAllyTest {

	WebDriver driver;

	private static final URL scriptUrl = AmazonAllyTest.class.getResource("/axe.min.js");

	@BeforeMethod
	public void setup() throws IOException {
		driver = new ChromeDriver();
		driver.get("https://www.upgrad.com/");
		AxeRunner axeRunner = new AxeRunner(driver);
		axeRunner.execute();
		axeRunner.generateHtmlReport();

	}

	@Test
	public void amazonAllyTest() throws IOException {
		driver.navigate().refresh();

		JSONObject responseJson = new AXE.Builder(driver, scriptUrl).analyze();

		JSONArray violations = responseJson.getJSONArray("violations");
		System.out.println("Violations=>" + violations.length());

		if (violations.length() == 0) {
			System.out.println("No errors");

		} else {

			System.out.println(violations.length() + " " + "violations found.");
			// name of the JsonReport ="amazonAllyTest"
			AXE.writeResults("amazonAllyTest", responseJson);
			// Test should fail coz we are showing the a11y violations
			Assert.assertTrue(false, AXE.report(violations));

		}

	}

	@Test
	public void testAccessabilitySelector() throws IOException {
		driver.navigate().refresh();
		JSONObject responseJson = new AXE.Builder(driver, scriptUrl).include("script").analyze();
		JSONArray violations = responseJson.getJSONArray("violations");
		System.out.println("Violations=>" + violations.length());
		if (violations.length() == 0) {
			System.out.println("No errors");

		} else {
			System.out.println(violations.length() + " " + "violations found.");
			AXE.writeResults("testAccessabilitySelector", responseJson);
			Assert.assertTrue(false, AXE.report(violations));
		}
	}

	@Test
	public void testAccessabilityWithTextLinked() {
		driver.navigate().refresh();
		JSONObject responseJson = new AXE.Builder(driver, scriptUrl)
				.analyze(driver.findElement(By.linkText("Customer Service")));
		JSONArray violations = responseJson.getJSONArray("violations");
		if (violations.length() == 0) {
			System.out.println("No errors");

		} else {
			System.out.println(violations.length() + " " + "violations found.");
			AXE.writeResults("testAccessabilityWithTextLinked", responseJson);
			Assert.assertTrue(false, AXE.report(violations));
		}

	}

	/**
	 * Test a WebElement
	 */
	@Test
	public void testAccessibilityWithWebElement() {
		driver.navigate().refresh();

		JSONObject responseJSON = new AXE.Builder(driver, scriptUrl).analyze(driver.findElement(By.tagName("p")));
		JSONArray violations = responseJSON.getJSONArray("violations");
		System.out.println("===" + violations.length());
		if (violations.length() == 0) {
			System.out.println("No errors");
		} else {
			System.out.println(violations.length() + " " + "violations found.");
			AXE.writeResults("testAccessibilityWithWebElement", responseJSON);
			Assert.assertTrue(false, AXE.report(violations));

		}
	}

//	@AfterMethod
	public void tearDown() {

		driver.quit();
	}
}
