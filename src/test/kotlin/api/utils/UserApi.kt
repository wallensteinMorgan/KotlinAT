package api.utils

import api.clients.ApiClient
import api.dto.UserData
import api.endpoints.Endpoints.USERS
import api.spec.ResponseSpec
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

object UserApi {

    fun getUser(userId: Int) = ApiClient.get("$USERS/$userId")
    fun checkUserExists(userId: Int) {
        val user: UserData = ApiClient
            .get("$USERS/$userId")
            .then()
            .spec(ResponseSpec.responseSpec(200))
            .extract().jsonPath().getObject("data", UserData::class.java)

        assertEquals(userId, user.id)
        assertTrue(user.avatar.contains(userId.toString()))
        assertTrue(user.email.endsWith("@reqres.in"))
    }
    fun checkUserNotFound(userId: Int) {
        getUser(userId)
            .then()
            .spec(ResponseSpec.responseSpec(404))
    }

    fun getUsersPage(page: Int): List<UserData> {
        return ApiClient.get("$USERS?page=$page")
            .then()
            .spec(ResponseSpec.responseSpec(200))
            .extract().jsonPath().getList("data", UserData::class.java)
    }
    fun deleteUser(userId: Int) {
        ApiClient.delete("$USERS/$userId")
            .then()
            .statusCode(204)
    }

    fun updateUser(userId: Int, request: api.dto.CreateUserRequest) {
        ApiClient.put("$USERS/$userId", request)
            .then()
            .spec(ResponseSpec.responseSpec(200))
    }
}