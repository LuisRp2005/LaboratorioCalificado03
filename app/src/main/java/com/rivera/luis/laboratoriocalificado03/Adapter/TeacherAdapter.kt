package com.rivera.luis.laboratoriocalificado03.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rivera.luis.laboratoriocalificado03.databinding.ItemTeacherBinding
import com.rivera.luis.laboratoriocalificado03.modelo.Teacher
import com.squareup.picasso.Picasso


class TeacherAdapter(
    private val teacherList: List<Teacher>,
    private val onItemClick: (Teacher, Action) -> Unit
) : RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder>() {

    enum class Action {
        CALL,
        EMAIL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewHolder {
        val binding = ItemTeacherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeacherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeacherViewHolder, position: Int) {
        holder.bind(teacherList[position])
    }

    override fun getItemCount(): Int = teacherList.size

    inner class TeacherViewHolder(private val binding: ItemTeacherBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(teacher: Teacher) {
            binding.textViewName.text = teacher.name
            binding.textViewSurname.text = teacher.last_name
            Picasso.get().load(teacher.imageUrl).into(binding.imageViewPhoto)

            binding.root.setOnClickListener {
                onItemClick(teacher, Action.CALL)
            }

            binding.root.setOnLongClickListener {
                onItemClick(teacher, Action.EMAIL)
                true
            }
        }
    }
}