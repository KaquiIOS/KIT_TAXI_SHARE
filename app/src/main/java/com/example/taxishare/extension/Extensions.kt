/*
 * Created by WonJongSeong on 2019-07-30
 */

package com.example.taxishare.extension

import android.app.Activity
import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

fun <T> Observable<T>.uiSubscribe(): Observable<T> =
    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())


val NestedScrollView.nestedScrollViewBottomDetectionPublisher: PublishSubject<Boolean> by lazy {
    PublishSubject.create<Boolean>()
}

fun NestedScrollView.observeBottomDetectionPublisher(): PublishSubject<Boolean> = nestedScrollViewBottomDetectionPublisher

fun NestedScrollView.setOnBottomDetection() {
    setOnScrollChangeListener { v: NestedScrollView?,
                                _: Int,
                                scrollY: Int,
                                _: Int,
                                oldScrollY: Int ->

        if (v?.getChildAt(v.childCount - 1) != null && v.childCount != 0) {
            if ((scrollY >= (v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight)) &&
                scrollY > oldScrollY
            ) {
                nestedScrollViewBottomDetectionPublisher.onNext(true)
            }
        }
    }
}

val RecyclerView.recyclerViewBottomDetectionPublisher : PublishSubject<Boolean> by lazy {
   PublishSubject.create<Boolean>()
}

fun RecyclerView.observeBottomDetectionPublisher() : PublishSubject<Boolean> = recyclerViewBottomDetectionPublisher

fun RecyclerView.setOnBottomDetection() {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(!recyclerView.canScrollVertically(1)) {
                recyclerViewBottomDetectionPublisher.onNext(true)
            }
        }
    })
}