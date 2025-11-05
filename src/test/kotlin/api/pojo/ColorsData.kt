package api.pojo

import com.fasterxml.jackson.annotation.JsonProperty

data class ColorsData(
    val id: Int,
    val name : String,
    val year : Int,
    val color: String,
    @JsonProperty("pantone_value")
    val pantoneValue : String,
)
