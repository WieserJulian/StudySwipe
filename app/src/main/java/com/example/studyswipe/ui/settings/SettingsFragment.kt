package com.example.studyswipe.ui.settings

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.studyswipe.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val exportPreference: Preference? = findPreference("export")
        exportPreference?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            // Create and show your dialog here

            true
        }

        val importPreference: Preference? = findPreference("import")
        importPreference?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            // Create and show your dialog here
            val dialog = ImportDialogFragment()
            dialog.show(parentFragmentManager, "ImportDialog")
            true
        }


    }
}