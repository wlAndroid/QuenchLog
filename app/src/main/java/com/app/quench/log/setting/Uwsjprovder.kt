package com.app.quench.log.setting

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri

class Uwsjprovder : ContentProvider() {

    private val list = arrayOf(
        "accountName",
        "accountType",
        "displayName",
        "typeResourceId",
        "exportSupport",
        "shortcutSupport",
        "photoSupport"
    )

    override fun delete(uri: Uri, str: String?, strArr: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        return null
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri, strArr: Array<String>?, str: String?, strArr2: Array<String>?, str2: String?
    ): Cursor? {
        context?.let {
            val matrix = MatrixCursor(list)
            matrix.addRow(arrayOf<Any>(it.packageName, it.packageName, it.packageName, 0, 1, 1, 1))
            return matrix
        }
        return null
    }

    override fun update(
        uri: Uri, contentValues: ContentValues?, str: String?, strArr: Array<String>?
    ): Int {
        return 0
    }
}