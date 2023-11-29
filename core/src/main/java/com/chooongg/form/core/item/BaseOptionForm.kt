package com.chooongg.form.core.item

import android.content.Context
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import com.chooongg.form.core.FormOptionLoader
import com.chooongg.form.core.FormUtils
import com.chooongg.form.core.FormViewHolder
import com.chooongg.form.core.enum.FormOptionLoadMode
import com.chooongg.form.core.option.IOption
import com.chooongg.form.core.option.OptionLoadResult
import com.chooongg.form.core.part.BaseFormPartAdapter
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseOptionForm<T>(name: Any?, field: String?) : BaseForm(name, field) {

    companion object {
        const val CHANGE_OPTION_PAYLOAD_FLAG = "CHANGE_OPTION"
    }

    abstract fun hasOpenOperation(): Boolean

    /**
     * 禁用提示: Int(StringRes), String, CharSequence
     */
    var disableHint: Any? = null

    /**
     * 本地设置的选项
     */
    private var localOptions: List<T>? = null

    /**
     * 选项加载模式
     */
    open var optionLoadMode = FormOptionLoadMode.EMPTY

    /**
     * 选项加载器
     */
    private var optionLoader: FormOptionLoader<T>? = null

    /**
     * 选项加载结果
     */
    var optionLoadResult: OptionLoadResult<T> = OptionLoadResult.Wait()
        protected set

    /**
     * 选项
     */
    var options: List<T>?
        get() = localOptions ?: (optionLoadResult as? OptionLoadResult.Success<T>)?.options
        set(value) {
            localOptions = value
        }

    fun isNeedToLoadOption(holder: FormViewHolder): Boolean {
        if (localOptions != null) return false
        if (optionLoader == null) return false
        if (holder.job?.isActive == true) return false
        return when (optionLoadMode) {
            FormOptionLoadMode.ALWAYS -> true
            FormOptionLoadMode.EMPTY -> options.isNullOrEmpty()
        }
    }

    open fun loadOption(
        holder: FormViewHolder,
        notifyUpdate: () -> Unit
    ) {
        if (optionLoader == null) {
            optionLoadResult = OptionLoadResult.Wait()
            notifyUpdate.invoke()
            return
        }
        val adapter = holder.bindingAdapter as? BaseFormPartAdapter ?: return
        holder.job = adapter.adapterScope.launch {
            try {
                withContext(Dispatchers.Main) {
                    optionLoadResult = OptionLoadResult.Loading()
                    notifyUpdate.invoke()
                }
                val result = withContext(Dispatchers.IO) {
                    optionLoader!!.invoke(this@BaseOptionForm)
                }
                withContext(Dispatchers.Main) {
                    optionLoadResult = if (result.isNullOrEmpty()) {
                        OptionLoadResult.Empty()
                    } else OptionLoadResult.Success(result)
                    notifyUpdate.invoke()
                }
            } catch (e: CancellationException) {
                optionLoadResult = OptionLoadResult.Wait()
                holder.job = null
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    optionLoadResult = OptionLoadResult.Error(e)
                    notifyUpdate.invoke()
                }
            }
        }
    }

    fun optionLoader(block: FormOptionLoader<T>?) {
        optionLoader = block
    }

    override fun getContentText(context: Context, enabled: Boolean): CharSequence? {
        if (content == null) return null
        if (content is IOption) {
            val option = content as IOption
            val optionName = option.getOptionName()
            val optionSecondaryName =
                option.getOptionSecondaryName() ?: return option.getOptionName()
            if (optionName == null) {
                return SpannableString(optionSecondaryName).apply {
                    setSpan(
                        RelativeSizeSpan(0.8f),
                        0, length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }
            return SpannableString("$optionName $optionSecondaryName").apply {
                setSpan(
                    RelativeSizeSpan(0.8f),
                    optionName.length + 1, length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        } else {
            return FormUtils.getText(context, content)
        }
    }
}