package com.example.gamegiveawayapp.views

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gamegiveawayapp.R
import com.example.gamegiveawayapp.adapter.GiveawaysAdapter
import com.example.gamegiveawayapp.adapter.GiveawaysClickListener
import com.example.gamegiveawayapp.model.Giveaways
import com.example.gamegiveawayapp.viewmodel.GiveawaysViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

open class BaseFragment: Fragment(), GiveawaysClickListener {


    protected val giveawaysViewModel: GiveawaysViewModel by sharedViewModel()
    protected val giveawaysAdapter by lazy{
        GiveawaysAdapter(this)
    }

    override fun onGiveawaysClicked(giveaways: Giveaways) {
        giveawaysViewModel.userChoice = giveaways
        findNavController().navigate(R.id.giveAwayItemFragment)
    }
}