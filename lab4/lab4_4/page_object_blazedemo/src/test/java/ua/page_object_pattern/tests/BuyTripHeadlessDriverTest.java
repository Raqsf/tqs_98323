package test.java.ua.page_object_pattern.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.time.Duration;

import ua.page_object_pattern.webpages.FinalPage;
import ua.page_object_pattern.webpages.PurchasePage;
import ua.page_object_pattern.webpages.FlightsPage;
import ua.page_object_pattern.webpages.HomePage;

public class BuyTripHeadlessDriverTest {

    @Test
    public void buyTripTest() {
        // System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver");

        //Set Firefox Headless mode as TRUE
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        
        //pass the options parameter in the Firefox driver declaration
        WebDriver driver = new FirefoxDriver(options);
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        //Create object of HomePage Class
        HomePage home = new HomePage(driver);

        // Open home page
        Assertions.assertTrue(home.isPageOpened());

        // Choose Boston as departure city
        home.selectFromPort();

        // Choose London as destination city
        home.selectToPort();

        // Search flights
        home.clickFindFlights();

        // Create object of FlightsPage
        FlightsPage flightsPage = new FlightsPage(driver);

        //Check if page is opened
        Assertions.assertTrue(flightsPage.isPageOpened());

        // Choose third flight
        flightsPage.clickFlight();

        // Create object of PurchasePage
        PurchasePage purchasePage = new PurchasePage(driver);

        // Check if page is opened
        Assertions.assertTrue(purchasePage.isPageOpened());

        // Remember me is not checked
        Assertions.assertTrue(purchasePage.isNotChecked());

        purchasePage.clickPurchaseFlight();

        // Create object of PurchasePage
        FinalPage finalPage = new FinalPage(driver);

        // Check if page is opened
        Assertions.assertTrue(finalPage.isPageOpened());

        // System.out.println(driver.getTitle());
            
        //Close the driver
        driver.close();

        // System.out.println("HEADLESS BROWSER TEST");
    }
    
}