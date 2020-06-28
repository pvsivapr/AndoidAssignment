package com.nytimes.uiviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nytimes.BR
import com.example.nytimes.R
import com.example.nytimes.databinding.FragmentNytimesItemsListCellBinding
import com.nytimes.localmodels.NYTimesLocalItemModel

class NYTimesItemsListAdapter(nyTimesLocalItemsList : List<NYTimesLocalItemModel>, nyTimesItemsListPageViewModel: NYTimesItemsListPageViewModel) : RecyclerView.Adapter<NYTimesItemsListAdapter.NYTimesItemsListViewHolder>()
{
    val nyItemsList: List<NYTimesLocalItemModel> = nyTimesLocalItemsList
    val nyTimesItemsPageViewModel: NYTimesItemsListPageViewModel = nyTimesItemsListPageViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NYTimesItemsListViewHolder {
        var binding: FragmentNytimesItemsListCellBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.fragment_nytimes_items_list_cell, parent, false)
        return NYTimesItemsListViewHolder(binding, nyTimesItemsPageViewModel)
    }

    override fun getItemCount(): Int {
        return nyItemsList.count()
    }

    override fun onBindViewHolder(holder: NYTimesItemsListViewHolder, position: Int) {
        holder.bindingView(nyItemsList[position])
    }

    class NYTimesItemsListViewHolder(binding: FragmentNytimesItemsListCellBinding, nyTimesItemsPageViewModel: NYTimesItemsListPageViewModel) : RecyclerView.ViewHolder(binding.root)
    {
        val cellItemBinding: FragmentNytimesItemsListCellBinding = binding
        val nyTimesItemsPageListViewModel: NYTimesItemsListPageViewModel = nyTimesItemsPageViewModel

        fun bindingView(cellItem: NYTimesLocalItemModel)
        {
            cellItemBinding.setVariable(BR.nyTimesLocalItem, cellItem)
            cellItemBinding.setVariable(BR.nyTimesItemsListPageVM, nyTimesItemsPageListViewModel)
        }
    }
}
