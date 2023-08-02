package com.grizz.wooman.coroutine.orderexample.blocking

import com.grizz.wooman.coroutine.orderexample.common.Customer
import com.grizz.wooman.coroutine.orderexample.common.DeliveryAddress
import com.grizz.wooman.coroutine.orderexample.common.Product
import com.grizz.wooman.coroutine.orderexample.common.Store

class OrderBlockingService {
    fun createOrder(
        customer: Customer,
        products: List<Product>,
        deliveryAddress: DeliveryAddress,
        stores: List<Store>,
    ): Order {
        return Order(
            stores = stores,
            products = products,
            customer = customer,
            deliveryAddress = deliveryAddress,
        )
    }
}