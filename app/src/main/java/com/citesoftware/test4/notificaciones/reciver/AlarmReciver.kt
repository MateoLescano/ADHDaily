package com.citesoftware.test4.notificaciones.reciver

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.format.DateFormat
import com.citesoftware.test4.R
import com.citesoftware.test4.main.MainActivity
import com.citesoftware.test4.notificaciones.service.AlarmService
import com.citesoftware.test4.notificaciones.util.Constants
import io.karn.notify.Notify

class AlarmReciver(): BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val timeInMillis = intent.getLongExtra(Constants.EXTRA_EXACT_ALARM_TIME, 0L)

        when(intent.action){
            Constants.ACTION_SET_EXACT_ALARM -> {
                buildNotification(context, context.getString(R.string.recordatorio), convertDate(timeInMillis,context))
            }
        }

        when(intent.action){
            Constants.ACTION_SET_EXACT_ALARM_EVENT-> {
                buildNotificationEvent(context, context.getString(R.string.recordatorio), convertDate(timeInMillis,context))
            }
        }

        when(intent.action){
            Constants.ACTION_SET_EXACT_ALARM_TAREA-> {
                buildNotificationTarea(context, context.getString(R.string.recordatorio), convertDate(timeInMillis,context))
            }
        }

        when(intent.action){
            Constants.ACTION_SET_EXACT_ALARM_MAIN-> {
                buildNotificationMain(context, context.getString(R.string.recordatorio), convertDate(timeInMillis,context))
            }
        }


    }

    //Objetivos
    private fun buildNotification(context: Context, title: String, message: String){

        Notify
            .with(context)
            .meta {
                clickIntent = PendingIntent.getActivity(context,
                    0,
                    Intent(context, MainActivity::class.java),
                    0)
                clearIntent = PendingIntent.getService(context,
                    0,
                    Intent(context, AlarmService::class.java)
                        .putExtra("action", "clear_badges"),
                    0)
            }
            .asBigText {
                this.title = title
                this.text = context.getString(R.string.notiObjetivo)

                this.expandedText = context.getString(R.string.notiObjetivo2)

                this.bigText = context.getString(R.string.notiMain3) + message
            }
            .show()
    }

    //Eventos
    private fun buildNotificationEvent(context: Context, title: String, message: String){

        Notify
            .with(context)
            .meta {
                clickIntent = PendingIntent.getActivity(context,
                    0,
                    Intent(context, MainActivity::class.java),
                    0)
                clearIntent = PendingIntent.getService(context,
                    0,
                    Intent(context, AlarmService::class.java)
                        .putExtra("action", "clear_badges"),
                    0)
            }
            .asBigText {
                this.title = title
                this.text = context.getString(R.string.notiEvento1)
                this.expandedText = context.getString(R.string.notiEvento2)
                this.bigText = context.getString(R.string.notiMain3) + message + "."
            }
            .show()
    }

    //Tareas
    private fun buildNotificationTarea(context: Context, title: String, message: String){

        Notify
            .with(context)
            .meta {
                clickIntent = PendingIntent.getActivity(context,
                    0,
                    Intent(context, MainActivity::class.java),
                    0)
                clearIntent = PendingIntent.getService(context,
                    0,
                    Intent(context, AlarmService::class.java)
                        .putExtra("action", "clear_badges"),
                    0)
            }
            .asBigText {
                this.title = title
                this.text = context.getString(R.string.notiTareas1)
                this.expandedText = context.getString(R.string.notiTareas2)
                this.bigText = context.getString(R.string.notiMain3) + message + "."
            }
            .show()
    }

    //MenuPrincipal
    private fun buildNotificationMain(context: Context, title: String, message: String){

        Notify
            .with(context)
            .meta {
                clickIntent = PendingIntent.getActivity(context,
                    0,
                    Intent(context, MainActivity::class.java),
                    0)
                clearIntent = PendingIntent.getService(context,
                    0,
                    Intent(context, AlarmService::class.java)
                        .putExtra("action", "clear_badges"),
                    0)
            }
            .asBigText {
                this.title = title
                this.text = context.getString(R.string.notiMain1)
                this.expandedText = context.getString(R.string.notiMain2)
                this.bigText = context.getString(R.string.notiMain3) + message + "."
            }
            .show()
    }

    private fun convertDate(timeInMillis: Long,context: Context):String =
        DateFormat.format(context.getString(R.string.formatoNoti), timeInMillis).toString()
}