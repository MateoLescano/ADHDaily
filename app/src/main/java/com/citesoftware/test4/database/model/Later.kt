package com.citesoftware.test4.database.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "tabla_later")
data class Later (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val color: String,
    val posicion: Int
): Parcelable