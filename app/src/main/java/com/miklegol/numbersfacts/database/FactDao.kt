package com.miklegol.numbersfacts.database

import androidx.room.*
import com.miklegol.numbersfacts.models.Fact

@Dao
interface FactDao {

    @Query("SELECT * FROM fact")
    suspend fun getAllFacts(): List<Fact>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFact(fact: Fact)

    @Delete
    suspend fun deleteFact(fact: Fact)
}
