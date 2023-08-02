package com.grizz.wooman.coroutine.orderexample.blocking

import com.grizz.wooman.coroutine.orderexample.common.Customer

class CustomerBlockingService {
    fun findCustomerById(id: Long): Customer {
        return Customer(id, "taewoo", listOf(1, 2, 3))
    }
}