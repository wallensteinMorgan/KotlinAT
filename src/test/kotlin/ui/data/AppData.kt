package ui.data

object AppData {
    object Urls {
        const val BASE_URL = "https://appleinsider.ru"

    }
    object Locators {
        // для модалки
        const val COOKIE_CONSENT_BUTTON = "//button[.//p[contains(text(), 'Согласиться')]]"

        // элементы главной страницы
        const val IPHONE_17_MENU_ITEM  = "//li[@id='menu-item-537097']"
        const val SEARCH_INPUT = "//input[@type='text']"
        const val PAGINATION_ELEMENT ="//span[@class='pagination-text']"

        // карточки статей и их элементов
        const val ARTICLE_TITLE = ".//article" // для проверки состояния всей карточки (заголовок, автор, дата)
        const val ARTICLE_DATE = ".//time"
        const val ARTICLE_TITLE_NAME = ".//a[@rel='bookmark']" // для проверки названия статьи
        const val ARTICLE_AUTHOR = ".//span[@class='entry-author']"
        const val READ_NEXT = "//a[contains(text(), 'Читать далее')]"
        const val ARTICLE_TITLE_LINKS = "//a[@rel='bookmark']" // для проверки href



    }
}