package pages;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;


public class ProductCardPage {
    private final By productName = By.xpath("//h1[@id='name']");
    private final By brandNameInParameters = By.xpath("//span[text()='Бренд']/parent::dt/following-sibling::dd");
    private final By productSizeInParameters = By.xpath("//span[text()='Размер']/parent::dt/following-sibling::dd");
    private final By productTypeInParameters = By.xpath("//span[text()='Тип продукта']/parent::dt/following-sibling::dd");
    private final By productColorInParameters = By.xpath("//span[text()='Цвет']/parent::dt/following-sibling::dd");

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