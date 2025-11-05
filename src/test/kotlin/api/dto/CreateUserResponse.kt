package api.dto

import java.time.Instant

data class CreateUserResponse(
    val name: String,
    val job: String,
    val id: String,
    val createdAt: Instant,
)
