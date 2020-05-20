package pages;

import base.TestBase;
import utilities.TestUtility;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UatPage extends TestBase {
    @FindBy(xpath = "//div[contains(@class,'tabs-products')]//li[@data-gb-name='Insurance']//a")
    WebElement insuranceTab;

    @FindBy(xpath = "//div[@id='Insurance']//li[@data-gb-name='Travel']//a")
    WebElement travelSubTab;

    @FindBy(xpath="//div[@id='travel-form']//button[@name='product-form-submit']")
    WebElement travelSubTabShowResultButton;


    public UatPage()
    {
        PageFactory.initElements(driver, this);
    }

    public SearchResultPage navigateToSearchResultPageFromUatPage()
    {
        TestUtility.clickOn(driver, insuranceTab, TestUtility.Element_Wait);
        TestUtility.clickOn(driver, travelSubTab, TestUtility.Element_Wait);
        TestUtility.clickOn(driver, travelSubTabShowResultButton, TestUtility.Element_Wait);
        return new SearchResultPage();
    }
}
