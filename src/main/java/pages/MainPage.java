package pages;

import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {
    private final By cookiesButton = By.xpath("//span[contains(text(), 'Согласен')]/parent::button");
    private final By catalogButton = By.xpath("//a[@id='rubrics-toggle']");
    private static final String categoryInCatalog = "//nav[@class='mega-burger__sidebar']//a[@title='%s']";
    private static final String subcategoryInCatalog = "//div[contains(@class, 'sub-content_show')]//a[@title='%s']";
    private static final String filterTypeInSideBar = "(//div[text()='%s']/following-sibling::button)[1]";
    private static final String filterNameInSideBar = "//input[@name='%s[]']/parent::label";
    private static final String productCardLink = "a[href$='V%s']";
    private static final String subcategoryInSideBar = "//nav[@class='rubrics-catalog']//a[contains(text(), '%s')]";

    public void acceptCookies() {
        if ($(cookiesButton).isDisplayed()) {
            $(cookiesButton).shouldBe(visible, Duration.ofSeconds(15)).click();
            $(cookiesButton).shouldNotBe(visible);
        }
    }

    public void goToCatalog() {
        $(catalogButton).shouldBe(visible).click();
    }

    public void goToCategoryInCatalog(String category) {
        $(By.xpath(String.format(categoryInCatalog, category))).shouldBe(visible).click();
    }

    public void showSubcategoriesInCatalog(String category) {
        $(By.xpath(String.format(categoryInCatalog, category))).should(appear).hover();
    }

    public void goToSubcategoryInCatalog(String subcategory) {
        $(By.xpath(String.format(subcategoryInCatalog, subcategory))).shouldBe(visible).click();
    }

    public void expandFilterTypeInSidebar(String filterTypeName) {
        $(By.xpath(String.format(filterTypeInSideBar, filterTypeName))).shouldBe(visible).click();
    }

    public void selectFilterNameInSidebar(String filterName) {
        $(By.xpath(String.format(filterNameInSideBar, filterName))).shouldBe(visible).shouldNotBe(checked).click();
    }

    public void goToProductCard(String productArticle) {
        $(String.format(productCardLink, productArticle)).shouldBe(visible).click();
    }

    public void goToSubcategoryInSideBar(String subcategory) {
        $(By.xpath(String.format(subcategoryInSideBar, subcategory))).shouldBe(visible).click();
    }

}
