package com.citesoftware.test4.fragments.lista.TareaLibre
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.citesoftware.test4.R
import com.citesoftware.test4.database.model.TareaLibre
import android.view.LayoutInflater
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.item_tarea_libre.view.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class ListaTareaLibreAdapter(val context: Context): RecyclerView.Adapter<ListaTareaLibreAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private var tareaLibreLista = emptyList<TareaLibre>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_tarea_libre, parent, false))
    }

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemActual = tareaLibreLista[position]

        holder.itemView.tvTituloTareaLibre.text = itemActual.titulo
        holder.itemView.tvDescrpiconTareaLibre.text = itemActual.descripcion
        holder.itemView.cbTareaLibre.isChecked = itemActual.exportar



        val diaTarea = itemActual.dia
        val mesTarea = itemActual.mes
        val anioTarea = itemActual.anio



        Log.d("AAA", "$diaTarea, $mesTarea, $anioTarea")
        val date1 = Date(anioTarea-1900,mesTarea-1,diaTarea)
        val date2 = Date()

        val long: Long = (date2.time - date1.time)
        val diasAgregado = TimeUnit.MILLISECONDS.toDays(long)



        when {
            diasAgregado.toInt() == 0 -> {
                holder.itemView.tvAgregado.text = context.getString(R.string.agregadoHoy)
            }
            diasAgregado.toInt() == 1 -> {
                holder.itemView.tvAgregado.text = context.getString(R.string.agregadoAyer)
            }
            else -> {
                holder.itemView.tvAgregado.text = context.getString(R.string.agregado)+ "\u0020" + diasAgregado + "\u0020" + context.getString(R.string.daysAgo)
            }
        }

        if(diaTarea == 0 && mesTarea == 0 && anioTarea == 0){
            holder.itemView.tvAgregado.text = "Actualizar la fecha"
        }



        // Tachar el titulo si la tarea esta completada
        if(itemActual.exportar){
            holder.itemView.tvTituloTareaLibre.apply {
                tvTituloTareaLibre.paintFlags = tvTituloTareaLibre.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
        }else{
            holder.itemView.tvTituloTareaLibre.apply {
                tvTituloTareaLibre.paintFlags = tvTituloTareaLibre.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }


        // Subrayar el titulo si la tarea es proxima
        if(itemActual.proxima && !itemActual.exportar){
            holder.itemView.tvTituloTareaLibre.apply {
                tvTituloTareaLibre.paintFlags = tvTituloTareaLibre.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            }
        }else{
            holder.itemView.tvTituloTareaLibre.apply {
                tvTituloTareaLibre.paintFlags = tvTituloTareaLibre.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()

            }
        }

        // Color del item

        when (itemActual.color) {
            context.getString(R.string.rosa) -> {
                holder.itemView.tvTituloTareaLibre.setTextColor(Color.parseColor(context.getString(R.string.hexRosa)))
            }
            context.getString(R.string.verde) -> {
                holder.itemView.tvTituloTareaLibre.setTextColor(Color.parseColor(context.getString(R.string.hexVerde)))
            }
            context.getString(R.string.azul) -> {
                holder.itemView.tvTituloTareaLibre.setTextColor(Color.parseColor(context.getString(R.string.hexAzul)))
            }
            context.getString(R.string.violeta) -> {
                holder.itemView.tvTituloTareaLibre.setTextColor(Color.parseColor(context.getString(R.string.hexVioleta)))
            }
            context.getString(R.string.naranja) -> {
                holder.itemView.tvTituloTareaLibre.setTextColor(Color.parseColor(context.getString(R.string.hexNaranja)))
            }
            context.getString(R.string.marron) -> {
                holder.itemView.tvTituloTareaLibre.setTextColor(Color.parseColor(context.getString(R.string.hexMarron)))
            }
            else -> {
                holder.itemView.tvTituloTareaLibre.setTextColor(Color.parseColor(context.getString(R.string.hexNegro)))
            }
        }

        holder.itemView.layoutFilaTareaLibre.setOnClickListener {
            val accion = FragmentTareaLibreDirections.actionFragmentTareaLibreToFragmentActualizarTareaLibre(itemActual)
            holder.itemView.findNavController().navigate(accion)
        }

        holder.itemView.cbTareaLibre.setOnClickListener {
            val accion = FragmentTareaLibreDirections.actionFragmentTareaLibreToFragmentActualizarTareaLibre(itemActual)
            holder.itemView.findNavController().navigate(accion)
        }

    }

    override fun getItemCount(): Int {
        return tareaLibreLista.size

    }

    fun setData(tareaLibre: List<TareaLibre>){
        this.tareaLibreLista = tareaLibre
        notifyDataSetChanged()
    }

    fun isEmpty(): Boolean{
        return tareaLibreLista.isEmpty()
    }

    fun getTarea(pos:Int): TareaLibre{
        return tareaLibreLista[pos]
    }

}