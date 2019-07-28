/*
 * Created by WonJongSeong on 2019-07-19
 */

package com.example.taxishare.view.main.taxisharelist.detail

import com.example.taxishare.data.model.Comment
import com.example.taxishare.data.remote.apis.server.request.RegisterCommentRequest
import com.example.taxishare.data.repo.ServerRepository
import io.reactivex.disposables.Disposable

class TaxiShareInfoDetailPresenter(
    private val view: TaxiShareInfoDetailView,
    private val serverRepo: ServerRepository
) {


    private lateinit var registerCommentDisposable: Disposable
    private lateinit var loadCommentDisposable: Disposable


    fun registerComment(id: String, uid: String, content: String) {

        if (!::registerCommentDisposable.isInitialized || registerCommentDisposable.isDisposed) {
            registerCommentDisposable =
                serverRepo.registerComment(RegisterCommentRequest(id, uid, content))
                    .subscribe({
                        view.registerCommentSuccess()
                    }, {
                        it.printStackTrace()
                        view.registerCommentFail()
                    })
        } else {
            view.registerCommentNotFinish()
        }
    }

    fun loadComments(id: String, commentId: Int) {

        if (!::loadCommentDisposable.isInitialized || loadCommentDisposable.isDisposed) {
            loadCommentDisposable = serverRepo.loadComments(id, commentId.toString())
                .subscribe({
                    if(it.size > 0) {
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

    fun changeButtonState(inputString: String) {
        view.changeRegisterButtonState(inputString.isNotEmpty())
    }

    fun onDestroy() {

        if (!loadCommentDisposable.isDisposed)
            loadCommentDisposable.dispose()

    }

}