package api.utils

import api.clients.ApiClient
import api.dto.RegistrationSuccessUserResponse
import api.dto.RegistrationUserRequest
import api.endpoints.Endpoints.REGISTER
import api.spec.ResponseSpec
import org.hamcrest.Matchers.equalTo

object RegistrationApi {


    fun checkSuccessfulRegistration(request: RegistrationUserRequest, expected: RegistrationSuccessUserResponse) {
        val response: RegistrationSuccessUserResponse = ApiClient.post(REGISTER, request)
            .then()
            .spec(ResponseSpec.responseSpec(200))
            .body("id", equalTo(expected.id))
            .body("token", equalTo(expected.token))
            .extract().`as`(RegistrationSuccessUserResponse::class.java)
    }

    fun checkFailedRegistration(request: RegistrationUserRequest, expectedError: String) {
        ApiClient.post(REGISTER, request)
            .then()
            .spec(ResponseSpec.responseSpec(400))
            .body("error", equalTo(expectedError))
    }
}