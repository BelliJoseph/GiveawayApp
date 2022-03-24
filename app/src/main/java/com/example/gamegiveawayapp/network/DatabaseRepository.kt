package com.example.gamegiveawayapp.network

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gamegiveawayapp.database.GiveawayDAO
import com.example.gamegiveawayapp.model.Giveaways

interface DatabaseRepository {


    suspend fun insertGiveaways(newGiveaways: List<Giveaways>)
    suspend fun getGiveaways(): List<Giveaways>
    suspend fun getGiveawaysById(searchId: Int): Giveaways
    suspend fun getGiveawaysByPlatform(platform: String): List<Giveaways>
    suspend fun deleteAllGiveaways()
    suspend fun deleteGiveaways(giveaways: Giveaways)

}

class DatabaseRepositoryImpl(
    private val giveawayDatabase: GiveawayDAO
) : DatabaseRepository{
    override suspend fun insertGiveaways(newGiveaways: List<Giveaways>) {
        giveawayDatabase.insertGiveaways(newGiveaways)
    }

    override suspend fun getGiveaways(): List<Giveaways> {
        return giveawayDatabase.getGiveaways()
    }

    override suspend fun getGiveawaysById(searchId: Int): Giveaways {
        return giveawayDatabase.getGiveawaysById(searchId)
    }

    override suspend fun getGiveawaysByPlatform(platform: String): List<Giveaways> {
        return giveawayDatabase.getGiveawaysByPlatform(platform)
    }

    override suspend fun deleteAllGiveaways() {
        giveawayDatabase.deleteAllGiveaways()

    }

    override suspend fun deleteGiveaways(giveaways: Giveaways) {
        giveawayDatabase.deleteGiveaways(giveaways)
    }

}