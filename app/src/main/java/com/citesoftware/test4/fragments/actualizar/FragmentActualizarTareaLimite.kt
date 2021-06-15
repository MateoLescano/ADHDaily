package com.citesoftware.test4.fragments.actualizar

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.citesoftware.test4.R
import com.citesoftware.test4.database.model.TareaLimite
import com.citesoftware.test4.database.viewModel.TareaLimiteViewModel
import kotlinx.android.synthetic.main.fragment_actualizar_tarea_evento.*
import kotlinx.android.synthetic.main.fragment_actualizar_tarea_evento.view.*
import kotlinx.android.synthetic.main.fragment_actualizar_tarea_libre.view.*
import kotlinx.android.synthetic.main.fragment_actualizar_tarea_limite.*
import kotlinx.android.synthetic.main.fragment_actualizar_tarea_limite.view.*
import kotlinx.android.synthetic.main.fragment_agregar_tarea_limite.*
import kotlinx.android.synthetic.main.fragment_agregar_tarea_limite.view.*
import java.text.SimpleDateFormat
import java.util.*

class FragmentActualizarTareaLimite : Fragment(), DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    private val args by navArgs<FragmentActualizarTareaLimiteArgs>()

    private lateinit var tareaLimiteViewModel: TareaLimiteViewModel

    var dia = 0
    var mes = 0
    var anio = 0

    var savedDia = 0
    var savedMes = 0
    var savedAnio = 0

    var color = " "
    var colorInicial = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_actualizar_tarea_limite, container, false)

        tareaLimiteViewModel = ViewModelProvider(this).get(TareaLimiteViewModel::class.java)

        view.etUpdateNombreTareaLimite.setText(args.tareaActualLimite.titulo)
        view.etUpdateDescripcionTareaLimite.setText(args.tareaActualLimite.descripcion)
        view.switchCompletadaLimite.isChecked = args.tareaActualLimite.completado
        view.etUpdateFechaLimite.setText(args.tareaActualLimite.fecha)
        savedDia = args.tareaActualLimite.dia
        savedMes = args.tareaActualLimite.mes
        savedAnio = args.tareaActualLimite.anio

        val colorV = args.tareaActualLimite.color

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

        if(args.tareaActualLimite.completado){
            view.tvEstadoSwitchObj.text = getString(R.string.si)
        }else{
            view.tvEstadoSwitchObj.text = getString(R.string.no)
        }
        view.switchCompletadaLimite.setOnClickListener {
            if(switchCompletadaLimite.isChecked){
                view.tvEstadoSwitchObj.text = getString(R.string.si)
            }else{
                view.tvEstadoSwitchObj.text = getString(R.string.no)
            }
        }


        view.etUpdateFechaLimite.setOnClickListener {
            getDateTimeCalendar()
            DatePickerDialog(requireContext(), this, anio,mes,dia).show()
        }

        view.btnUpdateTareaLimite.setOnClickListener {
            actualizarItem()
        }

        val spinner: Spinner = view.findViewById(R.id.spinnerActualizarColorLimite)
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
        val tituloActualizado = etUpdateNombreTareaLimite.text.toString()
        val descripcionActualizada = etUpdateDescripcionTareaLimite.text.toString()
        val completadaActualizada = switchCompletadaLimite.isChecked
        val fechaActualizada = etUpdateFechaLimite.text.toString()
        val dia = savedDia
        val mes = savedMes
        val anio = savedAnio
        val id = args.tareaActualLimite.id
        // vo tranca este error es mentira ta toooodo bn

        if(inputCheck(tituloActualizado, fechaActualizada)){

            val tareaLimiteActualizada = TareaLimite(id, tituloActualizado, descripcionActualizada, fechaActualizada,dia,mes,completadaActualizada,anio, color)

            tareaLimiteViewModel.updateTareaLimite(tareaLimiteActualizada)


            findNavController().navigate(R.id.action_fragmentActualizarTareaLimite_to_fragmentTareaLimite)

            if(completadaActualizada){
                Toast.makeText(requireContext(), getString(R.string.felicitacionObjetivoCompletada), Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(requireContext(), getString(R.string.objetivoActualizado), Toast.LENGTH_SHORT).show()
            }

        }else{
            Toast.makeText(requireContext(), getString(R.string.completarTituloFecha), Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(tituloTE: String,date:String): Boolean{
        var aprobado = false
        if(!TextUtils.isEmpty(tituloTE) && date != getString(R.string.elegir_la_fecha)){
            aprobado= true
        }
        return aprobado
    }

    private fun getDateTimeCalendar(){
        val cal = Calendar.getInstance()
        dia = cal.get(Calendar.DAY_OF_MONTH)
        mes = cal.get(Calendar.MONTH)
        anio = cal.get(Calendar.YEAR)
    }



    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDia = dayOfMonth
        savedMes = month
        savedAnio = year

        val date = Date(savedAnio, savedMes,savedDia)
        val dateFormat = SimpleDateFormat(getString(R.string.formatoDia))
        val fecha = dateFormat.format(date)

        getDateTimeCalendar()
        etUpdateFechaLimite.text = fecha

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
            tareaLimiteViewModel.deleteTareaLimite(args.tareaActualLimite)
            Toast.makeText(requireContext(),
                getString(R.string.tarea) + args.tareaActualLimite.titulo + getString(R.string.eliminada), Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_fragmentActualizarTareaLimite_to_fragmentTareaLimite)
        }
        builder.setNegativeButton(getString(R.string.no)){_,_->}
        builder.setTitle(getString(R.string.eliminar) + args.tareaActualLimite.titulo + "'")
        builder.setMessage(getString(R.string.confirmacionEliminarTarea) + args.tareaActualLimite.titulo + "?")
        builder.create().show()
    }
}