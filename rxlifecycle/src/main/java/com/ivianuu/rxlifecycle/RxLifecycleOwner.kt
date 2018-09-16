/*
 * Copyright 2018 Manuel Wrage
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ivianuu.rxlifecycle

import androidx.lifecycle.GenericLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable

/**
 * A lifecycle owner with convenience functions for subscribeUi calls
 */
interface RxLifecycleOwner : LifecycleOwner {

    /**
     * Disposes this disposable when the [event] occurs
     */
    fun Disposable.disposedWith(
        event: Lifecycle.Event = lifecycle.defaultDisposeEvent()
    ) = disposedWith(this@RxLifecycleOwner, event)

    /**
     * Subscribes to the [this], disposes on [event] and observes on the main thread
     */
    fun Completable.subscribeUi(
        event: Lifecycle.Event = lifecycle.defaultDisposeEvent(),
        onError: (Throwable) -> Unit = onErrorStub,
        onComplete: () -> Unit = onCompleteStub
    ) = subscribeUi(this@RxLifecycleOwner, event, onError, onComplete)

    /**
     * Subscribes to the [this], disposes on [event] and observes on the main thread
     */
    fun <T : Any> Flowable<T>.subscribeUi(
        event: Lifecycle.Event = lifecycle.defaultDisposeEvent(),
        onError: (Throwable) -> Unit = onErrorStub,
        onComplete: () -> Unit = onCompleteStub,
        onNext: (T) -> Unit = onNextStub
    ) = subscribeUi(this@RxLifecycleOwner, event, onError, onComplete, onNext)

    /**
     * Subscribes to the [this], disposes on [event] and observes on the main thread
     */
    fun <T : Any> Maybe<T>.subscribeUi(
        event: Lifecycle.Event = lifecycle.defaultDisposeEvent(),
        onError: (Throwable) -> Unit = onErrorStub,
        onComplete: () -> Unit = onCompleteStub,
        onSuccess: (T) -> Unit = onNextStub
    ) = subscribeUi(this@RxLifecycleOwner, event, onError, onComplete, onSuccess)

    /**
     * Subscribes to the [this], disposes on [event] and observes on the main thread
     */
    fun <T : Any> Observable<T>.subscribeUi(
        event: Lifecycle.Event = lifecycle.defaultDisposeEvent(),
        onError: (Throwable) -> Unit = onErrorStub,
        onComplete: () -> Unit = onCompleteStub,
        onNext: (T) -> Unit = onNextStub
    ) = subscribeUi(this@RxLifecycleOwner, event, onError, onComplete, onNext)

    /**
     * Subscribes to the [this], disposes on [event] and observes on the main thread
     */
    fun <T : Any> Single<T>.subscribeUi(
        event: Lifecycle.Event = lifecycle.defaultDisposeEvent(),
        onError: (Throwable) -> Unit = onErrorStub,
        onSuccess: (T) -> Unit = onNextStub
    ) = subscribeUi(this@RxLifecycleOwner, event, onError, onSuccess)

}