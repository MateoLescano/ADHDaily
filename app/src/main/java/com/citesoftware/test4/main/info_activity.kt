package com.citesoftware.test4.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.citesoftware.test4.R
import kotlinx.android.synthetic.main.activity_info.*

class info_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val intent = Intent(this, MainActivity::class.java)

        buttonInfo.setOnClickListener {
            startActivity(intent)
        }

        buttonContacto.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)

            val mailDesarrollador = getString(R.string.mailContacto)
            val asunto = getString(R.string.asuntoMail)
            val mensaje = getString(R.string.Version) + ", " + getString(R.string.localeInfo) + ".\n\n"

            intent.type = "text/html"

            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>(mailDesarrollador))

            intent.putExtra(Intent.EXTRA_SUBJECT, asunto)

            intent.putExtra(Intent.EXTRA_TEXT, mensaje)

            intent.setPackage("com.google.android.gm")

            startActivity(intent)
        }

        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}