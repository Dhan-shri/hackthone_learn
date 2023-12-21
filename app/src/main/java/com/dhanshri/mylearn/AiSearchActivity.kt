package com.dhanshri.mylearn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.dhanshri.mylearn.databinding.ActivityAiSearchBinding
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class AiSearchActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAiSearchBinding
    private val client = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_ai_search)


        binding.btnSearch.setOnClickListener {
            val question = binding.etSearch.text.toString()
            Toast.makeText(this, question, Toast.LENGTH_SHORT).show()
            getResponseQuestion(question){ response ->
                runOnUiThread {
                    binding.tvAnswer.text = response
                }
            }
        }
    }

    fun getResponseQuestion(question: String, callback: (String) -> Unit) {
        val apiKey = " " // "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"
        val url = "https://api.openai.com/v1/completions"

        val requestBody = """
            {
               "model": "gpt-3.5-turbo-instruct",
                "prompt": "$question",
                "max_tokens": 7,
                "temperature": 0
            }
        """.trimIndent()

        val request = Request.Builder()
            .url(url)
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer $apiKey")
            .post(requestBody.toRequestBody("application/json".toMediaTypeOrNull()))
            .build()


        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("AiSearchActivity", "Error getting response", e)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if (responseBody != null) {
                    Log.v("AiSearchActivity", "Response: $responseBody")

                } else {
                    Log.e("AiSearchActivity", "Response body is null")
                }
            }
        })
    }
}