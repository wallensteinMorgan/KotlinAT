package util

import io.qameta.allure.Allure
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.TestWatcher

object TestListener : TestWatcher {
    override fun testFailed(context: ExtensionContext, cause:Throwable){

    }
}