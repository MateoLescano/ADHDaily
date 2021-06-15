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
import com.citesoftware.test4.database.model.TareaLibre
import com.citesoftware.test4.database.viewModel.TareaLibreViewModel
import kotlinx.android.synthetic.main.fragment_actualizar_tarea_libre.*
import kotlinx.android.synthetic.main.fragment_actualizar_tarea_libre.view.*


class FragmentActualizarTareaLibre : Fragment(), AdapterView.OnItemSelectedListener {

    private val args by navArgs<FragmentActualizarTareaLibreArgs>()

    private lateinit var tareaLibreViewModel: TareaLibreViewModel

    var color = " "
    var colorInicial = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_actualizar_tarea_libre, container, false)

        tareaLibreViewModel = ViewModelProvider(this).get(TareaLibreViewModel::class.java)

        val id = args.tareaLibreActual.id
        val titulo = args.tareaLibreActual.titulo
        val descri = args.tareaLibreActual.descripcion
        val comple = args.tareaLibreActual.exportar
        val proxima = args.tareaLibreActual.proxima
        val colorV = args.tareaLibreActual.color

        when (colorV) {
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



        view.etUpdateNombreTareaLibre.setText(args.tareaLibreActual.titulo)
        view.etUpdateDescripcionTareaLibre.setText(args.tareaLibreActual.descripcion)
        view.switchCompletada.isChecked = args.tareaLibreActual.exportar
        view.switchProximaActualizar.isChecked = args.tareaLibreActual.proxima


        if(args.tareaLibreActual.exportar){
            view.tvEstadoSwitch.text = getString(R.string.si)
        }else{
            view.tvEstadoSwitch.text = getString(R.string.no)
        }
        view.switchCompletada.setOnClickListener {
            if(switchCompletada.isChecked){
                view.tvEstadoSwitch.text = getString(R.string.si)
            }else{
                view.tvEstadoSwitch.text = getString(R.string.no)
            }
        }

        view.btnUpdateTarea.setOnClickListener {
            actualizarItem()
        }

        view.btnTareaAObjetivo.setOnClickListener {
            val tareaLibreVieja: TareaLibre = TareaLibre(id, titulo, descri, comple, proxima, colorV)
            val action = FragmentActualizarTareaLibreDirections.actionFragmentActualizarTareaLibreToFragmentTareaAObjetivo(tareaLibreVieja)
            findNavController().navigate(
                action
            )
            tareaLibreViewModel.deleteTareaLibre(args.tareaLibreActual)
        }

        // SPINNER DE COLORES

        val spinner: Spinner = view.findViewById(R.id.spinnerActualizarColorLibre)
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
        val tituloActualizado = etUpdateNombreTareaLibre.text.toString()
        val descripcionActualizada = etUpdateDescripcionTareaLibre.text.toString()
        val completadaActualizada = switchCompletada.isChecked
        val siguienteActualizada = switchProximaActualizar.isChecked



        if(inputCheck(tituloActualizado)){

            val tareaLibreActualizada = TareaLibre(args.tareaLibreActual.id, tituloActualizado, descripcionActualizada, completadaActualizada,siguienteActualizada,color)

            tareaLibreViewModel.updateTareaLibre(tareaLibreActualizada)


            findNavController().navigate(R.id.action_fragmentActualizarTareaLibre_to_fragmentTareaLibre)

            if(completadaActualizada){
                Toast.makeText(requireContext(), getString(R.string.felicitacionTareaCompletada), Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(requireContext(), getString(R.string.objetivoActualizado), Toast.LENGTH_SHORT).show()
            }
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
            tareaLibreViewModel.deleteTareaLibre(args.tareaLibreActual)
            Toast.makeText(requireContext(),
                getString(R.string.tarea) + args.tareaLibreActual.titulo + getString(R.string.eliminada), Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_fragmentActualizarTareaLimite_to_fragmentTareaLimite)
        }
        builder.setNegativeButton(getString(R.string.no)){_,_->}
        builder.setTitle(getString(R.string.eliminar) + args.tareaLibreActual.titulo + "'")
        builder.setMessage(getString(R.string.confirmacionEliminarTarea) + args.tareaLibreActual.titulo + "?")
        builder.create().show()
    }
}