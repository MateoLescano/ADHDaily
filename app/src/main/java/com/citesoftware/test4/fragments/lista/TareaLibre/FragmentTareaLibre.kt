package com.citesoftware.test4.fragments.lista.TareaLibre

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.citesoftware.test4.R
import com.citesoftware.test4.database.viewModel.TareaLibreViewModel
import com.citesoftware.test4.fragments.lista.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.fragment_tarea_libre.view.*


class FragmentTareaLibre : Fragment() {

    private lateinit var tareaLibreViewModel: TareaLibreViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//         Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tarea_libre, container, false)

        view.fabAgregarTareaLibre.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentTareaLibre_to_fragmentAgregarTarea)
        }

        //RecyclerView
        val adapter = ListaTareaLibreAdapter(requireContext())
        val recyclerView = view.rvTareaLibre
        val mensaje = view.tvInstruccionesTareaLibre
        val imagen = view.imageView
        val textoImagen = view.tvImagenTL

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))


        //ViewModel

        tareaLibreViewModel = ViewModelProvider(this).get(TareaLibreViewModel::class.java)
        tareaLibreViewModel.readAllData.observe(viewLifecycleOwner, Observer {tareaLibre ->
            adapter.setData(tareaLibre)



            //Muestra el recyclerview solo si tiene items
            if(adapter.isEmpty()){
                recyclerView.visibility = INVISIBLE
                mensaje.visibility = VISIBLE
                textoImagen.visibility = VISIBLE
                imagen.visibility = VISIBLE
            }else{
                recyclerView.visibility = VISIBLE
                mensaje.visibility = INVISIBLE
                textoImagen.visibility = INVISIBLE
                imagen.visibility = INVISIBLE
            }
        })



        //Menu
        setHasOptionsMenu(true)


        // Swipe para eliminar Item
        val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                tareaLibreViewModel.deleteTareaLibre(adapter.getTarea(pos))
                adapter.notifyItemRemoved(pos)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        return view

    }



    // MENU ELIMINAR TODOS

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_eliminar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuEliminar){
            eliminarTodo()

        }

        return super.onOptionsItemSelected(item)
    }

    private fun eliminarTodo() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(R.string.si){_,_->
            tareaLibreViewModel.deleteAll()
            Toast.makeText(requireContext(), getString(R.string.tareasEliminadas), Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton(R.string.no){_,_->}
        builder.setTitle(getString(R.string.menuTituloEliminar))
        builder.setMessage(getString(R.string.eliminarTareasConfirmacion))
        builder.create().show()
    }

}

