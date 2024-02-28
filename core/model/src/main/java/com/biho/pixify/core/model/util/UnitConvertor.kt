package com.biho.pixify.core.model.util

object UnitConvertor {

    fun coerceAspectRatio(width: Int, height: Int): Float {
        return (width.toFloat() / height)
            .coerceIn(
                minimumValue = 0.5f,
                maximumValue = 2.5f,
            )
    }

}
