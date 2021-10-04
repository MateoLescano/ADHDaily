package com.citesoftware.test4.later

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.citesoftware.test4.R
import com.citesoftware.test4.main.MainActivity
import com.citesoftware.test4.main.info_activity

class DespuesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_despues)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_agenda, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intentInfo = Intent(this, info_activity::class.java)
        val intentAgenda = Intent(this, MainActivity::class.java)

        when(item.itemId){
            R.id.infoMenu -> startActivity(intentInfo)
            R.id.menuAgenda -> startActivity(intentAgenda)
        }
        return super.onOptionsItemSelected(item)
    }
}