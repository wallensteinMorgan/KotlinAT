package uiPage

import com.codeborne.selenide.Condition.empty
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide.`$x`
import data.AppData.Locators.ARTICLE_AUTHOR
import data.AppData.Locators.ARTICLE_DATE
import data.AppData.Locators.ARTICLE_TITLE


class ArticlePage {
    private val title = `$x`(ARTICLE_TITLE)
    private val content = `$x`("//div[contains(@class, 'entry-content')]")
    private val date = `$x`(ARTICLE_DATE)
    private val author = `$x`(ARTICLE_AUTHOR)
    fun getArticleTitle(): String {
        title.shouldBe(visible)
        return title.text()
    }

    fun assertArticleHasContent() {
        content.shouldBe(visible).shouldNotBe(empty)
    }

    fun assertArticleHasDate() {
        date.shouldBe(visible)
    }

    fun assertAuthorIfPresent() {
        if (author.exists()) {
            author.shouldBe(visible)
        }
    }

}