package pages;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.support.FindBys;
import utilities.TestUtility;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class SearchResultPage extends TestBase {
    @FindBy(xpath = "//div[contains(@class,'filter-detail')]//div[@data-gb-name='filter-bar']")
    WebElement filterSection;

    @FindBy(xpath = "//div[contains(@class,'sort-detail')]//div[@data-gb-name='sort-bar']")
    WebElement sortSection;

    @FindBy(xpath = "//div[contains(@class,'edit-detail')]//div[@data-gb-name='edit-detail-bar']")
    WebElement detailSection;

    @FindBy(xpath = "//div[contains(@class, 'filter-detail')]//a[@class='btn-ripple more']")
    WebElement seeMore_Button_Filter;

    @FindBy(xpath = "//div[contains(@class, 'filter-detail')]//a[@class='btn-ripple less']")
    WebElement seeLess_Button_Filter;

    @FindBy(xpath = "//div[@id='collapseSeemore']//label[contains(text(), 'Personal Accident')]/parent::div//div[@class='bootstrap-slider']")
    WebElement personalAccidentSlider;

    @FindBy(xpath = "//div[@id='collapseSeemore']//label[contains(text(), 'Personal Accident')]/parent::div//div[@class='bootstrap-slider']//div[contains(@class,'min-slider-handle')]")
    WebElement getPersonalAccidentSliderMinPoint;

    @FindBy(xpath = "//div[@id='collapseSeemore']//label[contains(text(), 'Personal Accident')]/parent::div//div[@class='bootstrap-slider']//div[contains(@class,'max-slider-handle')]")
    WebElement getPersonalAccidentSliderMaxPoint;

    @FindBy(xpath = "//div[@data-filter-by='insurerId']//div[@class=\"checkbox-wrap checkbox-group\"]/div[1]//input")
    WebElement firstInsurer;

    public final String searchItemsXPath = "//div[contains(@class,'card-full')]";
    @FindBys(@FindBy(xpath = searchItemsXPath))
    List<WebElement> listOfSearchedResult;

    @FindBy(xpath = "//div[@id='collapseTwo']/div/div[2]//input")
    WebElement sortByPriceLowToHigh;

    @FindBy(xpath = "//div[@class='field destination-field']//div[@class='select-component']//span[@class='filter-option clearfix']")
    WebElement editDetailByDestinationLabel;

    @FindBy(xpath = "//div[@class='field destination-field']//div[@class='select-component']//button")
    WebElement editDetailByDestinationButton;

    @FindBy(xpath = "//div[@class='dropdown-menu open']//ul//li[2]/a")
    WebElement editDetailByDestinationAsiaOption;

    @FindBy(xpath = "//input[@name='dates-startdate']")
    WebElement editDetailCalendarPicker;

    @FindBy(xpath = "//div[@class='datepicker-days']//td[@class='active selected range-start day']")
    WebElement editDetailCalendarPickerCurrentActiveOption;

    @FindBy(xpath = "//div[@class='datepicker-days']//td[@class='active selected range-start day']/following-sibling::td[1]")
    WebElement editDetailCalendarPickerNextDayOption;


    public final String expectedSearchResultPageTitle = "Search and compare the best travel insurance in the Philippines | GoBear";

    public SearchResultPage() {
        PageFactory.initElements(driver, this);
    }

    public boolean verifySearchResultPageTitle() {
        return TestUtility.verifyPageTitleContains(expectedSearchResultPageTitle, TestUtility.Element_Wait);
    }

    public boolean verifyFilterSectionIsDisplayed() {
        TestUtility.displayElement(filterSection);
        return TestUtility.verifyElementDisplayed(filterSection);
    }

    public boolean verifySortSectionIsDisplayed() {
        TestUtility.displayElement(sortSection);
        return TestUtility.verifyElementDisplayed(sortSection);
    }

    public boolean verifyDetailSectionIsDisplayed() {
        TestUtility.displayElement(detailSection);
        return TestUtility.verifyElementDisplayed(detailSection);
    }

    public void clickOnSeeMoreButton() {
        TestUtility.displayElement(seeMore_Button_Filter);
        TestUtility.enableElement(seeMore_Button_Filter);
        TestUtility.clickOn(driver, seeMore_Button_Filter, TestUtility.Element_Wait);
    }

    public boolean verifySeeMoreButtonIsDisplayed() {
        TestUtility.displayElement(seeMore_Button_Filter);
        return TestUtility.verifyElementDisplayed(seeMore_Button_Filter);
    }

    public boolean verifySeeLessButtonIsDisplayed() {
        TestUtility.displayElement(seeLess_Button_Filter);
        return TestUtility.verifyElementDisplayed(seeLess_Button_Filter);
    }

    public void setHValue(WebElement slider, double value) {
        double maxValue = Double.parseDouble(slider.getAttribute("aria-valuemax"));
        Point sliderLocation = slider.getLocation();
        int sliderX = sliderLocation.getX();
        int sliderY = sliderLocation.getY();
        Actions action = new Actions(driver);
        int newSliderX = (int) (value * (sliderY - sliderX) / maxValue);
        action.dragAndDropBy(slider, newSliderX, sliderY).release().build().perform();
        slider.click();
    }

    public int getMinValueAfterSlided() {
        return Integer.parseInt(getPersonalAccidentSliderMinPoint.getAttribute("aria-valuenow"));
    }

    public void setPersonalAccidentSliderMinPoint(double value) {
        TestUtility.displayElement(getPersonalAccidentSliderMinPoint);
        TestUtility.displayElement(getPersonalAccidentSliderMaxPoint);
        setHValue(getPersonalAccidentSliderMinPoint, value);
    }

    public boolean verifyCheckboxIsNotSelected(WebElement element) {
        return element.isSelected();
    }

    public int getSearchedItems() {
        return listOfSearchedResult.size();
    }

    public void clickOnCheckboxUsingJsExecutor(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public int getPriceFromString(String price) throws ParseException {
        NumberFormat format = NumberFormat.getInstance(Locale.US);
        Number number = format.parse(price);
        return number.intValue();
    }

    public List getAllPriceOfSearchResultItems() throws ParseException {
        List<Integer> list = new ArrayList<>();
        int searchItems = getSearchedItems();
        int itemPrice;
        for (int i = 1; i <= searchItems; i++) {
            String elementXPath = "//div[contains(@class,'card-full')][" + i + "]//div[@class='policy-price']//span[@class='value']";
            WebElement element = driver.findElement(By.xpath(elementXPath));
            itemPrice = getPriceFromString(element.getText());
            list.add(itemPrice);
        }
        return list;
    }

    public boolean verifyPriceItemsAreSortedByLowToHigh(List list) {
        boolean verifiedItem;
        Collections.sort(list);
        verifiedItem = list.equals(list);
        return verifiedItem;
    }

    public boolean verifyInsurerCheckboxesCanBeChecked() {
        boolean verifiedElement = false;
        int defaultSearchedItems = getSearchedItems();
        Log.info("default searched items: " + defaultSearchedItems);
        if (!verifyCheckboxIsNotSelected(firstInsurer)) {
            clickOnCheckboxUsingJsExecutor(firstInsurer);
            int newSearchItems = getSearchedItems();
            verifiedElement = newSearchItems <= defaultSearchedItems;
        }
        return verifiedElement;
    }

    public boolean verifySortByPriceLowToHighCanBeChecked() throws ParseException {
        boolean verifiedElement = false;
        int defaultSearchedItems = getSearchedItems();
        if (!verifyCheckboxIsNotSelected(sortByPriceLowToHigh)) {
            clickOnCheckboxUsingJsExecutor(sortByPriceLowToHigh);
            int newSearchItems = getSearchedItems();
            if (newSearchItems <= defaultSearchedItems) {
                if (verifyPriceItemsAreSortedByLowToHigh(getAllPriceOfSearchResultItems()))
                    verifiedElement = true;
            }
        }
        return verifiedElement;
    }

    public void selectEditDetailDestinationDropdown() {
        TestUtility.clickOn(driver, editDetailByDestinationButton, TestUtility.Element_Wait);
    }

    public void selectEditDetailAsiaOption() {
        TestUtility.displayElement(editDetailByDestinationAsiaOption);
        TestUtility.clickOn(driver, editDetailByDestinationAsiaOption, TestUtility.Element_Wait);
    }

    public String getEditDetailDestinationSelectedOption() {
        TestUtility.displayElement(editDetailByDestinationLabel);
        return editDetailByDestinationLabel.getText().trim();
    }

    public boolean verifyCanSelectAsiaOptionInDestinationDropdown() {
        String defaultOption = getEditDetailDestinationSelectedOption();
        selectEditDetailDestinationDropdown();
        selectEditDetailAsiaOption();
        String newSelectedOption = getEditDetailDestinationSelectedOption();
        boolean verifiedSelectedOption = false;
        if (!newSelectedOption.equals(defaultOption)) {
            verifiedSelectedOption = true;
        }
        return verifiedSelectedOption;
    }

    public void clickOnCalendarPicker() {
        TestUtility.elementEnable(driver, editDetailCalendarPicker, TestUtility.Element_Wait);
        TestUtility.clickOn(driver, editDetailCalendarPicker, TestUtility.Element_Wait);
    }

    public void selectNextDayOnCalendarPicker() {
        clickOnCalendarPicker();
        TestUtility.displayElement(editDetailCalendarPickerCurrentActiveOption);
        TestUtility.displayElement(editDetailCalendarPickerNextDayOption);
        clickOnCheckboxUsingJsExecutor(editDetailCalendarPickerNextDayOption);
    }

    public void verifyCanSelectNextDayOptionInCalendarPicker() {
        clickOnCalendarPicker();
        selectNextDayOnCalendarPicker();
    }
}
