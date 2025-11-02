package ui.pages

import com.codeborne.selenide.CollectionCondition.sizeGreaterThan
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.ElementsCollection
import com.codeborne.selenide.Selectors.byXpath
import com.codeborne.selenide.Selenide.*
import ui.base.BasePage
import ui.data.AppData.Locators.ARTICLE_TITLE
import ui.data.AppData.Locators.IPHONE_17_MENU_ITEM
import ui.data.AppData.Locators.PAGINATION_ELEMENT

import ui.data.AppData.Locators.SEARCH_INPUT
import ui.data.AppData.Urls.BASE_URL
/**
 * Главная страница appleinsider.ru
 */

class MainPage : BasePage(){

    private val searchElement  = `$`(byXpath(SEARCH_INPUT))
    private val iphone17Menu  = `$`(byXpath( IPHONE_17_MENU_ITEM))
    private val articleCards : ElementsCollection = `$$`(byXpath(ARTICLE_TITLE))
    private val paginationElement = `$`(byXpath(PAGINATION_ELEMENT))

    /**
     * Открываем главную страницу
     */
    fun openPage() : MainPage {
        open(BASE_URL)
        searchElement.shouldBe(visible)
        iphone17Menu.shouldBe(visible)
        handleModals()
        return this
    }

    /**
     * Выполняется поиск на сайте в форме( поисковая строка) и нажимается энтер
     */
    fun searchFor  (searchString : String ): SearchResultsPage {
        searchElement.setValue(searchString).pressEnter()
        return SearchResultsPage()
    }

    /**
     * Нажимаем в Навигационном меню на категорию Айфон 17
     */
    fun openIphone17Category() : Iphone17Page {
         iphone17Menu.shouldBe(visible).click()
        return  Iphone17Page()
    }
    fun getFirstArticleTitle(): String {
        articleCards.shouldHave(sizeGreaterThan(0))
       return articleCards.first().shouldBe(visible).text()
    }
    fun openFirstArticle() : ArticlePage {
        articleCards.first().shouldBe(visible).click()
        return ArticlePage()
    }

    fun scrollToTheEndOfThePage() : MainPage {
        paginationElement.scrollTo()
        return this
    }
    fun openNextPage(): MainPage {
        paginationElement.shouldBe(visible).click()
        return this
    }
    fun getAllArticleTitles() : List<String> {
        articleCards.shouldHave(sizeGreaterThan(0))
        return articleCards.map { it.text() }
    }
    fun currentUrlContains(param: String) : Boolean {
        return webdriver().driver().url().contains(param)
    }


}
