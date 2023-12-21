package com.dhanshri.mylearn

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.dhanshri.mylearn.databinding.ActivityLearnBinding
import com.dhanshri.mylearn.databinding.DialogSaveBinding
import java.util.Calendar

class LearnActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityLearnBinding

    private lateinit var alertDialog : DialogSaveBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_learn)

        createNotificationChannel()

        mBinding.btnExplore.setOnClickListener {
            val intent = Intent(this, ChatGptActivity::class.java)
            startActivity(intent)
        }
        mBinding.btnAddLearning.setOnClickListener {
//            mBinding.dialogSave.root.visibility = android.view.View.VISIBLE
            showDataDialog()
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun showDataDialog() {
        val dialog = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_save, null)
        dialog.setView(dialogView)
        dialog.setCancelable(true)
        val customDialog = dialog.create()
        customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        customDialog.window?.requestFeature(Window.FEATURE_NO_TITLE)

        val btnSave = dialogView.findViewById<View>(R.id.btn_save)
        val courseName = dialogView.findViewById<EditText>(R.id.et_course_name)
        val datepicker = dialogView.findViewById<DatePicker>(R.id.datePicker)
        val timepicker = dialogView.findViewById<TimePicker>(R.id.timePicker)
        val dailyTime = dialogView.findViewById<TimePicker>(R.id.daily_time)
        btnSave.setOnClickListener {
            Toast.makeText(this, "$courseName", Toast.LENGTH_SHORT).show()
            sheduleNotification()
        }
        customDialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun sheduleNotification() {
        val intent = Intent(applicationContext, NotificationReceiver::class.java)
        val title = "Learning"
        val message = "Learning"
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(applicationContext, notificationID,intent
            , PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        val alarManager =  getSystemService(Context.ALARM_SERVICE) as AlarmManager
        //val time = getTime()

//        alarManager.setExactAndAllowWhileIdle(
//            AlarmManager.RTC_WAKEUP,
//            time,
//            pendingIntent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = "Notification channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID,name,importance)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getTime(): Long {


        val minute = alertDialog.timePicker.minute
        val hour = alertDialog.timePicker.hour

        val day = alertDialog.datePicker.dayOfMonth
        val month = alertDialog.datePicker.month
        val year = alertDialog.datePicker.year

        val calendar = Calendar.getInstance()
        calendar.set(year,month,day, hour, minute)

        return calendar.timeInMillis

    }
}