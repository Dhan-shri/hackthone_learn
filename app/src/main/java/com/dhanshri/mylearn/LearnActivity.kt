package com.dhanshri.mylearn

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.dhanshri.mylearn.databinding.ActivityLearnBinding

class LearnActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityLearnBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_learn)


        mBinding.btnExplore.setOnClickListener {
            val intent = Intent(this, ChatGptActivity::class.java)
            startActivity(intent)
        }
        mBinding.btnAddLearning.setOnClickListener {
//            mBinding.dialogSave.root.visibility = android.view.View.VISIBLE
            showDataDialog()
        }
    }


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
        val startDate = dialogView.findViewById<DatePicker>(R.id.et_start_date)
        val endDate = dialogView.findViewById<DatePicker>(R.id.et_end_date)
        val dailyTime = dialogView.findViewById<TimePicker>(R.id.daily_time)
        btnSave.setOnClickListener {
            Toast.makeText(this, "$courseName", Toast.LENGTH_SHORT).show()
        }
        customDialog.show()
    }

}