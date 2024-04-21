package com.example.firebase.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.Fragments.eachtodoitemFragment
import com.example.firebase.databinding.FragmentEachtodoitemBinding

class Todoadapter(private val list: MutableList<Tododata>) :
    RecyclerView.Adapter<Todoadapter.TodoviewHolder>() {
    private var listner: Todoadaptertaskbtnclicklistner? = null
    fun setlistner(listner: Todoadaptertaskbtnclicklistner) {
        this.listner = listner
    }

    inner class TodoviewHolder(val binding: FragmentEachtodoitemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoviewHolder {
        val binding =
            FragmentEachtodoitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoviewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }




    override fun onBindViewHolder(holder: TodoviewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                binding.todotask.text = this.task

                binding.deletetask.setOnClickListener {
                    listner?.ondeletetaskbtnclick(this)
                }
                binding.edittext.setOnClickListener {
                    listner?.onedittaskbtnclik(this)
                }

            }
        }
    }
    interface Todoadaptertaskbtnclicklistner {
        fun ondeletetaskbtnclick(tododata: Tododata)
        fun onedittaskbtnclik(tododata: Tododata)

    }

}