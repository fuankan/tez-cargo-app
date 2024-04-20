package kg.fuankan.tezcargo.utils

import kotlinx.coroutines.CoroutineDispatcher

interface Dispatcher {
    fun ui(): CoroutineDispatcher
    fun io(): CoroutineDispatcher
    fun default() : CoroutineDispatcher
    fun unconfined(): CoroutineDispatcher
}