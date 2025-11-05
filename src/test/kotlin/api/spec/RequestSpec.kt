package api.spec

import io.qameta.allure.restassured.AllureRestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import util.Config


object RequestSpec {
    fun baseRequestSpec(): RequestSpecification {
        return RequestSpecBuilder()
            .setBaseUri(Config.get("baseUrlForApi"))
            .setContentType(ContentType.JSON)
            .addHeader("x-api-key", "reqres-free-v1")
            .addFilter(AllureRestAssured())
            .log(io.restassured.filter.log.LogDetail.ALL)
            .build()
    }
}