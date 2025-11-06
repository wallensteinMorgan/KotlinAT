package ui.tests.articles

import io.qameta.allure.Description
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import ui.base.BasePage
import ui.pages.MainPage


import kotlin.test.assertEquals
@Tag("UI")
class OpeningAnArticleTest: BasePage() {
@Test
@Description("Открывается первая статья на главной странице и проводится сравнение заголовков")
fun openingAnArticle() {
    val mainPage = MainPage().openPage()
    val expectedTitle = mainPage.getFirstArticleTitle()
    val articlePage = mainPage.openFirstArticle()
    val actualTitle = articlePage.getArticleTitle()


    assertEquals(expectedTitle, actualTitle, "Заголовок статьи должен совпадать")

    articlePage.assertArticleHasContent()
    articlePage.assertArticleHasDate()
    articlePage.assertAuthorIfPresent()
}
}