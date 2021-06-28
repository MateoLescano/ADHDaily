package com.citesoftware.test4.fragments.actualizar

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.citesoftware.test4.R
import com.citesoftware.test4.database.model.TareaEvento
import com.citesoftware.test4.database.viewModel.TareaEventoViewModel
import kotlinx.android.synthetic.main.fragment_actualizar_tarea_evento.*
import kotlinx.android.synthetic.main.fragment_actualizar_tarea_evento.view.*
import kotlinx.android.synthetic.main.fragment_agregar_tarea_evento.*
import java.sql.Time
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class FragmentActualizarTareaEvento : Fragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, AdapterView.OnItemSelectedListener {

    private val args by navArgs<FragmentActualizarTareaEventoArgs>()

    private lateinit var tareaEventoViewModel: TareaEventoViewModel

    var dia = 0
    var mes = 0
    var anio = 0
    var hora = 0
    var min = 0

    var savedDia = 0
    var savedMes = 0
    var savedAnio = 0
    var savedHora = 0
    var savedMin = 0

    var color = " "
    var colorInicial = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_actualizar_tarea_evento, container, false)

        tareaEventoViewModel = ViewModelProvider(this).get(TareaEventoViewModel::class.java)

        view.etUpdateNombreTareaEvento.setText(args.tareaActualEvento.titulo)
        view.etUpdateDescripcionTareaEvento.setText(args.tareaActualEvento.descripcion)
        view.switchCompletadaEvento.isChecked = args.tareaActualEvento.completado
        view.etUpdateFechaEvento.setText(args.tareaActualEvento.fecha)
        view.etUpdateHoraEvento.setText(args.tareaActualEvento.hora)
        savedDia = args.tareaActualEvento.dia
        savedMes = args.tareaActualEvento.mes
        savedAnio = args.tareaActualEvento.anio
        savedHora = args.tareaActualEvento.horario


        when (args.tareaActualEvento.color) {
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

        if(args.tareaActualEvento.completado){
            view.tvEstadoSwitchEve.text = getString(R.string.si)
        }else{
            view.tvEstadoSwitchEve.text = getString(R.string.no)
        }
        view.switchCompletadaEvento.setOnClickListener {
            if(switchCompletadaEvento.isChecked){
                view.tvEstadoSwitchEve.text = getString(R.string.si)
            }else{
                view.tvEstadoSwitchEve.text = getString(R.string.no)
            }
        }

        view.btnUpdateTareaEvento.setOnClickListener {
            actualizarItem()
        }

        view.etUpdateFechaEvento.setOnClickListener {
            getDateTimeCalendar()
            DatePickerDialog(requireContext(), this, anio,mes,dia).show()
        }

        view.etUpdateHoraEvento.setOnClickListener {
            getDateTimeCalendar()
            TimePickerDialog(requireContext(), this, hora,min,true).show()
        }

        val spinner: Spinner = view.findViewById(R.id.spinnerActualizarColorEvento)
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
        color = "Sin Color"
    }

    private fun actualizarItem(){
        val tituloActualizado = etUpdateNombreTareaEvento.text.toString()
        val descripcionActualizada = etUpdateDescripcionTareaEvento.text.toString()
        val completadaActualizada = switchCompletadaEvento.isChecked
        val fechaActualizada = etUpdateFechaEvento.text.toString()
        val horaActualizada = etUpdateHoraEvento.text.toString()
        val dia = savedDia
        val mes = savedMes
        val anio = savedAnio
        val horario = savedHora

        if(inputCheck(tituloActualizado, fechaActualizada, horaActualizada)){

            val tareaEventoActualizada = TareaEvento(args.tareaActualEvento.id, tituloActualizado, descripcionActualizada, fechaActualizada,horaActualizada,dia, mes, horario,completadaActualizada,anio, color)

            tareaEventoViewModel.updateTareaEvento(tareaEventoActualizada)


            findNavController().navigate(R.id.action_fragmentActualizarTareaEvento_to_fragmentTareaEvento)

            if(completadaActualizada){
                Toast.makeText(requireContext(), getString(R.string.felicitacionEventoCompletada), Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(requireContext(), getString(R.string.objetivoActualizado), Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(requireContext(), getString(R.string.inputCheckEvento), Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(tituloTE: String,date:String, hora:String): Boolean{
        var aprobado = false
        if(!TextUtils.isEmpty(tituloTE) && date != getString(R.string.elegir_la_fecha) && hora != getString(R.string.elegir_la_hora)){
            aprobado= true
        }
        return aprobado
    }

    private fun getDateTimeCalendar(){
        val cal = Calendar.getInstance()
        dia = cal.get(Calendar.DAY_OF_MONTH)
        mes = cal.get(Calendar.MONTH)
        anio = cal.get(Calendar.YEAR)

        hora = cal.get(Calendar.HOUR)
        min = cal.get(Calendar.MINUTE)
    }



    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDia = dayOfMonth
        savedMes = month
        savedAnio = year

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //Para API 26 en adelante
            val date = LocalDate.of(savedAnio, savedMes+1, savedDia)
            val dateFormat = DateTimeFormatter.ofPattern(getString(R.string.formatDiaEvento), Locale.forLanguageTag(getString(R.string.languageTag)))
            val fechaTxt = date.format(dateFormat)
            etFechaEvento.text = fechaTxt
        } else {
            val date = Date(savedAnio, savedMes,savedDia)
            val dateFormat = SimpleDateFormat(getString(R.string.formatoDia))
            val fechaTxt = dateFormat.format(date)
            etFechaEvento.text = fechaTxt
        }

        getDateTimeCalendar()
        TimePickerDialog(requireContext(), this, hora,min,true).show()

    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHora = hourOfDay
        savedMin = minute

        val date = Time(savedHora,savedMin,0)
        val dateFormat = SimpleDateFormat(getString(R.string.formatoHora))
        val hora = dateFormat.format(date)

        etUpdateHoraEvento.text = hora
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
        builder.setPositiveButton(getString(R.string.si)) { _, _ ->
            tareaEventoViewModel.deleteTareaEvento(args.tareaActualEvento)
            Toast.makeText(
                requireContext(),
                getString(R.string.tarea) + args.tareaActualEvento.titulo + getString(R.string.eliminada),
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_fragmentActualizarTareaLimite_to_fragmentTareaLimite)
        }
        builder.setNegativeButton(getString(R.string.no)) { _, _ -> }
        builder.setTitle(getString(R.string.eliminar) + args.tareaActualEvento.titulo + "'")
        builder.setMessage(getString(R.string.confirmacionEliminarTarea) + args.tareaActualEvento.titulo + "?")
        builder.create().show()
    }
}