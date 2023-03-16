package ru.litres.tests.tests;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.litres.tests.customtags.JIRAKEY;
import ru.litres.tests.data.MainMenuButtons;
import java.util.stream.Stream;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class SearchTests {
    static Stream <Arguments> navigationButtonsCheck() {
       return Stream.of(
               Arguments.of(MainMenuButtons.AUDIOBOOKS.toString(),"Что послушать?"),
               Arguments.of(MainMenuButtons.NEW_BOOKS.toString(),"Новинки книг"),
               Arguments.of(MainMenuButtons.POPULAR.toString(),"Лучшие книги"),
               Arguments.of(MainMenuButtons.SELF_MAKE.toString(),"Самиздат"),
               Arguments.of(MainMenuButtons.WHATS_READ.toString(),"Что почитать?"),
               Arguments.of(MainMenuButtons.SELECTIONS.toString(),"Подборки")
       );
    }

    @BeforeEach
    public void baseConfiguration(){
        Configuration.browserSize = "1920x1080";
        Configuration.browser = "chrome";
    }

    @Tag("Critical")
    @JIRAKEY("LTS-45641")
    @ParameterizedTest(name = "Поиск книг по жанру \"{0}\" должен возвращать не менее 12 карточек товаров для разрешения экрана 1920х1080")
    @ValueSource(strings = {"Классика","Фантастика","Детектив"})

    public void searchResultsShouldHaveThan12Cards(String genre){
        open("https://www.litres.ru/");
        $("input[data-test-id=header__search-input--desktop]").setValue(genre);
        $("button[type=submit]").click();
        $("h1.SearchTitle-module__title_YEbWG").shouldHave(text("Результаты поиска «" + genre + "»"));
        $$(".ArtV2-module__container_1tK4b").shouldHave(CollectionCondition.sizeGreaterThanOrEqual(12));
    }

    @Tag("Minor")
    @JIRAKEY("LTS-45649")
    @ParameterizedTest(name = "Жанр в карточке первого товара из поисковой выдачи по ключу \"{0}\" должен содержать текст \"{1}\"")
    @CsvSource(value = {"Биография|Современные детективы","История|Биографии и мемуары, Русская классика"},delimiter = '|')

    public void genreOnGoodCardShouldHaveSearchGenre(String genre, String genreOnCard){
        open("https://www.litres.ru/");
        $("input[data-test-id=header__search-input--desktop]").setValue(genre);
        $("button[type=submit]").click();
        $$(".ArtV2-module__container_1tK4b").first().click();
        $(".biblio_book_info ul li:nth-child(2)").shouldHave(text(genreOnCard));
    }

    @Tag("Critical")
    @JIRAKEY("LTS-45650")
    @ParameterizedTest(name = "Переход на страницу \"{0}\" с главной страницы при клике на элемент с текстом \"{0}\" из главного меню")
    @MethodSource("navigationButtonsCheck")

    void navigationButtonsCheck(String namePage, String valueInPageTitle){
        open("https://www.litres.ru/");
        $(".LowerMenu-module__wrapper_3onkJ").$(byText(namePage)).click();
        $("h1").shouldHave(text(valueInPageTitle));
    }


}
