package com.rivera.luis.laboratoriocalificado03.Controller;

import com.rivera.luis.laboratoriocalificado03.modelo.Teacher;

import java.util.List;

public class TeacherResponse {
    private List<Teacher> teachers;

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }
}


