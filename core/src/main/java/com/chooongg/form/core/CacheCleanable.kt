package com.chooongg.form.core

import androidx.annotation.CallSuper

/**
 * 清除缓存
 */
interface CacheCleanable {

    @CallSuper
    fun cleanCache()
}