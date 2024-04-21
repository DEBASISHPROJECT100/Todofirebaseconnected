package com.example.firebase.Fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebase.R
import com.example.firebase.databinding.FragmentBlankHomeBinding
import com.example.firebase.utils.Todoadapter
import com.example.firebase.utils.Tododata
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment(), AddtodopopupFragment.DialogNextbtnclickListner,
    Todoadapter.Todoadaptertaskbtnclicklistner {
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseref: DatabaseReference
    private lateinit var navcontroll: NavController
    private lateinit var binding: FragmentBlankHomeBinding
    private   var popupfragment: AddtodopopupFragment? = null
    private lateinit var adapter: Todoadapter
    private lateinit var mlist: MutableList<Tododata>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBlankHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        getdatafromfirebase()
        registerevnts()

    }

    private fun registerevnts() {
        binding.addbtn.setOnClickListener {
            if (popupfragment!=null)
                childFragmentManager.beginTransaction().remove(popupfragment!!).commit()
            popupfragment = AddtodopopupFragment()
            popupfragment!!.setListner(this)
            popupfragment!!.show(
                childFragmentManager,
                AddtodopopupFragment.TAG
            )
        }

    }

    private fun init(view: View) {
        navcontroll = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
        databaseref = FirebaseDatabase.getInstance().reference.child("tasks")
            .child(auth.currentUser?.uid.toString())
        binding.Recycelerv.setHasFixedSize(true)
        binding.Recycelerv.layoutManager = LinearLayoutManager(context)
        mlist = mutableListOf()
        adapter = Todoadapter(mlist)
        adapter.setlistner(this)
        binding.Recycelerv.adapter = adapter
    }

    private fun getdatafromfirebase() {
        databaseref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mlist.clear()
                for (tasksnapshot in snapshot.children) {
                    val todotask = tasksnapshot.key?.let {
                        Tododata(it, tasksnapshot.value.toString())
                    }

                    if (todotask != null) {
                        mlist.add(todotask)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onSaveTask(todo: String, addtext: TextInputEditText) {
        databaseref.push().setValue(todo).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "TOdo saved successfully", Toast.LENGTH_SHORT).show()
                addtext.text = null
            } else {
                Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
            }
            popupfragment!!.dismiss()
        }
    }

    override fun onupdateTask(tododata: Tododata, addtext: TextInputEditText) {
        val map =  HashMap<String , Any>()
        map [tododata.taskid] = tododata.task
        databaseref.updateChildren(map).addOnCompleteListener {

            if (it.isSuccessful){
                Toast.makeText(context, "Task update sucessfully", Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
            }
            addtext.text = null
            popupfragment!!.dismiss()
        }
    }

    override fun ondeletetaskbtnclick(tododata: Tododata) {
        databaseref.child(tododata.taskid).removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "Deleted sucessfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onedittaskbtnclik(tododata: Tododata) {
        if (popupfragment!=null)
            childFragmentManager.beginTransaction().remove(popupfragment!!).commit()
        popupfragment =  AddtodopopupFragment.newInstance(tododata.taskid,tododata.task)
        popupfragment!!.setListner(this)
        popupfragment!!.show(childFragmentManager , AddtodopopupFragment.TAG)
    }
}