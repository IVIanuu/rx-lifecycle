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

/**
 * Global settings
 */
object RxLifecyclePlugins {
    /**
     * Whether or not a subscribeUi call should automatically observe on the main thread
     */
    var observeOnMainThread = true

    /**
     * The default dispose event or null to automatically choose the right event
     * based on the current lifecycle state
     * e.g. ON_CREATE -> ON_DESTROY, ON_START -> ON_STOP, ON_RESUME -> ON_PAUSE
     */
    var defaultDisposeEvent: Lifecycle.Event? = null
        set(value) {
            if (value == Lifecycle.Event.ON_ANY) {
                throw IllegalArgumentException("ON_ANY is no valid option.")
            }
            field = value
        }

    /**
     * Whether or not a exception should be thrown when trying to subscribe while the state is
     * [Lifecycle.State.DESTROYED]
     */
    var throwOnEndedLifecycles = true
}