package com.miklegol.numbersfacts.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "fact")
data class Fact(
    @PrimaryKey
    val idFact: String,
    val number: String,
    val text: String
)
