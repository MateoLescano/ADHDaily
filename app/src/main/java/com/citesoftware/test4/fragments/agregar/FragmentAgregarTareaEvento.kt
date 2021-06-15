package com.citesoftware.test4.fragments.agregar

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.citesoftware.test4.R
import com.citesoftware.test4.database.model.TareaEvento
import com.citesoftware.test4.database.viewModel.TareaEventoViewModel
import com.citesoftware.test4.notificaciones.service.AlarmService
import kotlinx.android.synthetic.main.fragment_agregar_tarea_evento.*
import kotlinx.android.synthetic.main.fragment_agregar_tarea_evento.view.*
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

class FragmentAgregarTareaEvento : Fragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, AdapterView.OnItemSelectedListener {

    private lateinit var tareaEventoViewModel: TareaEventoViewModel
    lateinit var alarmService: AlarmService

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alarmService = AlarmService(requireContext())
    }


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_agregar_tarea_evento, container, false)

        tareaEventoViewModel = ViewModelProvider(this).get(TareaEventoViewModel::class.java)

        view.btnTareaEvento.setOnClickListener {
            insertDataToDatabase()
        }

        view.etFechaEvento.setOnClickListener {
            getDateTimeCalendar()
            DatePickerDialog(requireContext(), this, anio,mes,dia).show()
        }

        view.etHoraEvento.setOnClickListener {
            getDateTimeCalendar()
            TimePickerDialog(requireContext(), this, hora,min,true).show()
        }

        view.tvAgregarAlarmaEvento.setOnClickListener {
            setAlarm{timeInMillis -> alarmService.setExactAlarmEvent(timeInMillis)
                val cuando = DateFormat.format(getString(R.string.formatoNoti), timeInMillis).toString()
                tvAgregarAlarmaEvento.text = getString(R.string.serasNoti) + cuando}
        }

        val spinner: Spinner = view.findViewById(R.id.spinnerColorEvento)
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
        }

        return view
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        color = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        color = getString(R.string.sinColor)
    }


    private fun insertDataToDatabase() {

        val tituloTE = etNombreTareaEvento.text.toString()
        val descripcionTE = etDescripcionTareaEvento.text.toString()
        val fechaTE = etFechaEvento.text.toString()
        val horaTE = etHoraEvento.text.toString()
        val dia = savedDia
        val mes = savedMes
        val horario = savedHora
        val anio = savedAnio

        if(inputCheck(tituloTE, fechaTE, horaTE)){

            // Crear Objeto Tarea
            val tareaObj = TareaEvento(0,tituloTE, descripcionTE, fechaTE,horaTE,dia,mes,horario,false, anio, color)

            // Agregar data a la DB
            tareaEventoViewModel.addTareaEvento(tareaObj)
            Toast.makeText(requireContext(), getString(R.string.eventoAgregado),Toast.LENGTH_SHORT).show()

            // Navigate back
            findNavController().navigate(R.id.action_fragmentAgregarTareaEvento_to_fragmentTareaEvento)

        } else{
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


        val date = Date(savedAnio, savedMes,savedDia)
        val dateFormat = SimpleDateFormat(getString(R.string.formatoDia))
        val fecha = dateFormat.format(date)


        getDateTimeCalendar()
        TimePickerDialog(requireContext(), this, hora,min,true).show()

        etFechaEvento.text = fecha
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHora = hourOfDay
        savedMin = minute

        val date = Time(savedHora,savedMin,0)
        val dateFormat = SimpleDateFormat(getString(R.string.formatoHora))
        val hora = dateFormat.format(date)

        etHoraEvento.text = hora
    }


    private fun setAlarm(callback: (Long) -> Unit){
        Calendar.getInstance().apply {
            this.set(Calendar.SECOND, 0)
            this.set(Calendar.MILLISECOND, 0)

            DatePickerDialog(requireContext(),
                0,
                { _, year, month, dayOfMonth ->
                    this.set(Calendar.YEAR, year)
                    this.set(Calendar.MONTH, month)
                    this.set(Calendar.DAY_OF_MONTH,dayOfMonth)

                    TimePickerDialog(
                        requireContext(),
                        { _, hourOfDay, minute ->
                            this.set(Calendar.HOUR_OF_DAY, hourOfDay)
                            this.set(Calendar.MINUTE, minute)
                            callback(this.timeInMillis)
                        },
                        this.get(Calendar.HOUR_OF_DAY),
                        this.get(Calendar.MINUTE),
                        true
                    ).show()

                },
                this.get(Calendar.YEAR),
                this.get(Calendar.MONTH),
                this.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }


}