package com.chooongg.form.boundary

import androidx.annotation.Px

data class FormInsideInfo(
    @Px val start: Int,
    @Px val top: Int,
    @Px val end: Int,
    @Px val bottom: Int,
    @Px val middleStart: Int = start / 2,
    @Px val middleTop: Int = top / 2,
    @Px val middleEnd: Int = end / 2,
    @Px val middleBottom: Int = bottom / 2
)