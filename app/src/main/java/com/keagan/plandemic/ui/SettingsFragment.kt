package com.keagan.plandemic.ui

import android.content.Intent
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.keagan.plandemic.R
import com.keagan.plandemic.auth.AuthActivity
import com.keagan.plandemic.auth.SessionManager

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val dailyReminder = findPreference<SwitchPreferenceCompat>("pref_daily_reminder")
        val reminderTime = findPreference<Preference>("pref_reminder_time")
        reminderTime?.isEnabled = dailyReminder?.isChecked == true
        dailyReminder?.setOnPreferenceChangeListener { _, newValue ->
            reminderTime?.isEnabled = (newValue as? Boolean) == true
            true
        }

        val signOut = findPreference<Preference>("pref_sign_out")
        signOut?.setOnPreferenceClickListener {
            SessionManager(requireContext()).signOut()
            startActivity(Intent(requireContext(), AuthActivity::class.java))
            requireActivity().finish()
            true
        }
    }
}
