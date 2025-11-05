package api.tests

import api.pojo.*
import api.spec.RequestSpec
import api.spec.ResponseSpec
import io.restassured.RestAssured.given
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue



class UserTest {
    @Test
    @DisplayName("GET /Проверка аватара и Id")
    fun checkAvatarAndIdTest() {
        val users: List<UserData> = given()
            .spec(RequestSpec.baseRequestSpec())
            .get("/api/users?page=2")
            .then()
            .spec(ResponseSpec.responseSpecOK200())
            .log().all()
            .extract().body().jsonPath().getList("data", UserData::class.java)
        users.forEach{user ->
            assertTrue (user.avatar.contains(user.id.toString()))
            assertTrue(user.email.endsWith("@reqres.in"))
        }
    }
    @Test
    @DisplayName("GET /Запрос пользователя по ID")
    fun checkSingleUserTest () { given()
            .spec(RequestSpec.baseRequestSpec())
            .get("/api/users/2")
            .then()
            .spec(ResponseSpec.responseSpecOK200())
            .log().all()
            .body("data.id", equalTo(2))
            .body("data.avatar", containsString("2-image.jpg"))
    }
    @Test
    @DisplayName("GET /Пользователь не найдет")
    fun checkUserNotFoundTest (){
        given()
            .spec(RequestSpec.baseRequestSpec())
            .get("/api/users/23")
            .then()
            .spec(ResponseSpec.responseSpecError404())
            .log().all()
    }

    @Test
    @DisplayName("POST /Успешное создание пользователя")
    fun successCreateUserTest (){
        val userRequest = CreateUserRequest(
            "morpheus",  "leader")

        given()
            .spec(RequestSpec.baseRequestSpec())
            .body(userRequest)
            .post("/api/users")
            .then()
            .spec(ResponseSpec.responseSpec201())
            .log().all()
            .body("name", equalTo("morpheus"))
            .body("job", equalTo("leader"))
            .body("id", matchesPattern("\\d+"))
            .body("createdAt", notNullValue())


    }
    @Test
    @DisplayName("PUT /Успешное обновление данных пользователя по Id")
    fun successUpdateUserTest (){
        val userRequest = CreateUserRequest(
            "morpheus",  "zion resident")

        given()
            .spec(RequestSpec.baseRequestSpec())
            .body(userRequest)
            .put("/api/users/2")
            .then()
            .spec(ResponseSpec.responseSpecOK200())
            .log().all()
            .body("name", equalTo("morpheus"))
            .body("job", equalTo("zion resident"))
    }

    @Test
    @DisplayName("DELETE /Успешное удаление пользователя по ID")
    fun successDeleteUserTest (){
        given()
            .spec(RequestSpec.baseRequestSpec())
            .delete("/api/users/2")
            .then()
            .spec(ResponseSpec.responseSpecDelete204())
            .log().all()
    }
    @Test
    @DisplayName("POST /Успешная регистрация пользователя")
    fun successRegistrationUserTest() {
        val userRequest = RegistrationUser("eve.holt@reqres.in", "pistol")
        val id = 4
        val token = "QpwL5tke4Pnpja7X4"
        val response: SuccessRegistrationUser = given()
            .spec(RequestSpec.baseRequestSpec())
            .body(userRequest)
            .post("/api/register")
            .then()
            .spec(ResponseSpec.responseSpecOK200())
            .log().all()
            .body("id", greaterThan(0))
            .body("token", not(emptyString()))
            .body("id", equalTo(id))
            .body("token", equalTo(token))
            .extract().body().`as`(SuccessRegistrationUser::class.java)
        //далее сложные проверки response по необходимости, валидации и т.д.
    }
    @Test
    @DisplayName("POST /Не валидная регистрация пользователя")
    fun unSuccessRegistrationUserTest(){
        val userRequest = RegistrationUser("sydney@fife", "")

        val response: UnSuccessRegistrationUser = given()
            .spec(RequestSpec.baseRequestSpec())
            .body(userRequest)
            .post("/api/register")
            .then()
            .spec(ResponseSpec.responseSpecError400())
            .log().all()
            .body("error", equalTo("Missing password"))
            .extract().body().`as`(UnSuccessRegistrationUser::class.java)
        //далее сложные проверки response по необходимости, валидации и т.д.
    }
    @Test
    @DisplayName("GET /Сортировка списка цветов по годам")
    fun sortedYearsTest(){
        val colors: List<ColorsData> = given()
            .spec(RequestSpec.baseRequestSpec())
            .get("/api/unknown")
            .then()
            .spec(ResponseSpec.responseSpecOK200())
            .extract().body().jsonPath().getList("data", ColorsData::class.java)

        val years: List<Int> = colors.map { it.year }
        val sortedYear : List<Int> = years.sorted()
        assertEquals(sortedYear, years)
    }
}
