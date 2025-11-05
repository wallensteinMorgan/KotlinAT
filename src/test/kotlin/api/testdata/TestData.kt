package api.testdata

import api.dto.CreateUserRequest
import api.dto.RegistrationSuccessUserResponse
import api.dto.RegistrationUserRequest

object TestData {
    fun morpheusLeader() = CreateUserRequest(
        name = "morpheus",
        job = "leader"
    )

    fun morpheusZionResident() = CreateUserRequest(
        name = "morpheus",
        job = "zion resident"
    )

    fun eveHoltPistol() = RegistrationUserRequest(
        email = "eve.holt@reqres.in",
        password = "pistol"
    )

    fun invalidUserNoPassword() = RegistrationUserRequest(
        email = "sydney@fife",
        password = ""
    )


    val registeredEveHolt = RegistrationSuccessUserResponse(
        id = 4,
        token = "QpwL5tke4Pnpja7X4"
    )
}