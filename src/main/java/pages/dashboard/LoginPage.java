package pages.dashboard;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;


import static utilities.links.Links.*;

import java.time.Duration;
import java.util.List;

public class LoginPage {
	
    WebDriver driver;
    WebDriverWait wait;
    
    SoftAssert soft = new SoftAssert();
    
    public LoginPage (WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "#username")
    WebElement USERNAME;

    @FindBy (css = "#password")
    WebElement PASSWORD;

    @FindBy (css = "button.gs-button")
    WebElement LOGIN_BTN;

    @FindBy (css = "div.invalid-feedback")
    List<WebElement> USER_PASSWORD_ERROR;

    @FindBy (css = "div.alert__wrapper")
    WebElement INVALID_USER_ERROR;	
    
    @FindBy (css = ".login-widget__btnSubmitFaceBook")
    WebElement FACEBOOK_BTN;	
    
    @FindBy(css = "#email")
    WebElement FACEBOOK_USERNAME;	

    @FindBy (css = "#pass")
    WebElement FACEBOOK_PASSWORD;    
    
    public LoginPage navigate() {
        driver.get(DOMAIN + LOGIN_PATH);
        wait.until(ExpectedConditions.titleIs(LOGIN_PAGE_TITLE));
        return this;
    }

    public LoginPage clickFacebookBtn() {
        FACEBOOK_BTN.click();
        return this;
    }

    @FindBy (css = "input[name=\"login\"]")
    WebElement FACEBOOK_LOGIN_BTN;    
    
    public LoginPage inputEmailOrPhoneNumber(String username) {
        wait.until(ExpectedConditions.visibilityOf(USERNAME)).sendKeys(username);
        return this;
    }

    public LoginPage inputPassword(String password) {
        PASSWORD.sendKeys(password);
        return this;
    }

    public LoginPage inputFacebookUsername(String username) {
        wait.until(ExpectedConditions.visibilityOf(FACEBOOK_USERNAME)).sendKeys(username);
        return this;
    }

    public LoginPage inputFacebookPassword(String password) {
    	FACEBOOK_PASSWORD.sendKeys(password);
        return this;
    }    
    
    public LoginPage clickLoginBtn() {
        LOGIN_BTN.click();
        return this;
    }

    public LoginPage clickFacebookLoginBtn() {
        FACEBOOK_LOGIN_BTN.click();
        return this;
    }    
    
    public LoginPage performLogin(String username, String password) {
    	inputEmailOrPhoneNumber(username);
    	inputPassword(password);
    	clickLoginBtn();
        return this;
    }

    public LoginPage performLoginWithFacebook(String username, String password) throws InterruptedException {
    	String originalWindow = driver.getWindowHandle();
    	
    	clickFacebookBtn();
    	Thread.sleep(1000);
    	
    	for (String windowHandle : driver.getWindowHandles()) {
    	    if(!originalWindow.contentEquals(windowHandle)) {
    	        driver.switchTo().window(windowHandle);
    	        break;
    	    }
    	}
    	
    	inputFacebookUsername(username);
    	inputFacebookPassword(password);
    	clickFacebookLoginBtn();
    	driver.switchTo().window(originalWindow);
        return this;
    }      
    
    public LoginPage verifyEmailOrPhoneNumberError(String errMessage) {
        String text = wait.until(ExpectedConditions.visibilityOf(USER_PASSWORD_ERROR.get(0))).getText();
        soft.assertEquals(text, errMessage, "[Login][Email or Phone Number] Message does not match.");
        return this;
    }

    public LoginPage verifyPasswordError(String errMessage) {
        String text = wait.until(ExpectedConditions.visibilityOf(USER_PASSWORD_ERROR.get(1))).getText();
        soft.assertEquals(text,errMessage, "[Login][Password] Message does not match");
        return this;
    }

    public LoginPage verifyEmailOrPasswordIncorrectError(String errMessage) {
        String text = wait.until(ExpectedConditions.visibilityOf(INVALID_USER_ERROR)).getText();
        soft.assertEquals(text,errMessage, "[Login][Invalid Email/Password] Message does not match");
        return this;
    }

    public void completeVerify() {
        soft.assertAll();
    }    
    
}
