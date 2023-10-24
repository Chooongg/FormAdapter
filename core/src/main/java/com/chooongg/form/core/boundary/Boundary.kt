package com.chooongg.form.core.boundary

import androidx.annotation.IntDef

data class Boundary(
    @BoundaryInt val start: Int,
    @BoundaryInt val top: Int,
    @BoundaryInt val end: Int,
    @BoundaryInt val bottom: Int
) {
    constructor() : this(NONE)
    constructor(any: Int) : this(any, any, any, any)

    companion object {
        const val NONE = 0
        const val MIDDLE = 1
        const val MARGIN = 2
    }

    internal fun update(
        @BoundaryInt start: Int = this.start,
        @BoundaryInt top: Int = this.top,
        @BoundaryInt end: Int = this.end,
        @BoundaryInt bottom: Int = this.bottom
    ) = Boundary(start, top, end, bottom)

    @IntDef(NONE, MIDDLE, MARGIN)
    annotation class BoundaryInt
}
