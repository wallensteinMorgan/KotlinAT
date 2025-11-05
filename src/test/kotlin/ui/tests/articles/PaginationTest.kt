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
        MainPage().openPage().scrollToTheEndOfThePage()
        val firstPageTitle = MainPage().getAllArticleTitles()
        MainPage().openNextPage()
        val secondPageTitle = MainPage().getAllArticleTitles()

        assertTrue(firstPageTitle != secondPageTitle, "Статьи совпадают")
        assertTrue(MainPage().currentUrlContains("page/2"), "URL не содержит параметр page/2")
    }
}