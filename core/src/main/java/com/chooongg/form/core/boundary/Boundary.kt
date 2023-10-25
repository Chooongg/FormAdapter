package com.chooongg.form.core.boundary

import androidx.annotation.IntDef

class Boundary(
    @BoundaryInt start: Int,
    @BoundaryInt top: Int,
    @BoundaryInt end: Int,
    @BoundaryInt bottom: Int
) {

    @BoundaryInt
    var start: Int = start
        internal set

    @BoundaryInt
    var top: Int = top
        internal set

    @BoundaryInt
    var end: Int = end
        internal set

    @BoundaryInt
    var bottom: Int = bottom
        internal set

    constructor() : this(NONE)
    constructor(any: Int) : this(any, any, any, any)

    companion object {
        const val NONE = 0
        const val MIDDLE = 1
        const val GLOBAL = 2
    }

    @IntDef(NONE, MIDDLE, GLOBAL)
    annotation class BoundaryInt
}
