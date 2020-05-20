package base;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import utilities.TestUtility;
import utilities.WebEventListener;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {
    public static WebDriver driver;
    public static Properties property;
    public static ChromeOptions chromeOptions;
    public static EventFiringWebDriver e_driver;
    public static WebEventListener eventListener;
    public static Logger Log;
    public static ExtentReports extent;
    public static ExtentTest extentTest;

    public TestBase() {
        try {
            property = new Properties();
            FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/config/Configuration.properties");
            property.load(ip);
            Log = Logger.getLogger(this.getClass());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

    @BeforeTest
    public void setExtent() {
        TestUtility.setDateForLog4j();

        extent = new ExtentReports(System.getProperty("user.dir") + "/src/test/java/ExtentResults/" + TestUtility.getSystemDate() + ".html");
    }

    public static void initialization(String Browser) {
		switch (Browser) {
			case "chrome":
				chromeOptions = new ChromeOptions();
				chromeOptions.setExperimentalOption("useAutomationExtension", false);
				chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
				WebDriverManager.getInstance(DriverManagerType.CHROME).setup();
				driver = new ChromeDriver(chromeOptions);
				break;
			case "IE":
				WebDriverManager.getInstance(DriverManagerType.IEXPLORER).setup();
				driver = new InternetExplorerDriver();
				break;
			case "firefox":
				WebDriverManager.getInstance(DriverManagerType.FIREFOX).setup();
				driver = new FirefoxDriver();
				break;
			default:
				Log.error("Cannot instance browser driver");
				break;
		}

        e_driver = new EventFiringWebDriver(driver);
        //Now create object of EventListerHandler to register it with EventFiringWebDriver.
        eventListener = new WebEventListener();
        e_driver.register(eventListener);
        driver = e_driver;

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(TestUtility.Page_Load_TimeOut, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(TestUtility.Implicit_Wait, TimeUnit.SECONDS);

        driver.get(property.getProperty("url"));
    }

    @AfterTest
    public void endReport() {
        extent.flush();
        extent.close();
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
        String screenshotPath;

        if (result.getStatus() == ITestResult.FAILURE) {
            extentTest.log(LogStatus.FAIL, "Test Case Failed is " + result.getName()); //To Add Name in Extent Report.
            extentTest.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable()); //To Add Errors and Exceptions in Extent Report.
            screenshotPath = TestUtility.getScreenshot(driver, result.getName());
            extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(screenshotPath)); //To Add Screenshot in Extent Report.
        } else if (result.getStatus() == ITestResult.SKIP) {
            extentTest.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentTest.log(LogStatus.PASS, "Test Case Passed is " + result.getName());
            screenshotPath = TestUtility.getScreenshot(driver, result.getName());
            System.out.println(screenshotPath);
            extentTest.log(LogStatus.PASS, extentTest.addScreenCapture(screenshotPath)); //To Add Screenshot in Extent Report.
        }
        extent.endTest(extentTest); //Ending Test and Ends the Current Test and Prepare to Create HTML Report.
        driver.quit();
        Log.info("Browser Terminated");
        Log.info("-----------------------------------------------");
    }
}
