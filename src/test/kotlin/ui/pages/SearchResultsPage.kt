package ui.pages

import com.codeborne.selenide.CollectionCondition.sizeGreaterThan
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.ElementsCollection
import com.codeborne.selenide.Selectors.byXpath
import com.codeborne.selenide.Selenide
import ui.data.AppData.Locators.ARTICLE_AUTHOR
import ui.data.AppData.Locators.ARTICLE_DATE
import ui.data.AppData.Locators.ARTICLE_TITLE
import ui.data.AppData.Locators.ARTICLE_TITLE_LINKS

data class ArticleData(val title: String, val author: String, val date: String)

class SearchResultsPage {
    private val articleTail : ElementsCollection = Selenide.`$$`(byXpath(ARTICLE_TITLE_LINKS))
    private val articleCard : ElementsCollection = Selenide.`$$`(byXpath(ARTICLE_TITLE))
    /**]
     * Возвращает href из первой статьи
     */
    fun getHrefFirstArticle(): String? {
       return articleTail.first().getAttribute("href")
    }
    fun getArticlesTitleDateAuthor() : List<ArticleData> {
        articleCard.shouldHave(sizeGreaterThan(0))

        return articleCard.map { card ->
            val titleElement = card.find(byXpath(ARTICLE_TITLE)).shouldBe(visible).text().trim()
            val dateElement = card.find(byXpath(ARTICLE_DATE)).shouldBe(visible).text().trim()
            val authorElement = card.find(byXpath(ARTICLE_AUTHOR)).shouldBe(visible).text().trim()

            ArticleData( titleElement, dateElement, authorElement)
        }
    }
}