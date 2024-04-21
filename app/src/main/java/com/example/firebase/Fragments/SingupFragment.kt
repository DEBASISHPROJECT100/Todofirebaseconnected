package com.example.firebase.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.firebase.R
import com.example.firebase.databinding.FragmentBlankSingupBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class SingupFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var Navcontrol: NavController
    private lateinit var binding: FragmentBlankSingupBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBlankSingupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        registerevents()
    }

    private fun init(view: View) {
        Navcontrol = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()

    }

    private fun registerevents() {
        binding.Singin.setOnClickListener{
            Navcontrol.navigate(R.id.action_singupFragment_to_singinFragment)
        }

        binding.singup.setOnClickListener {
            val email = binding.Email.text.toString().trim()
            val pass = binding.Pass.text.toString().trim()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                 auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(
                     OnCompleteListener{
                     if (it.isSuccessful){
                         Toast.makeText(context , "Suceessfully Register" , Toast.LENGTH_SHORT).show()
                         Navcontrol.navigate(R.id.action_singupFragment_to_homeFragment)
                     }else{
                         Toast.makeText(context , it.exception?.message , Toast.LENGTH_SHORT).show()
                     }


                     })
            }
        }
    }
}