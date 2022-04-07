package ua.page_object_pattern.tests;

import static io.github.bonigarcia.seljup.BrowserType.CHROME;

import io.github.bonigarcia.seljup.DockerBrowser;
import io.github.bonigarcia.seljup.SeleniumJupiter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import ua.page_object_pattern.webpages.FinalPage;
import ua.page_object_pattern.webpages.PurchasePage;
import ua.page_object_pattern.webpages.FlightsPage;
import ua.page_object_pattern.webpages.HomePage;

// import static org.hamcrest.MatcherAssert.assertThat;

// import java.net.URL;
import java.time.Duration;

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

       // // System.out.println(driver.getTitle());

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

       // System.out.println(driver.getTitle());

       // Choose third flight
       flightsPage.clickFlight();

       // Create object of PurchasePage
       PurchasePage purchasePage = new PurchasePage(driver);

       // Check if page is opened
       Assertions.assertTrue(purchasePage.isPageOpened());

       // System.out.println(driver.getTitle());

       // Remember me is not checked
       Assertions.assertTrue(purchasePage.isNotChecked());

       purchasePage.clickPurchaseFlight();

       // Create object of PurchasePage
       FinalPage finalPage = new FinalPage(driver);

       // Check if page is opened
       Assertions.assertTrue(finalPage.isPageOpened());

       // System.out.println(driver.getTitle());

       // System.out.println("DOCKER TEST");
   }
}
