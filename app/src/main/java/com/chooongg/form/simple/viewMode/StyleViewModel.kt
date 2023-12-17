package com.chooongg.form.simple.viewMode

import androidx.lifecycle.ViewModel
import com.chooongg.form.FormAdapter
import com.chooongg.form.addSlider
import com.chooongg.form.addText
import com.chooongg.form.data.FormPartData
import com.chooongg.form.initCardElevatedPart
import com.chooongg.form.initCardFilledPart
import com.chooongg.form.initCardOutlinedPart
import com.chooongg.form.initCardSeparateElevatedPart
import com.chooongg.form.initCardSeparateFilledPart
import com.chooongg.form.initCardSeparateOutlinedPart
import com.chooongg.form.initNotAlignmentPart
import com.chooongg.form.typeset.VerticalTypeset

class StyleViewModel : ViewModel() {

    val adapter = FormAdapter(true)

    init {
        adapter.setNewInstance {
            initPart {
                partName = "NoneStyle"
                addChild()
            }
            initNotAlignmentPart {
                partName = "NotAlignmentStyle"
                addChild()
            }
            initCardElevatedPart {
                partName = "CardElevatedStyle"
                addChild()
            }
            initCardFilledPart {
                partName = "CardFilledStyle"
                addChild()
            }
            initCardOutlinedPart {
                partName = "CardOutlinedStyle"
                addChild()
            }
            initCardSeparateElevatedPart {
                partName = "CardSeparateElevatedStyle"
                addChild()
            }
            initCardSeparateFilledPart {
                partName = "CardSeparateFilledStyle"
                addChild()
            }
            initCardSeparateOutlinedPart {
                partName = "CardSeparateOutlinedStyle"
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
        childGroup {
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