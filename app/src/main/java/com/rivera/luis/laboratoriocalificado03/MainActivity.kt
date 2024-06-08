package com.rivera.luis.laboratoriocalificado03

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.rivera.luis.laboratoriocalificado03.Adapter.TeacherAdapter
import com.rivera.luis.laboratoriocalificado03.Controller.ApiClient
import com.rivera.luis.laboratoriocalificado03.Controller.TeacherResponse
import com.rivera.luis.laboratoriocalificado03.Service.ApiService
import com.rivera.luis.laboratoriocalificado03.databinding.ActivityMainBinding
import com.rivera.luis.laboratoriocalificado03.modelo.Teacher
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TeacherAdapter
    private val teacherList = mutableListOf<Teacher>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        fetchTeachers()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TeacherAdapter(teacherList) { teacher, action ->
            when (action) {
                TeacherAdapter.Action.CALL -> callTeacher(teacher.phone)
                TeacherAdapter.Action.EMAIL -> emailTeacher(teacher.email)
            }
        }
        binding.recyclerView.adapter = adapter
    }



    private fun fetchTeachers() {
        val apiService = ApiClient.getRetrofitInstance().create(ApiService::class.java)
        val call = apiService.getTeachers()
        call.enqueue(object : Callback<TeacherResponse> {
            override fun onResponse(call: Call<TeacherResponse>, response: Response<TeacherResponse>) {
                if (response.isSuccessful) {
                    val teacherResponse = response.body()
                    if (teacherResponse != null && teacherResponse.getTeachers() != null) {
                        teacherList.addAll(teacherResponse.getTeachers()!!)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<TeacherResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }

    private fun callTeacher(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        startActivity(intent)
    }

    private fun emailTeacher(email: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$email")
            putExtra(Intent.EXTRA_SUBJECT, "Subject")
            putExtra(Intent.EXTRA_TEXT, "Body")
        }
        startActivity(Intent.createChooser(intent, "Send email..."))
    }
}
