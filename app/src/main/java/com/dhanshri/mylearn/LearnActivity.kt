package com.dhanshri.mylearn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }
}