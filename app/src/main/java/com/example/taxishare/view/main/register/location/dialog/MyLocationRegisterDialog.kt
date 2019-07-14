/*
 * Created by WonJongSeong on 2019-07-14
 */

package com.example.taxishare.view.main.register.location.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.taxishare.R
import com.example.taxishare.data.local.room.entity.MyLocationModel
import com.example.taxishare.data.model.Location
import com.example.taxishare.data.repo.MyLocationRepository
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.dialog_mylocation_register.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.textChangedListener
import org.jetbrains.anko.support.v4.toast
import java.util.*

class MyLocationRegisterDialog(
    private val myLocationRepo: MyLocationRepository,
    private val selectedLocation: Location
) : DialogFragment() {

    private lateinit var textChangeDisposable: Disposable

    companion object {
        fun newInstance(myLocationRepo: MyLocationRepository, selectedLocation: Location) =
            MyLocationRegisterDialog(myLocationRepo, selectedLocation)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.dialog_mylocation_register, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false

        btn_register_dialog_ok.onClick {
            with(selectedLocation) {
                myLocationRepo.insert(
                    MyLocationModel(
                        et_register_dialog_name.text.toString(),
                        latitude, longitude, locationName,
                        roadAddress, jibunAddress, Date()
                    )
                ).subscribe({
                    toast(resources.getString(R.string.location_save_complete))
                    dismiss()
                }, {
                    it.printStackTrace()
                    dismiss()
                })
            }
        }

        btn_register_dialog_cancel.onClick {
            dismiss()
        }

        textChangeDisposable = et_register_dialog_name.textChanges().subscribe {
            changeButtonState(et_register_dialog_name.text.length > 1)
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        if (::textChangeDisposable.isInitialized && !textChangeDisposable.isDisposed)
            textChangeDisposable.dispose()
    }

    private fun changeButtonState(state: Boolean) {
        btn_register_dialog_ok.isEnabled = state
    }
}