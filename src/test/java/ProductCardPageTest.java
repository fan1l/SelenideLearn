import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pages.ProductPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;


public class ProductCardPageTest {
    private static ProductPage productPage;

    @BeforeAll
    public static void setUp() {
        System.setProperty("selenide.holdBrowserOpen", "true");
        Configuration.browser = "chrome";
        Configuration.timeout = 30000;
        Configuration.pageLoadTimeout = 60000;
        Configuration.browserSize = "1920x1080";
        productPage = new ProductPage();
    }

    @Test
    public void checkProductParameters() {
        open("https://navisale.ru/product/new-balance-athletics-premium-logo-relaxed-tee-A57060382#V151389264");
        String productName = productPage.getFullName();

        // Извлекаем параметры из названия товара
        String typeFromName = productPage.extractType(productName);
        String brandFromName = productPage.extractBrand(productName);
        String colorFromName = productPage.extractColor(productName);
        String sizeFromName = productPage.extractSize(productName);

        // Получаем параметры из характеристик товара
        String typeFromParameters = productPage.getTypeFromParameters();
        String brandFromParameters = productPage.getBrandFromParameters();
        String colorFromParameters = productPage.getColorFromParameters();
        String sizeFromParameters = productPage.getSizeFromParameters();

        // Выполняем проверки на равенство параметров из названия параметрам из характеристик товара
        assertAll(
                // Первый способ:
                () -> assertEquals(typeFromName, typeFromParameters),
                () -> assertEquals(brandFromName, brandFromParameters),
                () -> assertEquals(colorFromName, colorFromParameters),
                () -> assertEquals(sizeFromName, sizeFromParameters),

                // Второй способ:
                () -> assertTrue(productName.contains(typeFromParameters)),
                () -> assertTrue(productName.contains(brandFromParameters)),
                () -> assertTrue(productName.contains(colorFromParameters)),
                () -> assertTrue(productName.contains(sizeFromParameters))
        );

        productPage.printComparedName(productName);
    }
}
