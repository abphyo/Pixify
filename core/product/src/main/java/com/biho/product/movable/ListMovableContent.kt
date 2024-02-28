package com.biho.product.movable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember

@Composable
fun <T> List<T>.movable(
    transform: @Composable (item: T) -> Unit
): @Composable (item: T) -> Unit {
    val composedItem = remember(key1 = this) {
        mutableMapOf<T, @Composable () -> Unit>()
    }
    return { item: T ->
        composedItem.getOrPut(item) {
            movableContentOf { transform(item) }
        }.invoke()
    }
}