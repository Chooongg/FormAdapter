package com.chooongg.form.simple.fragment

import android.os.Bundle
import android.view.View
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.addText
import com.chooongg.form.core.style.CardElevatedStyle
import com.chooongg.form.core.style.CardFilledStyle
import com.chooongg.form.core.style.NoneStyle
import com.chooongg.form.simple.R

class StyleFragment : BaseFragment() {

    private val adapter = FormAdapter(true)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbar.setTitle(R.string.style)
        formView.setFormAdapter(adapter)
        adapter.setNewInstance {
            addPart {
                partName = "Default"
                addText("Style") {
                    content = "Default"
                }
                addText("Style") {
                    content = "Default"
                }
                addText("Style") {
                    content = "Default"
                }
                addText("Style") {
                    content = "Default"
                }
            }
            addPart(NoneStyle()) {
                partName = "NoneStyle"
                addText("Style") {
                    content = "Default"
                }
                addText("Style") {
                    content = "Default"
                }
                addText("Style") {
                    content = "Default"
                }
                addText("Style") {
                    content = "Default"
                }
            }
            addPart(CardElevatedStyle()) {
                partName = "CardStyle"
                addText("Style") {
                    content = "Default"
                }
                addText("Style") {
                    content = "Default"
                }
                addText("Style") {
                    content = "Default"
                }
                addText("Style") {
                    content = "Default"
                }
            }
            addPart(CardFilledStyle()) {
                partName = "CardStyle"
                addText("Style") {
                    content = "Default"
                }
                addText("Style") {
                    content = "Default"
                }
                addText("Style") {
                    content = "Default"
                }
                addText("Style") {
                    content = "Default"
                }
            }
            addPart(CardElevatedStyle()) {
                partName = "CardStyle"
                addText("Style") {
                    content = "Default"
                }
                addText("Style") {
                    content = "Default"
                }
                addText("Style") {
                    content = "Default"
                }
                addText("Style") {
                    content = "Default"
                }
            }
            addPart(CardElevatedStyle()) {
                partName = "CardStyle"
                addText("Style") {
                    content = "Default"
                }
                addText("Style") {
                    content = "Default"
                }
                addText("Style") {
                    content = "Default"
                }
                addText("Style") {
                    content = "Default"
                }
            }
        }
    }
}