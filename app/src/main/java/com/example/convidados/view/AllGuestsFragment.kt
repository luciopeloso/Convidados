package com.example.convidados.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.convidados.constansts.DataBaseConstants
import com.example.convidados.databinding.FragmentAllGuestsBinding
import com.example.convidados.view.Adapter.GuestsAdapter
import com.example.convidados.view.Listener.OnGuestListener
import com.example.convidados.viewmodel.GuestsViewModel

class AllGuestsFragment : Fragment() {

    private var _binding: FragmentAllGuestsBinding? = null
    private lateinit var viewModel: GuestsViewModel
    private val binding get() = _binding!!
    private val adapter = GuestsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {
        viewModel = ViewModelProvider(this).get(GuestsViewModel::class.java)

        _binding = FragmentAllGuestsBinding.inflate(inflater, container, false)

        binding.recyclerGuests.layoutManager = LinearLayoutManager(context)
        binding.recyclerGuests.adapter = adapter

        val listener = object : OnGuestListener{
            override fun onClick(id: Int) {
                val intent = Intent(context, GuestFormActivity::class.java)
                val bundle = Bundle()
                bundle.putInt(DataBaseConstants.GUESTS.ID,id)
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                viewModel.delete(id)
                viewModel.getAll()
            }
        }


        observe()
        adapter.attachListener(listener)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAll()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        viewModel.guests.observe(viewLifecycleOwner) {
            adapter.updateGuests(it)
        }
    }

}