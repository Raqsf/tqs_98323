package ua.page_object_pattern.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FreelancerApplyPage {
    private WebDriver driver;

    @FindBy(tagName = "h1")
    WebElement heading;
 
    @FindBy(id="talent_create_applicant_email")
    WebElement developer_email;
 
    @FindBy(id = "talent_create_applicant_password")
    WebElement developer_password;
 
    @FindBy(id = "talent_create_applicant_password_confirmation")
    WebElement developer_password_confirmation;
 
    @FindBy(id = "talent_create_applicant_full_name")
    WebElement developer_full_name;
 
    /* @FindBy(id = "developer_skype")
    WebElement developer_skype; */
 
    @FindBy(id ="save_new_talent_create_applicant")
    WebElement join_toptal_button;
 
 
    //Constructor
    public FreelancerApplyPage(WebDriver driver){
        this.driver=driver;
 
        //Initialise Elements
        PageFactory.initElements(driver, this);
    }
 
    public void setDeveloper_email(String email){
        developer_email.clear();
        developer_email.sendKeys(email);
    }
 
    public void setDeveloper_password(String password){
        developer_password.clear();
        developer_password.sendKeys(password);
    }
 
    public void  setDeveloper_password_confirmation(String password_confirmation){
        developer_password_confirmation.clear();
        developer_password_confirmation.sendKeys(password_confirmation);
    }
 
    public void setDeveloper_full_name (String fullname){
        developer_full_name.clear();
        developer_full_name.sendKeys(fullname);
    }
 
    /* public void setDeveloper_skype (String skype){
        developer_skype.clear();
        developer_skype.sendKeys(skype);
    } */
 
    public void clickOnJoin(){
        join_toptal_button.click();
    }
    public boolean isPageOpened(){
        //Assertion
        // the title has a '<br>'
        return heading.getText().toString().contains("Apply to Join") && heading.getText().toString().contains("the World's Top Talent Network");
    }
}
