package api.spec

import api.data.AppData.BASE_URL
import io.restassured.builder.RequestSpecBuilder
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification

object  RequestSpec {
    fun baseSpec() : RequestSpecification {
        return RequestSpecBuilder()
            .setBaseUri(BASE_URL)
            .setContentType(ContentType.JSON)
            .addHeader("x-api-key", "reqres-free-v1")
            .build()
    }
}