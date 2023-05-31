package com.grizz.wooman.reactivestreamsimpl.rxjava;

import com.grizz.wooman.reactivestreamsimpl.reactor.ContinuousRequestSubscriber;
import io.reactivex.rxjava3.core.Flowable;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class FlowableContinuousRequestSubscriberExample {
    public static void main(String[] args) {
        log.info("start main");
        getItems()
                .subscribe(new ContinuousRequestSubscriber<>());
        log.info("end main");
    }

    private static Flowable<Integer> getItems() {
        return Flowable.fromIterable(List.of(1, 2, 3, 4, 5));
    }
}
