package com.example.mobilliumcase.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel() : ViewModel() {
    protected val mProgress by lazy { MutableLiveData<Boolean>() }
    val progress: LiveData<Boolean> by lazy { mProgress }

    /** To observe messages that needed to be shown on an AlertDialog */
    protected val mAlertMessage by lazy { MutableLiveData<String>() }
    val alertMessage: LiveData<String> by lazy { mAlertMessage }


    open fun init() {
        // Triggered when initializing a view model
    }

    fun updateProgress(inProgress: Boolean) = mProgress.postValue(inProgress)

}