package com.chooongg.form.core.boundary

import androidx.annotation.Px

data class FormMarginInfo(
    @Px val start: Int,
    @Px val top: Int,
    @Px val end: Int,
    @Px val bottom: Int,
    @Px val middleStart: Int = 0,
    @Px val middleTop: Int = 0,
    @Px val middleEnd: Int = 0,
    @Px val middleBottom: Int = 0
)