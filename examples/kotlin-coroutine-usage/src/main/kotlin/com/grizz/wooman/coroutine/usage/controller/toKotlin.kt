package com.grizz.wooman.coroutine.usage.controller

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactor.asFlux
import kotlinx.coroutines.reactor.mono
import org.reactivestreams.Publisher
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.*
import kotlin.reflect.full.callSuspend
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.kotlinFunction

class toKotlin {
    private fun getSuspendedFunctionArgs(target: Any, vararg args: Any): Array<Any?>? {
        val functionArgs = arrayOfNulls<Any>(args.size)
        functionArgs[0] = target
        System.arraycopy(args, 0, functionArgs, 1, args.size - 1)
        return functionArgs
    }

    fun invokeSuspendingFunction(
        method: Method, target: Any, vararg args: Any
    ): Publisher<*>? {
        val function = Objects.requireNonNull(method.kotlinFunction)!!
        if (method.isAccessible && !function.isAccessible) {
            function.isAccessible = true
        }
        val classifier = function.returnType.classifier
        val mono = mono(Dispatchers.Unconfined) {
            function.callSuspend(
                getSuspendedFunctionArgs(target, *args)
            )
        }.filter { result ->
            result != Unit
        }.onErrorMap(InvocationTargetException::class.java) { obj ->
            obj.targetException
        }

        return if (classifier != null && classifier is Flow<*>) {
            mono.flatMapMany { (it as Flow<Any>).asFlux() }
        } else mono
    }
}