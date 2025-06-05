package ru.athletica.crm.api

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.athletica.api.schemas.LoginRequest
import ru.athletica.api.schemas.LoginResponse
import ru.athletica.crm.db.suspendTransaction


@RestController
@RequestMapping("/api/login")
class Login() {

    @PostMapping
    suspend fun login(@RequestBody request: LoginRequest): LoginResponse = suspendTransaction {
        LoginResponse(token = request.login)
    }
}
