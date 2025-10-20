import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import pages.BasketPage;
import pages.ProductPage;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.CollectionCondition.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GoogleSearchTest {
    private static ProductPage productPage;
    private static BasketPage basketPage;

    @BeforeAll
    public static void setUp() {
        System.setProperty("selenide.holdBrowserOpen", "true");
        Configuration.browser = "chrome";
        Configuration.timeout = 15000;
        Configuration.browserSize = "1920x1080";
        productPage = new ProductPage();
        basketPage = new BasketPage();
//        Configuration.headless = true; // запуск без GUI
    }

    @Test
    @Disabled("Пока не реализовано / временно отключено для отладки")
    public void userCanSearchOnGoogle() {
        open("https://navisale.ru");
        SelenideElement element = $(By.xpath("//span[text()='Топ-модели обуви']"));
        ElementsCollection products = $$(By.xpath("//ul/div[@class='tw']//div[@class='product-mini-card__info']"));

        $( "[id=search-input]" ).setValue("nike").pressEnter();
        $(".rubrics-layout").shouldHave(visible).shouldHave(text("Nike M2K Tekno Sp 'Linen'"));
        //$(by())
        System.out.println(element.text());
        System.out.println(products.size());
        assertEquals("Топ-модели обуви", element.text());
        assertEquals(60, products.size());
        products.shouldHave(size(60));

    }

    @Test
    public void buySomeProductsAndCheck() {
        open("https://navisale.ru");
        $(By.xpath("//a[text()='Контактная информация']")).shouldBe(visible);
        // Подтверждаем уведомление о куки
        SelenideElement cookiesButton = $(By.xpath("//span[contains(text(), 'Согласен')]/parent::button"));
        if (cookiesButton.isDisplayed()) {
            cookiesButton.shouldBe(visible).click();
            cookiesButton.shouldNotBe(visible);
        }
        // Переходим в Каталог
        SelenideElement catalogButton = $(By.xpath("//a[@id='rubrics-toggle']"));
        catalogButton.shouldBe(visible).click();
        // Выбираем категорию "Мужчинам"
        SelenideElement menuLinkMans = $(By.xpath("//nav[@class='mega-burger__sidebar']//a[@title='Мужчинам']"));
        menuLinkMans.shouldBe(visible).click();
        // Выбираем подкатегорию "Футболки и майки"
        SelenideElement menuLinkMansShirts = $(By.xpath("//nav[@class='rubrics-catalog']//a[contains(text(), 'Футболки и майки')]"));
        menuLinkMansShirts.shouldBe(visible).click();
        // Открываем карточку товара с артикулом 206488989
        SelenideElement productLinkShirt = $("a[href$='V206488989']");
        productLinkShirt.shouldBe(visible).click();
        // Выбираем размер
        SelenideElement productShirtSize = $(By.xpath("//label[@data-aspect-name='M']"));
        productShirtSize.shouldBe(visible).click();
        // Добавляем в корзину
        productPage.putToBasket();
        // Ждём появления и исчезновения уведомления о добавлении товара в корзину
        SelenideElement basketNotification = $(By.xpath("//div[@class='notifications__item-area-content']"));
        basketNotification.should(appear);
        basketNotification.should(disappear);
        // Получаем параметры товара (наименование, цвет, размер, цену)
        String shirtNameInCard = productPage.getProductName();
        String shirtSizeInCard = productPage.getProductSize();;
        String shirtColorInCard = productPage.getProductColor();
        double shirtPriceInCard = productPage.getProductPrice();
        String shirtArticleInCard = productPage.getProductArticle();
        int shirtCountInCard = productPage.getProductCount();

        System.out.println(String.format("\nНаименование: %s\nЦвет: %s\nРазмер: %s\nЦена: %s\nАртикул: %s\nКоличество: %s", shirtNameInCard, shirtColorInCard,
                shirtSizeInCard, shirtPriceInCard, shirtArticleInCard, shirtCountInCard));


        // Для добавления второго товара переходим в Каталог, выбираем категорию "Женщины"
        catalogButton.shouldBe(visible).click();
        SelenideElement menuLinkWoman = $(By.xpath("//nav[@class='mega-burger__sidebar']//a[@title='Женщинам']/parent::li"));
        $(By.xpath("//nav[@class='mega-burger__sidebar']//a[@title='Мужчинам']/parent::li")).should(appear).hover();
        $("body").should(appear).hover();
        menuLinkWoman.should(appear).hover();
        // Переходим в категорию Джинсы
        SelenideElement menuLinkWomanJeans = $(By.xpath("//a[@title='Жилеты']/parent::div/preceding-sibling::div/a[@title='Джинсы']"));
        //$(By.xpath("//div[@data-idx='1']")).shouldBe(visible);
        menuLinkWomanJeans.shouldBe(visible).click();
        // Выбираем Джинсы
        $(By.xpath("(//div[text()='Бренд']/following-sibling::button)[1]")).shouldBe(visible).click();
        $(By.xpath("//input[@name='Calvin Klein[]']/parent::label")).shouldBe(visible).shouldNotBe(checked).click();
        SelenideElement productWomanJeans = $("a[href$='V185476290']");
        productWomanJeans.shouldBe(visible).click();
        // Выбираем размер Джинсов
        SelenideElement productJeansSize = $(By.xpath("//label[@data-aspect-name='25']"));
        productJeansSize.shouldBe(visible).click();
        // Добавляем в корзину
        //SelenideElement basketButton = $(By.xpath("//span[text()='Добавить в корзину']/parent::button"));
        productPage.putToBasket();
        //SelenideElement basketNotification = $(By.xpath("//div[@class='notifications__item-area-content']"));
        basketNotification.should(appear);
        basketNotification.should(disappear);
        // Получаем параметры товара (наименование, цвет, размер, цену)
        String jeansNameInCard = productPage.getProductName();
        String jeansSizeInCard = productPage.getProductSize();
        String jeansColorInCard = productPage.getProductColor();
        double jeansPriceInCard = productPage.getProductPrice();
        String jeansArticleInCard = productPage.getProductArticle();
        int jeansCountInCard = productPage.getProductCount();

        System.out.println(String.format("\nНаименование: %s\nЦвет: %s\nРазмер: %s\nЦена: %s\nАртикул: %s\nКоличество: %s", jeansNameInCard, jeansColorInCard,
                jeansSizeInCard, jeansPriceInCard, jeansArticleInCard, jeansCountInCard));


        // Для добавления третьего товара переходим в категорию Мужчинам -> Носки
        catalogButton.shouldBe(visible).click();
        // Выбираем категорию "Мужчинам"
        $(By.xpath("//nav[@class='mega-burger__sidebar']//a[@title='Мужчинам']")).shouldBe(visible).hover();
        $(By.xpath("//a[@title='Пижамы']/parent::li/preceding-sibling::li/a[@title='Носки']")).shouldBe(visible).click();
        $("a[href$='V209398695']").shouldBe(visible).click();
        $(By.xpath("//label[@data-aspect-name='3 Pack (Gray)']")).shouldBe(visible).click();
        $(By.xpath("//label[@data-aspect-name='M']")).shouldBe(visible).click();
        productPage.putToBasket();
        $(By.xpath("//input[@name='basket_add[quantity]']")).shouldBe(enabled).setValue("4");
        $(By.xpath("//div[@class='notifications__item-area-content']")).should(appear).should(disappear);
        // Получаем параметры товара (наименование, цвет, размер, цену)
        String socksNameInCard = productPage.getProductName();
        String socksSizeInCard = productPage.getProductSize();
        String socksColorInCard = productPage.getProductColor();
        double socksPriceInCard = productPage.getProductPrice();
        String socksArticleInCard = productPage.getProductArticle();
        int socksCountInCard = productPage.getProductCount();

        System.out.println(String.format("\nНаименование: %s\nЦвет: %s\nРазмер: %s\nЦена: %s\nАртикул: %s\nКоличество: %s", socksNameInCard, socksColorInCard,
                socksSizeInCard, socksPriceInCard, socksArticleInCard, socksCountInCard));


        // Выполняем проверки в Корзине
        productPage.goToBasket();

        // Проверяем товар 1 (футболка)
 //       SelenideElement shirtInBasket = $(By.xpath(String.format("(//a[contains(@href, '#V%s')])[1]", shirtArticleInCard)));
        String shirtNameInBasket = basketPage.getProductName(shirtArticleInCard);
        String shirtSizeInBasket = basketPage.getProductSize(shirtArticleInCard);
        String shirtColorInBasket = basketPage.getProductColor(shirtArticleInCard);
        double shirtPriceInBasket = basketPage.getProductPrice(shirtArticleInCard);
        int shirtCountInBasket = basketPage.getProductCount(shirtArticleInCard);

        System.out.println("\nВ корзине:\n" + shirtNameInBasket + "\n" + shirtSizeInBasket + "\n" + shirtColorInBasket +
                "\n" + shirtPriceInBasket + "\n" + shirtCountInBasket);

        assertEquals(shirtNameInCard, shirtNameInBasket);
        assertEquals(shirtSizeInCard, shirtSizeInBasket);
        assertEquals(shirtColorInCard, shirtColorInBasket);
        assertEquals(shirtPriceInCard, shirtPriceInBasket);
        assertEquals(shirtCountInCard, shirtCountInBasket);

        // Проверяем товар 2 (джинсы)
//        SelenideElement jeansInBasket = $(By.xpath(String.format("(//a[contains(@href, '#V%s')])[1]", jeansArticleInCard)));
        String jeansNameInBasket = basketPage.getProductName(jeansArticleInCard);
        String jeansSizeInBasket = basketPage.getProductSize(jeansArticleInCard);
        String jeansColorInBasket = basketPage.getProductColor(jeansArticleInCard);
        double jeansPriceInBasket = basketPage.getProductPrice(jeansArticleInCard);
        int jeansCountInBasket = basketPage.getProductCount(jeansArticleInCard);

        System.out.println("\nВ корзине:\n" + jeansNameInBasket + "\n" + jeansSizeInBasket + "\n" + jeansColorInBasket +
                "\n" + jeansPriceInBasket + "\n" + jeansCountInBasket);

        assertEquals(jeansNameInCard, jeansNameInBasket);
        assertEquals(jeansSizeInCard, jeansSizeInBasket);
        assertEquals(jeansColorInCard, jeansColorInBasket);
        assertEquals(jeansPriceInCard, jeansPriceInBasket);
        assertEquals(jeansCountInCard, jeansCountInBasket);

        // Проверяем товар 3 (носки)
//        SelenideElement socksInBasket = $(By.xpath(String.format("(//a[contains(@href, '#V%s')])[1]", socksArticleInCard)));
        String socksNameInBasket = basketPage.getProductName(socksArticleInCard);
        String socksSizeInBasket = basketPage.getProductSize(socksArticleInCard);
        String socksColorInBasket = basketPage.getProductColor(socksArticleInCard);
        double socksPriceInBasket = basketPage.getProductPrice(socksArticleInCard);
        int socksCountInBasket = basketPage.getProductCount(socksArticleInCard);

        System.out.println("\nВ корзине:\n" + socksNameInBasket + "\n" + socksSizeInBasket + "\n" + socksColorInBasket +
                "\n" + socksPriceInBasket + "\n" + socksCountInBasket);

        assertEquals(socksNameInCard, socksNameInBasket);
        assertEquals(socksSizeInCard, socksSizeInBasket);
        assertEquals(socksColorInCard, socksColorInBasket);
        assertEquals(socksPriceInCard, socksPriceInBasket);
        assertEquals(socksCountInCard, socksCountInBasket);


        // Проверяем общее количество и сумму заказа в Корзине.
        // Вычисляем количество и сумму товаров из списка в Корзине
        int expectedOrderCount = shirtCountInBasket + jeansCountInBasket + socksCountInBasket;
        double expectedOrderPrice = shirtPriceInBasket * shirtCountInBasket + jeansPriceInBasket * jeansCountInBasket +
                socksPriceInBasket * socksCountInBasket;
        System.out.println("Количество товаров: " + expectedOrderCount);
        System.out.println("Общая сумма товаров: " + expectedOrderPrice);

        // Получаем данные заказа из сайдбара (количество, общая сумма, доставка, сумма с доставкой)
        double orderPriceInSidebar = basketPage.getOrderPriceInSidebar();
        int sidebarCount = basketPage.getProductsCountInSidebar();
        basketPage.showProductsAndDeliveryPrice();
        double productsPrice = basketPage.getProductsPriceInSidebar();
        double deliveryPrice = basketPage.getDeliveryPriceInSidebar();

        System.out.println("Количество товаров в сайдбаре: " + sidebarCount);
        System.out.println("Общая сумма заказа с доставкой: " + orderPriceInSidebar);
        System.out.println("Сумма товаров в корзине: " + productsPrice);
        System.out.println("Сумма доставки: " + deliveryPrice);

        assertEquals(expectedOrderCount, sidebarCount);
        assertEquals(expectedOrderPrice, productsPrice);
        assertEquals(orderPriceInSidebar, productsPrice + deliveryPrice);

    }
}
























