package com.projectdelta.medsapp.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TimePicker
import androidx.appcompat.app.AlertDialog
import androidx.core.view.children
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.projectdelta.medsapp.constant.WEEKDAYS
import com.projectdelta.medsapp.data.UserDatabaseManager
import com.projectdelta.medsapp.R
import com.projectdelta.medsapp.util.fromMilliSecondsToString
import com.projectdelta.medsapp.util.fromMinutesToMilliSeconds
import com.projectdelta.medsapp.viewModel.AddEventViewModel
import kotlinx.android.synthetic.main.activity_add_event.*
import java.util.*

class AddEventActivity : AppCompatActivity() {

	lateinit var eventViewModel: AddEventViewModel
	var id : Int = 0
	var cur_day : String = ""

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		eventViewModel = ViewModelProvider( this , ViewModelProvider.AndroidViewModelFactory.getInstance(this.application) ).get( AddEventViewModel::class.java )

		setTheme(R.style.Theme_MedsApp)
		setContentView(R.layout.activity_add_event)

		id = intent.getIntExtra("ID" , -1)
		cur_day = intent.getStringExtra("DAY")!!

		eventViewModel.getDataById( id ).observe(this , androidx.lifecycle.Observer { userData ->
			add_event_tw_today.text = userData.day
		})

		// set defalut time
		val calender = Calendar.getInstance()
		val offset = calender.get( Calendar.ZONE_OFFSET ) + calender.get( Calendar.DST_OFFSET )
		val sinceMidnight = ( calender.timeInMillis + offset ) % (24 * 60 * 60 * 1000)
		add_event_tw_display.text = fromMilliSecondsToString( sinceMidnight )

		// create Chips
		for( day in WEEKDAYS ){
			val chip = Chip( add_event_cg_main.context )
			chip.apply {
				text = day
				isCheckable = true
				isClickable = true
				isChecked = day == cur_day
				chipBackgroundColor = resources.getColorStateList(R.color.bg_chip_state_list)
			}
			add_event_cg_main.addView( chip )
		}

		// for time
		var time_select : Long = sinceMidnight
		add_event_btn_time.setOnClickListener {
			val dialogLayout = layoutInflater.inflate(R.layout.time_layout , null)
			val clock = dialogLayout.findViewById<TimePicker>(R.id.time_layout_clock)
			clock.setIs24HourView(true)
			val dialog = AlertDialog.Builder(this)
				.setView(dialogLayout)
				.setPositiveButton("ADD"){_ , _ ->
					time_select = fromMinutesToMilliSeconds(clock.hour*60L + clock.minute)
					add_event_tw_display.text = fromMilliSecondsToString(time_select)
				}
				.setNegativeButton("Cancel"){_ , _ -> }
				.create()
			dialog.show()
		}

		add_event_btn_add.setOnClickListener {

			val name = add_event_et_name.text.toString().trim().capitalize()
			val selectedDays = add_event_cg_main.checkedChipIds
			val df = add_event_cg_main.children.first().id // chipIds are a sequence hence not same after every onCreate
			for( i in selectedDays.size - 1 downTo 0 )
				selectedDays[i] -= df

			if( name != "" ) {
				UserDatabaseManager.updateMultipleData( this , selectedDays , name , time_select )
				resultOk()
			}
			resultCancel()
		}

		add_event_btn_cancel.setOnClickListener {
			resultCancel()
		}
	}

	private fun resultOk(){
		setResult( Activity.RESULT_OK)
		finish()
	}
	private fun resultCancel(){
		setResult( Activity.RESULT_CANCELED )
		finish()
	}

	override fun onDestroy() {
		super.onDestroy()
		add_event_cg_main.removeAllViews()
	}

}