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
import io.reactivex.disposables.Disposable

/**
 * Disposes this disposable when the [event] occurs
 */
fun Disposable.disposedWith(
    owner: LifecycleOwner,
    event: Lifecycle.Event = owner.lifecycle.defaultDisposeEvent()
) = apply {
    event.checkValidEvent()
    owner.lifecycle.testValidState()
    owner.lifecycle.addObserver(object : GenericLifecycleObserver {
        override fun onStateChanged(source: LifecycleOwner, e: Lifecycle.Event) {
            if (event == e || e == Lifecycle.Event.ON_DESTROY) {
                owner.lifecycle.removeObserver(this)
                dispose()
            }
        }
    })
}