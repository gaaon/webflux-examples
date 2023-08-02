package com.grizz.wooman.coroutine.context

import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

public data class CoroutineName2(
    /**
     * User-defined coroutine name.
     */
    val name: String
) : AbstractCoroutineContextElement(CoroutineName2) {
    public companion object Key : CoroutineContext.Key<CoroutineName2>

    override fun toString(): String = "CoroutineName($name)"
}