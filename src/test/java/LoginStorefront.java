import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.util.Assert;

import pages.storefront.LoginPage;

import static utilities.account.AccountTest.*;
import static utilities.message.Message.*;

public class LoginStorefront extends BaseTest {
	
	LoginPage LoginPage;
	
    @BeforeMethod
    public void setup() {
    	super.setup();
        LoginPage = new LoginPage(driver);
    }	
	
    @Test
    public void TC06_SF_LoginWithValidPhoneNumberAccount() {
        LoginPage.navigate()
        .performLogin("0856256079", "12345678x@X");
        org.testng.Assert.assertTrue(false, "OMG So bad!");
    }

    @Test
    public void TC07_SF_LoginWithValidMailAccount() {
        LoginPage.navigate()
        .performLogin("buyertest@mailnesia.com", "fortesting!4");
    }

    @AfterMethod
    public void tearDown() {
    	super.tearDown();
    }    

}
