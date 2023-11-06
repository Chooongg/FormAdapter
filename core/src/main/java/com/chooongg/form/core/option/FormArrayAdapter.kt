package com.chooongg.form.core.option

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import com.chooongg.form.core.FormManager
import com.chooongg.form.core.R
import com.chooongg.form.core.getTextAppearance
import com.google.android.material.textview.MaterialTextView

class FormArrayAdapter<T>(list: List<T>?) : BaseAdapter(), Filterable {

    private var mOriginalValues: ArrayList<T> = ArrayList()

    private var mObjects: ArrayList<T>

    init {
        mObjects = if (list == null) ArrayList() else ArrayList(list)
    }

    override fun getCount(): Int = mObjects.size

    override fun getItem(position: Int): T = mObjects[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: MaterialTextView(parent.context).apply {
            setTextAppearance(getTextAppearance(this, R.attr.formTextAppearanceContent))
            setPaddingRelative(
                FormManager.Default.style.insideInfo.middleStart,
                FormManager.Default.style.insideInfo.middleTop,
                FormManager.Default.style.insideInfo.middleEnd,
                FormManager.Default.style.insideInfo.middleBottom
            )
        }
        val text = view as MaterialTextView
        val item = getItem(position)
        text.text = if (item is CharSequence) item else item.toString()
        return view
    }

    override fun getFilter(): Filter = FormFilter()

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