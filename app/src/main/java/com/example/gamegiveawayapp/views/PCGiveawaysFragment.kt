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
import com.example.gamegiveawayapp.databinding.FragmentPCGiveawaysBinding
import com.example.gamegiveawayapp.model.Giveaways
import com.example.gamegiveawayapp.utils.GiveawayState

class PCGiveawaysFragment : BaseFragment() {

    private val binding by lazy{
        FragmentPCGiveawaysBinding.inflate(layoutInflater)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.pcRecyclerView.apply {
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

        binding.pcBackButton.setOnClickListener{
            findNavController().navigateUp()
        }

        // Inflate the layout for this fragment
        return binding.root
    }




}