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
import com.citesoftware.test4.database.model.TareaLimite
import com.citesoftware.test4.database.viewModel.TareaLimiteViewModel
import com.citesoftware.test4.notificaciones.service.AlarmService
import kotlinx.android.synthetic.main.fragment_agregar_tarea_limite.*
import kotlinx.android.synthetic.main.fragment_agregar_tarea_limite.view.*
import java.text.SimpleDateFormat
import java.util.*

class FragmentAgregarTareaLimite : Fragment(), DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    private lateinit var tareaLimiteViewModel: TareaLimiteViewModel
    lateinit var alarmService: AlarmService

    var dia = 0
    var mes = 0
    var anio = 0

    var savedDia = 0
    var savedMes = 0
    var savedAnio = 0

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
        val view = inflater.inflate(R.layout.fragment_agregar_tarea_limite, container, false)

        tareaLimiteViewModel = ViewModelProvider(this).get(TareaLimiteViewModel::class.java)

        view.btnTareaLimite.setOnClickListener {
            insertDataToDatabase()
        }

        view.etFechaLimite.setOnClickListener {
            getDateTimeCalendar()

            DatePickerDialog(requireContext(), this, anio,mes,dia).show()
        }

        view.tvAgregarAlarmaLimite.setOnClickListener {
            setAlarm{timeInMillis -> alarmService.setExactAlarm(timeInMillis)
            val cuando = DateFormat.format(getString(R.string.formatoNoti), timeInMillis).toString()
            tvAgregarAlarmaLimite.text = getString(R.string.serasNoti) + cuando
            }

        }

        val spinner: Spinner = view.findViewById(R.id.spinnerColorLimite)
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

        val tituloTE = etNombreTareaLimite.text.toString()
        val descripcionTE = etDescripcionTareaLimite.text.toString()
        val fechaTL = etFechaLimite.text.toString()
        val dia = savedDia
        val mes = savedMes
        val anio = savedAnio


        if(inputCheck(tituloTE, fechaTL)){

            // Crear Objeto Tarea
            val tareaObj = TareaLimite(0,tituloTE, descripcionTE, fechaTL,dia, mes,false, anio, color)

            // Agregar data a la DB
            tareaLimiteViewModel.addTareaLimite(tareaObj)
            Toast.makeText(requireContext(), getString(R.string.objetivoAgregado),Toast.LENGTH_SHORT).show()

            // Navigate back
            findNavController().navigate(R.id.action_fragmentAgregarTareaLimite_to_fragmentTareaLimite)

        } else{
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



    @SuppressLint("SetTextI18n")
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDia = dayOfMonth
        savedMes = month
        savedAnio = year

        val date = Date(savedAnio, savedMes,savedDia)
        val dateFormat = SimpleDateFormat(getString(R.string.formatoDia))
        val fecha = dateFormat.format(date)

        getDateTimeCalendar()
        etFechaLimite.text = fecha

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