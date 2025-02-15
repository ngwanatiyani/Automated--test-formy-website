package selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.time.Duration;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class FormyAutocomplete {

    private WebDriver driver;

    @Before
    public void setUp()
    {
        //ChromeDriver path on my downloads
        String chromeDriverPath = System.getProperty("user.home") + File.separator + "Downloads" + File.separator + "chromedriver-win64" + File.separator + "chromedriver.exe";


        chromeDriverPath = chromeDriverPath.replace("\"", "");

        System.setProperty("webdriver.chrome.driver", chromeDriverPath);

        // Initialize WebDriver
        driver = new ChromeDriver();
        driver.manage().window().maximize();


    }

    @Test
    public void testAutocomplete()
    {
        // Open the Formy Autocomplete page

        driver.get("https://formy-project.herokuapp.com/autocomplete");

        // Locate the autocomplete input field
        WebElement address = driver.findElement(By.id("autocomplete"));
        address.sendKeys("Cape Town, ForeShore");

        WebElement streetAddress = driver.findElement(By.id("street_number"));
        streetAddress.sendKeys("88");

        WebElement streetAddress2 = driver.findElement(By.id("route"));
        streetAddress2.sendKeys("Cape Town, CBD");

        WebElement city = driver.findElement(By.id("locality"));
        city.sendKeys("Cape Town");

        WebElement state = driver.findElement(By.id("administrative_area_level_1"));
        state.sendKeys("Western Cape");

        WebElement zipCode = driver.findElement(By.id("postal_code"));
        zipCode.sendKeys("8001");

        WebElement country = driver.findElement(By.id("country"));
        country.sendKeys("South Africa");

        // Wait for autocomplete suggestions to appear

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement firstSuggestion = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class, 'pac-item')]")));
        firstSuggestion.click();

        // Verify if the address was selected correctly
        String selectedAddress = address.getAttribute("value");
        System.out.println("Selected Address: " + selectedAddress);



        assertNotNull("Autocomplete failed - No address populated!", selectedAddress);
        assertTrue("Autocomplete did not select the correct address!", selectedAddress.contains("1600 Amphitheatre"));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
