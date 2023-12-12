package com.chooongg.form.option

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.FrameLayout
import androidx.annotation.GravityInt
import com.chooongg.form.boundary.Boundary
import com.chooongg.form.core.R
import com.chooongg.form.getTextAppearance
import com.google.android.material.color.MaterialColors
import com.google.android.material.textview.MaterialTextView

class FormArrayAdapter<T>(
    private val boundary: Boundary
) : BaseAdapter(), Filterable {

    private var mOriginalValues: ArrayList<T> = ArrayList()

    private var mObjects: ArrayList<T> = ArrayList()

    @GravityInt
    private var gravity: Int = Gravity.NO_GRAVITY

    var hint: CharSequence? = null

    var current: Any? = null

    fun setNewData(list: List<T>?, gravity: Int?) {
        mObjects = if (list == null) ArrayList() else ArrayList(list)
        if (gravity != null) this.gravity = gravity
        notifyDataSetChanged()
    }

    override fun getCount(): Int = mObjects.size

    override fun getItem(position: Int): T = mObjects[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: FrameLayout(parent.context).also {
            it.addView(MaterialTextView(parent.context).apply {
                id = R.id.formInternalContentView
                setTextAppearance(getTextAppearance(this, R.attr.formTextAppearanceContent))
                setPaddingRelative(boundary.start, boundary.top, boundary.end, boundary.bottom)
            })
            it.layoutParams = ViewGroup.LayoutParams(-1, -1)
        }
        val textView = view.findViewById<MaterialTextView>(R.id.formInternalContentView)
        val item = getItem(position)
        textView.hint = hint
        textView.text = when (item) {
            is CharSequence -> item
            is IOption -> item.getSpannableString(view.context)
            else -> item.toString()
        }
        textView.setTextColor(
            if (current == item || (item is CharSequence && current == item.toString())) {
                MaterialColors.getColor(textView, com.google.android.material.R.attr.colorPrimary)
            } else {
                MaterialColors.getColor(textView, com.google.android.material.R.attr.colorOnSurface)
            }
        )
        textView.gravity = gravity
        return view
    }

    private lateinit var _filter: FormFilter

    override fun getFilter(): Filter {
        if (!this::_filter.isInitialized) {
            _filter = FormFilter()
        }
        return _filter
    }

    private inner class FormFilter : Filter() {

        private val mLock = Any()

        override fun performFiltering(prefix: CharSequence?): FilterResults {
            val results = FilterResults()
            if (mOriginalValues.isEmpty()) {
                synchronized(mLock) {
                    mOriginalValues.addAll(mObjects)
                }
            }
            if (prefix.isNullOrEmpty()) {
                synchronized(mLock) {
                    val list = ArrayList<T>(mOriginalValues)
                    results.values = list
                    results.count = list.size
                }
            } else {
                val newValues = synchronized(mLock) {
                    mOriginalValues.filter {
                        val regex = Regex(".*${prefix.toString().lowercase()}.*")
                        val value = it?.toString() ?: return@filter false
                        regex.matches(value.lowercase())
                    }
                }
                results.values = newValues
                results.count = newValues.size
            }
            return results
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            mObjects = results.values as ArrayList<T>
            if (results.count > 0) notifyDataSetChanged() else notifyDataSetInvalidated()
        }
    }
}