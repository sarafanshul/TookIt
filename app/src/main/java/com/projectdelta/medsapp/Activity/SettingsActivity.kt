package com.projectdelta.medsapp.activity

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.*
import com.projectdelta.medsapp.R
import com.projectdelta.medsapp.util.NotificationUtil


class SettingsActivity : AppCompatActivity() ,
		PreferenceFragmentCompat.OnPreferenceStartFragmentCallback ,
		SharedPreferences.OnSharedPreferenceChangeListener {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setTheme(R.style.Theme_MedsApp)
		setContentView(R.layout.settings_activity)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		if (savedInstanceState == null) {
			supportFragmentManager
				.beginTransaction()
				.replace(R.id.settings, SettingsFragment())
				.commit()
		}

	}

	override fun onResume() {
		super.onResume()
		PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this)
	}

	override fun onPause() {
		super.onPause()
		PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this)
	}

	private val workTag = "notificationWork"

	override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
		if(key!! == "notifications" ) {
			if (sharedPreferences?.getBoolean(key, false) == true) {
				val time : Long = when(sharedPreferences?.getString("notification_pref" , "1 hour")){
					"30 minutes" -> 3600000 / 2
					"6 hours" -> 3600000 * 6
					else -> 3600000
				}
				NotificationUtil.startNotifications(this ,workTag , time )
			} else {
				NotificationUtil.stopNotifications( this , workTag )
			}
		}
		if( key!! == "notification_pref" && sharedPreferences!!.getBoolean("notifications" , false) ){
			val time : Long = when(sharedPreferences?.getString(key , "1 hour")){
				"30 minutes" -> 3600000 / 2
				"6 hours" -> 3600000 * 6
				else -> 3600000
			}
			NotificationUtil.startNotifications(this ,workTag , time )
		}
		if( key!! == "night_mode" ){
			if( sharedPreferences?.getBoolean(key , false) == true ){
				AppCompatDelegate.setDefaultNightMode( AppCompatDelegate.MODE_NIGHT_YES )
			}else {
				AppCompatDelegate.setDefaultNightMode( AppCompatDelegate.MODE_NIGHT_NO )
				AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
			}
		}
	}



	class SettingsFragment : PreferenceFragmentCompat() {
		override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
			setPreferencesFromResource(R.xml.root_preferences, rootKey)

			val night_mode = findPreference<SwitchPreference>("night_mode")
			val mode = context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)
			when (mode) {
				Configuration.UI_MODE_NIGHT_YES -> { night_mode?.isChecked = true }
				Configuration.UI_MODE_NIGHT_NO -> { night_mode?.isChecked = false }
				Configuration.UI_MODE_NIGHT_UNDEFINED -> { night_mode?.isChecked = false }
			}
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