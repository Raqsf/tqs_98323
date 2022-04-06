package ua.page_object_pattern.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import ua.page_object_pattern.webpages.FinalPage;
import ua.page_object_pattern.webpages.PurchasePage;
import ua.page_object_pattern.webpages.FlightsPage;
import ua.page_object_pattern.webpages.HomePage;

// import java.net.URL;
import java.time.Duration;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BuyTripTest {
    WebDriver driver;

   @BeforeAll
   public void setup(){
       //use FF Driver
       driver = new FirefoxDriver();
       driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
   }

   @Test
   public void buyTripTest() {
       //Create object of HomePage Class
       HomePage home = new HomePage(driver);
       // home.clickOnDeveloperApplyButton();

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

       /* //Create object of DeveloperPortalPage
       DeveloperPortalPage devportal= new DeveloperPortalPage(driver);

       //Check if page is opened
       Assert.assertTrue(devportal.isPageOpened());

       //Click on Join Toptal
       devportal.clikOnJoin(); */

       //Create object of DeveloperApplyPage
       // FreelancerApplyPage applyPage = new FreelancerApplyPage(driver);

       //Check if page is opened
       // Assert.assertTrue(applyPage.isPageOpened());

       //Fill up data
       /* applyPage.setDeveloper_email("dejan@toptal.com");
       applyPage.setDeveloper_full_name("Dejan Zivanovic Automated Test");
       applyPage.setDeveloper_password("password123");
       applyPage.setDeveloper_password_confirmation("password123"); */
       /* applyPage.setDeveloper_skype("automated_test_skype"); */

       //Click on join
       //applyPage.clickOnJoin(); 
   }

    @AfterAll
    public void close(){
        driver.close();
    }
}
