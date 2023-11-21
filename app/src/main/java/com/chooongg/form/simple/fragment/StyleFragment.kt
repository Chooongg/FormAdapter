package com.chooongg.form.simple.fragment

import android.os.Bundle
import android.view.View
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.addSlider
import com.chooongg.form.core.addText
import com.chooongg.form.core.data.FormPartData
import com.chooongg.form.core.multiColumn
import com.chooongg.form.core.singleLine
import com.chooongg.form.core.style.CardElevatedStyle
import com.chooongg.form.core.style.CardFilledStyle
import com.chooongg.form.core.style.CardOutlinedStyle
import com.chooongg.form.core.style.NoneStyle
import com.chooongg.form.core.typeset.VerticalTypeset
import com.chooongg.form.simple.R

class StyleFragment : BaseFragment() {

    private val adapter = FormAdapter(true)

    override fun change() {
        adapter.isEnabled = !adapter.isEnabled
    }

    override fun smoothScroll() {
        formView.smoothScrollToPosition(35)
    }

    override fun errorNotify() {
        adapter.errorNotifyOfField("input")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbar.setTitle(R.string.style)
        formView.setFormAdapter(adapter)
        adapter.setNewInstance {
            addPart {
                partName = "NoneNotAlignmentStyle"
                addChild()
            }
            addPart(NoneStyle()) {
                partName = "NoneStyle"
                addChild()
            }
            addPart(CardElevatedStyle()) {
                partName = "CardElevatedStyle"
                addChild()
            }
            addPart(CardFilledStyle()) {
                partName = "CardFilledStyle"
                addChild()
            }
            addPart(CardOutlinedStyle()) {
                partName = "CardOutlinedStyle"
                addChild()
            }
        }
    }

    private fun FormPartData.addChild() {
        addText("Style") {
            content = "StyleTest"
        }
        addText("Style") {
            content = "StyleTest"
        }
        addText("Style") {
            content = "StyleTest"
        }
        singleLine {
            for (i in 1..5) {
                addSlider("Style${i}") {
                    typeset = VerticalTypeset()
                }
            }
        }
        multiColumn {
            column = 2f
            for (i in 1..5) {
                addSlider("Style${i}") {
                    typeset = VerticalTypeset()
                }
            }
        }
        addText("Style") {
            content = "StyleTest"
        }
    }
}