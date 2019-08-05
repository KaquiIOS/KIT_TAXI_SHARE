/*
 * Created by WonJongSeong on 2019-07-19
 */

package com.example.taxishare.view.main.taxisharelist.detail

import com.example.taxishare.app.Constant
import com.example.taxishare.data.model.ServerResponse
import com.example.taxishare.data.model.TaxiShareInfo
import com.example.taxishare.data.remote.apis.server.request.*
import com.example.taxishare.data.repo.ServerRepository
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import java.util.*

class TaxiShareInfoDetailPresenter(
    private val view: TaxiShareInfoDetailView,
    private val serverRepo: ServerRepository
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
                    Constant.CURRENT_USER.studentId.toString()
                )
            )
                .subscribe({

                    if (it.responseCode == ServerResponse.DETAIL_TAXISHARE_LOAD_FAIL.code) {
                        view.failLoadDetailInfo()
                    } else if (it.responseCode == ServerResponse.DETAIL_TAXISHARE_DELETED.code) {
                        view.detailInfoDeleted()
                    } else {
                        view.setDetailInfo(with(it) {
                            TaxiShareInfo(
                                id, uid, title, Date(startDate), startLocation, endLocation, limit,
                                nickname, major, participantsNum, (isParticipate == 1)
                            )
                        })
                    }
                }, {
                    it.printStackTrace()
                    view.failLoadDetailInfo()
                })
        } else {
            view.loadDetailInfoNotFinish()
        }
    }

    fun registerComment(id: String, uid: String, content: String) {

        if (!::registerCommentDisposable.isInitialized || registerCommentDisposable.isDisposed) {
            registerCommentDisposable =
                serverRepo.registerComment(RegisterCommentRequest(id, uid, content))
                    .subscribe({
                        if (it.commentId == -1) {
                            view.registerCommentFail()
                        } else {
                            nextCommentId = it.commentId
                            view.insertComment(it)
                            view.registerCommentSuccess()
                        }
                    }, {
                        it.printStackTrace()
                        view.registerCommentFail()
                    })
        } else {
            view.registerCommentNotFinish()
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

                    if(it.size > 0) {
                        nextCommentId = it[it.size - 1].commentId
                        view.addComments(it)
                    }

                    noCommentExist = it.size < 5
                }, {
                    it.printStackTrace()
                    view.loadCommentFail()
                })
        } else if (::loadCommentDisposable.isInitialized || !loadCommentDisposable.isDisposed) {
            view.loadCommentNotFinished()
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
        } else {
            view.removeCommentNotFinished()
        }
    }

    fun participateTaxiShare(postId: String) {

        if (!::participateTaxiShareDisposable.isInitialized || participateTaxiShareDisposable.isDisposed) {
            participateTaxiShareDisposable = serverRepo.participateTaxiShare(ParticipateTaxiShareRequest(postId))
                .subscribe({
                    if (it == ServerResponse.PARTICIPATE_TAXI_SHARE_SUCCESS) {
                        view.showParticipateTaxiShareSuccess()
                    } else {
                        view.showParticipateTaxiShareFail()
                    }
                }, {
                    it.printStackTrace()
                    view.showParticipateTaxiShareFail()
                })
        } else {
            view.showParticipateTaxiShareNotFinish()
        }
    }

    fun leaveTaxiShare(postId: String) {
        if (!::leaveTaxiShareDisposable.isInitialized || leaveTaxiShareDisposable.isDisposed) {
            leaveTaxiShareDisposable = serverRepo.leaveTaxiShare(LeaveTaxiShareRequest(postId))
                .subscribe({
                    if (it == ServerResponse.TAXISHARE_LEAVE_SUCCESS) {
                        view.showLeaveTaxiShareSuccess()
                    } else {
                        view.showLeaveTaxiShareFail()
                    }
                }, {
                    it.printStackTrace()
                    view.showLeaveTaxiShareFail()
                })
        } else {
            view.showLeaveTaxiShareNotFinish()
        }
    }

    fun removeTaxiShare(postId: String) {
        if (!::removeTaxiShareDisposable.isInitialized || removeTaxiShareDisposable.isDisposed) {
            removeTaxiShareDisposable = serverRepo.removeTaxiShare(TaxiShareRemoveRequest(postId))
                .subscribe({
                    if (it == ServerResponse.TAXISHARE_REMOVE_SUCCESS) {
                        view.showRemoveTaxiShareSuccess()
                    } else {
                        view.removeCommentFail()
                    }
                }, {
                    it.printStackTrace()
                    view.removeCommentFail()
                })
        } else {
            view.showRemoveTaxiShareNotFinish()
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