package com.example.taxishare.data.repo

import com.example.taxishare.data.model.User
import com.example.taxishare.data.remote.apis.server.ServerClient
import com.example.taxishare.data.remote.apis.server.request.LoginRequest
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.mockito.Mock

class RxSchedulerRule : TestRule {
    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
                RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
                RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
                RxJavaPlugins.setSingleSchedulerHandler { Schedulers.trampoline() }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
                try {
                    base?.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }
}

class ServerRepositoryImplTest {

    @Rule
    @JvmField
    val rule: RxSchedulerRule = RxSchedulerRule()

    @Mock
    lateinit var user : User

    private lateinit var repo: ServerRepository

    // 모든 Test Method 가 실행되기 전에
    // 수행되는 코드들을 @Before 에 넣어두자
    @Before
    fun setUp() {
        repo = ServerRepositoryImpl.getInstance(ServerClient.getInstance())

        RxJavaPlugins.setIoSchedulerHandler {
            Schedulers.trampoline()
        }

        RxAndroidPlugins.setInitMainThreadSchedulerHandler {
            Schedulers.trampoline()
        }
    }

    @Test
    fun loginRequest() {


        val testScheduler: TestScheduler = TestScheduler()

        val testObserver = repo.loginRequest(LoginRequest("20140797", "whdtjd1!q"))
            .subscribeOn(testScheduler)
            .observeOn(testScheduler)
            .test()

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

    }

    @After
    fun teardown() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }
}