package com.chooongg.form.simple.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.chooongg.form.core.FormView
import com.chooongg.form.simple.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar

abstract class BaseFragment : Fragment() {

    protected lateinit var appBarLayout: AppBarLayout

    protected lateinit var toolbar: MaterialToolbar

    protected lateinit var formView: FormView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_base, container, false)
        appBarLayout = view.findViewById(R.id.appbarLayout)
        toolbar = view.findViewById(R.id.toolbar)
        formView = view.findViewById(R.id.formView)
        configWindowInset(view)
        return view
    }

    private fun configWindowInset(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
            val inset = insets.getInsets(WindowInsetsCompat.Type.displayCutout())
            if (appBarLayout.isLiftOnScroll) {
                formView.setPadding(inset.left, 0, inset.right, 0)
            } else {
                if (formView.layoutDirection == View.LAYOUT_DIRECTION_LTR) {
                    formView.setPadding(0, 0, inset.right, 0)
                } else {
                    formView.setPadding(inset.left, 0, 0, 0)
                }
            }
            insets
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}