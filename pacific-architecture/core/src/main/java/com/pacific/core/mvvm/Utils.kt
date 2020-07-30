package com.pacific.core.mvvm

import androidx.activity.result.contract.ActivityResultContracts

@get:JvmName("startActivityForResult")
val startActivityForResult by lazy { ActivityResultContracts.StartActivityForResult() }

@get:JvmName("startIntentSenderForResult")
val startIntentSenderForResult by lazy { ActivityResultContracts.StartIntentSenderForResult() }

@get:JvmName("requestMultiplePermissions")
val requestMultiplePermissions by lazy { ActivityResultContracts.RequestMultiplePermissions() }

@get:JvmName("requestPermission")
val requestPermission by lazy { ActivityResultContracts.RequestPermission() }

@get:JvmName("takePicturePreview")
val takePicturePreview by lazy { ActivityResultContracts.TakePicturePreview() }

@get:JvmName("takePicture")
val takePicture by lazy { ActivityResultContracts.TakePicture() }

@get:JvmName("takeVideo")
val takeVideo by lazy { ActivityResultContracts.TakeVideo() }

@get:JvmName("pickContact")
val pickContact by lazy { ActivityResultContracts.PickContact() }

@get:JvmName("getContent")
val getContent by lazy { ActivityResultContracts.GetContent() }

@get:JvmName("getMultipleContents")
val getMultipleContents by lazy { ActivityResultContracts.GetMultipleContents() }

@get:JvmName("openDocument")
val openDocument by lazy { ActivityResultContracts.OpenDocument() }

@get:JvmName("openMultipleDocuments")
val openMultipleDocuments by lazy { ActivityResultContracts.OpenMultipleDocuments() }

@get:JvmName("openDocumentTree")
val openDocumentTree by lazy { ActivityResultContracts.OpenDocumentTree() }

@get:JvmName("createDocument")
val createDocument by lazy { ActivityResultContracts.CreateDocument() }