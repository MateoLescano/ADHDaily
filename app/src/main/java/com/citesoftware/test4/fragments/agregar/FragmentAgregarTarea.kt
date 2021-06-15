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
import com.citesoftware.test4.database.model.TareaLibre
import com.citesoftware.test4.database.viewModel.TareaLibreViewModel
import com.citesoftware.test4.notificaciones.service.AlarmService
import kotlinx.android.synthetic.main.fragment_agregar_tarea_libre.*
import kotlinx.android.synthetic.main.fragment_agregar_tarea_libre.view.*
import java.util.*

class FragmentAgregarTarea : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var tareaLibreViewModel: TareaLibreViewModel
    lateinit var alarmService: AlarmService
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
        val view = inflater.inflate(R.layout.fragment_agregar_tarea_libre, container, false)

        tareaLibreViewModel = ViewModelProvider(this).get(TareaLibreViewModel::class.java)

        view.btnTareaLista.setOnClickListener {
            insertDataToDatabase()
        }

        view.tvAgregarAlarmaTarea.setOnClickListener {
            setAlarm{timeInMillis -> alarmService.setExactAlarmTarea(timeInMillis)
                val cuando = DateFormat.format(getString(R.string.formatoNoti), timeInMillis).toString()
                tvAgregarAlarmaTarea.text = getString(R.string.serasNoti) + cuando}
        }


        // SPINNER DE COLORES

        val spinner: Spinner = view.findViewById(R.id.spinnerColorLibre)
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

        val tituloTL = etNombreTareaLibre.text.toString()
        val descripcionTL = etDescripcionTareaLibre.text.toString()
        val proxima = switchProxima.isChecked


        if (inputCheck(tituloTL)) {

            // Crear Objeto Tarea
            val tareaObj = TareaLibre(0, tituloTL, descripcionTL, false,proxima,color)

            // Agregar data a la DB
            tareaLibreViewModel.addTareaLibre(tareaObj)
            Toast.makeText(requireContext(), getString(R.string.tareaAgregada), Toast.LENGTH_SHORT).show()

            // Navigate back
            findNavController().navigate(R.id.action_fragmentAgregarTarea_to_fragmentTareaLibre)

        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.completarCampos),
                Toast.LENGTH_LONG
            ).show()
        }

    }

    private fun inputCheck(tituloTL: String): Boolean {
        return !(TextUtils.isEmpty(tituloTL))
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
