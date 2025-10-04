package com.keagan.plandemic.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.keagan.plandemic.MainActivity
import com.keagan.plandemic.R

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val googleLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            // Mark session as logged-in
            val sm = SessionManager(requireContext())
            sm.setLoggedIn(account.email ?: "unknown@google", account.displayName)
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        } catch (e: Exception) {
            // Optional: show a Toast
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val email = view.findViewById<EditText>(R.id.inputEmail)
        val password = view.findViewById<EditText>(R.id.inputPassword)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)
        val btnGoogle = view.findViewById<Button>(R.id.btnGoogle)
        val linkSignup = view.findViewById<TextView>(R.id.linkSignup)

        btnLogin.setOnClickListener {
            // For Part 2, local “fake” sign-in is fine (no backend yet).
            if (email.text.isNotEmpty() && password.text.isNotEmpty()) {
                val sm = SessionManager(requireContext())
                sm.setLoggedIn(email.text.toString(), null)
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            } else {
                // Optional: show error
            }
        }

        btnGoogle.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()     // Part 2: SSO without server token is fine
                .build()
            val client = GoogleSignIn.getClient(requireContext(), gso)
            googleLauncher.launch(client.signInIntent)
        }

        linkSignup.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_signup)
        }
    }
}
