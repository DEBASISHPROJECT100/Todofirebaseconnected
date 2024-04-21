package com.example.firebase.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.firebase.R
import com.example.firebase.databinding.FragmentAddtodopopupBinding
import com.example.firebase.utils.Tododata
import com.google.android.material.textfield.TextInputEditText

class AddtodopopupFragment : DialogFragment() {
 private lateinit var binding: FragmentAddtodopopupBinding
 private lateinit var Listner : DialogNextbtnclickListner
private var tododata : Tododata? = null
 fun setListner(Listner : DialogNextbtnclickListner){
     this.Listner  =  Listner
 }

    companion object{
        const val TAG = "AddtodopopupFragment"
        @JvmStatic
        fun newInstance(taskid:String , task:String) = AddtodopopupFragment().apply {
            arguments = Bundle().apply {
                putString("taskid" , taskid)
                putString("task", task)
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddtodopopupBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
if (arguments!=null)
    tododata = Tododata(arguments?.getString("taskid").toString()
        ,arguments?.getString("task").toString()


    )
    binding.addtext.setText(tododata?.task)


        registerevent()
    }
    private fun registerevent(){
        binding.addtask.setOnClickListener {
            val todotask =  binding.addtext.text.toString()
            if (todotask.isNotEmpty()){
                if (tododata == null){
                    Listner.onSaveTask(todotask  , binding.addtext)
                }else{
                    tododata?.task = todotask
                    Listner.onupdateTask(tododata!!,  binding.addtext)
                }

            }else{
                Toast.makeText(context,"Please type Task",Toast.LENGTH_SHORT).show()
            }
        }
        binding.closebtn.setOnClickListener {
            dismiss()
        }
    }
    interface DialogNextbtnclickListner{
        fun onSaveTask(todo : String , addtext : TextInputEditText)
        fun onupdateTask(tododata: Tododata , addtext : TextInputEditText)
    }
}