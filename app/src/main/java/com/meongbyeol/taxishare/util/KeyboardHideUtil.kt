package com.meongbyeol.taxishare.util

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

object KeyboardHideUtil {

    fun keyboardStateChange(activity: Activity, hide : Boolean) {
        val imm : InputMethodManager = activity.getSystemService (Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view : View? = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View (activity)
        }

        if(hide) {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        } else {
            imm.showSoftInput(view, 0)
        }
    }
}