package com.keagan.plandemic.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.keagan.plandemic.MainActivity
import com.keagan.plandemic.R

class SignupFragment : Fragment(R.layout.fragment_signup) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val name = view.findViewById<EditText>(R.id.inputName)
        val email = view.findViewById<EditText>(R.id.inputEmail)
        val password = view.findViewById<EditText>(R.id.inputPassword)
        val btnCreate = view.findViewById<Button>(R.id.btnCreate)
        val linkLogin = view.findViewById<TextView>(R.id.linkLogin)

        btnCreate.setOnClickListener {
            // Part 2: local stub "account creation"
            if (name.text.isNotEmpty() && email.text.isNotEmpty() && password.text.isNotEmpty()) {
                val sm = SessionManager(requireContext())
                sm.setLoggedIn(email.text.toString(), name.text.toString())
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            } else {
                // Optional: show error
            }
        }

        linkLogin.setOnClickListener {
            findNavController().navigate(R.id.action_signup_to_login)
        }
    }
}
