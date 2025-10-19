import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.by;
import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.CollectionCondition.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GoogleSearchTest {
    @BeforeAll
    public static void setUp() {
        System.setProperty("selenide.holdBrowserOpen", "true");
        Configuration.browser = "chrome";
        Configuration.timeout = 15000;
        Configuration.browserSize = "1920x1080";
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
        SelenideElement basketButton = $(By.xpath("//span[text()='Добавить в корзину']/parent::button"));
        basketButton.shouldBe(visible).click();
        // Ждём появления и исчезновения уведомления о добавлении товара в корзину
        SelenideElement basketNotification = $(By.xpath("//div[@class='notifications__item-area-content']"));
        basketNotification.should(appear);
        basketNotification.should(disappear);
        // Получаем параметры товара (наименование, цвет, размер, цену)
        String shirtNameInCard = $(By.xpath("//h1[@id='name']")).shouldBe(visible).text()
                .replaceAll("\\s*(Цвет:|Размер:).*", "").trim();
        String shirtSizeInCard = $(By.xpath("//span[text()='Размер:']/following-sibling::span")).shouldBe(visible).text();
        String shirtColorInCard = $(By.xpath("//span[text()='Цвет']/parent::dt/following-sibling::dd")).text();
        double shirtPriceInCard = Double.parseDouble($(By.xpath("//div[@class='price__regular']")).text()
                .replaceAll("[^\\d,]", "").replace(",", "."));
        String shirtArticleInCard = $(By.xpath("//dd[@data-select='product-article']")).text();
        int shirtCountInCard = Integer.parseInt($(By.xpath("//div[@class='quantity-group__number']")).text());
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
        basketButton.shouldBe(visible).click();
        //SelenideElement basketNotification = $(By.xpath("//div[@class='notifications__item-area-content']"));
        basketNotification.should(appear);
        basketNotification.should(disappear);
        // Получаем параметры товара (наименование, цвет, размер, цену)
        String jeansNameInCard = $(By.xpath("//h1[@id='name']")).shouldBe(visible).text()
                .replaceAll("\\s*(Цвет:|Размер:).*", "").trim();
        String jeansSizeInCard = $(By.xpath("//span[text()='Размер:']/following-sibling::span")).shouldBe(visible).text();
        String jeansColorInCard = $(By.xpath("//span[text()='Цвет']/parent::dt/following-sibling::dd")).shouldBe(visible).text();
        double jeansPriceInCard = Double.parseDouble($(By.xpath("//div[@class='price__regular']")).shouldBe(visible).text()
                .replaceAll("[^\\d,]", "").replace(",", "."));
        String jeansArticleInCard = $(By.xpath("//dd[@data-select='product-article']")).text();
        int jeansCountInCard = Integer.parseInt($(By.xpath("//div[@class='quantity-group__number']")).text());
        System.out.println(String.format("\nНаименование: %s\nЦвет: %s\nРазмер: %s\nЦена: %s\nАртикул: %s\nКоличество: %s", jeansNameInCard, jeansColorInCard,
                jeansSizeInCard, jeansPriceInCard, jeansArticleInCard, jeansCountInCard));


        // Для добавления третьего товара переходим в категорию Мужчинам -> Носки
        catalogButton.shouldBe(visible).click();
        // Выбираем категорию "Мужчинам"
        $(By.xpath("//nav[@class='mega-burger__sidebar']//a[@title='Мужчинам']")).shouldBe(visible).hover();
        $(By.xpath("//a[@title='Пижамы']/parent::li/preceding-sibling::li/a[@title='Носки']")).shouldBe(visible).click();
        $("a[href$='V209398684']").shouldBe(visible).click();
        $(By.xpath("//label[@data-aspect-name='3 Pack (White)']")).shouldBe(visible).click();
        $(By.xpath("//label[@data-aspect-name='M']")).shouldBe(visible).click();
        $(By.xpath("//span[text()='Добавить в корзину']/parent::button")).shouldBe(visible).click();
        $(By.xpath("//input[@name='basket_add[quantity]']")).shouldBe(exist).setValue("4");
        $(By.xpath("//div[@class='notifications__item-area-content']")).should(appear).should(disappear);
        // Получаем параметры товара (наименование, цвет, размер, цену)
        String socksNameInCard = $(By.xpath("//h1[@id='name']")).shouldBe(visible).text()
                .replaceAll("\\s*(Цвет:|Размер:).*", "").trim();
        String socksSizeInCard = $(By.xpath("//span[text()='Размер:']/following-sibling::span")).shouldBe(visible).text();
        String socksColorInCard = $(By.xpath("//span[text()='Цвет:']/following-sibling::span")).shouldBe(visible).text();
        double socksPriceInCard = Double.parseDouble($(By.xpath("//div[@class='price__regular']")).shouldBe(visible).text()
                .replaceAll("[^\\d,]", "").replace(",", "."));
        String socksArticleInCard = $(By.xpath("//dd[@data-select='product-article']")).text();
        int socksCountInCard = Integer.parseInt($(By.xpath("//div[@class='quantity-group__number']")).text());
        System.out.println(String.format("\nНаименование: %s\nЦвет: %s\nРазмер: %s\nЦена: %s\nАртикул: %s\nКоличество: %s", socksNameInCard, socksColorInCard,
                socksSizeInCard, socksPriceInCard, socksArticleInCard, socksCountInCard));


        // Выполняем проверки в Корзине
        $(By.xpath("//a[@data-selector='basket-desktop']")).shouldBe(visible).click();

        // Проверяем товар 1 (футболка)
        SelenideElement shirtInBasket = $(By.xpath(String.format("(//a[contains(@href, '#V%s')])[1]", shirtArticleInCard)));
        String shirtNameInBasket = shirtInBasket.shouldBe(visible).find(By.xpath("./following-sibling::div/a")).text();
        String shirtSizeInBasket = shirtInBasket.shouldBe(visible).find(By.xpath("./following-sibling::div/ul/li[1]"))
                .text().replaceAll("Размер:\\s*", "").trim();
        String shirtColorInBasket = shirtInBasket.shouldBe(visible).find(By.xpath("./following-sibling::div/ul/li[2]"))
                .text().replaceAll("Цвет:\\s*", "").trim();
        double shirtPriceInBasket = Double.parseDouble(shirtInBasket.shouldBe(visible).find(By.xpath("./following-sibling::div[2]/div"))
                .text().replaceAll("[^\\d,]", "").replace(",", "."));
        int shirtCountInBasket = Integer.parseInt(shirtInBasket.shouldBe(visible)
                .find(By.xpath("./following-sibling::div[4]//div[@class='quantity-group__number']")).text());
        System.out.println("\nВ корзине:\n" + shirtNameInBasket + "\n" + shirtSizeInBasket + "\n" + shirtColorInBasket +
                "\n" + shirtPriceInBasket + "\n" + shirtCountInBasket);

        assertEquals(shirtNameInCard, shirtNameInBasket);
        assertEquals(shirtSizeInCard, shirtSizeInBasket);
        assertEquals(shirtColorInCard, shirtColorInBasket);
        assertEquals(shirtPriceInCard, shirtPriceInBasket);
        assertEquals(shirtCountInCard, shirtCountInBasket);

        // Проверяем товар 2 (джинсы)
        SelenideElement jeansInBasket = $(By.xpath(String.format("(//a[contains(@href, '#V%s')])[1]", jeansArticleInCard)));
        String jeansNameInBasket = jeansInBasket.shouldBe(visible).find(By.xpath("./following-sibling::div/a")).text();
        String jeansSizeInBasket = jeansInBasket.shouldBe(visible).find(By.xpath("./following-sibling::div/ul/li[1]"))
                .text().replaceAll("Размер:\\s*", "").trim();
        String jeansColorInBasket = jeansInBasket.shouldBe(visible).find(By.xpath("./following-sibling::div/ul/li[2]"))
                .text().replaceAll("Цвет:\\s*", "").trim();
        double jeansPriceInBasket = Double.parseDouble(jeansInBasket.shouldBe(visible).find(By.xpath("./following-sibling::div[2]/div"))
                .text().replaceAll("[^\\d,]", "").replace(",", "."));
        int jeansCountInBasket = Integer.parseInt(jeansInBasket.shouldBe(visible)
                .find(By.xpath("./following-sibling::div[4]//div[@class='quantity-group__number']")).text());
        System.out.println("\nВ корзине:\n" + jeansNameInBasket + "\n" + jeansSizeInBasket + "\n" + jeansColorInBasket +
                "\n" + jeansPriceInBasket + "\n" + jeansCountInBasket);

        assertEquals(jeansNameInCard, jeansNameInBasket);
        assertEquals(jeansSizeInCard, jeansSizeInBasket);
        assertEquals(jeansColorInCard, jeansColorInBasket);
        assertEquals(jeansPriceInCard, jeansPriceInBasket);
        assertEquals(jeansCountInCard, jeansCountInBasket);

        // Проверяем товар 3 (носки)
        SelenideElement socksInBasket = $(By.xpath(String.format("(//a[contains(@href, '#V%s')])[1]", socksArticleInCard)));
        String socksNameInBasket = socksInBasket.shouldBe(visible).find(By.xpath("./following-sibling::div/a")).text();
        String socksSizeInBasket = socksInBasket.shouldBe(visible).find(By.xpath("./following-sibling::div/ul/li[1]"))
                .text().replaceAll("Размер:\\s*", "").trim();
        String socksColorInBasket = socksInBasket.shouldBe(visible).find(By.xpath("./following-sibling::div/ul/li[2]"))
                .text().replaceAll("Цвет:\\s*", "").trim();
        double socksPriceInBasket = Double.parseDouble(socksInBasket.shouldBe(visible).find(By.xpath("./following-sibling::div[2]/div"))
                .text().replaceAll("[^\\d,]", "").replace(",", "."));
        int socksCountInBasket = Integer.parseInt(socksInBasket.shouldBe(visible)
                .find(By.xpath("./following-sibling::div[4]//div[@class='quantity-group__number']")).text());
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
        double orderPriceInSidebar = Double.parseDouble($(By.xpath("//div[@class='basket-summary__price js-price']")).shouldBe(visible)
                .text().replaceAll("[^\\d,]", "").replace(",", "."));
        int sidebarCount = Integer.parseInt($(By.xpath("//span[@class='js-more']")).shouldBe(visible)
                .text().split("\\s+")[0]);
        $(By.xpath("//button[@class='basket-summary__btn-more']")).shouldBe(visible).click();
        double productsPrice = Double.parseDouble($(By.xpath("//span[contains(text(), 'Товары')]/following-sibling::span"))
                .text().replaceAll("[^\\d,]", "").replace(",", "."));
        double deliveryPrice = Double.parseDouble($(By.xpath("//span[contains(text(), 'Доставка')]/following-sibling::span"))
                .text().replaceAll("[^\\d,]", "").replace(",", "."));

        System.out.println("Количество товаров в сайдбаре: " + sidebarCount);
        System.out.println("Общая сумма заказа с доставкой: " + orderPriceInSidebar);
        System.out.println("Сумма товаров в корзине: " + productsPrice);
        System.out.println("Сумма доставки: " + deliveryPrice);

        assertEquals(expectedOrderCount, sidebarCount);
        assertEquals(expectedOrderPrice, productsPrice);
        assertEquals(orderPriceInSidebar, productsPrice + deliveryPrice);

    }
}
























