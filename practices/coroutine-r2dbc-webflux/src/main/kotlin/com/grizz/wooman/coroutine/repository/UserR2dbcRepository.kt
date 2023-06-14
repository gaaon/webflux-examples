package com.grizz.wooman.coroutine.repository

import com.grizz.wooman.webflux.common.repository.UserEntity
import org.springframework.data.repository.kotlin.CoroutineSortingRepository

interface UserR2dbcRepository : CoroutineSortingRepository<UserEntity, Long>
