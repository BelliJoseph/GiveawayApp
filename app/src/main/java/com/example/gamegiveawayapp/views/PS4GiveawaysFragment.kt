package com.example.gamegiveawayapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamegiveawayapp.R
import com.example.gamegiveawayapp.adapter.GiveawaysClickListener
import com.example.gamegiveawayapp.databinding.FragmentPS4GiveawaysBinding
import com.example.gamegiveawayapp.model.Giveaways
import com.example.gamegiveawayapp.utils.GiveawayState


class PS4GiveawaysFragment : BaseFragment()  {

    private val binding by lazy{
        FragmentPS4GiveawaysBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.ps4RecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = giveawaysAdapter
        }

        giveawaysViewModel.giveaways.observe(viewLifecycleOwner){ state ->
            when(state){
                is GiveawayState.LOADING -> {
                    Toast.makeText(
                        requireContext(), "Loading....", Toast.LENGTH_LONG
                    ).show()
                }
                is GiveawayState.SUCCESS<*> -> {
                    val giveaways = state.giveaways as List<Giveaways>
                    giveawaysAdapter.setNewGiveaways(giveaways)
                }
                is GiveawayState.ERROR -> {
                    Toast.makeText(requireContext(), state.error.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }
        }

        giveawaysViewModel.getGiveawaysByPlatform()

        binding.ps4BackButton.setOnClickListener{
            findNavController().navigateUp()
        }


        // Inflate the layout for this fragment
        return binding.root
    }



}