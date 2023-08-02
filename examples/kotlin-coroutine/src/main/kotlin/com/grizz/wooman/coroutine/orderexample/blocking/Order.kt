package com.grizz.wooman.coroutine.orderexample.blocking

import com.grizz.wooman.coroutine.orderexample.common.Customer
import com.grizz.wooman.coroutine.orderexample.common.DeliveryAddress
import com.grizz.wooman.coroutine.orderexample.common.Product
import com.grizz.wooman.coroutine.orderexample.common.Store

data class Order(
    val stores: List<Store>,
    val products: List<Product>,
    val customer: Customer,
    val deliveryAddress: DeliveryAddress,
)