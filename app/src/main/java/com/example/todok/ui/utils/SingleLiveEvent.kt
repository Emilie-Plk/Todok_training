package com.example.todok.ui.utils

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val pending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            throw IllegalStateException("Multiple observers are registered for this SingleLiveEvent")
        }

        // Observe the internal MutableLiveData
        super.observe(owner) { t ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        }
    }

    @MainThread
    override fun setValue(t: T?) {
        pending.set(true)
        super.setValue(t)
    }

    @Deprecated(
        message = "Use setValue instead by switching to the Main Thread yourself",
        replaceWith = ReplaceWith("setValue(value)"),
        level = DeprecationLevel.HIDDEN
    )
    override fun postValue(value: T) {
        super.postValue(value)
    }
}