/*
 * Created by WonJongSeong on 2019-08-14
 */

package com.example.taxishare.customview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import com.example.taxishare.R
import kotlinx.android.synthetic.main.loading_dialog_layout.view.*

class LoadingDialog(
    @StringRes
    private var messageId: Int = 0
) : DialogFragment() {

    companion object {
        fun newInstance(@StringRes messageId: Int) = LoadingDialog(messageId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        LayoutInflater.from(context).inflate(R.layout.loading_dialog_layout, container, false).apply {
            setStyle(STYLE_NORMAL, R.style.Theme_AppCompat_Light_Dialog_Alert)
            isCancelable = false
            this.tv_loading_message.setText(messageId)
        }


    //    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        view.tv_loading_message.setText(messageId)
//    }
}