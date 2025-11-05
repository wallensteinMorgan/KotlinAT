package api.spec

import io.restassured.builder.ResponseSpecBuilder
import io.restassured.http.ContentType
import io.restassured.specification.ResponseSpecification

object  ResponseSpec {
    fun responseSpec(statusCode: Int, contentType: ContentType = ContentType.JSON): ResponseSpecification =
        ResponseSpecBuilder()
            .expectStatusCode(statusCode)
            .expectContentType(contentType)
            .build()
}
