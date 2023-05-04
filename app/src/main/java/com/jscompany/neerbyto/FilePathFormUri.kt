package com.jscompany.neerbyto

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.loader.content.CursorLoader


class FilePathFormUri  {

    companion object {
        //Uri -- > 절대경로로 바꿔서 리턴시켜주는 메소드
        fun getFilePathFromUri(uri: Uri, context:Context): String {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            val loader = CursorLoader(context, uri, proj, null, null, null)
            val cursor: Cursor = loader.loadInBackground()!!
            val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            val result: String = cursor.getString(column_index)
            cursor.close()
            return result
        }

        fun getFilePathFromUri2(uri: Uri, context:Context): String {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            val loader = CursorLoader(context, uri, proj, null, null, null)
            val cursor: Cursor = loader.loadInBackground()!!
            val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            var result: String = ""
            if (cursor.getType(column_index) == Cursor.FIELD_TYPE_STRING) {
                result = cursor.getString(column_index);
            }
            cursor.close()
            return result
        }

    }

}