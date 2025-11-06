package ui.tests.search

import io.qameta.allure.Description
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import ui.base.BasePage
import ui.pages.MainPage


const val TEXT_FOR_SEARCH = "iPhone 15 Pro"
const val SEARCH_STRING  = "Чем iPhone 13 отличается от iPhone 12"
const val KEY_WORD = "iphone-13"
@Tag("UI")
class SearchTest : BasePage() {
    @Test
    @Description("Поиск по запросу")
    fun checkHref() {
        val href = MainPage().openPage().searchFor(SEARCH_STRING)
            .getHrefFirstArticle() ?: throw AssertionError("href не найден")

        assertTrue(href.contains(KEY_WORD))
    }

    @Test
    @Description("Поиск по запросу и проверка, что статьи, полученные в результате запроса имеют название, автора и дату публикации")
    fun searchArticles() {
        val result = MainPage().openPage().searchFor(TEXT_FOR_SEARCH)
            .getArticlesTitleDateAuthor()

        assertTrue(result.isNotEmpty(), "Результаты поиска пустые")
        result.forEachIndexed { index, article ->
            assertTrue(article.title.isNotBlank(), "Заголовок статьи №${index + 1} пустой")
            assertTrue(article.author.isNotBlank(), "Автор статьи №${index + 1} пустой")
            assertTrue(article.date.isNotBlank(), "Дата статьи №${index + 1} пустая")
        }
    }
}