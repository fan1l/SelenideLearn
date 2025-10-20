package pages;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class BasketPage {

    private static final String productName = "(//a[contains(@href, '#V%s')])[1]/following-sibling::div/a";
    private static final String productSize = "(//a[contains(@href, '#V%s')])[1]/following-sibling::div/ul/li[1]";
    private static final String productColor = "(//a[contains(@href, '#V%s')])[1]/following-sibling::div/ul/li[2]";
    private static final String productPrice = "(//a[contains(@href, '#V%s')])[1]/following-sibling::div[2]/div";
    private static final String productCount = "(//a[contains(@href, '#V%s')])[1]/following-sibling::div[4]//div[@class='quantity-group__number']";
    private static final String orderPriceInSidebar = "//div[@class='basket-summary__price js-price']";
    private static final String productsCountInSidebar = "//span[@class='js-more']";
    private static final String productsPriceInSidebar = "//span[contains(text(), 'Товары')]/following-sibling::span";
    private static final String deliveryPriceInSidebar = "//span[contains(text(), 'Доставка')]/following-sibling::span";
    private static final String moreButtonInSidebar = "//button[@class='basket-summary__btn-more']";


    public String getProductName(String productArticle) {
        return $(By.xpath(String.format(productName, productArticle))).shouldBe(visible).text();
    }

    public String getProductColor(String productArticle) {
        return $(By.xpath(String.format(productColor, productArticle))).shouldBe(visible)
                .text().replaceAll("Цвет:\\s*", "").trim();
    }

    public String getProductSize(String productArticle) {
        return $(By.xpath(String.format(productSize, productArticle))).shouldBe(visible)
                .text().replaceAll("Размер:\\s*", "").trim();
    }

    public double getProductPrice(String productArticle) {
        return Double.parseDouble($(By.xpath(String.format(productPrice, productArticle))).shouldBe(visible)
                .text().replaceAll("[^\\d,]", "").replace(",", "."));
    }

    public int getProductCount(String productArticle) {
        return Integer.parseInt($(By.xpath(String.format(productCount, productArticle))).shouldBe(visible).text());
    }

    public double getOrderPriceInSidebar() {
        return Double.parseDouble($(By.xpath(orderPriceInSidebar)).shouldBe(visible)
                .text().replaceAll("[^\\d,]", "").replace(",", "."));
    }

    public int getProductsCountInSidebar() {
        return Integer.parseInt($(By.xpath(productsCountInSidebar)).shouldBe(visible)
                .text().split("\\s+")[0]);
    }

    public double getProductsPriceInSidebar() {
        return Double.parseDouble($(By.xpath(productsPriceInSidebar))
                .text().replaceAll("[^\\d,]", "").replace(",", "."));
    }

    public double getDeliveryPriceInSidebar() {
        return Double.parseDouble($(By.xpath(deliveryPriceInSidebar))
                .text().replaceAll("[^\\d,]", "").replace(",", "."));
    }

    public void showProductsAndDeliveryPrice() {
        $(By.xpath(moreButtonInSidebar)).shouldBe(visible).click();
    }

}
