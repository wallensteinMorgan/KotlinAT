package ui.tests.articles

import io.qameta.allure.Description
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import ui.base.BasePage
import ui.pages.MainPage
import util.TestListener

import kotlin.test.assertEquals

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