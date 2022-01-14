package com.citesoftware.test4.main

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavDeepLinkBuilder
import com.citesoftware.test4.BuildConfig
import com.citesoftware.test4.R
import kotlinx.android.synthetic.main.activity_info.*


class info_activity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

                val pendingIntent = Intent(this, MainActivity::class.java)
//        val pendingIntent = NavDeepLinkBuilder(this.applicationContext)
//            .setGraph(R.navigation.navigation)
//            .setDestination(R.id.fragmentTareaLibre)
//            .createPendingIntent()

        val vc = BuildConfig.VERSION_CODE
        val versionName = BuildConfig.VERSION_NAME

        tvVersion.text = getString(R.string.version)+" (" + vc + ") " + versionName

        buttonInfo.setOnClickListener {
            startActivity(pendingIntent)
        }

        buttonContacto.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)

            val mailDesarrollador = getString(R.string.mailContacto)
            val asunto = getString(R.string.asuntoMail)
            val mensaje = getString(R.string.version)+" (" + vc + ") " + versionName + ", " + getString(R.string.localeInfo) + ".\n\n"

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