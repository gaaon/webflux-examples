package com.grizz.wooman.coroutine.orderexample.async

import com.grizz.wooman.coroutine.help.kLogger

private val log = kLogger()

class OrderAsyncExample(
    private val customerService: CustomerFutureService,
    private val productService: ProductRxjava3Service,
    private val storeService: StoreMutinyService,
    private val deliveryAddressService: DeliveryAddressPublisherService,
    private val orderService: OrderReactorService,
) {
    fun execute(userId: Long, productIds: List<Long>) {
        // 1. 고객 정보 조회
        customerService.findCustomerFuture(userId).thenAccept { customer ->

            // 2. 상품 정보 조회
            productService.findAllProductsFlowable(productIds)
                .toList()
                .subscribe { products ->

                    // 3. 스토어 조회
                    val storeIds = products.map { it.storeId }
                    storeService.findStoresMutli(storeIds)
                        .collect().asList()
                        .subscribe()
                        .with { stores ->

                            // 4. 주소 조회
                            val daIds = customer.deliveryAddressIds
                            deliveryAddressService.findDeliveryAddressesPublisher(daIds)
                                .subscribe(FirstFinder { deliveryAddress ->

                                    // 5. 주문 생성
                                    orderService.createOrderMono(
                                        customer, products, deliveryAddress, stores,
                                    ).subscribe { order ->
                                        log.info("order: {}", order)
                                    }
                                })
                        }
                }
        }
    }
}

fun main(args: Array<String>) {
    val customerService = CustomerFutureService()
    val productService = ProductRxjava3Service()
    val storeService = StoreMutinyService()
    val deliveryAddressService = DeliveryAddressPublisherService()
    val orderService = OrderReactorService()

    val example = OrderAsyncExample(
        customerService = customerService,
        productService = productService,
        storeService = storeService,
        deliveryAddressService = deliveryAddressService,
        orderService = orderService,
    )

    example.execute(1, listOf(1, 2, 3));
}