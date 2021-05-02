package com.projectdelta.medsapp.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.projectdelta.medsapp.R

class SettingsActivity : AppCompatActivity() , PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.settings_activity)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		if (savedInstanceState == null) {
			supportFragmentManager
				.beginTransaction()
				.replace(R.id.settings, SettingsFragment())
				.commit()
		}

	}

	class SettingsFragment : PreferenceFragmentCompat() {
		override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
			setPreferencesFromResource(R.xml.root_preferences, rootKey)

		}
	}

	override fun onPreferenceStartFragment(
		caller: PreferenceFragmentCompat?,
		pref: Preference?
	): Boolean {
		val args = pref!!.extras
		val fragment = supportFragmentManager.fragmentFactory.instantiate(
			classLoader,
			pref!!.fragment)
		fragment.arguments = args
		fragment.setTargetFragment(caller, 0)
		// Replace the existing Fragment with the new Fragment
		supportFragmentManager.beginTransaction()
			.setCustomAnimations(R.anim.enter_anim , R.anim.exit_anim)
			.replace(R.id.settings, fragment)
			.addToBackStack(null)
			.commit()
		return true
	}
}