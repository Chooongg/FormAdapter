package com.chooongg.form.option

sealed class OptionLoadResult<T> {

    /**
     * 等待状态
     */
    class Wait<T> : OptionLoadResult<T>()

    /**
     * 加载中
     */
    class Loading<T> : OptionLoadResult<T>()

    /**
     * 空结果
     */
    class Empty<T> : OptionLoadResult<T>()

    /**
     * 成功结果
     */
    class Success<T>(val options: List<T>) : OptionLoadResult<T>()

    /**
     * 错误结果
     */
    class Error<T>(val e: Exception) : OptionLoadResult<T>()
}

