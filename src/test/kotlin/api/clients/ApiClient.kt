package api.clients

import api.spec.RequestSpec
import io.restassured.RestAssured.given
import io.restassured.response.Response

object ApiClient {
    fun get(path: String, params: Map<String, Any?> = emptyMap()): Response =
        given()
            .spec(RequestSpec.baseRequestSpec())
            .queryParams(params)
            .get(path)

    fun post(path:String, body: Any? = null): Response =
        given()
            .spec(RequestSpec.baseRequestSpec())
            .body(body)
            .post(path)

    fun put(path: String, body: Any? = null): Response =
        given()
            .spec(RequestSpec.baseRequestSpec())
            .body(body)
            .put(path)

    fun patch(path: String, body: Any? = null): Response =
        given()
            .spec(RequestSpec.baseRequestSpec())
            .body(body)
            .patch(path)

    fun delete(path: String): Response =
        given()
            .spec(RequestSpec.baseRequestSpec())
            .delete(path)
}
