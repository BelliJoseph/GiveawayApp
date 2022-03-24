package com.example.gamegiveawayapp.utils

import com.example.gamegiveawayapp.model.Giveaways

sealed class GiveawayState{
    object LOADING: GiveawayState()
    class SUCCESS<T>(val giveaways: T, isLocalData: Boolean = false): GiveawayState()
    class ERROR(val error: Throwable): GiveawayState()
}
