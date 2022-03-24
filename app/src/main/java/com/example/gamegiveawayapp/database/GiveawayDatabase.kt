package com.example.gamegiveawayapp.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.gamegiveawayapp.model.Giveaways
import retrofit2.http.DELETE

@Database(
    entities = [Giveaways::class],
    version = 1
)
abstract class GiveawayDatabase : RoomDatabase(){
    abstract fun getGiveawayDao(): GiveawayDAO
}

@Dao
interface GiveawayDAO{

    @Insert(onConflict = REPLACE)
    suspend fun insertGiveaways(newGiveaways: List<Giveaways>)

    @Query("SELECT * FROM giveaways")
    suspend fun getGiveaways(): List<Giveaways>

    @Query("SELECT * FROM giveaways WHERE id = :searchId LIMIT 1")
    suspend fun getGiveawaysById(searchId: Int): Giveaways

    @Query("SELECT * FROM giveaways WHERE platforms LIKE '%' || :platform || '%'")
    suspend fun getGiveawaysByPlatform(platform: String): List<Giveaways>

    @Query("DELETE FROM giveaways")
    suspend fun deleteAllGiveaways()

    @Delete
    suspend fun deleteGiveaways(giveaways: Giveaways)
}