/*
 * Created by WonJongSeong on 2019-07-19
 */

package com.example.taxishare.view.main.taxisharelist.detail

import com.example.taxishare.data.model.ServerResponse
import com.example.taxishare.data.remote.apis.server.request.RegisterCommentRequest
import com.example.taxishare.data.remote.apis.server.request.RemoveCommentRequest
import com.example.taxishare.data.repo.ServerRepository
import io.reactivex.disposables.Disposable

class TaxiShareInfoDetailPresenter(
    private val view: TaxiShareInfoDetailView,
    private val serverRepo: ServerRepository
) {


    private lateinit var registerCommentDisposable: Disposable
    private lateinit var loadCommentDisposable: Disposable
    private lateinit var removeCommentDisposable: Disposable

    private var nextCommentId: Int = -1

    fun registerComment(id: String, uid: String, content: String) {

        if (!::registerCommentDisposable.isInitialized || registerCommentDisposable.isDisposed) {
            registerCommentDisposable =
                serverRepo.registerComment(RegisterCommentRequest(id, uid, content))
                    .subscribe({
                        if (it.commentId == -1) {
                            view.registerCommentFail()
                        } else {
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

    fun loadComments(id: String) {

        if (!::loadCommentDisposable.isInitialized || loadCommentDisposable.isDisposed) {
            loadCommentDisposable = serverRepo.loadComments(id, nextCommentId.toString())
                .subscribe({
                    if (it.size > 0) {
                        nextCommentId = it[it.size - 1].commentId
                        view.addComments(it)
                        view.loadCommentSuccess()
                    } else {
                        view.noCommentExist()
                    }
                }, {
                    it.printStackTrace()
                    view.loadCommentFail()
                })
        } else {
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

    fun changeButtonState(inputString: String) {
        view.changeRegisterButtonState(inputString.isNotEmpty())
    }

    fun onDestroy() {

        if (!loadCommentDisposable.isDisposed)
            loadCommentDisposable.dispose()

    }

}