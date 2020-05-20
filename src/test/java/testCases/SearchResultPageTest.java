package testCases;

import base.TestBase;
import org.testng.Assert;
import pages.SearchResultPage;
import pages.UatPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.text.ParseException;

public class SearchResultPageTest extends TestBase {
    UatPage uatPage;
    SearchResultPage searchResultPage;

    public SearchResultPageTest() {
        super();
    }

    @Parameters("Browser")
    @BeforeMethod
    public void setUp(String Browser) {
        initialization(Browser);
        Log.info("UAT page launched successfully");
        uatPage = new UatPage();
    }

    @Test(priority = 1, enabled = true)
    public void accessSearchResultPageFromUatPageTest(Method method) {
        extentTest = extent.startTest(method.getName());
        searchResultPage = uatPage.navigateToSearchResultPageFromUatPage();
        Log.info("Successfully Accessed Search Result Page");
    }

    @Test(priority = 2, enabled = true)
    public void verifySearchResultPageDisplayingTest(Method method) {
        extentTest = extent.startTest(method.getName());
        searchResultPage = uatPage.navigateToSearchResultPageFromUatPage();
        Assert.assertTrue(searchResultPage.verifySearchResultPageTitle());
        Assert.assertTrue(searchResultPage.verifyFilterSectionIsDisplayed());
        Assert.assertTrue(searchResultPage.verifyDetailSectionIsDisplayed());
        Assert.assertTrue(searchResultPage.verifySortSectionIsDisplayed());
        Log.info("Search Result Page is displaying");
    }

    @Test(priority = 3, enabled = true)
    public void verifyClickOnSeeMoreButtonOfFilterSection(Method method) {
        extentTest = extent.startTest(method.getName());
        searchResultPage = uatPage.navigateToSearchResultPageFromUatPage();
        Assert.assertTrue(searchResultPage.verifySeeMoreButtonIsDisplayed());
        searchResultPage.clickOnSeeMoreButton();
        Assert.assertTrue(searchResultPage.verifySeeLessButtonIsDisplayed());
        Assert.assertFalse(searchResultPage.verifySeeMoreButtonIsDisplayed());
        Log.info("Click on See More Button successfully");
    }

    @Test(priority = 4, enabled = true)
    public void verifySlideMinPointOfPersonalAccidentSliderOfFilterSection(Method method) throws InterruptedException {
        extentTest = extent.startTest(method.getName());
        searchResultPage = uatPage.navigateToSearchResultPageFromUatPage();
        searchResultPage.clickOnSeeMoreButton();
        searchResultPage.setPersonalAccidentSliderMinPoint(20000);
        Assert.assertNotEquals(0, searchResultPage.getMinValueAfterSlided());
    }

    @Test(priority = 5, enabled = true)
    public void verifyResultItemsAreChangedAfterFiltering(Method method) throws InterruptedException {
        extentTest = extent.startTest(method.getName());
        searchResultPage = uatPage.navigateToSearchResultPageFromUatPage();
        searchResultPage.clickOnSeeMoreButton();
        Assert.assertTrue(searchResultPage.verifyInsurerCheckboxesCanBeChecked());
    }

    @Test(priority = 6, enabled = true)
    public void verifyResultItemsAreChangedAfterSorting(Method method) throws InterruptedException, ParseException {
        extentTest = extent.startTest(method.getName());
        searchResultPage = uatPage.navigateToSearchResultPageFromUatPage();
        searchResultPage.clickOnSeeMoreButton();
        Assert.assertTrue(searchResultPage.verifySortByPriceLowToHighCanBeChecked());
    }

    @Test(priority = 7, enabled = true)
    public void verifyCanSelectNewDestinationOption(Method method) throws InterruptedException, ParseException {
        extentTest = extent.startTest(method.getName());
        searchResultPage = uatPage.navigateToSearchResultPageFromUatPage();
        Assert.assertTrue(searchResultPage.verifyCanSelectAsiaOptionInDestinationDropdown());
    }

    @Test(priority = 8, enabled = true)
    public void verifyCanSelectNewDateFromCalendarPicker(Method method) throws InterruptedException, ParseException {
        extentTest = extent.startTest(method.getName());
        searchResultPage = uatPage.navigateToSearchResultPageFromUatPage();
        searchResultPage.verifyCanSelectNextDayOptionInCalendarPicker();
    }
}
