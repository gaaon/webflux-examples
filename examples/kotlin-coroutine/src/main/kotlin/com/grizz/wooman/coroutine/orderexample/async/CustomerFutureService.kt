package com.grizz.wooman.coroutine.orderexample.async

import com.grizz.wooman.coroutine.orderexample.common.Customer
import java.util.concurrent.CompletableFuture

class CustomerFutureService {
    fun findCustomerFuture(id: Long): CompletableFuture<Customer> {
        return CompletableFuture.supplyAsync {
            Thread.sleep(1000)
            Customer(id, "taewoo", listOf(1, 2, 3))
        }
    }
}