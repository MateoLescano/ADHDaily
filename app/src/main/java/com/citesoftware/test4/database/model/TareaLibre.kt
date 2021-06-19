package com.citesoftware.test4.database.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "tabla_tareaLibre")
data class TareaLibre (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val exportar: Boolean,
    val proxima: Boolean,
    val color: String,
    val fecha: String,
    val dia: Int,
    val mes: Int,
    val anio: Int,
): Parcelable