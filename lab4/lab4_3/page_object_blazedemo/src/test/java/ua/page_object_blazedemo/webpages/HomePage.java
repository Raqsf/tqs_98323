package ua.page_object_blazedemo.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
  

public class HomePage {

    private WebDriver driver;

    //Page URL
    private static String PAGE_URL="https://blazedemo.com";

    //Locators

    @FindBy(tagName = "h1")
    WebElement heading;

    @FindBy(name = "fromPort")
    WebElement fromPort;

    @FindBy(name = "toPort")
    WebElement toPort;

    @FindBy(tagName = "input")
    WebElement findFlights;

    //Apply as Developer Button
    @FindBy(how = How.LINK_TEXT, using = "Apply as a Freelancer")
    private WebElement developerApplyButton;

    //Constructor
    public HomePage(WebDriver driver){
        this.driver=driver;
        driver.get(PAGE_URL);
        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    public boolean isPageOpened(){
        //Assertion
        return heading.getText().toString().contains("Welcome to the Simple Travel Agency!");
    }

    public void selectFromPort() {
        fromPort.click();
        Select dropdown = new Select(fromPort);
        dropdown.selectByValue("Boston");
    }

    public void selectToPort() {
        toPort.click();
        Select dropdown = new Select(toPort);
        dropdown.selectByValue("London");
    }

    public void clickFindFlights() {
        findFlights.click();
    }

}