package io.king.loginsignup.API

data class UserBody(
    val amount: Int,
    val email: String,
    val first_name: String,
    val last_name: String,
    val phone_number: String,
    val reference: String
)

data class SignInBody(
    val email: String,
    val password: String
    )
