package util

import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selectors.byXpath
import com.codeborne.selenide.Selenide.`$`
import data.AppData.Locators.COOKIE_CONSENT_BUTTON
import java.time.Duration


object  ModalHelper {

    private const val consentInModal: String = COOKIE_CONSENT_BUTTON

    fun handleConsentModal() {
        val modal = `$`(byXpath(consentInModal))

        // Проверяем, существует ли элемент и виден ли он
        if (modal.exists() && modal.isDisplayed) {
            // Ждем видимость до 5 секунд и кликаем
            modal.shouldBe(visible, Duration.ofSeconds(5)).click()
            println("Consent modal clicked")
        } else {
            println("Consent modal not found or already closed, continue test")
        }
    }
}