import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.crypto.Data;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class HomePage extends BasePage {
	private By emialControl= By.id("landing_home_email");
	private By getStartedButton= By.id("landing_home_get_started_button");
	private By photoHeader= By.xpath("//div[contains(@class,\'header-container\')]");


	public HomePage GetStarted()
	{
		WebElement emial= driver.findElement(emialControl);
		WebElement getStarted= driver.findElement(getStartedButton);
		
		emial.sendKeys("testemail@gmail.com");
		getStarted.click();
		
		return new HomePage();
	}
	
	public String getHeadingText()
	{
		return driver.findElement(photoHeader).getText();
	}
}
