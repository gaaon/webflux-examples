package com.grizz.wooman.coroutine.orderexample.coroutine

import com.grizz.wooman.coroutine.help.kLogger
import com.grizz.wooman.coroutine.orderexample.async.CustomerFutureService
import com.grizz.wooman.coroutine.orderexample.async.DeliveryAddressPublisherService
import com.grizz.wooman.coroutine.orderexample.async.OrderReactorService
import com.grizz.wooman.coroutine.orderexample.async.ProductRxjava3Service
import com.grizz.wooman.coroutine.orderexample.async.StoreMutinyService
import com.grizz.wooman.coroutine.orderexample.blocking.Order
import io.smallrye.mutiny.coroutines.awaitSuspending
import kotlinx.coroutines.future.await
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.rx3.await

private val log = kLogger()

class OrderCoroutineExample(
    private val customerService: CustomerFutureService,
    private val productService: ProductRxjava3Service,
    private val storeService: StoreMutinyService,
    private val deliveryAddressService: DeliveryAddressPublisherService,
    private val orderService: OrderReactorService,
) {
    suspend fun execute(userId: Long, productIds: List<Long>): Order {
        // 1. 고객 정보 조회
        val customer = customerService.findCustomerFuture(userId)
            .await()

        // 2. 상품 정보 조회
        val products = productService
            .findAllProductsFlowable(productIds)
            .toList().await()

        // 3. 스토어 조회
        val storeIds = products.map { it.storeId }
        val stores = storeService.findStoresMutli(storeIds)
            .collect().asList().awaitSuspending()

        // 4. 주소 조회
        val daIds = customer.deliveryAddressIds
        val deliveryAddress = deliveryAddressService
            .findDeliveryAddressesPublisher(daIds)
            .awaitFirst()

        // 5. 주문 생성
        val order = orderService.createOrderMono(
            customer, products, deliveryAddress, stores,
        ).awaitSingle()

        return order
    }
}

suspend fun main(args: Array<String>) {
    val customerService = CustomerFutureService()
    val productService = ProductRxjava3Service()
    val storeService = StoreMutinyService()
    val deliveryAddressService = DeliveryAddressPublisherService()
    val orderService = OrderReactorService()

    val example = OrderCoroutineExample(
        customerService = customerService,
        productService = productService,
        storeService = storeService,
        deliveryAddressService = deliveryAddressService,
        orderService = orderService,
    )

    example.execute(1, listOf(1, 2, 3));
}