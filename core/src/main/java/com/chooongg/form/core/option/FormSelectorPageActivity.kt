package com.chooongg.form.core.option

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.res.use
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.form.core.FormUtils
import com.chooongg.form.core.R
import com.chooongg.form.core.databinding.FormActivityAdapterSelectorPageBinding
import com.chooongg.form.core.getTextAppearance
import com.chooongg.form.core.item.FormSelector
import com.google.android.material.button.MaterialButton
import com.google.android.material.motion.MotionUtils
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.google.android.material.transition.platform.MaterialSharedAxis
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FormSelectorPageActivity : AppCompatActivity() {

    internal object Controller {
        var formSelector: FormSelector? = null
        var resultBlock: ((IOption?) -> Unit)? = null
    }

    private val binding by lazy { FormActivityAdapterSelectorPageBinding.inflate(layoutInflater) }

    private val concatAdapter = ConcatAdapter()

    private val optionAdapter = OptionAdapter(Controller.formSelector?.content) {
        Controller.resultBlock?.invoke(it)
        finishAfterTransition()
        Controller.formSelector = null
        Controller.resultBlock = null
    }.apply { concatAdapter.addAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configWindow()
        setContentView(binding.root)
        binding.editSearch.setTextAppearance(
            getTextAppearance(binding.editSearch, R.attr.formTextAppearanceSearch)
        )
        if (Controller.formSelector == null || Controller.resultBlock == null) {
            finishAfterTransition()
            return
        }
        if (Controller.formSelector?.required == false) {
            val nullAdapter = NullAdapter(FormUtils.getText(this, Controller.formSelector?.hint)) {
                Controller.resultBlock?.invoke(it)
                finishAfterTransition()
                Controller.formSelector = null
                Controller.resultBlock = null
            }
            concatAdapter.addAdapter(0, nullAdapter)
        }
        binding.recyclerView.adapter = concatAdapter
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    binding.inputSearch.clearFocus()
                    FormUtils.hideIme(binding.inputSearch)
                }
            }
        })
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        val options = Controller.formSelector?.options
        optionAdapter.submit(options)
        var job: Job? = null
        binding.editSearch.doAfterTextChanged {
            job?.cancel()
            job = null
            val search = binding.editSearch.text?.toString()
            if (search.isNullOrEmpty()) {
                optionAdapter.submit(options)
            } else {
                job = lifecycleScope.launch {
                    val searchList = options?.filter {
                        val regex = Regex(".*${search.lowercase()}.*")
                        val name = it.getOptionName()
                        if (name != null) {
                            return@filter regex.matches(name.lowercase())
                        }
                        val secondary = it.getOptionSecondaryName()
                        if (secondary != null) {
                            return@filter regex.matches(secondary.lowercase())
                        }
                        false
                    }
                    withContext(Dispatchers.Main) { optionAdapter.submit(searchList) }
                }
            }
        }
    }

    private fun configWindow() {
        createEdgeToEdge()
        with(window) {
            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
            sharedElementsUseOverlay = false
            enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
            exitTransition = null
            returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)
            reenterTransition = null
            transitionBackgroundFadeDuration = MotionUtils.resolveThemeDuration(
                context, com.google.android.material.R.attr.motionDurationLong1, -1
            ).toLong()
        }
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        findViewById<FrameLayout>(android.R.id.content).transitionName = "FormSelectorPage"
        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementEnterTransition = buildContainerTransform(true)
        window.sharedElementReturnTransition = buildContainerTransform(false)
    }

    private fun createEdgeToEdge() {
        val isWindowActionBar =
            obtainStyledAttributes(intArrayOf(androidx.appcompat.R.attr.windowActionBar))
                .use { it.getBoolean(0, false) }
        if (isWindowActionBar) return
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT
        ViewCompat.setOnApplyWindowInsetsListener(binding.recyclerView) { v, insets ->
            val inset = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updateLayoutParams<CoordinatorLayout.LayoutParams> {
                bottomMargin = inset.bottom
            }
            insets
        }
    }

    private fun buildContainerTransform(entering: Boolean): MaterialContainerTransform {
        val transform = MaterialContainerTransform(this, entering)
        transform.addTarget(android.R.id.content)
        transform.fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
        transform.pathMotion = MaterialArcMotion()
        return transform
    }

    private class NullAdapter(
        private val hint: CharSequence?,
        private val selectBlock: (IOption?) -> Unit
    ) : RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(
                MaterialButton(
                    parent.context, null, com.google.android.material.R.attr.borderlessButtonStyle
                ).apply {
                    gravity = Gravity.CENTER_VERTICAL
                    setTextAppearance(getTextAppearance(this, R.attr.formTextAppearanceContent))
                    setPaddingRelative(
                        context.resources.getDimensionPixelSize(R.dimen.formMarginMiddleStart),
                        context.resources.getDimensionPixelSize(R.dimen.formMarginMiddleTop),
                        context.resources.getDimensionPixelSize(R.dimen.formMarginMiddleEnd),
                        context.resources.getDimensionPixelSize(R.dimen.formMarginMiddleBottom),
                    )
                    layoutParams = ViewGroup.MarginLayoutParams(
                        ViewGroup.MarginLayoutParams.MATCH_PARENT,
                        ViewGroup.MarginLayoutParams.WRAP_CONTENT
                    ).apply {
                        marginStart =
                            context.resources.getDimensionPixelSize(R.dimen.formMarginStart) - paddingStart
                        marginEnd =
                            context.resources.getDimensionPixelSize(R.dimen.formMarginEnd) - paddingEnd
                    }
                })

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            with(holder.itemView as MaterialButton) {
                hint = this@NullAdapter.hint ?: resources.getString(R.string.formDefaultHintSelect)
                setOnClickListener {
                    selectBlock.invoke(null)
                }
            }
        }

        override fun getItemCount() = 1
    }

    private class OptionAdapter(
        private val selected: Any?,
        private val selectBlock: (IOption?) -> Unit
    ) : RecyclerView.Adapter<ViewHolder>() {

        private val asyncDiffer =
            AsyncListDiffer(this, object : DiffUtil.ItemCallback<IOption>() {
                override fun areItemsTheSame(oldItem: IOption, newItem: IOption) =
                    oldItem.getOptionName() == newItem.getOptionName() &&
                            oldItem.getOptionSecondaryName() == newItem.getOptionSecondaryName()

                override fun areContentsTheSame(oldItem: IOption, newItem: IOption) =
                    oldItem.getValue() == newItem.getValue()
            })

        fun submit(options: List<IOption>?) {
            asyncDiffer.submitList(options)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(
                MaterialButton(
                    parent.context, null, com.google.android.material.R.attr.borderlessButtonStyle
                ).apply {
                    gravity = Gravity.CENTER_VERTICAL
                    setTextAppearance(getTextAppearance(this, R.attr.formTextAppearanceContent))
                    setPaddingRelative(
                        context.resources.getDimensionPixelSize(R.dimen.formMarginMiddleStart),
                        context.resources.getDimensionPixelSize(R.dimen.formMarginMiddleTop),
                        context.resources.getDimensionPixelSize(R.dimen.formMarginMiddleEnd),
                        context.resources.getDimensionPixelSize(R.dimen.formMarginMiddleBottom),
                    )
                    layoutParams = ViewGroup.MarginLayoutParams(
                        ViewGroup.MarginLayoutParams.MATCH_PARENT,
                        ViewGroup.MarginLayoutParams.WRAP_CONTENT
                    ).apply {
                        marginStart =
                            context.resources.getDimensionPixelSize(R.dimen.formMarginStart) - paddingStart
                        marginEnd =
                            context.resources.getDimensionPixelSize(R.dimen.formMarginEnd) - paddingEnd
                    }
                })

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val option = asyncDiffer.currentList[position]
            with(holder.itemView as MaterialButton) {
                val span = option.getSpannableString(context).apply {
                    setSpan(
                        ForegroundColorSpan(
                            if (selected == option) {
                                context.obtainStyledAttributes(
                                    intArrayOf(com.google.android.material.R.attr.colorPrimary)
                                ).use { it.getColor(0, Color.GRAY) }
                            } else {
                                context.obtainStyledAttributes(
                                    intArrayOf(com.google.android.material.R.attr.colorOnSurface)
                                ).use { it.getColor(0, Color.GRAY) }
                            }
                        ), 0, length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                text = span
                setOnClickListener { selectBlock(option) }
            }
        }

        override fun getItemCount() = asyncDiffer.currentList.size
    }

    private class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}