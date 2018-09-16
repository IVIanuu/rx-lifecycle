package com.ivianuu.rxlifecycle.sample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.GenericLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.ivianuu.rxlifecycle.RxLifecycleOwner
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), RxLifecycleOwner {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycle.addObserver(GenericLifecycleObserver { _: LifecycleOwner, event: Lifecycle.Event ->
            Log.d("Lifecycle", "on event -> $event")
        })

        // disposes in on destroy
        Observable.interval(1, TimeUnit.SECONDS)
            .doOnSubscribe { Log.d("OnCreate", "on sub") }
            .doOnDispose { Log.d("OnCreate", "on dispose") }
            .subscribeUi()
    }

    override fun onStart() {
        super.onStart()
        // disposes in on stop
        Observable.interval(1, TimeUnit.SECONDS)
            .doOnSubscribe { Log.d("OnStart", "on sub") }
            .doOnDispose { Log.d("OnStart", "on dispose") }
            .subscribeUi()
    }

    override fun onResume() {
        super.onResume()
        // disposes in on pause
        Observable.interval(1, TimeUnit.SECONDS)
            .doOnSubscribe { Log.d("OnResume", "on sub") }
            .doOnDispose { Log.d("OnResume", "on dispose") }
            .subscribeUi()
    }

}