package util

import java.lang.RuntimeException
import java.util.Properties

object Config {
    private val properties : Properties = Properties ()
    init {
        val inputStream = this :: class.java.classLoader.getResourceAsStream("config.properties")
            ?: throw RuntimeException("Файл config.properties не найден")
        properties.load(inputStream)
    }

    fun get(key: String): String {
        return properties.getProperty(key)
            ?: throw  RuntimeException("Ключ `$key` не найден в config.properties")
    }
}