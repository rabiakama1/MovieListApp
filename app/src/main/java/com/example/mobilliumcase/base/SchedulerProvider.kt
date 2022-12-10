package com.example.mobilliumcase.base

import kotlinx.coroutines.CoroutineDispatcher

interface SchedulerProvider {
    /**
     * The Dispatchers.IO that is designed for offloading blocking IO tasks
     * Uses a shared pool of on-demand created threads
     */
    fun io(): io.reactivex.Scheduler

    fun ui(): io.reactivex.Scheduler

    /**
     * A coroutine dispatcher that is confined to the Main thread
     * Usually such dispatcher is single-threaded.
     */
    fun main(): kotlinx.coroutines.CoroutineDispatcher

}