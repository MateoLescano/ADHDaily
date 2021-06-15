package com.citesoftware.test4.fragments.lista.TareaEvento

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
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
import com.citesoftware.test4.database.viewModel.TareaEventoViewModel
import com.citesoftware.test4.fragments.lista.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.fragment_tarea_evento.view.*


class FragmentTareaEvento : Fragment() {

    private lateinit var tareaEventoViewModel: TareaEventoViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//         Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tarea_evento, container, false)

        view.fabAgregarTareaEvento.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentTareaEvento_to_fragmentAgregarTareaEvento)
        }


        //RecyclerView
        val adapter = ListaTareaEventoAdapter(requireContext())
        val recyclerView = view.rvTareaEvento
        val mensaje = view.tvInstruccionesTareaEvento
        val imagen = view.imageViewEvento
        val textoImagen = view.tvImagenEvento

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))

        //ViewModel

        tareaEventoViewModel = ViewModelProvider(this).get(TareaEventoViewModel::class.java)
        tareaEventoViewModel.readAllData.observe(viewLifecycleOwner, Observer {tareaEvento ->
            adapter.setData(tareaEvento)

            //Muestra el recyclerview solo si tiene items
            if(adapter.isEmpty()){
                recyclerView.visibility = View.INVISIBLE
                mensaje.visibility = View.VISIBLE
                textoImagen.visibility = View.VISIBLE
                imagen.visibility = View.VISIBLE
            }else{
                recyclerView.visibility = View.VISIBLE
                mensaje.visibility = View.INVISIBLE
                textoImagen.visibility = View.INVISIBLE
                imagen.visibility = View.INVISIBLE
            }

        })

        //Menu
        setHasOptionsMenu(true)

        // Swipe para eliminar Item
        val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                tareaEventoViewModel.deleteTareaEvento(adapter.getTarea(pos))
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
            tareaEventoViewModel.deleteAll()
            Toast.makeText(requireContext(), getString(R.string.tareasEliminadas), Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton(R.string.no){_,_->}
        builder.setTitle(getString(R.string.menuTituloEliminar))
        builder.setMessage(getString(R.string.eliminarEventosConfirmacion))
        builder.create().show()
    }



}