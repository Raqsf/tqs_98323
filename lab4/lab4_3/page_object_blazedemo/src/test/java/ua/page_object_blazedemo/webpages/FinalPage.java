package ua.page_object_pattern.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class FinalPage {

    private WebDriver driver;

    //Constructor
    public FinalPage(WebDriver driver){
        this.driver=driver;
 
        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    public boolean isPageOpened(){
        //Assertion
        return driver.getTitle().contains("BlazeDemo Confirmation");
    }

    
}