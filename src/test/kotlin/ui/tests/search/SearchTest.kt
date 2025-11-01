package tests.search

import org.junit.jupiter.api.Test
import ui.base.BaseTest
import ui.pages.MainPage
import org.junit.jupiter.api.Assertions.assertTrue

const val TEXT_FOR_SEARCH = "iPhone 15 Pro"
class SearchArticlesTest : BaseTest() {
    @Test
    fun searchArticles(){
        val result = MainPage().clickOnSearch(TEXT_FOR_SEARCH)
            .getArticlesTitleDateAuthor()
        assertTrue(result.isNotEmpty(), "Результаты поиска пустые")
        result.forEachIndexed { index, article ->
            assertTrue(article.title.isNotBlank(), "Заголовок статьи №${index + 1} пустой")
            assertTrue(article.author.isNotBlank(), "Автор статьи №${index + 1} пустой")
            assertTrue(article.date.isNotBlank(), "Дата статьи №${index + 1} пустая")
        }

    }
}