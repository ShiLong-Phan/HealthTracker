package com.hci.healthtracker

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.content.BroadcastReceiver
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.core.app.NotificationCompat

class ReminderDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Reminders.db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE medication_reminders (
                id INTEGER PRIMARY KEY,
                name TEXT,
                medication TEXT,
                dosage TEXT,
                time TEXT,
                image_path TEXT
            )
        """)
        db.execSQL("""
            CREATE TABLE appointment_reminders (
                id INTEGER PRIMARY KEY,
                name TEXT,
                date TEXT,
                time TEXT,
                location TEXT,
                reminder_time TEXT
            )
        """)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database upgrades
    }

    fun insertMedicationReminder(name: String, medication: String, dosage: String, time: String, imagePath: String?): Long {
        val values = ContentValues().apply {
            put("name", name)
            put("medication", medication)
            put("dosage", dosage)
            put("time", time)
            put("image_path", imagePath)
        }
        return writableDatabase.insert("medication_reminders", null, values)
    }

    fun insertAppointmentReminder(name: String, date: String, time: String, location: String, reminderTime: String): Long {
        val values = ContentValues().apply {
            put("name", name)
            put("date", date)
            put("time", time)
            put("location", location)
            put("reminder_time", reminderTime)
        }
        return writableDatabase.insert("appointment_reminders", null, values)
    }

    // Add methods for querying, updating, and deleting reminders
}

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Create and show a notification
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("reminders", "Reminders", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, "reminders")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(intent.getStringExtra("title"))
            .setContentText(intent.getStringExtra("message"))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(intent.getIntExtra("id", 0), notification)
    }
}

fun setAlarm(context: Context, timeInMillis: Long, title: String, message: String, id: Int) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java).apply {
        putExtra("title", title)
        putExtra("message", message)
        putExtra("id", id)
    }
    val pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
}