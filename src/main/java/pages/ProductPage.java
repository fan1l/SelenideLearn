package pages;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class ProductPage {
    private final By productName = By.xpath("//h1[@id='name']");
    private final By productSize = By.xpath("//span[text()='Размер:']/following-sibling::span");
    private final By productColor = By.xpath("//span[text()='Цвет']/parent::dt/following-sibling::dd");
    private final By productPrice = By.xpath("//div[@class='price__regular']");
    private final By productArticle = By.xpath("//dd[@data-select='product-article']");
    private final By productCount = By.xpath("//div[@class='quantity-group__number']");
    private final By basketLink = By.xpath("//a[@data-selector='basket-desktop']");
    private final By addBasketBtn = By.xpath("//span[text()='Добавить в корзину']/parent::button");

    public String getProductName() {
        return $(productName).shouldBe(visible).text()
                .replaceAll("\\s*(Цвет:|Размер:).*", "").trim();
    }

    public String getProductSize() {
        return $(productSize).shouldBe(visible).text();
    }

    public String getProductColor() {
        return $(productColor).shouldBe(visible).text();
    }

    public double getProductPrice() {
        return Double.parseDouble($(productPrice).shouldBe(visible).text()
                .replaceAll("[^\\d,]", "").replace(",", "."));
    }

    public String getProductArticle() {
        return $(productArticle).shouldBe(visible).text();
    }

    public int getProductCount() {
        return Integer.parseInt($(productCount).shouldBe(visible).text());
    }

    public void goToBasket() {
        $(basketLink).shouldBe(visible).click();
    }

    public void putToBasket() {
        $(addBasketBtn).shouldBe(visible).click();
    }
}
