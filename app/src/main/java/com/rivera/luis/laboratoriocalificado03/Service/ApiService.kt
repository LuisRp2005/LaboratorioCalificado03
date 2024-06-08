package com.rivera.luis.laboratoriocalificado03.Service

import com.rivera.luis.laboratoriocalificado03.Controller.TeacherResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/list/teacher")
    fun getTeachers(): Call<TeacherResponse>
}


