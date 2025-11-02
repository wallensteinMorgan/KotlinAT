package api.spec

import io.restassured.builder.ResponseSpecBuilder
import io.restassured.http.ContentType
import io.restassured.specification.ResponseSpecification
import org.hamcrest.Matchers.*

object  ResponseSpec {

    fun responseSpecOK200() : ResponseSpecification{
        return ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON)
            .build()
    }
    fun responseSpecDelete204() : ResponseSpecification{
        return ResponseSpecBuilder()
            .expectStatusCode(204)
            .expectBody(blankOrNullString())
            .build()
    }
    fun responseSpecError400() : ResponseSpecification{
        return ResponseSpecBuilder()
            .expectStatusCode(400)
            .expectContentType(ContentType.JSON)
            .build()
    }
    fun responseSpec201() : ResponseSpecification{
        return ResponseSpecBuilder()
            .expectStatusCode(201)
            .expectContentType(ContentType.JSON)
            .expectBody("id", notNullValue())
            .expectBody("createdAt", notNullValue())
            .build()
    }
    fun responseSpecError404() : ResponseSpecification{
        return ResponseSpecBuilder()
            .expectStatusCode(404)
            .expectContentType(ContentType.JSON)
            .expectBody(equalTo("{}"))
            .build()
    }
}