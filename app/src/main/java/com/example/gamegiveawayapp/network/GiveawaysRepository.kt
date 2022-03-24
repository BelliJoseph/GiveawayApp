package com.example.gamegiveawayapp.network

import com.example.gamegiveawayapp.model.Giveaways
import com.example.gamegiveawayapp.utils.PlatformType
import com.example.gamegiveawayapp.utils.SortType
import retrofit2.Response

interface GiveawaysRepository {
    suspend fun getAllGiveaways(sortedBy: SortType): Response<List<Giveaways>>
    suspend fun getAllGiveawaysByPlatform(platform: PlatformType): Response<List<Giveaways>>
}

class GiveawaysRepositoryImpl(
    private val giveawayService: GiveawayService
) : GiveawaysRepository{
    override suspend fun getAllGiveaways(sortedBy: SortType): Response<List<Giveaways>> =
        giveawayService.getAllGiveaways(sortedBy.realValue)

    override suspend fun getAllGiveawaysByPlatform(platform: PlatformType): Response<List<Giveaways>> =
        giveawayService.getGiveawaysByPlatform(platform.realValue)


}