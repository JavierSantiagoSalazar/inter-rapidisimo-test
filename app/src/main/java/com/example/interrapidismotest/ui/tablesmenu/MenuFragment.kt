package com.example.interrapidismotest.ui.tablesmenu

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.interrapidismotest.App
import com.example.interrapidismotest.R
import com.example.interrapidismotest.data.repository.TableRepository
import com.example.interrapidismotest.data.database.TableLocalDataSourceImpl
import com.example.interrapidismotest.data.server.TableRemoteDataSourceImpl
import com.example.interrapidismotest.databinding.FragmentMenuBinding
import com.example.interrapidismotest.ui.common.TablesNamesAdapter
import com.example.interrapidismotest.ui.common.diff
import com.example.interrapidismotest.ui.common.launchAndCollect
import com.example.interrapidismotest.ui.common.setVisibleOrGone
import com.example.interrapidismotest.ui.common.showErrorSnackBar
import com.example.interrapidismotest.ui.common.toggleText
import com.example.interrapidismotest.ui.common.toggleVisibility
import com.example.interrapidismotest.usecases.GetTablesUseCase
import com.example.interrapidismotest.usecases.RequestTablesUseCase


class MenuFragment : Fragment(R.layout.fragment_menu) {

    private val viewModel: MenuViewModel by viewModels {
        val app = requireActivity().applicationContext as App
        val repository = TableRepository(
            TableLocalDataSourceImpl(app.db.tableDao()),
            TableRemoteDataSourceImpl("admin")
        )
        MenuViewModelFactory(
            GetTablesUseCase(repository),
            RequestTablesUseCase(repository)
        )
    }

    private val adapter = TablesNamesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentMenuBinding.bind(view).apply {
            recyclerTables.adapter = adapter
        }

        with(binding) {
            getTablesDataButton.setOnClickListener { viewModel.getTables() }
            showDataButton.setOnClickListener {
                binding.lyTablesScreen.toggleVisibility()
                showDataButton.toggleText(
                    getString(R.string.menu_button_show_data),
                    getString(R.string.menu_button_hide_data)
                )
                tvFull.setVisibleOrGone(!tvEmpty.isShown && !etFilter.isShown)
            }
        }

        with(viewModel.state) {
            diff(this, { it.loading }) {
                it.let { binding.progress.setVisibleOrGone(it) }
            }
            diff(this, { it.tables }) { tables ->
                binding.etFilter.addTextChangedListener { userFilter ->
                    val filteredTablesList = tables?.filter { table ->
                        table.tableName.lowercase().contains(userFilter.toString().lowercase())
                    }
                    adapter.submitList(filteredTablesList)
                }
                adapter.submitList(tables)
                binding.tvFull.setVisibleOrGone(tables?.isNotEmpty() ?: false && !binding.recyclerTables.isShown)
                binding.tvEmpty.setVisibleOrGone(tables?.isEmpty() ?: true)
            }
            launchAndCollect(this) {
                it.error?.let { error ->
                    if (view.isShown) {
                        view.showErrorSnackBar(error) {
                            viewModel.getTables()
                        }
                    }
                }
            }
        }
    }
}
