package com.grizz.wooman.coroutine.orderexample.async

import com.grizz.wooman.coroutine.help.kLogger
import com.grizz.wooman.coroutine.orderexample.blocking.Order
import com.grizz.wooman.coroutine.orderexample.common.Customer
import com.grizz.wooman.coroutine.orderexample.common.DeliveryAddress
import com.grizz.wooman.coroutine.orderexample.common.Product
import com.grizz.wooman.coroutine.orderexample.common.Store

private val log = kLogger()

class OrderAsyncExampleUpgrade1(
    private val customerService: CustomerFutureService,
    private val productService: ProductRxjava3Service,
    private val storeService: StoreMutinyService,
    private val deliveryAddressService: DeliveryAddressPublisherService,
    private val orderService: OrderReactorService,
) {
    class Shared {
        var result: Any? = null
        var label = 0

        // variables
        lateinit var customer: Customer
        lateinit var products: List<Product>
        lateinit var stores: List<Store>
        lateinit var deliveryAddress: DeliveryAddress
    }

    fun execute(userId: Long,
                productIds: List<Long>,
                shared: Shared? = null) {

        val cont = shared ?: Shared()

        when (cont.label) {
            0 -> {
                // 1. 고객 정보 조회
                cont.label = 1

                customerService.findCustomerFuture(userId)
                    .thenAccept { customer ->
                        cont.result = customer
                        execute(userId, productIds, cont)
                    }
            }
            1 -> {
                // 2. 상품 정보 조회
                cont.customer = cont.result as Customer
                cont.label = 2

                productService.findAllProductsFlowable(productIds)
                    .toList()
                    .subscribe { products ->
                        cont.result = products
                        execute(userId, productIds, cont)
                    }
            }
            2 -> {
                // 3. 스토어 조회
                cont.products = cont.result as List<Product>
                cont.label = 3

                val products = cont.products
                val storeIds = products.map { it.storeId }
                storeService.findStoresMutli(storeIds)
                    .collect().asList()
                    .subscribe()
                    .with { stores ->
                        cont.result = stores
                        execute(userId, productIds, cont)
                    }
            }
            3 -> {
                // 4. 주소 조회
                cont.stores = cont.result as List<Store>
                cont.label = 4

                val customer = cont.customer
                val daIds = customer.deliveryAddressIds
                deliveryAddressService.findDeliveryAddressesPublisher(daIds)
                    .subscribe(FirstFinder { deliveryAddress ->
                        cont.result = deliveryAddress
                        execute(userId, productIds, cont)
                    })
            }
            4 -> {
                // 5. 주문 생성
                cont.deliveryAddress = cont.result as DeliveryAddress
                cont.label = 5

                val customer = cont.customer
                val products = cont.products
                val deliveryAddress = cont.deliveryAddress
                val stores = cont.stores

                orderService.createOrderMono(
                    customer, products, deliveryAddress, stores,
                ).subscribe { order ->
                    cont.result = order
                    execute(userId, productIds, cont)
                }
            }
            5 -> {
                val order = cont.result as Order
                log.info("order: $order")
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

    val example = OrderAsyncExampleUpgrade1(
        customerService = customerService,
        productService = productService,
        storeService = storeService,
        deliveryAddressService = deliveryAddressService,
        orderService = orderService,
    )

    example.execute(1, listOf(1, 2, 3));
}