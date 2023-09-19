package com.example.interrapidismotest.ui.tablesmenu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.interrapidismotest.App
import com.example.interrapidismotest.R
import com.example.interrapidismotest.data.repository.TableRepository
import com.example.interrapidismotest.dataapp.database.TableLocalDataSourceImpl
import com.example.interrapidismotest.dataapp.server.TableRemoteDataSourceImpl
import com.example.interrapidismotest.databinding.FragmentMenuBinding
import com.example.interrapidismotest.usecases.tables.RequestTablesUseCase


class MenuFragment : Fragment(R.layout.fragment_menu) {

    private val viewModel: MenuViewModel by viewModels {
        val app = requireActivity().applicationContext as App
        val repository = TableRepository(
            TableLocalDataSourceImpl(app.db.tableDao()),
            TableRemoteDataSourceImpl("admin")
        )
        MenuViewModelFactory(
            RequestTablesUseCase(repository)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentMenuBinding.bind(view)

        binding.getDataButton.setOnClickListener {
            viewModel.onUiReady()
        }
    }

}
