package com.example.mobilliumcase.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment<T : BaseViewModel> : Fragment() {
    /** Layout resource int */
    @LayoutRes
    abstract fun getLayoutRes(): Int

    /** Creates ViewModel object which is [T] */
    abstract fun setViewModel(): T

    /** After fragment created method */
    abstract fun init(context: Context)

    /** Context of caller */
    private lateinit var mContext: Context

    /** ViewModel object for Fragment that extends [BaseFragment] */
    protected lateinit var viewModel: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(getLayoutRes(), container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mContext = view.context
        initViewModel()
        init(view.context)
    }

    /** Gets ViewModel class */
    @Suppress("USELESS_CAST", "UNCHECKED_CAST")
    private fun initViewModel() {
        viewModel = setViewModel()
    }
}