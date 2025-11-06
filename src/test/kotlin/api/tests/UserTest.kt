package api.tests

import api.dto.*
import api.endpoints.Endpoints.UNKNOWN
import api.endpoints.Endpoints.USERS
import api.testdata.TestData
import api.utils.RegistrationApi
import api.utils.UserApi
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertEquals
import kotlin.test.assertTrue


@Tag("API")
class UserTest {
    @Test
    @DisplayName("GET /Проверка аватара и Id на 2 странице")
    fun checkAvatarAndIdTest() {
        val users = UserApi.getUsersPage(2)

        users.forEach{user ->
            assertTrue (user.avatar.contains(user.id.toString()), "Avatar не содержит id пользователя")
            assertTrue(user.email.endsWith("@reqres.in"), "Email не оканчивается на @reqres.in")
        }
    }
    @ParameterizedTest
    @ValueSource(ints = [1, 2, 3, 4])
    @DisplayName("GET /Запрос пользователя по ID")
    fun checkSucssesUserTest (userId: Int) {
        UserApi.checkUserExists(userId)
    }

    @ParameterizedTest
    @ValueSource(ints = [23,99,1001])
    @DisplayName("GET /Пользователь не найдет")
    fun checkUserNotFoundUserTest (userId: Int){
        UserApi.checkUserNotFound(userId)
    }

    @Test
    @DisplayName("POST /Успешное создание пользователя")
    fun successCreateUserTest () {
        val request = TestData.morpheusLeader()
        val response = api.clients.ApiClient.post(USERS, request)
            .then()
            .spec(api.spec.ResponseSpec.responseSpec(201))
            .extract().body().`as`(CreateUserResponse::class.java)

        assertEquals(request.name, response.name)
        assertEquals(request.job, response.job)
        assertTrue(response.id.matches(Regex("\\d+")))
    }
    @Test
    @DisplayName("PUT /Успешное обновление данных пользователя по Id")
    fun successUpdateUserTest (){
        val request = TestData.morpheusZionResident()
        UserApi.updateUser(2, request)
    }

    @Test
    @DisplayName("DELETE /Успешное удаление пользователя по ID")
    fun successDeleteUserTest (){
        UserApi.deleteUser(2)
    }

    companion object{
        @JvmStatic
        fun registrationTestData() = listOf(
            arrayOf(TestData.eveHoltPistol(), TestData.registeredEveHolt)
        )
    }
    @ParameterizedTest
    @MethodSource("registrationTestData")
    @DisplayName("POST /Успешная регистрация пользователя")
    fun successRegistrationUserTest(request: RegistrationUserRequest, expected: RegistrationSuccessUserResponse) {
        RegistrationApi.checkSuccessfulRegistration(request, expected)
    }
    @Test
    @DisplayName("POST /Регистрация с ошибкой")
    fun unSuccessRegistrationUserTest(){
        RegistrationApi.checkFailedRegistration(TestData.invalidUserNoPassword(), "Missing password")
    }
    @Test
    @DisplayName("GET /Сортировка списка цветов по годам")
    fun sortedYearsTest(){
        val colors: List<ColorsData> = api.clients.ApiClient.get(UNKNOWN)
            .then()
            .spec(api.spec.ResponseSpec.responseSpec(200))
            .extract().body().jsonPath().getList("data", ColorsData::class.java)

        val years: List<Int> = colors.map { it.year }
        assertEquals(years.sorted(), years)
    }
}
