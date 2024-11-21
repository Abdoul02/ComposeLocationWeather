package com.example.composelocationweather.feature_location.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.composelocationweather.feature_location.domain.model.LOCATION_TABLE
import com.example.composelocationweather.feature_location.domain.model.UserLocation
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLocation(userLocation: UserLocation)

    @Delete
    suspend fun deleteLocation(userLocation: UserLocation)

    @Query("SELECT * FROM $LOCATION_TABLE WHERE id = :id")
    suspend fun getLocationById(id: Int): UserLocation?

    @Query("SELECT * FROM $LOCATION_TABLE ORDER BY dateCreated")
    fun getLocations(): Flow<List<UserLocation>>
}