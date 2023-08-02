package com.grizz.wooman.coroutine.orderexample.blocking

import com.grizz.wooman.coroutine.help.kLogger

private val log = kLogger()

class OrderBlockingExample(
    private val customerService: CustomerBlockingService,
    private val productService: ProductBlockingService,
    private val storeService: StoreBlockingService,
    private val deliveryAddressService: DeliveryAddressBlockingService,
    private val orderService: OrderBlockingService,
) {
    fun execute(userId: Long, productIds: List<Long>): Order {
        // 1. 고객 정보 조회
        val customer = customerService.findCustomerById(userId)

        // 2. 상품 정보 조회
        val products = productService
            .findAllProductsByIds(productIds)

        // 3. 스토어 조회
        val storeIds = products.map { it.storeId }
        val stores = storeService.findStoresByIds(storeIds)

        // 4. 주소 조회
        val daIds = customer.deliveryAddressIds
        val deliveryAddress = deliveryAddressService
            .findDeliveryAddresses(daIds)
            .first()

        // 5. 주문 생성
        val order = orderService.createOrder(
            customer, products, deliveryAddress, stores
        )

        return order
    }
}

fun main(args: Array<String>) {
    val customerService = CustomerBlockingService()
    val productService = ProductBlockingService()
    val storeService = StoreBlockingService()
    val deliveryAddressService = DeliveryAddressBlockingService()
    val orderService = OrderBlockingService()

    val example = OrderBlockingExample(
        customerService = customerService,
        productService = productService,
        storeService = storeService,
        deliveryAddressService = deliveryAddressService,
        orderService = orderService,
    )

    val order = example.execute(1, listOf(1, 2, 3))
    log.info("order: {}", order)
}