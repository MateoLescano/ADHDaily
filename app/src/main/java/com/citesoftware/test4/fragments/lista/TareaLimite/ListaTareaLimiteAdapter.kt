package com.citesoftware.test4.fragments.lista.TareaLimite

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.citesoftware.test4.R
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.navigation.findNavController
import com.citesoftware.test4.database.model.TareaLimite
import kotlinx.android.synthetic.main.item_tarea_limite.view.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class ListaTareaLimiteAdapter(val context: Context): RecyclerView.Adapter<ListaTareaLimiteAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    private var tareaLimiteLista = emptyList<TareaLimite>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_tarea_limite, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemActual = tareaLimiteLista[position]

        holder.itemView.tvTituloTareaLimite.text = itemActual.titulo
        holder.itemView.tvDescrpiconTareaLimite.text = itemActual.descripcion
        holder.itemView.tvFechaTareaLimite.text = itemActual.fecha
        holder.itemView.cbTareaLimite.isChecked = itemActual.completado

        if(itemActual.completado){
            holder.itemView.tvTituloTareaLimite.apply {
                tvTituloTareaLimite.paintFlags = tvTituloTareaLimite.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
        }else{
            holder.itemView.tvTituloTareaLimite.apply {
                tvTituloTareaLimite.paintFlags = tvTituloTareaLimite.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
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


        if(!sinCompletar && fechaCompletada < fechaHoy){
            holder.itemView.tvFechaTareaLimite.setTextColor(Color.parseColor("#E70000"))

            if(diasRetraso.toInt() == 1){
                holder.itemView.tvRestante.text = context.getString(R.string.objRetrasado)
            }else{
                holder.itemView.tvRestante.text = context.getString(R.string.objRetrasado2) + diasRetraso + context.getString(R.string.dias)
            }

        }else if(!sinCompletar && fechaCompletada.compareTo(fechaHoy) == 0) {
            holder.itemView.tvFechaTareaLimite.setTextColor(Color.parseColor("#E7B600"))
            holder.itemView.tvRestante.text = context.getString(R.string.esHoy)
        }else if(!sinCompletar && fechaHoy < fechaCompletada){

            if(diasRestantes.toInt() == 1){
                holder.itemView.tvRestante.text = context.getString(R.string.ultimoDia)
            }else{
                holder.itemView.tvRestante.text = context.getString(R.string.quedan) + diasRestantes + context.getString(R.string.dias2)
            }

        }else if(sinCompletar && fechaHoy < fechaCompletada){
            holder.itemView.tvFechaTareaLimite.setTextColor(Color.parseColor("#28a745"))

        }else{
            holder.itemView.tvFechaTareaLimite.setTextColor(Color.parseColor("#000000"))
        }

        if(holder.itemView.tvRestante.text.isEmpty()){
            holder.itemView.tvRestante.visibility = GONE
        }else{
            holder.itemView.tvRestante.visibility = VISIBLE
        }


        when (itemActual.color) {
            context.getString(R.string.rosa) -> {
                holder.itemView.tvTituloTareaLimite.setTextColor(Color.parseColor(context.getString(R.string.hexRosa)))
            }
            context.getString(R.string.verde) -> {
                holder.itemView.tvTituloTareaLimite.setTextColor(Color.parseColor(context.getString(R.string.hexVerde)))
            }
            context.getString(R.string.azul) -> {
                holder.itemView.tvTituloTareaLimite.setTextColor(Color.parseColor(context.getString(R.string.hexAzul)))
            }
            context.getString(R.string.violeta) -> {
                holder.itemView.tvTituloTareaLimite.setTextColor(Color.parseColor(context.getString(R.string.hexVioleta)))
            }
            context.getString(R.string.naranja) -> {
                holder.itemView.tvTituloTareaLimite.setTextColor(Color.parseColor(context.getString(R.string.hexNaranja)))
            }
            context.getString(R.string.marron) -> {
                holder.itemView.tvTituloTareaLimite.setTextColor(Color.parseColor(context.getString(R.string.hexMarron)))
            }
            else -> {
                holder.itemView.tvTituloTareaLimite.setTextColor(Color.parseColor(context.getString(R.string.hexNegro)))
            }
        }

        // Esto va a estar cuando cree el fragmento actualizar
        holder.itemView.layoutFilaTareaLimite.setOnClickListener {
            val accion = FragmentTareaLimiteDirections.actionFragmentTareaLimiteToFragmentActualizarTareaLimite(itemActual)
            holder.itemView.findNavController().navigate(accion)
        }

        holder.itemView.cbTareaLimite.setOnClickListener {
            val accion = FragmentTareaLimiteDirections.actionFragmentTareaLimiteToFragmentActualizarTareaLimite(itemActual)
            holder.itemView.findNavController().navigate(accion)
        }



    }

    override fun getItemCount(): Int {
        return tareaLimiteLista.size
    }

    fun setData(tareaLimite: List<TareaLimite>) {
        this.tareaLimiteLista = tareaLimite
        notifyDataSetChanged()
    }

    fun isEmpty(): Boolean{
        return tareaLimiteLista.isEmpty()
    }

    fun getTarea(pos:Int): TareaLimite {
        return tareaLimiteLista[pos]
    }


}