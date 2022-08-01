package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;


import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mailnesia {
	
    WebDriver driver;
    WebDriverWait wait;
    
    SoftAssert soft = new SoftAssert();
    
    public Mailnesia (WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//tr[@class=\"emailheader\"][1]/td[4]")
    WebElement EMAIL;
    
    public Mailnesia navigate(String user) {
        driver.get("https://mailnesia.com/mailbox/" + user);
        return this;
    }

    public String getVerificationCode() {
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(EMAIL.getText());
		if (m.find()) {
			return m.group();
		}
		return null;
    }    
    
}
