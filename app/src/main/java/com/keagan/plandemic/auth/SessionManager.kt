package com.keagan.plandemic.auth

import android.content.Context
import androidx.core.content.edit

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("plandemic_session", Context.MODE_PRIVATE)

    fun setLoggedIn(email: String, displayName: String?) {
        prefs.edit {
            putBoolean("logged_in", true)
            putString("email", email)
            putString("display_name", displayName)
        }
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean("logged_in", false)
    fun email(): String? = prefs.getString("email", null)
    fun displayName(): String? = prefs.getString("display_name", null)

    fun signOut() {
        prefs.edit {
            clear()
        }
    }
}
