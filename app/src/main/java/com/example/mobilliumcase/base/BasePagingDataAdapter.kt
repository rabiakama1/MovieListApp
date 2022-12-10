package com.example.mobilliumcase.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BasePagingDataAdapter<T : Any, K : RecyclerView.ViewHolder>(diffCallback: DiffUtil.ItemCallback<T>) :
    PagingDataAdapter<T, K>(diffCallback) {
    /** Data list property to get access from child classes */
    protected var dataList: List<T> = ArrayList()
    protected var pagedDataList: PagingData<T> =  PagingData.empty<T>()
    /** Layout resource id for adapter's list */
    abstract val layoutRes: Int

    /** to write data to views */
    abstract fun onBind(holder: K, position: Int)

    /** to get access to layout's views */
    abstract fun onCreateVH(parent: ViewGroup, viewType: Int, itemView: View): K

    /** if the adapter needs different layouts, this methods helps to return desired layout if has any */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): K {
        return if (viewType != 0)
            onCreateVH(
                parent,
                viewType,
                LayoutInflater.from(parent.context).inflate(viewType, parent, false)
            )
        else
            onCreateVH(
                parent,
                viewType,
                LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
            )
    }


    /** bind layout resource to view holder class to access views */
    override fun onBindViewHolder(holder: K, position: Int) {
        onBind(holder, position)
    }

    /** returns a View object to access layoutRes views */
    private fun getItemView(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
    }

    fun getItemByPos(position: Int): T? {
        return getItem(position)
    }

}