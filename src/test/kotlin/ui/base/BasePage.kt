package ui.base

import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selenide.closeWebDriver
import io.github.bonigarcia.wdm.WebDriverManager

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import ui.util.ModalHelper

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class BasePage {
    private fun setUp(){
        WebDriverManager.chromedriver().setup()
        Configuration.browser = "chrome"
        Configuration.webdriverLogsEnabled = true
        Configuration.browserSize = "1920x1080"
        Configuration.headless = false
    }
        @BeforeEach
        fun init() {
            setUp()
        }
        @AfterEach
        fun tearDown() {
            closeWebDriver()
    }
    fun handleModals() {
        ModalHelper.handleConsentModal()
    }

}