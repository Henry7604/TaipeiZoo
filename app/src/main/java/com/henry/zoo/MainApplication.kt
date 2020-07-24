package com.henry.zoo

import android.app.Application
import com.henry.zoo.database.DBHelper
import com.henry.zoo.utility.ResourceProvider

/**
 *
 * @author Henry
 */
class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        DBHelper.init(this)

        ResourceProvider.createInstance(this)
    }
}