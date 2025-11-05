package api.utils

import api.clients.ApiClient
import api.dto.ColorsData
import api.endpoints.Endpoints.UNKNOWN
import api.spec.ResponseSpec

object ColorsApi {
    fun getColors(): List<ColorsData> {
        return ApiClient.get(UNKNOWN)
            .then()
            .spec(ResponseSpec.responseSpec(200))
            .extract().jsonPath().getList("data", ColorsData::class.java)
    }
}