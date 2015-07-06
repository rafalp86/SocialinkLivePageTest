import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;


import net.lightbody.bmp.proxy.ProxyServer;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.gargoylesoftware.htmlunit.javascript.host.Proxy;


public class BasePage {
public static Config appConfig = new Config();
	
	protected static WebDriver driver;
	protected static ProxyServer server;
	@BeforeClass
	protected  void BeforeClass() 
	{   
		try
		{
			server = new ProxyServer();  // package net.lightbody.bmp.proxy
		    server.start();
		    server.setCaptureHeaders(true);
		    server.blacklistRequests("https?://.*\\.google-analytics\\.com/.*", 410);
		    //server.whitelistRequests("https://socialink.me,https://socialink.me/karoltest".split(","), 200);
		    org.openqa.selenium.Proxy proxy = server.seleniumProxy(); 
		    DesiredCapabilities capabilities = new DesiredCapabilities();
		    
		    capabilities.setCapability(CapabilityType.PROXY, proxy);
		    
			driver = new FirefoxDriver(capabilities);
			driver.navigate().to(new URL(appConfig.get("url")));

		}
		catch (Exception ex)
		{
			ScreenshotDesktop("FAIL");
			Assert.fail("Problem with create Webdrive instantion: "+ ex.toString());
		}
		 driver.manage().timeouts().implicitlyWait(Integer.parseInt(appConfig.get("timeout")), TimeUnit.SECONDS);

	}
	
	@AfterClass
	protected void CloseConnection()
	{
		driver.quit();
		server.abort();
		System.out.println("CloseConnection");
	}
	
	public  void Screenshot(String fileName) {
        try {
        	File screenshotDir = new File("Screenshot");
        	if (!screenshotDir.exists()) screenshotDir.mkdir();
        	
            FileOutputStream out = new FileOutputStream("Screenshot/"+fileName + "_Socialink.png");
            out.write(((org.openqa.selenium.TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
            out.close();
        } catch (Exception e) {
          
        }
	 }
	protected void ScreenshotDesktop(String fileName)
	{
		Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage capture;
		try {
			File screenshotDir = new File("Screenshot");
        	if (!screenshotDir.exists()) screenshotDir.mkdir();
			capture = new Robot().createScreenCapture(screenRect);
			ImageIO.write(capture, "jpg", new File("Screenshot/"+fileName + "_Socialink.png"));
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@AfterMethod
	protected  void ScreenshotWhenFail(ITestResult result) {
		  if (result.getStatus() == ITestResult.FAILURE) {
		    	Screenshot("FAIL"+result.getName());
		    }   
	}


}
