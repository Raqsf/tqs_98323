package covidtracker.covidtracker.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.Duration;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class StatsSteps {

    private WebDriver driver;

    @ParameterType("([0-9]{2})/([0-9]{2})/([0-9]{4})")
	public String date(String day, String month, String year){
		return String.format("%s/%s/%s", day, month, year);
	}

    @ParameterType("([0-2]{1}[0-9]{1}):([0-5]{1}[0-9]{1}):([0-5]{1}[0-9]{1})")
	public String time(String hours, String minutes, String seconds){
		return String.format("%s:%s:%s", hours, minutes, seconds);
	}

    @When("user navigates to the covid tracker application")
    public void userNavigateTo() {
        driver = WebDriverManager.firefoxdriver().create();
        driver.get("http://localhost:3000");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @And("orders alphabetically the countries")
    public void orderAlphabetically() {
        driver.findElement(By.cssSelector(".fa-arrow-down-a-z")).click();
    }

    @Then("the recovered cases should be greater than or equal to {int}")
    public void totalCases(int total) {
        assertThat(driver.findElement(By.cssSelector(".MuiGrid-root:nth-child(1) > .MuiGrid-root:nth-child(1) .MuiTypography-root:nth-child(7)")).getText(), greaterThanOrEqualTo("Total: " + total));
    }

    @Then("the first country should be {string}")
    public void firstCountry(String country) {
        assertThat(driver.findElement(By.cssSelector(".MuiBox-root:nth-child(2) > .MuiGrid-root > .MuiGrid-root:nth-child(1) .MuiTypography-root:nth-child(2)")).getText(), is("Name: " + country));
    }

    @And("user selects {string}")
    public void selectCountry(String country) {
        {
            WebElement element = driver.findElement(By.id("demo-simple-select"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).clickAndHold().perform();
        }
        {
            WebElement element = driver.findElement(By.cssSelector("#menu- > .MuiBackdrop-root"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).release().perform();
        }
        {
            WebElement element = driver.findElement(By.cssSelector(".MuiMenuItem-root:nth-child(5)"));
            assertThat(element.getText(), is(country));
            element.click();
        }
    }
        
    @Then("the continent should be {string}")
    public void getContinent(String continent) {
        assertThat(driver.findElement(By.cssSelector(".MuiBox-root:nth-child(1) > .MuiGrid-root > .MuiGrid-root:nth-child(1) .MuiTypography-root:nth-child(3)")).getText(), is("Continent: " + continent));
    }
        
    @Then("the total cases should be greater than or equal to {int}")
    public void getTotalCases(int total) {
        assertThat(driver.findElement(By.cssSelector(".MuiGrid-root:nth-child(1) > .MuiGrid-root:nth-child(2) .MuiTypography-root:nth-child(6)")).getText(), is("Total: " + total));
    }

    @And("selects {date}")
    public void selectDay(String date) {
        driver.findElement(By.cssSelector(".css-i4bv87-MuiSvgIcon-root")).click();
        driver.findElement(By.cssSelector(".css-fd2y78-MuiSvgIcon-root")).click();
        driver.findElement(By.cssSelector(".PrivatePickersYear-root:nth-child(3) > .PrivatePickersYear-yearButton")).click();
        driver.findElement(By.cssSelector(".MuiIconButton-edgeStart > .MuiSvgIcon-root")).click();
        driver.findElement(By.cssSelector(".MuiIconButton-edgeStart > .MuiSvgIcon-root")).click();
        driver.findElement(By.cssSelector(".MuiIconButton-edgeStart > .MuiSvgIcon-root")).click();
        driver.findElement(By.cssSelector(".MuiIconButton-edgeStart > .MuiSvgIcon-root")).click();
        driver.findElement(By.cssSelector(".MuiIconButton-edgeStart > .MuiSvgIcon-root")).click();
        driver.findElement(By.cssSelector(".css-mvmu1r:nth-child(2) > div:nth-child(4) > .MuiButtonBase-root")).click();
        assertThat(driver.findElement(By.cssSelector(".MuiBox-root:nth-child(3) > .MuiTypography-root")).getText(), startsWith(date));
    }

    @Then("the new cases at {time} should be {int}")
    public void getNewCasesAtSpecificTime(String time, int cases) {
        assertThat(driver.findElement(By.cssSelector(".MuiBox-root:nth-child(3) > .MuiTypography-root")).getText(), endsWith(time));
        assertThat(driver.findElement(By.cssSelector(".MuiBox-root:nth-child(3) > .MuiGrid-root > .MuiGrid-root:nth-child(2) .MuiTypography-root:nth-child(2)")).getText(), is("New: " + cases));
    }
    
}
