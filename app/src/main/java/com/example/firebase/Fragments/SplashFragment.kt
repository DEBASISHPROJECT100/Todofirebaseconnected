package com.example.firebase.Fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.firebase.R
import com.google.firebase.auth.FirebaseAuth


private lateinit var auth: FirebaseAuth
private lateinit var navcontrol : NavController

class SplashFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        navcontrol = Navigation.findNavController(view)
        Handler(Looper.myLooper()!!).postDelayed(Runnable {
            if (auth.currentUser!=null){
                navcontrol.navigate(R.id.action_splashFragment_to_homeFragment)
            }else{
                navcontrol.navigate(R.id.action_splashFragment_to_singupFragment)
            }
        },2000)

    }
}