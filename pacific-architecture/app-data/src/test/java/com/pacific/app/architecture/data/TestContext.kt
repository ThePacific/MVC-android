package com.pacific.app.architecture.data

import com.pacific.app.architecture.data.base.AppContext
import java.io.File

object TestContext : AppContext {
    
    override fun getCacheDir(): File {
        TODO("Not yet implemented")
    }

    override fun getFilesDir(): File {
        TODO("Not yet implemented")
    }

    override fun getDatabasePath(name: String): File {
        TODO("Not yet implemented")
    }

    override fun getExternalCacheDir(): File {
        TODO("Not yet implemented")
    }

    override fun getExternalFilesDir(type: String?): File {
        TODO("Not yet implemented")
    }
}