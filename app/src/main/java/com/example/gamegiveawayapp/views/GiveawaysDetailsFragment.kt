package com.example.gamegiveawayapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.gamegiveawayapp.R
import com.example.gamegiveawayapp.databinding.FragmentGiveawaysDetailsBinding
import com.example.gamegiveawayapp.databinding.GiveawayitemBinding
import com.example.gamegiveawayapp.model.Giveaways
import com.example.gamegiveawayapp.viewmodel.GiveawaysViewModel
import com.squareup.picasso.Picasso

class GiveawaysDetailsFragment : BaseFragment() {

    private val binding by lazy{
        FragmentGiveawaysDetailsBinding.inflate(layoutInflater)
    }

    private var choice: Giveaways? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        choice = giveawaysViewModel.userChoice

        binding.detailsTitle.text = choice?.title
        binding.detailsCost.text = choice?.worth
        binding.detailsPublishDate.text = choice?.publishedDate
        binding.detailsType.text = choice?.type
        binding.detailsPlatform.text = choice?.platforms
        binding.detailsEndDate.text = choice?.endDate
        binding.detailsStatus.text = choice?.status

        Picasso.get()
            .load(choice?.image)
            .resize(200,200)
            .into(binding.detailsImage)

        binding.detailsBackButton.setOnClickListener {
            findNavController().navigateUp()
        }

        // Inflate the layout for this fragment
        return binding.root
    }


}