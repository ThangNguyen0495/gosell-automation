package pages.storefront;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.database.InitConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;

import static java.lang.Thread.sleep;
import static utilities.links.Links.*;

public class SignupPage {

    WebDriver driver;
    WebDriverWait wait;

    public SignupPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".navbar-brand.nav-link")
    WebElement USER_INFO_ICON;    

    @FindBy(css = "#btn-signup")
    WebElement SIGNUP_ICON;
    
    @FindBy(css = "#signup-username")
    WebElement USERNAME;
    
    @FindBy(css = "#signup-displayName")
    WebElement DISPLAY_USERNAME;
    
    @FindBy(css = "#signup-dob")
    WebElement BIRTHDAY;

    @FindBy (css = "#signup-password")
    WebElement PASSWORD;

    @FindBy (css = "#frm-signup .btn-submit")
    WebElement SIGNUP_BTN;

    @FindBy (css = "div.uik-select__valueWrapper>div>div:nth-child(2)")
    WebElement COUNTRY_CODE;

    @FindBy (css = "#signup-country-code")
    WebElement COUNTRY_DROPDOWN;

    @FindBy (css = "button.uik-select__option>span>div>div>div>div:nth-child(1)")
    List<WebElement> COUNTRY_LIST;

    @FindBy (css = "#activate-code")
    WebElement OTP;

    @FindBy (css = "#frm-activate .btn-submit")
    WebElement CONFIRM_OTP;

    @FindBy (css = ".resend-otp")
    WebElement RESEND_OTP;    
    
    public SignupPage navigate() {
        driver.get(DOMAIN1);
        return this;
    }
    
    public SignupPage clickUserInfoIcon() {
    	USER_INFO_ICON.click();
        return this;
    }    
    
    public SignupPage clickSignupIcon() {
    	SIGNUP_ICON.click();
    	return this;
    }    
    
    public SignupPage selectCountry(String country) {
    	wait.until(ExpectedConditions.elementToBeClickable(COUNTRY_DROPDOWN)).click();
    	driver.findElement(By.xpath("//ul[@id='signup-country-code-menu']//span[text()='%s']".formatted(country))).click();
        return this;
    }

    public SignupPage inputMailOrPhoneNumber(String user) {
    	USERNAME.clear();
        USERNAME.sendKeys(user);
        return this;
    }

    public SignupPage inputPassword(String password) {
    	PASSWORD.clear();
        PASSWORD.sendKeys(password);
        return this;
    }
    
    public SignupPage inputDisplayName(String name) {
    	DISPLAY_USERNAME.clear();
    	DISPLAY_USERNAME.sendKeys(name);
    	return this;
    }
    
    public SignupPage inputBirthday(String date) {
    	BIRTHDAY.clear();
    	BIRTHDAY.sendKeys(date);
    	return this;
    }

    public SignupPage clickSignupBtn() {
        SIGNUP_BTN.click();
        return this;
    }

    public String getOTPCode(String userLoginDB) throws SQLException {
        Connection connection = new InitConnection().createConnection();
        String query = "select * from \"gateway-services\".jhi_user ju where login = '%s'".formatted(userLoginDB);
        ResultSet resultSet = connection.prepareStatement(query).executeQuery();
        String OTP_CODE = null;
        while (resultSet.next()) {
            OTP_CODE = resultSet.getString("activation_key");
        }
        return OTP_CODE;
    }    
    
    public SignupPage inputVerificationCode(String verificationCode) throws SQLException {
        wait.until(ExpectedConditions.visibilityOf(OTP)).sendKeys(verificationCode);
        return this;
    }

    public void clickConfirmBtn() {
        CONFIRM_OTP.click();
    }
    
}
