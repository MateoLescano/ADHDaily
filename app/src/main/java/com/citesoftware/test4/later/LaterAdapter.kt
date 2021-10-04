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
import android.view.LayoutInflater
import androidx.navigation.findNavController
import com.citesoftware.test4.database.model.Later
import com.citesoftware.test4.later.FragmentLaterDirections
import kotlinx.android.synthetic.main.fragment_agregar_later.view.*
import kotlinx.android.synthetic.main.item_later.view.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class LaterAdapter(val context: Context): RecyclerView.Adapter<LaterAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private var LaterLista = emptyList<Later>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_later, parent, false))
    }

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemActual = LaterLista[position]

        holder.itemView.tvTituloLater.text = itemActual.titulo
        holder.itemView.tvDescrpiconLater.text = itemActual.descripcion



        // Color del item

        when (itemActual.color) {
            context.getString(R.string.rosa) -> {
                holder.itemView.tvTituloLater.setTextColor(Color.parseColor(context.getString(R.string.hexRosa)))
            }
            context.getString(R.string.verde) -> {
                holder.itemView.tvTituloLater.setTextColor(Color.parseColor(context.getString(R.string.hexVerde)))
            }
            context.getString(R.string.azul) -> {
                holder.itemView.tvTituloLater.setTextColor(Color.parseColor(context.getString(R.string.hexAzul)))
            }
            context.getString(R.string.violeta) -> {
                holder.itemView.tvTituloLater.setTextColor(Color.parseColor(context.getString(R.string.hexVioleta)))
            }
            context.getString(R.string.naranja) -> {
                holder.itemView.tvTituloLater.setTextColor(Color.parseColor(context.getString(R.string.hexNaranja)))
            }
            context.getString(R.string.marron) -> {
                holder.itemView.tvTituloLater.setTextColor(Color.parseColor(context.getString(R.string.hexMarron)))
            }
            else -> {
                holder.itemView.tvTituloLater.setTextColor(Color.parseColor(context.getString(R.string.hexNegro)))
            }
        }

        holder.itemView.layoutFilaLater.setOnClickListener {
            val accion = FragmentLaterDirections.actionFragmentLaterToFragmentActualizarLater(itemActual)
            holder.itemView.findNavController().navigate(accion)
        }



    }

    override fun getItemCount(): Int {
        return LaterLista.size

    }

    fun setData(later: List<Later>){
        this.LaterLista = later
        notifyDataSetChanged()
    }

    fun isEmpty(): Boolean{
        return LaterLista.isEmpty()
    }

    fun getTarea(pos:Int): Later{

        return LaterLista[pos]
    }

}