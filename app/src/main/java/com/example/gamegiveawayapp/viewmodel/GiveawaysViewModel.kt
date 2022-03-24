package com.example.gamegiveawayapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamegiveawayapp.model.Giveaways
import com.example.gamegiveawayapp.network.DatabaseRepository
import com.example.gamegiveawayapp.network.GiveawaysRepository
import com.example.gamegiveawayapp.utils.GiveawayState
import com.example.gamegiveawayapp.utils.PlatformType
import com.example.gamegiveawayapp.utils.SortType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class GiveawaysViewModel(
    private val networkRepo: GiveawaysRepository,
    private val databaseRepository: DatabaseRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

): ViewModel() {

    init{
        Log.d("GiveawaysViewModel", "VIEWMODEL initializing")
    }

    var platform: PlatformType = PlatformType.ANDROID
    var dbPlatform: PlatformType = platform

    var userChoice: Giveaways? = null

    private val _sortedGiveaways: MutableLiveData<GiveawayState> =
        MutableLiveData(GiveawayState.LOADING)
    val giveaways: LiveData<GiveawayState> get()= _sortedGiveaways

    fun getSortedGiveaways(sortBy: SortType = SortType.DATE){
        viewModelScope.launch(ioDispatcher) {
            try{
                val response = networkRepo.getAllGiveaways(sortBy)
                if(response.isSuccessful){
                    response.body()?.let {
                        //if not null do something here
                        databaseRepository.insertGiveaways(it)
                        val localData = databaseRepository.getGiveaways()
                        _sortedGiveaways.postValue(GiveawayState.SUCCESS(localData))

                    }?: throw Exception("Response is null")
                }else{
                    throw Exception("No successful response")
                }

            }catch(e: Exception){
                //catch all errors
                _sortedGiveaways.postValue(GiveawayState.ERROR(e))
                loadSortedFromDB()
            }
        }
    }

    private suspend fun loadSortedFromDB() {
        try{
            val localData = databaseRepository.getGiveaways()
            _sortedGiveaways.postValue(GiveawayState.SUCCESS(localData, isLocalData = true))
        }catch(e: Exception){
            _sortedGiveaways.postValue(GiveawayState.ERROR(e))
        }
    }

    private suspend fun loadPlatformFromDB(){
        try{
            dbPlatform = if(platform == PlatformType.PS4){
                PlatformType.PS4DB
            }else if(platform == PlatformType.PS5){
                PlatformType.PS5DB
            }else{
                platform
            }
            Log.d("GiveawaysViewModel","dbPlatform= ${dbPlatform.realValue}")
            val localData = databaseRepository.getGiveawaysByPlatform(dbPlatform.realValue)
            _sortedGiveaways.postValue(GiveawayState.SUCCESS(localData))
        }catch (e: Exception){
            _sortedGiveaways.postValue(GiveawayState.ERROR(e))
        }
    }


    fun getGiveawaysByPlatform(){
        viewModelScope.launch(ioDispatcher) {
            Log.d("GiveawayAdapter","platform = ${platform.realValue}")
            try{
                val response = networkRepo.getAllGiveawaysByPlatform(platform)
                if(response.isSuccessful){
                    response.body()?.let {
                        //if not null do something here
                        databaseRepository.insertGiveaways(it)
                        _sortedGiveaways.postValue(GiveawayState.SUCCESS(it))
                    }?: throw Exception("Response is null")
                }else{
                    throw Exception("No successful response")

                }

            }catch(e: Exception){
                //catch all errors
                _sortedGiveaways.postValue(GiveawayState.ERROR(e))
                loadPlatformFromDB()
            }
        }
    }



    override fun onCleared() {
        super.onCleared()

        Log.d("GiveawaysViewModel", "VIEWMODEL cleared")
    }
}