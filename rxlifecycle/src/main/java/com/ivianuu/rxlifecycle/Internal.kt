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

import androidx.lifecycle.Lifecycle
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.exceptions.OnErrorNotImplementedException
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.internal.functions.Functions
import io.reactivex.plugins.RxJavaPlugins

internal val onCompleteStub: () -> Unit = {}
internal val onNextStub: (Any) -> Unit = {}
internal val onErrorStub: (Throwable) -> Unit =
    { RxJavaPlugins.onError(OnErrorNotImplementedException(it)) }

internal fun Lifecycle.testValidState() {
    if (RxLifecyclePlugins.throwOnEndedLifecycles
            && currentState == Lifecycle.State.DESTROYED) {
        throw IllegalStateException("the lifecycle has ended.")
    }
}

internal fun Lifecycle.defaultDisposeEvent() =
    RxLifecyclePlugins.defaultDisposeEvent ?: correspondingEvent()

internal fun Lifecycle.correspondingEvent(): Lifecycle.Event {
    // get last value based on the current state
    val lastEvent = when (currentState) {
        Lifecycle.State.INITIALIZED -> Lifecycle.Event.ON_CREATE
        Lifecycle.State.CREATED -> Lifecycle.Event.ON_START
        Lifecycle.State.STARTED, Lifecycle.State.RESUMED -> Lifecycle.Event.ON_RESUME
        Lifecycle.State.DESTROYED -> Lifecycle.Event.ON_DESTROY
    }

    return when (lastEvent) {
        Lifecycle.Event.ON_CREATE -> Lifecycle.Event.ON_DESTROY
        Lifecycle.Event.ON_START -> Lifecycle.Event.ON_STOP
        Lifecycle.Event.ON_RESUME -> Lifecycle.Event.ON_PAUSE
        Lifecycle.Event.ON_PAUSE -> Lifecycle.Event.ON_STOP
        Lifecycle.Event.ON_STOP -> Lifecycle.Event.ON_DESTROY
        else -> throw IllegalStateException("Lifecycle has ended! Last event was $lastEvent")
    }
}

internal fun Completable.maybeObserveOnMainThread(): Completable = if (RxLifecyclePlugins.observeOnMainThread) {
    observeOn(AndroidSchedulers.mainThread())
} else {
    this
}

internal fun <T> Flowable<T>.maybeObserveOnMainThread(): Flowable<T> = if (RxLifecyclePlugins.observeOnMainThread) {
    observeOn(AndroidSchedulers.mainThread())
} else {
    this
}

internal fun <T> Maybe<T>.maybeObserveOnMainThread(): Maybe<T> = if (RxLifecyclePlugins.observeOnMainThread) {
    observeOn(AndroidSchedulers.mainThread())
} else {
    this
}

internal fun <T> Observable<T>.maybeObserveOnMainThread(): Observable<T> = if (RxLifecyclePlugins.observeOnMainThread) {
    observeOn(AndroidSchedulers.mainThread())
} else {
    this
}

internal fun <T> Single<T>.maybeObserveOnMainThread(): Single<T> = if (RxLifecyclePlugins.observeOnMainThread) {
    observeOn(AndroidSchedulers.mainThread())
} else {
    this
}

internal fun <T : Any> ((T) -> Unit).asConsumer(): Consumer<T> =
    if (this === onNextStub) Functions.emptyConsumer() else Consumer(this)

internal fun ((Throwable) -> Unit).asOnErrorConsumer(): Consumer<Throwable> =
    if (this === onErrorStub) Functions.ON_ERROR_MISSING else Consumer(this)

internal fun (() -> Unit).asOnCompleteAction(): Action =
    if (this === onCompleteStub) Functions.EMPTY_ACTION else Action(this)