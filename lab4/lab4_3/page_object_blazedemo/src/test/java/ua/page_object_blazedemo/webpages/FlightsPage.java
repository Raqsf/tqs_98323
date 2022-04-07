package ua.page_object_blazedemo.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FlightsPage {

    private WebDriver driver;

    //Locators

    @FindBy(tagName = "h3")
    WebElement heading;

    @FindBy(xpath = "//tbody/tr[3]/td[1]/input")
    WebElement flight;

    //Constructor
    public FlightsPage(WebDriver driver){
        this.driver=driver;
 
        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    public boolean isPageOpened(){
        //Assertion
        return heading.getText().toString().contains("Flights from Boston to London:");
    }

    public void clickFlight() {
        flight.click();
    }
    
}