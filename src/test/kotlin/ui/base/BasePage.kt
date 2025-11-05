package ui.base

import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selenide.closeWebDriver
import com.codeborne.selenide.Selenide.open
import com.codeborne.selenide.logevents.SelenideLogger
import io.github.bonigarcia.wdm.WebDriverManager
import io.qameta.allure.selenide.AllureSelenide


import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import util.Config
import util.ModalHelper

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class BasePage {
    private fun setUp(){
        WebDriverManager.chromedriver().setup()
        Configuration.baseUrl = Config.get("baseUrl")
        Configuration.browser = Config.get("browser")
        Configuration.webdriverLogsEnabled = true
        Configuration.browserSize = "1920x1080"
        Configuration.headless = true
        SelenideLogger.addListener("AllureSelenide", AllureSelenide()
            .screenshots(true)
            .savePageSource(true)
            .includeSelenideSteps(true))
    }
        @BeforeEach
        fun init() {
            setUp()
        }
        @AfterEach
        fun tearDown() {
            closeWebDriver()
    }

    fun openBasePage() {
        open(Configuration.baseUrl)
    }
    fun handleModals() {
        ModalHelper.handleConsentModal()
    }
}