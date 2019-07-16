/*
 * Created by WonJongSeong on 2019-04-13
 */

package com.example.taxishare.view.signup

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import com.example.taxishare.R
import com.example.taxishare.app.Constant
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gun0912.tedpermission.TedPermissionResult
import com.jakewharton.rxbinding3.view.clicks
import com.tedpark.tedpermission.rx2.TedRx2Permission
import io.reactivex.Single
import kotlinx.android.synthetic.main.bottom_sheet_camera_image_pick.*

class BottomSheetFragment : BottomSheetDialogFragment() {

    private var fragmentView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.bottom_sheet_camera_image_pick, container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    @SuppressWarnings("all")
    private fun initView() {
        // bottom sheet init
        text_view_bottom_sheet_camera.clicks().subscribe({
            requestPermission(
                R.string.sign_up_external_permission_require,
                arrayOf(Manifest.permission.CAMERA)
            )
                .subscribe({
                    if (it.isGranted)
                        openCamera()
                    else
                        dismiss()
                }, {
                    it.stackTrace[0]
                })
        }, {
            it.stackTrace[0]
        })

        text_view_bottom_sheet_gallery.clicks().subscribe({
            requestPermission(
                R.string.sign_up_external_permission_require,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            )
                .subscribe({
                    if (it.isGranted)
                        openGallery()
                    else
                        dismiss()
                }, {
                    it.stackTrace[0]
                })
        }, {
            it.stackTrace[0]
        })
    }

    // kotlin array -> java vararg
    private fun requestPermission(@StringRes id: Int, permissionList: Array<String>): Single<TedPermissionResult> =
        TedRx2Permission.with(context)
            .setDeniedMessage(id)
            .setPermissions(*permissionList)
            .request()

    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(activity!!.packageManager)?.also {
                activity!!.startActivityForResult(takePictureIntent, Constant.CAMERA_REQUEST_CODE)
            }
        }
    }

    private fun openGallery() {
        Intent().apply {
            action = Intent.ACTION_GET_CONTENT
            type = "image/*"
            activity!!.startActivityForResult(this, Constant.GALLERY_REQUEST_CODE)
            dismiss()
        }
    }
}