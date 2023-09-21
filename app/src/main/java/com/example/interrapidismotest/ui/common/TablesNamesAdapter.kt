package com.example.interrapidismotest.ui.common

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.interrapidismotest.R
import com.example.interrapidismotest.databinding.ItemTableNameBinding
import com.example.interrapidismotest.domain.Table

class TablesNamesAdapter :
    ListAdapter<Table, TablesNamesAdapter.ViewHolder>(basicDiffUtil { old, new ->
        old.tableName == new.tableName
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_table_name, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val table = getItem(position)
        holder.bind(table)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemTableNameBinding.bind(view)
        fun bind(table: Table) = with(binding) {
            tvTableName.text = table.tableName
        }
    }
}
