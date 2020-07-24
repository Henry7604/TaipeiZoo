package com.henry.zoo.utility

import android.content.Context

/**
 * ResourceProvider
 *
 * @author Henry
 */
class ResourceProvider private constructor(context: Context) {

    init {
        mContext = context
    }

    companion object {
        private lateinit var mContext: Context
        @Volatile
        protected var sInst: ResourceProvider? = null


        fun createInstance(context: Context) {
            if (sInst == null) {
                sInst = ResourceProvider(context)
            }
        }

        fun getString(resId: Int): String {
            return mContext.getString(resId)
        }

        fun getString(resId: Int, value: String): String {
            return mContext.getString(resId, value)
        }
    }
}
