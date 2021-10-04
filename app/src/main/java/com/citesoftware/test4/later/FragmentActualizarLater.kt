package com.citesoftware.test4.fragments.actualizar

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.citesoftware.test4.R
import com.citesoftware.test4.database.model.Later
import com.citesoftware.test4.database.viewModel.LaterViewModel
import kotlinx.android.synthetic.main.fragment_actualizar_later.*
import kotlinx.android.synthetic.main.fragment_actualizar_later.view.*


class FragmentActualizarLater : Fragment(), AdapterView.OnItemSelectedListener {

    private val args by navArgs<FragmentActualizarLaterArgs>()
    private lateinit var laterViewModel: LaterViewModel


    var color = " "
    var colorInicial = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_actualizar_later, container, false)

        laterViewModel = ViewModelProvider(this).get(LaterViewModel::class.java)

        val id = args.laterActual.id
        val titulo = args.laterActual.titulo
        val descri = args.laterActual.descripcion


        when (args.laterActual.color) {
            getString(R.string.rosa) -> {
                colorInicial = 2
            }
            getString(R.string.verde) -> {
                colorInicial = 6
            }
            getString(R.string.azul) -> {
                colorInicial = 3
            }
            getString(R.string.violeta) -> {
                colorInicial = 4
            }
            getString(R.string.naranja) -> {
                colorInicial = 1
            }
            getString(R.string.marron) -> {
                colorInicial = 5
            }
            else -> {
                colorInicial = 0
            }
        }

        view.etUpdateNombreLater.setText(args.laterActual.titulo)
        view.etUpdateDescripcionLater.setText(args.laterActual.descripcion)

        val posicion = args.laterActual.posicion
        if(posicion == 1479){
            view.etPosicion.setHint(getString(R.string.ultimaPosicion)).toString()
        }else{
            view.etPosicion.setText((posicion).toString())
        }

        view.btnUpdateLater.setOnClickListener {
            actualizarItem()
        }


        // SPINNER DE COLORES

        val spinner: Spinner = view.findViewById(R.id.spinnerActualizarColorLater)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.ArrayColor,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
            spinner.onItemSelectedListener = this
            spinner.setSelection(colorInicial)
        }

        //Agregar menu eliminacion
        setHasOptionsMenu(true)

        return view
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        color = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        color = getString(R.string.sinColor)
    }



    private fun actualizarItem(){
        val tituloActualizado = etUpdateNombreLater.text.toString()
        val descripcionActualizada = etUpdateDescripcionLater.text.toString()
        val posicionActualizada: Int

        if(etPosicion.text.isEmpty()){
            posicionActualizada = 1479
        }else{
            posicionActualizada = etPosicion.text.toString().toInt()
        }


        if(inputCheck(tituloActualizado)){

            val LaterActualizada = Later(args.laterActual.id, tituloActualizado, descripcionActualizada, color, posicionActualizada)

            laterViewModel.updateLater(LaterActualizada)


            findNavController().navigate(R.id.action_fragmentActualizarLater_to_fragmentLater)


        }else{
            Toast.makeText(requireContext(), getString(R.string.completarCampos), Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(tituloTL: String): Boolean{
        return !(TextUtils.isEmpty(tituloTL))
    }

    // MENU DE ELIMINAR UNA TAREA


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_eliminar, menu)
    }




    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.menuEliminar){
            eliminarTarea()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun eliminarTarea() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.si)){_,_->
            laterViewModel.deleteLater(args.laterActual)
            Toast.makeText(requireContext(),
                getString(R.string.tarea) + args.laterActual.titulo + getString(R.string.eliminada), Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_fragmentActualizarLater_to_fragmentLater)
        }
        builder.setNegativeButton(getString(R.string.no)){_,_->}
        builder.setTitle(getString(R.string.eliminar) + args.laterActual.titulo + "'")
        builder.setMessage(getString(R.string.confirmacionEliminarTarea) + args.laterActual.titulo + "?")
        builder.create().show()
    }
}