package com.example.studyswipe.ui.settings

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.studyswipe.R
import com.example.studyswipe.utils.FileUtils
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val exportPreference: Preference? = findPreference("export")
        exportPreference?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            // Create and show your dialog here
            Log.d("SettingsFragment", "Exporting")
            FileUtils.exportTopics()

            AlertDialog.Builder(requireContext())
                .setTitle("Success")
                .setMessage("Topics exported successfully")
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
            true
        }

        val importPreference: Preference? = findPreference("import")
        importPreference?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            Log.d("SettingsFragment", "Importing")
            performFileSearch()
            true
        }

    }

    private val READ_REQUEST_CODE: Int = 42

    private fun performFileSearch() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("application/octet-stream"))
        }

        startActivityForResult(intent, READ_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            resultData?.data?.also { uri ->
                importFile(uri)
            }
        }
    }

    private fun importFile(uri: Uri) {
            try {
                val inputStream = requireContext().contentResolver.openInputStream(uri)
                val file = File.createTempFile("temp", ".expSS")
                FileOutputStream(file).use { output ->
                    inputStream?.copyTo(output)
                }
                FileUtils.importTopics(file, this.requireContext())
                AlertDialog.Builder(requireContext())
                    .setTitle("Success")
                    .setMessage("Topics imported successfully")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            } catch (e: IOException) {
                // Handle the exception
                e.printStackTrace()
            }
    }
}