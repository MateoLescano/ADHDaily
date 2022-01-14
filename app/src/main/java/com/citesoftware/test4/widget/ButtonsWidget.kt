package com.citesoftware.test4.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import androidx.navigation.NavDeepLinkBuilder
import com.citesoftware.test4.R

/**
 * Implementation of App Widget functionality.
 */
class ButtonsWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val views = RemoteViews(context.packageName, R.layout.buttons_widget)

    val pendingIntentT = NavDeepLinkBuilder(context.applicationContext)
        .setGraph(R.navigation.navigation)
        .setDestination(R.id.fragmentTareaLibre)
        .createPendingIntent()

    val pendingIntentO = NavDeepLinkBuilder(context.applicationContext)
        .setGraph(R.navigation.navigation)
        .setDestination(R.id.fragmentTareaLimite)
        .createPendingIntent()


    val pendingIntentE = NavDeepLinkBuilder(context.applicationContext)
        .setGraph(R.navigation.navigation)
        .setDestination(R.id.fragmentTareaEvento)
        .createPendingIntent()



    views.setOnClickPendingIntent(R.id.btnWidgetTareas,pendingIntentT)
    views.setOnClickPendingIntent(R.id.btnWidgetObj,pendingIntentO)
    views.setOnClickPendingIntent(R.id.btnWidgetEventos,pendingIntentE)

    appWidgetManager.updateAppWidget(appWidgetId, views)



//    val widgetText = context.getString(R.string.appwidget_text)
//    // Construct the RemoteViews object
//    val views = RemoteViews(context.packageName, R.layout.buttons_widget)
//    views.setTextViewText(R.id.appwidget_text, widgetText)
//
//    // Instruct the widget manager to update the widget
//    appWidgetManager.updateAppWidget(appWidgetId, views)

}