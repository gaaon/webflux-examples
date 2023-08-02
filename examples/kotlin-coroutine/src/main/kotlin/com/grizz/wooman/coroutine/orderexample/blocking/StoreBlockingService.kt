package com.grizz.wooman.coroutine.orderexample.blocking

import com.grizz.wooman.coroutine.orderexample.common.Store

class StoreBlockingService {
    fun findStoresByIds(storeIds: List<Long>): List<Store> {
        return storeIds.map { Store(it, "매장 $it") }
    }
}