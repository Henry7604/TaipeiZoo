package com.example.taipeizoo

import android.app.AlertDialog
import android.content.Context
import android.text.TextUtils

object DialogUtility {
    fun defaultApiError(context: Context, errorMsg: String?, retry: () -> Unit) {
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.api_error_title))
            .setMessage(if (TextUtils.isEmpty(errorMsg)) context.getString(R.string.api_error_msg) else errorMsg)
            .setPositiveButton(context.getString(R.string.retry)) { _, _ ->
                retry()
            }
            .setNegativeButton(context.getString(R.string.cancel)) { _, _ ->
            }.create().show()
    }

    fun defaultNetworkError(context: Context, retry: () -> Unit) {
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.network_error_title))
            .setMessage(context.getString(R.string.network_error_msg))
            .setPositiveButton(context.getString(R.string.retry)) { _, _ ->
                retry()
            }
            .setNegativeButton(context.getString(R.string.cancel)) { _, _ ->
            }.create().show()
    }
}