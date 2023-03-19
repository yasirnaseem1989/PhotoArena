package com.code.challenge.photoarena.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.challenge.photoarena.base.BaseViewModel.ViewEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

abstract class BaseViewModel<T : ViewEvent> : ViewModel() {

    private val eventChannel = Channel<T>(Channel.BUFFERED)

    private val classTag = this.javaClass.simpleName

    init {
        Timber.d("[VIEW MODEL] $classTag initialized")
    }

    protected suspend fun emitViewEvent(event: T) {
        withContext(Dispatchers.Main) {
            eventChannel.send(event)
        }
    }

    protected fun tryEmitViewEvent(event: T) {

        viewModelScope.launch {
            emitViewEvent(event)
        }
    }

    protected fun tryEmitViewEvent(eventBuilder: () -> T) {
        tryEmitViewEvent(eventBuilder())
    }

    override fun onCleared() {
        Timber.d("[VIEW MODEL] $classTag cleared")
    }

    suspend fun observeViewEvents(eventHandler: (T) -> Unit) {
        eventChannel.receiveAsFlow().collect { eventHandler(it) }
    }

    open class ViewEvent
}
