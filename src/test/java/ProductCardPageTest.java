import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pages.ProductCardPage;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ProductCardPageTest {
    private final ProductCardPage productCardPage = new ProductCardPage();

    @BeforeAll
    public static void setUp() {
        System.setProperty("selenide.holdBrowserOpen", "true");
        Configuration.browser = "chrome";
        Configuration.timeout = 15000;
        Configuration.browserSize = "1920x1080";
        open("https://navisale.ru/product/new-balance-athletics-premium-logo-relaxed-tee-A57060382#V151389264");
    }

    @Test
    public void checkProductParameters() {
        String productName = productCardPage.getName();
        assertEquals(productCardPage.extractType(productName), productCardPage.getTypeFromParameters());
        assertEquals(productCardPage.extractBrand(productName), productCardPage.getBrandFromParameters());
        assertEquals(productCardPage.extractColor(productName), productCardPage.getColorFromParameters());
        assertEquals(productCardPage.extractSize(productName), productCardPage.getSizeFromParameters());

        System.out.println(productName);
        System.out.println(productCardPage.extractType(productName));
        System.out.println(productCardPage.extractBrand(productName));
        System.out.println(productCardPage.extractColor(productName));
        System.out.println(productCardPage.extractSize(productName));
        System.out.println("----------------------");
        System.out.println(productCardPage.getBrandFromParameters());
        System.out.println(productCardPage.getSizeFromParameters());
        System.out.println(productCardPage.getTypeFromParameters());
        System.out.println(productCardPage.getColorFromParameters());
    }
}
