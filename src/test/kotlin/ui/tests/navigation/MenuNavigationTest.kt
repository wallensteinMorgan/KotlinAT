package tests.navigation

import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selectors.byTagName
import com.codeborne.selenide.Selenide.`$`
import org.junit.jupiter.api.Test
import ui.base.BaseTest
import ui.pages.MainPage
import kotlin.test.assertTrue

class MenuNavigationTest: BaseTest() {
    @Test
    fun menuNavigation() {
         MainPage().openIphone17Category()
        val h1Text =`$`(byTagName("h1")).shouldBe(visible).text()
        assertTrue(h1Text.contains("iPhone"), "Заголовок страницы не содержит слово 'iPhone'")
    }
}