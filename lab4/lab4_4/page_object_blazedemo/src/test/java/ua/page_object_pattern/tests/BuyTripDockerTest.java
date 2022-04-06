package ua.page_object_pattern.tests;

import static io.github.bonigarcia.seljup.BrowserType.CHROME;

import io.github.bonigarcia.seljup.DockerBrowser;
import io.github.bonigarcia.seljup.SeleniumJupiter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import ua.page_object_pattern.webpages.FinalPage;
import ua.page_object_pattern.webpages.PurchasePage;
import ua.page_object_pattern.webpages.FlightsPage;
import ua.page_object_pattern.webpages.HomePage;

// import java.net.URL;
import java.time.Duration;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.MatcherAssert.*;

import org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SeleniumJupiter.class)
public class BuyTripDockerTest {
    /* WebDriver driver; */

   /* @Before
   public void setup(){
       //use FF Driver
       driver = new FirefoxDriver();
       driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
   } */

   @Test
   public void buyTripTest(@DockerBrowser(type = CHROME) WebDriver driver) {
       driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
       assertThat(driver.getTitle()).contains("Selenium WebDriver");
       driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

       //Create object of HomePage Class
       HomePage home = new HomePage(driver);
       // home.clickOnDeveloperApplyButton();

       // Open home page
       Assert.assertTrue(home.isPageOpened());

       // Choose Boston as departure city
       home.selectFromPort();

       // Choose London as destination city
       home.selectToPort();

       // Search flights
       home.clickFindFlights();

       // Create object of FlightsPage
       FlightsPage flightsPage = new FlightsPage(driver);

       //Check if page is opened
       Assert.assertTrue(flightsPage.isPageOpened());

       // Choose third flight
       flightsPage.clickFlight();

       // Create object of PurchasePage
       PurchasePage purchasePage = new PurchasePage(driver);

       // Check if page is opened
       Assert.assertTrue(purchasePage.isPageOpened());

       // Remember me is not checked
       Assert.assertTrue(purchasePage.isNotChecked());

       purchasePage.clickPurchaseFlight();

       // Create object of PurchasePage
       FinalPage finalPage = new FinalPage(driver);

       // Check if page is opened
       Assert.assertTrue(finalPage.isPageOpened());

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
