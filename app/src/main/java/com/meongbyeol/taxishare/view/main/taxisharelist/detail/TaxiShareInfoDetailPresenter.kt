/*
 * Created by WonJongSeong on 2019-07-19
 */

package com.meongbyeol.taxishare.view.main.taxisharelist.detail

import com.meongbyeol.taxishare.app.AlarmManagerInterface
import com.meongbyeol.taxishare.app.Constant
import com.meongbyeol.taxishare.data.model.ServerResponse
import com.meongbyeol.taxishare.data.model.TaxiShareDetailInfo
import com.meongbyeol.taxishare.data.remote.apis.server.request.*
import com.meongbyeol.taxishare.data.repo.ServerRepository
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import java.util.*

class TaxiShareInfoDetailPresenter(
    private val view: TaxiShareInfoDetailView,
    private val serverRepo: ServerRepository,
    private val alarmManger: AlarmManagerInterface
) {


    private lateinit var registerCommentDisposable: Disposable
    private lateinit var loadCommentDisposable: Disposable
    private lateinit var removeCommentDisposable: Disposable
    private lateinit var loadDetailTaxiShareDisposable: Disposable
    private lateinit var participateTaxiShareDisposable: Disposable
    private lateinit var leaveTaxiShareDisposable: Disposable
    private lateinit var removeTaxiShareDisposable: Disposable

    private lateinit var bottomDetectSubscriber: Disposable

    private var nextCommentId: Int = -1
    private var noCommentExist: Boolean = false

    fun setOnBottomDetectSubscriber(publisher: PublishSubject<Boolean>) {
        bottomDetectSubscriber = publisher.filter { true }.subscribe {
            loadComments(nextCommentId.toString(), false)
        }
    }

    fun loadDetailTaxiShareInfo(postId: String, uid: String) {

        if (!::loadDetailTaxiShareDisposable.isInitialized || loadDetailTaxiShareDisposable.isDisposed) {
            loadDetailTaxiShareDisposable = serverRepo.loadDetailTaxiShareInfo(
                DetailTaxiShareLoadRequest(
                    postId,
                    Constant.CURRENT_USER.studentId
                )
            ).subscribe({

                if (it.responseCode == ServerResponse.DETAIL_TAXISHARE_LOAD_FAIL.code) {
                    view.failLoadDetailInfo()
                } else if (it.responseCode == ServerResponse.DETAIL_TAXISHARE_DELETED.code) {
                    view.detailInfoDeleted()
                } else {

                    view.setDetailInfo(with(it) {
                        TaxiShareDetailInfo(
                            id, uid, title, Date(startDate), startLocation, endLocation, limit,
                            nickname, major, participants.size, (isParticipate == 1), participants
                        )
                    })

                    if (System.currentTimeMillis() > it.startDate + Constant.ALARM_NOTIFY_TIME) {
                        view.disableAllComponents()
                    }

                }
            }, {
                it.printStackTrace()
                view.failLoadDetailInfo()
            })
        }
    }

    fun registerComment(id: String, uid: String, content: String) {

        if (!::registerCommentDisposable.isInitialized || registerCommentDisposable.isDisposed) {

            view.disableSendBtn()

            registerCommentDisposable =
                serverRepo.registerComment(RegisterCommentRequest(id, uid, content))
                    .subscribe({
                        if (it.commentId == -1) {
                            view.registerCommentFail()
                        } else {
                            nextCommentId = it.commentId
                            view.insertComment(it)
                            view.registerCommentSuccess()
                            view.clearText()
                            view.hideKeyboard()
                        }
                        view.enableSendBtn()
                    }, {
                        it.printStackTrace()
                        view.registerCommentFail()
                        view.enableSendBtn()
                    })
        }
    }

    fun loadComments(id: String, isRefresh: Boolean) {

        nextCommentId = when (isRefresh) {
            true -> -1
            else -> nextCommentId
        }

        if (!noCommentExist && (!::loadCommentDisposable.isInitialized || loadCommentDisposable.isDisposed)) {
            loadCommentDisposable = serverRepo.loadComments(id, nextCommentId.toString())
                .subscribe({

                    if (it.isNotEmpty()) {
                        nextCommentId = it[it.size - 1].commentId
                    }
                    view.addComments(it)
                    noCommentExist = it.size < 5
                }, {
                    it.printStackTrace()
                    view.loadCommentFail()
                })
        }
    }

    fun removeComment(commentId: Int) {
        if (!::removeCommentDisposable.isInitialized || removeCommentDisposable.isDisposed) {
            removeCommentDisposable = serverRepo.removeComment(RemoveCommentRequest(commentId))
                .subscribe({
                    if (it == ServerResponse.COMMENT_REMOVE_SUCCESS) {
                        view.removeCommentSuccess(commentId)
                    } else {
                        view.removeCommentFail()
                    }
                }, {
                    it.printStackTrace()
                    view.removeCommentFail()
                })
        }
    }

    fun participateTaxiShare(postId: String) {

        if (!::participateTaxiShareDisposable.isInitialized || participateTaxiShareDisposable.isDisposed) {
            participateTaxiShareDisposable =
                serverRepo.participateTaxiShare(ParticipateTaxiShareRequest(postId))
                    .subscribe({
                        if (it == ServerResponse.PARTICIPATE_TAXI_SHARE_SUCCESS) {
                            view.saveCurrentTaxiShareInfo()
                            view.showParticipateTaxiShareSuccess()
                        } else {
                            view.showParticipateTaxiShareFail()
                        }
                    }, {
                        it.printStackTrace()
                        view.showParticipateTaxiShareFail()
                    })
        }
    }

    fun leaveTaxiShare(postId: String) {
        if (!::leaveTaxiShareDisposable.isInitialized || leaveTaxiShareDisposable.isDisposed) {
            leaveTaxiShareDisposable = serverRepo.leaveTaxiShare(LeaveTaxiShareRequest(postId))
                .subscribe({
                    if (it == ServerResponse.TAXISHARE_LEAVE_SUCCESS) {
                        view.showLeaveTaxiShareSuccess()
                        view.saveCurrentTaxiShareInfo()
                    } else {
                        view.showLeaveTaxiShareFail()
                    }
                }, {
                    it.printStackTrace()
                    view.showLeaveTaxiShareFail()
                })
        }
    }

    fun removeTaxiShare(postId: String) {
        if (!::removeTaxiShareDisposable.isInitialized || removeTaxiShareDisposable.isDisposed) {
            removeTaxiShareDisposable = serverRepo.removeTaxiShare(TaxiShareRemoveRequest(postId))
                .subscribe({
                    if (it == ServerResponse.TAXISHARE_REMOVE_SUCCESS) {
                        view.showRemoveTaxiShareSuccess()
                        alarmManger.cancelAlarm(postId.toInt())
                    } else {
                        view.removeCommentFail()
                    }
                }, {
                    it.printStackTrace()
                    view.removeCommentFail()
                })
        }
    }

    fun changeButtonState(inputString: String) {
        view.changeRegisterButtonState(inputString.isNotEmpty())
    }

    fun onDestroy() {

        if (::registerCommentDisposable.isInitialized && !registerCommentDisposable.isDisposed)
            registerCommentDisposable.dispose()

        if (::loadCommentDisposable.isInitialized && !loadCommentDisposable.isDisposed)
            loadCommentDisposable.dispose()

        if (::removeCommentDisposable.isInitialized && !removeCommentDisposable.isDisposed)
            removeCommentDisposable.dispose()

        if (::loadDetailTaxiShareDisposable.isInitialized && !loadDetailTaxiShareDisposable.isDisposed)
            loadDetailTaxiShareDisposable.dispose()

        if (::removeTaxiShareDisposable.isInitialized && !removeTaxiShareDisposable.isDisposed)
            removeTaxiShareDisposable.dispose()
    }

}