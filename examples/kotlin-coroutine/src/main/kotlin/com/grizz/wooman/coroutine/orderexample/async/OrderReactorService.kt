package com.grizz.wooman.coroutine.orderexample.async

import com.grizz.wooman.coroutine.orderexample.blocking.Order
import com.grizz.wooman.coroutine.orderexample.common.Customer
import com.grizz.wooman.coroutine.orderexample.common.DeliveryAddress
import com.grizz.wooman.coroutine.orderexample.common.Product
import com.grizz.wooman.coroutine.orderexample.common.Store
import reactor.core.publisher.Mono

class OrderReactorService {
    fun createOrderMono(
        customer: Customer,
        products: List<Product>,
        deliveryAddress: DeliveryAddress,
        stores: List<Store>,
    ): Mono<Order> {
        return Mono.create { sink ->
            Thread.sleep(1000)
            sink.success(
                Order(
                    stores = stores,
                    products = products,
                    customer = customer,
                    deliveryAddress = deliveryAddress,
                )
            )
        }
    }
}