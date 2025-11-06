package ui.tests.navigation

import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selectors.byTagName
import com.codeborne.selenide.Selenide.`$`
import io.qameta.allure.Description
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import ui.base.BasePage
import ui.pages.MainPage
import kotlin.test.assertTrue
@Tag("UI")
class MenuNavigationTest: BasePage() {
    @Test
    @Description("Проверка работоспособности категорий на главной странице")
    fun clickOnTheIphone17Category() {
         MainPage().openPage().openIphone17Category()
        val h1Text =`$`(byTagName("h1")).shouldBe(visible).text()
        assertTrue(h1Text.contains("iPhone"), "Заголовок страницы не содержит слово 'iPhone'")
    }
}