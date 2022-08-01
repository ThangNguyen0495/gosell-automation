import org.openqa.selenium.WebDriver;

import utilities.data.DataGenerator;
import utilities.driver.InitWebdriver;

public class BaseTest {
    WebDriver driver;
    
    DataGenerator generate;

    public void setup() {
        driver = new InitWebdriver().getDriver("chrome", "false");
        generate = new DataGenerator();
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
