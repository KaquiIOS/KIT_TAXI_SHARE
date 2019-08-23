package com.example.taxishare

import com.example.taxishare.data.remote.apis.server.ServerClient
import com.example.taxishare.data.repo.ServerRepository
import com.example.taxishare.data.repo.ServerRepositoryImpl
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before


abstract class TestSchedulerSettingManager {

    open lateinit var serverRepo: ServerRepository

    abstract val TAG: String

    @Before
    open fun setUp() {
        RxJavaPlugins.setIoSchedulerHandler {
            Schedulers.trampoline()
        }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler {
            Schedulers.trampoline()
        }

        serverRepo = ServerRepositoryImpl.getInstance(ServerClient.getInstance())
    }

    @After
    open fun tearDown() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }

    fun getMessage(pos: String, msg: String, isSuccess: Boolean): String =
        String.format(
            "%s %s : %s", pos, msg, when (isSuccess) {
                true -> "Success"
                else -> "False"
            }
        )

}