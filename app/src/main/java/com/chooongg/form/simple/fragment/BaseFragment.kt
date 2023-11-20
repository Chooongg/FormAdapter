package com.chooongg.form.simple.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chooongg.form.core.FormView
import com.chooongg.form.simple.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.shape.MaterialShapeDrawable

abstract class BaseFragment : Fragment() {

    private lateinit var appBarLayout: AppBarLayout

    protected lateinit var toolbar: MaterialToolbar

    protected lateinit var formView: FormView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_base, container, false)
        appBarLayout = view.findViewById(R.id.appbarLayout)
        appBarLayout.addLiftOnScrollListener { _, backgroundColor ->
            activity?.window?.statusBarColor = backgroundColor
        }
        toolbar = view.findViewById(R.id.toolbar)
        formView = view.findViewById(R.id.formView)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.change -> change()
                R.id.scroll -> smoothScroll()
                else -> return@setOnMenuItemClickListener false
            }

            true
        }
        return view
    }

    abstract fun change()

    abstract fun smoothScroll()

    override fun onResume() {
        super.onResume()
        val color = (appBarLayout.background as? MaterialShapeDrawable)?.fillColor?.defaultColor
        if (color != null && activity != null) {
            requireActivity().window.statusBarColor = color
        }
    }
}