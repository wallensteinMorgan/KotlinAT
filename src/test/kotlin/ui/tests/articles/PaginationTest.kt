package ui.tests.articles

import io.qameta.allure.Description
import org.junit.jupiter.api.Test
import ui.base.BasePage
import ui.pages.MainPage
import kotlin.test.assertTrue

class PaginationTest : BasePage(){
    @Test
    @Description("Проверка пагинации и что на второй странице статьи отличаются от первой")
    fun paginationTest(){
        val mainPage  = MainPage().openPage()
        mainPage.scrollToTheEndOfThePage()
        val firstPageTitle = mainPage.getAllArticleTitles()
        mainPage.openNextPage()
        val secondPageTitle = mainPage.getAllArticleTitles()

        assertTrue(firstPageTitle != secondPageTitle, "Статьи совпадают")
        assertTrue(MainPage().currentUrlContains("page/2"), "URL не содержит параметр page/2")
    }
}