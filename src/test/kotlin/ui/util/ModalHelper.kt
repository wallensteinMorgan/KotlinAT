package ui.util

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selectors.byXpath
import com.codeborne.selenide.Selenide.`$`
import ui.data.AppData.Locators.COOKIE_CONSENT_BUTTON
import java.time.Duration



object  ModalHelper {

    private const val consentInModal: String = COOKIE_CONSENT_BUTTON

    fun handleConsentModal() {
        val modal = `$`(byXpath(consentInModal))
        if (modal.exists()) {
            modal.shouldBe(visible, Duration.ofSeconds(10)).click()
        } else {
            println("Согласие не требуется, продолжаем тест")
        }
    }
}