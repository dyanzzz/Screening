package com.suitmedia.screeningtest.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.suitmedia.screeningtest.wrapper.Event
import com.suitmedia.screeningtest.wrapper.VoidEvent

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: androidx.lifecycle.Observer<T>) {
    observe(lifecycleOwner, Observer { t ->
        observer.onChanged(t)
        removeObservers(lifecycleOwner)
    })
}

fun <T> LiveData<out Event<T>>.observeEvent(owner: LifecycleOwner, onEventUnhandled: (T) -> Unit) {
    observe(owner, Observer { it?.getContentIfNotHandled()?.let(onEventUnhandled) })
}

fun LiveData<out VoidEvent>.observeEvent(owner: LifecycleOwner, onEventUnhandled: () -> Unit) {
    observe(owner, Observer { if (!it.hasBeenHandled()) onEventUnhandled() })
}
