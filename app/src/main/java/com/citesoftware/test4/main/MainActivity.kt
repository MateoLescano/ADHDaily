package com.citesoftware.test4.main

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.Menu
import android.view.MenuItem
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.citesoftware.test4.R
import com.citesoftware.test4.notificaciones.service.AlarmService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_agregar_tarea_libre.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var alarmService: AlarmService


    
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        alarmService = AlarmService(this)

        val topLevelDestinations = setOf(R.id.fragmentTareaLibre,R.id.fragmentTareaEvento,R.id.fragmentTareaLimite)

        AppBarConfiguration.Builder(topLevelDestinations).build()

        button1.setOnClickListener {
            myNavHostFragment.findNavController().navigate(R.id.fragmentTareaLibre)

            button1.setBackgroundColor(Color.parseColor("#6699cc"))
            button2.setBackgroundColor(Color.parseColor("#1034a6"))
            button3.setBackgroundColor(Color.parseColor("#1034a6"))
            tvFechaMain.visibility = INVISIBLE
        }

        button2.setOnClickListener {
            myNavHostFragment.findNavController().navigate(R.id.fragmentTareaLimite)

            button1.setBackgroundColor(Color.parseColor("#1034a6"))
            button2.setBackgroundColor(Color.parseColor("#6699cc"))
            button3.setBackgroundColor(Color.parseColor("#1034a6"))
            tvFechaMain.visibility = VISIBLE
        }

        button3.setOnClickListener {
            myNavHostFragment.findNavController().navigate(R.id.fragmentTareaEvento)

            button1.setBackgroundColor(Color.parseColor("#1034a6"))
            button2.setBackgroundColor(Color.parseColor("#1034a6"))
            button3.setBackgroundColor(Color.parseColor("#6699cc"))
            tvFechaMain.visibility = VISIBLE

        }

        val dateFormat = SimpleDateFormat(getString(R.string.formatFechaMain), Locale.forLanguageTag(getString(R.string.languageTag)))
        val date = dateFormat.format(Date())

        tvFechaMain.text = getString(R.string.fechaHome) + date
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()

        val dateFormat = SimpleDateFormat(getString(R.string.formatFechaMain), Locale.forLanguageTag(getString(R.string.languageTag)))
        val date = dateFormat.format(Date())

        tvFechaMain.text = getString(R.string.fechaHome) + date
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.info_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this, info_activity::class.java)
        when(item.itemId){
            R.id.infoMenu -> startActivity(intent)
            R.id.alarmMenu -> setAlarm{timeInMillis -> alarmService.setExactAlarmMain(timeInMillis)
                val cuando = DateFormat.format(getString(R.string.formatoNoti), timeInMillis).toString()
                Toast.makeText(this, getString(R.string.serasNoti) + cuando,Toast.LENGTH_LONG).show()}
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setAlarm(callback: (Long) -> Unit){
        Calendar.getInstance().apply {
            this.set(Calendar.SECOND, 0)
            this.set(Calendar.MILLISECOND, 0)

            DatePickerDialog(this@MainActivity,
                0,
                { _, year, month, dayOfMonth ->
                    this.set(Calendar.YEAR, year)
                    this.set(Calendar.MONTH, month)
                    this.set(Calendar.DAY_OF_MONTH,dayOfMonth)

                    TimePickerDialog(
                        this@MainActivity,
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

