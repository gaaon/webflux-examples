package com.grizz.wooman.coroutine.orderexample.async

import com.grizz.wooman.coroutine.orderexample.common.DeliveryAddress
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux

class DeliveryAddressPublisherService {
    fun findDeliveryAddressesPublisher(
        ids: List<Long>
    ): Publisher<DeliveryAddress> {
        return Flux.create { sink ->
            ids.map { id ->
                DeliveryAddress(
                    id = id,
                    roadNameAddress = "도로명 주소 $id",
                    detailAddress = "상세 주소 $id",
                )
            }.forEach {
                Thread.sleep(100)
                sink.next(it)
            }
            sink.complete()
        }
    }
}