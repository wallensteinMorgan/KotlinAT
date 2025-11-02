package ui.tests.articles

import org.junit.jupiter.api.Test
import ui.base.BasePage
import ui.pages.MainPage

import kotlin.test.assertEquals

class OpeningAnArticleTest: BasePage() {
@Test
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