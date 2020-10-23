package com.pacific.guava.android.mvvm

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.result.ActivityResult

interface ForActivityResultCallback {

    fun onGetContent(uri: Uri)

    fun onGetMultipleContents(uris: List<Uri>)

    fun onCreateDocument(uri: Uri)

    fun onOpenDocument(uri: Uri)

    fun onOpenDocumentTree(uri: Uri)

    fun onOpenMultipleDocuments(uris: List<Uri>)

    fun onRequestMultiplePermissions(map: Map<String, Boolean>)

    fun onRequestPermission(isPermissionGranted: Boolean)

    fun onPickContact(uri: Uri)

    fun onStartActivityForResult(result: ActivityResult)

    fun onStartIntentSenderForResult(result: ActivityResult)

    fun onTakePicture(isPictureSaved: Boolean)

    fun onTakePicturePreview(bitmap: Bitmap)

    fun onTakeVideo(bitmap: Bitmap)
}