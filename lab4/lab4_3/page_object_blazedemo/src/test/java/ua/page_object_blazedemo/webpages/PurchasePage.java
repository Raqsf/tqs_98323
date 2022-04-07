package ua.page_object_blazedemo.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PurchasePage {

    private WebDriver driver;

    //Locators
    
    @FindBy(tagName = "h2")
    WebElement heading;

    @FindBy(id = "rememberMe")
    WebElement rememberMe;

    @FindBy(css = ".btn-primary")
    WebElement purchaseFlight;

    //Constructor
    public PurchasePage(WebDriver driver){
        this.driver=driver;
 
        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    public boolean isPageOpened(){
        //Assertion
        return heading.getText().toString().contains("Your flight from TLV to SFO has been reserved.");
    }

    public boolean isNotChecked() {
        return !rememberMe.isSelected();
    }

    public void clickPurchaseFlight() {
        purchaseFlight.click();
    }
}