package com.citesoftware.test4.later

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.citesoftware.test4.R
import com.citesoftware.test4.database.model.Later
import com.citesoftware.test4.database.viewModel.LaterViewModel
import kotlinx.android.synthetic.main.fragment_agregar_later.*
import kotlinx.android.synthetic.main.fragment_agregar_later.view.*
import java.text.SimpleDateFormat
import java.util.*

class FragmentAgregarLater : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var laterViewModel: LaterViewModel
    var color = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_agregar_later, container, false)

        laterViewModel = ViewModelProvider(this).get(LaterViewModel::class.java)

        view.btnAgregarLater.setOnClickListener {
            insertDataToDatabase()
        }


        // SPINNER DE COLORES

        val spinner: Spinner = view.findViewById(R.id.spinnerColorLater)
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

    @SuppressLint("SimpleDateFormat")
    private fun insertDataToDatabase() {

        val tituloTL = etNombreLater.text.toString()
        val descripcionTL = etDescripcionLater.text.toString()




        if (inputCheck(tituloTL)) {

            // Crear Objeto Tarea
            val tareaObj = Later(0, tituloTL, descripcionTL, color)

            // Agregar data a la DB
            laterViewModel.addLater(tareaObj)
            Toast.makeText(requireContext(), getString(R.string.tareaAgregada), Toast.LENGTH_SHORT).show()

            // Navigate back
            findNavController().navigate(R.id.action_fragmentAgregarLater_to_fragmentLater)

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


}
