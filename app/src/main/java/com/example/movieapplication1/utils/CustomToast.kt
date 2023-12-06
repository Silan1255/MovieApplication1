package com.example.movieapplication1.utils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.example.movieapplication1.utils.ToastMessageType
import kotlinx.android.synthetic.main.item_custom_toast_message.view.*

class CustomToast(private val context: Context) : Toast(context) {
    private val TOAST_TIMEOUT: Long = 2000
    private var LAST_TOAST_TIME: Long = 0

    fun show(message: String, toastMessageType: ToastMessageType) {
        val now = System.currentTimeMillis()
        if (LAST_TOAST_TIME + TOAST_TIMEOUT < now) {
            val layout: View? =
                LayoutInflater.from(context).inflate(toastMessageType.layout, null, false)
            layout!!.item_custom_toast_message.text = message
            val toast = Toast(context.applicationContext)
            toast.duration = LENGTH_LONG
            toast.setGravity(Gravity.TOP or Gravity.FILL_HORIZONTAL, 0, 20)
            toast.view = layout
            toast.show()
            LAST_TOAST_TIME = now
        }
    }
}