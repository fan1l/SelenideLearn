import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.BasketPage;
import pages.MainPage;
import pages.ProductPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasketPageTest {
    private static MainPage mainPage;
    private static ProductPage productPage;
    private static BasketPage basketPage;

    @BeforeAll
    public static void setUp() {
        System.setProperty("selenide.holdBrowserOpen", "true");
        Configuration.browser = "chrome";
        Configuration.timeout = 30000;
        Configuration.pageLoadTimeout = 60000;
        Configuration.browserSize = "1920x1080";
        mainPage = new MainPage();
        productPage = new ProductPage();
        basketPage = new BasketPage();
    }

    @Test
    public void buySomeProductsAndCheck() {
        open("https://navisale.ru");

        // Подтверждаем уведомление о куки
        mainPage.acceptCookies();
        // Переходим в Каталог
        mainPage.goToCatalog();
        // Выбираем категорию "Мужчинам"
        mainPage.goToCategoryInCatalog("Мужчинам");
        // Выбираем подкатегорию "Футболки и майки"
        mainPage.goToSubcategoryInSideBar("Футболки и майки");
        // Открываем карточку товара с артикулом 206488989
        mainPage.goToProductCard("206488989");
        // Выбираем размер
        productPage.selectProductSize("M");
        // Добавляем в корзину
        productPage.putToBasket();
        // Ждём появления и исчезновения уведомления о добавлении товара в корзину
        productPage.waitBasketNotification();

        // Получаем параметры (наименование, цвет, размер, цену) из карточки товара
        String shirtNameInCard = productPage.getProductName();
        String shirtSizeInCard = productPage.getProductSize();;
        String shirtColorInCard = productPage.getProductColor();
        double shirtPriceInCard = productPage.getProductPrice();
        String shirtArticleInCard = productPage.getProductArticle();
        int shirtCountInCard = productPage.getProductCount();

        System.out.printf(
                "%nНаименование: %s%nЦвет: %s%nРазмер: %s%nЦена: %s%nАртикул: %s%nКоличество: %s%n",
                shirtNameInCard, shirtColorInCard, shirtSizeInCard, shirtPriceInCard, shirtArticleInCard, shirtCountInCard
        );


        // Для добавления второго товара переходим в Каталог, выбираем категорию "Женщины"
        mainPage.goToCatalog();
        mainPage.showSubcategoriesInCatalog("Мужчинам");
        mainPage.showSubcategoriesInCatalog("Женщинам");
        // Переходим в Каталоге в подкатегорию Джинсы категории Женщинам
        mainPage.goToSubcategoryInCatalog("Джинсы");
        // Выбираем Джинсы
        mainPage.expandFilterTypeInSidebar("Бренд");
        mainPage.selectFilterNameInSidebar("Calvin Klein");
        mainPage.goToProductCard("185476290");
        // Выбираем размер Джинсов
        productPage.selectProductSize("25");
        // Добавляем в корзину
        productPage.putToBasket();
        productPage.waitBasketNotification();

        // Получаем параметры товара (наименование, цвет, размер, цену) из карточки товара
        String jeansNameInCard = productPage.getProductName();
        String jeansSizeInCard = productPage.getProductSize();
        String jeansColorInCard = productPage.getProductColor();
        double jeansPriceInCard = productPage.getProductPrice();
        String jeansArticleInCard = productPage.getProductArticle();
        int jeansCountInCard = productPage.getProductCount();

        System.out.printf(
                "%nНаименование: %s%nЦвет: %s%nРазмер: %s%nЦена: %s%nАртикул: %s%nКоличество: %s%n",
                jeansNameInCard, jeansColorInCard, jeansSizeInCard, jeansPriceInCard, jeansArticleInCard, jeansCountInCard
        );

        // Для добавления третьего товара переходим в категорию Мужчинам -> Носки
        mainPage.goToCatalog();
        // Выбираем категорию "Мужчинам"
        mainPage.showSubcategoriesInCatalog("Мужчинам");
        mainPage.goToSubcategoryInCatalog("Носки");
        mainPage.goToProductCard("180428921");
        productPage.selectProductColor("2 Pack (Medium Gray)");
        productPage.selectProductSize("L");
        productPage.putToBasket();
        productPage.setProductQuantity(4);
        productPage.waitBasketNotification();

        // Получаем параметры товара (наименование, цвет, размер, цену) из карточки товара
        String socksNameInCard = productPage.getProductName();
        String socksSizeInCard = productPage.getProductSize();
        String socksColorInCard = productPage.getProductColor();
        double socksPriceInCard = productPage.getProductPrice();
        String socksArticleInCard = productPage.getProductArticle();
        int socksCountInCard = productPage.getProductCount();

        System.out.printf(
                "%nНаименование: %s%nЦвет: %s%nРазмер: %s%nЦена: %s%nАртикул: %s%nКоличество: %s%n",
                socksNameInCard, socksColorInCard, socksSizeInCard, socksPriceInCard, socksArticleInCard, socksCountInCard
        );

        // Переходим в Корзину и выполняем проверки
        productPage.goToBasket();

        // Получаем параметры товара 1 (футболка) в корзине и выполняем проверки
        String shirtNameInBasket = basketPage.getProductName(shirtArticleInCard);
        String shirtSizeInBasket = basketPage.getProductSize(shirtArticleInCard);
        String shirtColorInBasket = basketPage.getProductColor(shirtArticleInCard);
        double shirtPriceInBasket = basketPage.getProductPrice(shirtArticleInCard);
        int shirtCountInBasket = basketPage.getProductCount(shirtArticleInCard);

        System.out.printf(
                "%nВ корзине:%n%s%n%s%n%s%n%s%n%s%n",
                shirtNameInBasket, shirtSizeInBasket, shirtColorInBasket,
                shirtPriceInBasket, shirtCountInBasket
        );

        assertAll(
                () -> assertEquals(shirtNameInCard, shirtNameInBasket),
                () -> assertEquals(shirtSizeInCard, shirtSizeInBasket),
                () -> assertEquals(shirtColorInCard, shirtColorInBasket),
                () -> assertEquals(shirtPriceInCard, shirtPriceInBasket, 0.01),
                () -> assertEquals(shirtCountInCard, shirtCountInBasket)
        );

        // Получаем параметры товара 2 (джинсы) в корзине и выполняем проверки
        String jeansNameInBasket = basketPage.getProductName(jeansArticleInCard);
        String jeansSizeInBasket = basketPage.getProductSize(jeansArticleInCard);
        String jeansColorInBasket = basketPage.getProductColor(jeansArticleInCard);
        double jeansPriceInBasket = basketPage.getProductPrice(jeansArticleInCard);
        int jeansCountInBasket = basketPage.getProductCount(jeansArticleInCard);

        System.out.printf(
                "%nВ корзине:%n%s%n%s%n%s%n%s%n%s%n",
                jeansNameInBasket, jeansSizeInBasket, jeansColorInBasket,
                jeansPriceInBasket, jeansCountInBasket
        );

        assertAll(
                () -> assertEquals(jeansNameInCard, jeansNameInBasket),
                () -> assertEquals(jeansSizeInCard, jeansSizeInBasket),
                () -> assertEquals(jeansColorInCard, jeansColorInBasket),
                () -> assertEquals(jeansPriceInCard, jeansPriceInBasket, 0.01),
                () -> assertEquals(jeansCountInCard, jeansCountInBasket)
        );

        // Получаем параметры товара 3 (носки) в корзине и выполняем проверки
        String socksNameInBasket = basketPage.getProductName(socksArticleInCard);
        String socksSizeInBasket = basketPage.getProductSize(socksArticleInCard);
        String socksColorInBasket = basketPage.getProductColor(socksArticleInCard);
        double socksPriceInBasket = basketPage.getProductPrice(socksArticleInCard);
        int socksCountInBasket = basketPage.getProductCount(socksArticleInCard);

        System.out.printf(
                "%nВ корзине:%n%s%n%s%n%s%n%s%n%s%n",
                socksNameInBasket, socksSizeInBasket, socksColorInBasket,
                socksPriceInBasket, socksCountInBasket
        );

        assertAll(
                () -> assertEquals(socksNameInCard, socksNameInBasket),
                () -> assertEquals(socksSizeInCard, socksSizeInBasket),
                () -> assertEquals(socksColorInCard, socksColorInBasket),
                () -> assertEquals(socksPriceInCard, socksPriceInBasket, 0.01),
                () -> assertEquals(socksCountInCard, socksCountInBasket)
        );

        // Проверяем общее количество и сумму заказа в Корзине.
        // Вычисляем количество и сумму товаров из списка в Корзине
        int expectedOrderCount = shirtCountInBasket + jeansCountInBasket + socksCountInBasket; // Общее кол-во товаров
        double expectedOrderPrice = shirtPriceInBasket * shirtCountInBasket + jeansPriceInBasket * jeansCountInBasket +
                socksPriceInBasket * socksCountInBasket; // Общая сумма товаров (без доставки)
        System.out.println("Количество товаров: " + expectedOrderCount);
        System.out.println("Общая сумма товаров (без доставки): " + expectedOrderPrice);

        // Получаем данные заказа из сайдбара (количество, общая сумма, доставка, сумма с доставкой)
        double priceWithDeliveryInSidebar = basketPage.getOrderPriceInSidebar();
        int countInSidebar = basketPage.getProductsCountInSidebar();
        basketPage.showProductsAndDeliveryPrice();
        double productsPriceInSidebar = basketPage.getProductsPriceInSidebar();
        double deliveryPrice = basketPage.getDeliveryPriceInSidebar();

        System.out.println("Количество товаров в сайдбаре: " + countInSidebar);
        System.out.println("Общая сумма заказа с доставкой: " + priceWithDeliveryInSidebar);
        System.out.println("Сумма товаров в корзине: " + productsPriceInSidebar);
        System.out.println("Сумма доставки: " + deliveryPrice);

        assertAll(
                () -> assertEquals(expectedOrderCount, countInSidebar),
                () -> assertEquals(expectedOrderPrice, productsPriceInSidebar, 0.01),
                () -> assertEquals(priceWithDeliveryInSidebar, productsPriceInSidebar + deliveryPrice, 0.01)
        );

    }
}




