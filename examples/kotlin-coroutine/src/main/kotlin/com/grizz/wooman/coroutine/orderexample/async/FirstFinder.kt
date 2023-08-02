package com.grizz.wooman.coroutine.orderexample.async

import com.grizz.wooman.coroutine.orderexample.common.DeliveryAddress
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.util.concurrent.atomic.AtomicBoolean

class FirstFinder<T>(
    private val handleComplete: (T) -> Unit
) : Subscriber<T> {

    private val isSent = AtomicBoolean(false)

    override fun onSubscribe(subscription: Subscription) {
        subscription.request(Long.MAX_VALUE)
    }

    override fun onError(throwable: Throwable) {}

    override fun onComplete() {
    }

    override fun onNext(item: T) {
        if (!isSent.getAndSet(true)) {
            handleComplete(item)
        }
    }
}