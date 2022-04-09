package io.cucumber.skeleton;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BlazeDemoSteps {
	
	private WebDriver driver;
    
    @Given("I navigate to {string}")
    public void I_visit_google(String url) {
		driver = new FirefoxDriver();
        driver.get(url);
    }

	@Given("I select flight from {string} to {string}")
	public void i_select_flight_from_to(String from, String to) {
		driver.findElement(By.name("fromPort")).click();
		{
			driver.findElement(By.cssSelector(String.format("option[value=%s]", from))).click();
        }
		driver.findElement(By.name("toPort")).click();
		{
			driver.findElement(By.cssSelector(String.format("option[value=%s]", to))).click();
        }
	}
	@Given("I click Find Flights")
	public void i_click_find_flights() {
		driver.findElement(By.cssSelector(".btn-primary")).click();
	}
	@Given("I click Choose This Flight")
	public void i_click_choose_this_flight() {
		driver.findElement(By.xpath("//tbody/tr[3]/td[1]/input")).click();
	}
	@Given("I click Purchase Flight")
	public void i_click_purchase_flight() {
		driver.findElement(By.cssSelector(".btn-primary")).click();
	}
	@Then("I should be shown the {string} page")
	public void i_should_be_shown_page(String string) {
		assertThat(driver.getTitle(), equalTo(string));
	}

}