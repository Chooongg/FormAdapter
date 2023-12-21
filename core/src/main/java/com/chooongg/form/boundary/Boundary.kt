package com.chooongg.form.boundary

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
        const val NOTIFY_BOUNDARY_FLAG = "FormUpdateBoundary"

        const val NONE = 0
        const val MIDDLE_NO_MARGIN = 1
        const val MIDDLE = 2
        const val GLOBAL = 3
    }

    @IntDef(NONE, MIDDLE, GLOBAL)
    annotation class BoundaryInt

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Boundary) return false

        if (start != other.start) return false
        if (top != other.top) return false
        if (end != other.end) return false
        if (bottom != other.bottom) return false

        return true
    }

    override fun hashCode(): Int {
        var result = start
        result = 31 * result + top
        result = 31 * result + end
        result = 31 * result + bottom
        return result
    }
}
