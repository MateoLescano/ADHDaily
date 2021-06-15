package com.citesoftware.test4.database.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "tabla_tareaEvento")
data class TareaEvento (
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val titulo: String,
        val descripcion: String,
        val fecha: String,
        val hora: String,
        val dia: Int,
        val mes: Int,
        val horario: Int,
        val completado: Boolean,
        val anio: Int,
        val color: String
): Parcelable