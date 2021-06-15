package com.citesoftware.test4.fragments.tareaToObjetivo

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.citesoftware.test4.R
import com.citesoftware.test4.database.model.TareaLimite
import com.citesoftware.test4.database.viewModel.TareaLimiteViewModel
import com.citesoftware.test4.notificaciones.service.AlarmService
import kotlinx.android.synthetic.main.fragment_actualizar_tarea_libre.view.*
import kotlinx.android.synthetic.main.fragment_agregar_tarea_limite.*
import kotlinx.android.synthetic.main.fragment_agregar_tarea_limite.view.*
import kotlinx.android.synthetic.main.fragment_tarea_a_objetivo.*
import kotlinx.android.synthetic.main.fragment_tarea_a_objetivo.view.*
import java.util.*

class FragmentTareaAObjetivo : Fragment(), DatePickerDialog.OnDateSetListener {

    private val args : FragmentTareaAObjetivoArgs by navArgs()
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tarea_a_objetivo, container, false)
        tareaLimiteViewModel = ViewModelProvider(this).get(TareaLimiteViewModel::class.java)

        val objetivoConvertido = args.tareaLibreVieja

        val titulo = objetivoConvertido.titulo
        val descri = objetivoConvertido.descripcion
        color = objetivoConvertido.color

        view.etNombreConvertir.setText(titulo)
        view.etDescripcionConvertir.setText(descri)

        view.btnConvertir.setOnClickListener {
            insertDataToDatabase()
        }

        view.etFechaConvertir.setOnClickListener {
            getDateTimeCalendar()

            DatePickerDialog(requireContext(), this, anio,mes,dia).show()
        }

        view.tvAgregarAlarmaConvertir.setOnClickListener {
            setAlarm{timeInMillis -> alarmService.setExactAlarm(timeInMillis)
                val cuando = DateFormat.format(getString(R.string.formatoNoti), timeInMillis).toString()
                tvAgregarAlarmaConvertir.text = getString(R.string.serasNoti) + cuando
            }

        }

        return view
    }

    private fun insertDataToDatabase() {

        val tituloTE = etNombreConvertir.text.toString()
        val descripcionTE = etDescripcionConvertir.text.toString()
        val fechaTL = etFechaConvertir.text.toString()
        val dia = savedDia
        val mes = savedMes
        val anio = savedAnio



        if(inputCheck(tituloTE,fechaTL)){

            // Crear Objeto Tarea
            val tareaObj = TareaLimite(0,tituloTE, descripcionTE, fechaTL,dia, mes,false,anio,color)

            // Agregar data a la DB
            tareaLimiteViewModel.addTareaLimite(tareaObj)
            Toast.makeText(requireContext(), getString(R.string.objetivoAgregado), Toast.LENGTH_SHORT).show()

            // Navigate back
            findNavController().navigate(R.id.action_fragmentTareaAObjetivo_to_fragmentTareaLibre)

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

        getDateTimeCalendar()
        etFechaConvertir.text = "$savedDia/$savedMes"

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