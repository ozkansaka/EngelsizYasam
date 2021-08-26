package com.engelsizyasam.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.engelsizyasam.R
import com.engelsizyasam.adapter.RecyclerAdapter
import com.engelsizyasam.adapter.SleepNightListener
import com.engelsizyasam.database.BookDatabase
import com.engelsizyasam.databinding.BookFragmentBinding
import com.engelsizyasam.viewmodel.BookViewModel
import com.engelsizyasam.viewmodel.BookViewModelFactory

class BookFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: BookFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.book_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = BookDatabase.getInstance(application).bookDatabaseDao
        val viewModelFactory = BookViewModelFactory(dataSource)

        val bookViewModel = ViewModelProvider(this, viewModelFactory).get(BookViewModel::class.java)
        binding.viewModel = bookViewModel

        val adapter = RecyclerAdapter(application, SleepNightListener { bookId ->
            bookViewModel.onBookClicked(bookId)
        })

        binding.recyclerView.adapter = adapter

        bookViewModel.books.observe(viewLifecycleOwner, {
            it?.let {
                adapter.data = it
            }
        })

        binding.setLifecycleOwner(this)

        bookViewModel.navigateToBookDetail.observe(viewLifecycleOwner, { night ->
            night?.let {
                this.findNavController().navigate(BookFragmentDirections.actionBookFragmentToBookDetailFragment(night))
                bookViewModel.onBookDetailNavigated()
            }
        })



        return binding.root
    }

}