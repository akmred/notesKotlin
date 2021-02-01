package com.example.notes.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.notes.R

class LogOutDialog: DialogFragment {

    companion object{
        val TAG = LogOutDialog::class.java.name + "TAG"
        fun createInstance() = LogOutDialog()
    }

    override fun OnCreateDialog(saveInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(context!!)
            .setTitle(R.string.logout_dialog_titile)
            .setMessage(R.string.logout_dialog_message)
            .setPositiveButton(R.string.ok_bth_title) {_, _ -> (activity as LogoutListener).onLogout()}
            .setNegativeButton(R.string.logout_dialog_cancel) {_, _ -> dismiss()}
            .create()

    interface LogoutListener{
        fun onLogout()
    }
}