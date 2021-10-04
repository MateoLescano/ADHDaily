package com.citesoftware.test4.fragments.lista.TareaEvento

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.citesoftware.test4.R
import android.view.LayoutInflater
import androidx.navigation.findNavController
import com.citesoftware.test4.database.model.TareaEvento
import kotlinx.android.synthetic.main.item_tarea_evento.view.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class ListaTareaEventoAdapter(val context: Context): RecyclerView.Adapter<ListaTareaEventoAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    private var tareaEventoLista = emptyList<TareaEvento>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_tarea_evento, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemActual = tareaEventoLista[position]

        holder.itemView.tvTituloTareaEvento.text = itemActual.titulo
        holder.itemView.tvDescrpiconTareaEvento.text = itemActual.descripcion
        holder.itemView.tvFechaTareaEvento.text = itemActual.fecha
        holder.itemView.tvHoraTareaEvento.text = itemActual.hora
        holder.itemView.cbTareaEvento.isChecked = itemActual.completado

        if(itemActual.completado){
            holder.itemView.tvTituloTareaEvento.apply {
                tvTituloTareaEvento.paintFlags = tvTituloTareaEvento.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
        }else{
            holder.itemView.tvTituloTareaEvento.apply {
                tvTituloTareaEvento.paintFlags = tvTituloTareaEvento.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
        val sinCompletar = itemActual.completado

        val diaTarea = itemActual.dia
        val mesTarea = itemActual.mes
        val anioTarea = itemActual.anio

        val dateFormat = SimpleDateFormat("yyyy/M/dd")

        val date1 = Date(anioTarea-1900,mesTarea,diaTarea)
        val date2 = Date()
        val fechaCompletada = dateFormat.format(date1)
        val fechaHoy = dateFormat.format(date2)

        val long: Long = (date1.time - date2.time)
        val diasRestantes = TimeUnit.MILLISECONDS.toDays(long) +1
        val diasRetraso = TimeUnit.MILLISECONDS.toDays(long) *-1

        val esHoy = date1.compareTo(date2)

        if(!sinCompletar && diasRetraso > 0){
            holder.itemView.tvFechaTareaEvento.setTextColor(Color.parseColor("#E70000"))
        }else if(!sinCompletar && esHoy == -1){
            holder.itemView.tvFechaTareaEvento.setTextColor(Color.parseColor("#E7B600"))
        }else if(sinCompletar && diasRetraso < 0){
            holder.itemView.tvFechaTareaEvento.setTextColor(Color.parseColor("#28a745"))
        }else{
            holder.itemView.tvFechaTareaEvento.setTextColor(Color.parseColor("#000000"))
        }

        when (itemActual.color) {
            context.getString(R.string.rosa) -> {
                holder.itemView.tvTituloTareaEvento.setTextColor(Color.parseColor(context.getString(R.string.hexRosa)))
            }
            context.getString(R.string.verde) -> {
                holder.itemView.tvTituloTareaEvento.setTextColor(Color.parseColor(context.getString(R.string.hexVerde)))
            }
            context.getString(R.string.azul) -> {
                holder.itemView.tvTituloTareaEvento.setTextColor(Color.parseColor(context.getString(R.string.hexAzul)))
            }
            context.getString(R.string.violeta) -> {
                holder.itemView.tvTituloTareaEvento.setTextColor(Color.parseColor(context.getString(R.string.hexVioleta)))
            }
            context.getString(R.string.naranja) -> {
                holder.itemView.tvTituloTareaEvento.setTextColor(Color.parseColor(context.getString(R.string.hexNaranja)))
            }
            context.getString(R.string.marron) -> {
                holder.itemView.tvTituloTareaEvento.setTextColor(Color.parseColor(context.getString(R.string.hexMarron)))
            }
            else -> {
                holder.itemView.tvTituloTareaEvento.setTextColor(Color.parseColor(context.getString(R.string.hexNegro)))
            }
        }


        holder.itemView.layoutFilaTareaEvento.setOnClickListener {
            val accion = FragmentTareaEventoDirections.actionFragmentTareaEventoToFragmentActualizarTareaEvento(itemActual)
            holder.itemView.findNavController().navigate(accion)

        }

        holder.itemView.cbTareaEvento.setOnClickListener {
            val accion = FragmentTareaEventoDirections.actionFragmentTareaEventoToFragmentActualizarTareaEvento(itemActual)
            holder.itemView.findNavController().navigate(accion)
        }


    }

    override fun getItemCount(): Int {
        return tareaEventoLista.size
    }

    fun setData(tareaEvento: List<TareaEvento>) {
        this.tareaEventoLista = tareaEvento
        notifyDataSetChanged()
    }

    fun isEmpty(): Boolean{
        return tareaEventoLista.isEmpty()
    }

    fun getTarea(pos:Int): TareaEvento {
        return tareaEventoLista[pos]
    }


}