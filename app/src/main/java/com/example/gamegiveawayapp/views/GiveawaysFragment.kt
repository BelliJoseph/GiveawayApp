package com.example.gamegiveawayapp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamegiveawayapp.R
import com.example.gamegiveawayapp.adapter.GiveawaysClickListener
import com.example.gamegiveawayapp.databinding.FragmentGiveawaysBinding
import com.example.gamegiveawayapp.model.Giveaways
import com.example.gamegiveawayapp.utils.GiveawayState
import com.example.gamegiveawayapp.utils.PlatformType


class GiveawaysFragment : BaseFragment() {

    private val binding by lazy{
        FragmentGiveawaysBinding.inflate(layoutInflater)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding.giveawaysRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = giveawaysAdapter
        }

        giveawaysViewModel.giveaways.observe(viewLifecycleOwner){ state->
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
                    Toast.makeText(
                        requireContext(), state.error.localizedMessage, Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        giveawaysViewModel.getSortedGiveaways()

        binding.goToPC.setOnClickListener{
            giveawaysViewModel.platform = PlatformType.PC
            findNavController().navigate(R.id.action_giveawaysFragment_to_pcGiveawaysFragment)
        }


        binding.goToPS4.setOnClickListener{
            giveawaysViewModel.platform = PlatformType.PS4
            findNavController().navigate(R.id.action_giveawaysFragment_to_ps4GiveawaysFragment)
        }

        // Inflate the layout for this fragment
        return binding.root
    }



}