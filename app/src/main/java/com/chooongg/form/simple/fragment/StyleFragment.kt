package com.chooongg.form.simple.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chooongg.form.core.FormAdapter
import com.chooongg.form.core.addText
import com.chooongg.form.core.style.CardStyle
import com.chooongg.form.core.style.NoneStyle
import com.chooongg.form.simple.databinding.FragmentStyleBinding

class StyleFragment : Fragment() {

    private lateinit var binding: FragmentStyleBinding

    private val adapter = FormAdapter(true)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentStyleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.formView.setFormAdapter(adapter)
        adapter.setNewInstance {
            addPart {
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
            addPart(CardStyle()) {
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