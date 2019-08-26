package com.meongbyeol.taxishare.data.repo

import com.meongbyeol.taxishare.TestSchedulerSettingManager
import com.meongbyeol.taxishare.data.model.User
import com.meongbyeol.taxishare.data.remote.apis.server.request.LoginRequest
import org.junit.Test

class ServerRepositoryImplTest : TestSchedulerSettingManager() {

    override val TAG: String
        get() = ServerRepositoryImplTest::class.java.simpleName

    private val userInfo: User = User(20140797, "어드민", "제어 및 로봇전공")

    @Test
    fun loginRequest() {
        serverRepo.loginRequest(LoginRequest("20140797", "9619"))
            .subscribe({
                println(getMessage("$TAG::loginRequest", "", true))
                assert(User(20140797, it.nickname, it.major) == userInfo)
            }, {
                println(getMessage("$TAG::loginRequest", it.message.toString(), false))
                assert(false)
            })
    }

}