package com.citesoftware.test4.later

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
import com.citesoftware.test4.database.viewModel.LaterViewModel

import com.citesoftware.test4.fragments.lista.SwipeToDeleteCallback
import com.citesoftware.test4.fragments.lista.TareaLibre.LaterAdapter
import kotlinx.android.synthetic.main.fragment_later.view.*


class FragmentLater : Fragment() {

    private lateinit var laterViewModel: LaterViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//         Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_later, container, false)

        view.fabAgregarLater.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentLater_to_fragmentAgregarLater)
        }

        //RecyclerView
        val adapter = LaterAdapter(requireContext())
        val recyclerView = view.rvLater
        val mensaje = view.tvInstruccionesLater
        val imagen = view.imageView
        val textoImagen = view.tvImagenLater

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))


        //ViewModel

        laterViewModel = ViewModelProvider(this).get(LaterViewModel::class.java)
        laterViewModel.readAllData.observe(viewLifecycleOwner, Observer {later ->
            adapter.setData(later)



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
                laterViewModel.deleteLater(adapter.getTarea(pos))
                adapter.notifyItemRemoved(pos)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        return view

    }

    override fun onResume() {
        super.onResume()
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
            laterViewModel.deleteAll()
            Toast.makeText(requireContext(), getString(R.string.tareasEliminadas), Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton(R.string.no){_,_->}
        builder.setTitle(getString(R.string.menuTituloEliminar))
        builder.setMessage(getString(R.string.eliminarTareasConfirmacion))
        builder.create().show()
    }

}