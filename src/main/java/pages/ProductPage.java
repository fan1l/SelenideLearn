package pages;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class ProductPage {
    private final By productName = By.xpath("//h1[@id='name']");
    private final By productSize = By.xpath("//span[text()='Размер:']/following-sibling::span");
    //private final By productColor = By.xpath("//span[text()='Цвет']/parent::dt/following-sibling::dd");
    private final By productPrice = By.xpath("//div[@class='price__regular']");
    private final By productArticle = By.xpath("//dd[@data-select='product-article']");
    private final By productCount = By.xpath("//div[@class='quantity-group__number']");
    private final By basketLink = By.xpath("//a[@data-selector='basket-desktop']");
    private final By addBasketBtn = By.xpath("//span[text()='Добавить в корзину']/parent::button");
    private final By basketAddQuantity = By.xpath("//input[@name='basket_add[quantity]']");
    private final By basketNotification = By.xpath("//div[@class='notifications__item-area-content']");
    private static final String productSizeLabel = "//label[@data-aspect-name='%s']";
    private static final String productColorLabel = "//label[@data-aspect-name='%s']";
    private final By brandNameInParameters = By.xpath("//span[text()='Бренд']/parent::dt/following-sibling::dd");
    private final By productSizeInParameters = By.xpath("//span[text()='Размер']/parent::dt/following-sibling::dd");
    private final By productTypeInParameters = By.xpath("//span[text()='Тип продукта']/parent::dt/following-sibling::dd");
    private final By productColorInParameters = By.xpath("//span[text()='Цвет']/parent::dt/following-sibling::dd");


    public String getProductName() {
        return $(productName).shouldBe(visible).text()
                .replaceAll("\\s*(Цвет:|Размер:).*", "").trim();
    }

    public String getProductSize() {
        return $(productSize).shouldBe(visible).text();
    }

    public String getProductColor() {
        return $(productName).shouldBe(visible).text().replaceAll(".*Цвет:\\s*([^;]+).*", "$1").trim();
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

    public void selectProductSize(String productSize) {
        $(By.xpath(String.format(productSizeLabel, productSize))).shouldBe(visible).click();
        Selenide.sleep(1000);
    }

    public void selectProductColor(String productSize) {
        $(By.xpath(String.format(productColorLabel, productSize))).shouldBe(visible).click();
        Selenide.sleep(1000);
    }

    public void setProductQuantity(int quantity) {
        $(basketAddQuantity).shouldBe(enabled).setValue(String.valueOf(quantity));
    }

    public void waitBasketNotification() {
        $(basketNotification).should(appear).should(disappear);
    }

    // Получаем название товара
    public String getFullName() {
        return $(productName).shouldBe(visible).getText();
    }

    // Извлекаем тип товара из названия
    public String extractType(String productTitle) {
        return productTitle.split(" ")[0];
    }

    // Извлекаем бренд товара из названия
    public String extractBrand(String productTitle) {
        return String.format("%s %s", productTitle.split(" ")[1], productTitle.split(" ")[2]);
    }

    // Извлекаем цвет товара из названия
    public String extractColor(String productTitle) {
        return productTitle.split(" ")[8].replace(";", "");
    }

    // Извлекаем размер товара из названия
    public String extractSize(String productTitle) {
        return productTitle.split(" ")[10];
    }

    // Получаем бренд из характеристик товара
    public String getBrandFromParameters() {
        return $(brandNameInParameters).shouldBe(visible).getText();
    }

    // Получаем размер из характеристик товара
    public String getSizeFromParameters() {
        return $(productSizeInParameters).shouldBe(visible).getText();
    }

    // Получаем тип из характеристик товара
    public String getTypeFromParameters() {
        return $(productTypeInParameters).shouldBe(visible).getText();
    }

    // Получаем цвет из характеристик товара
    public String getColorFromParameters() {
        return $(productColorInParameters).shouldBe(visible).getText();
    }

    public void printComparedName(String productName) {
        System.out.println("Полное наименование:\n" + productName + "\n");
        System.out.println("Параметры из заголовка     Параметры из характеристик");
        System.out.println("               " + extractType(productName) + " = " + getTypeFromParameters());
        System.out.println("            " + extractBrand(productName) + " = " + getBrandFromParameters());
        System.out.println("                 " + extractColor(productName) + " = " + getColorFromParameters());
        System.out.println("                      " + extractSize(productName) + " = " + getSizeFromParameters());
    }
}