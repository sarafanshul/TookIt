package com.projectdelta.medsapp.Activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.projectdelta.medsapp.Constant.WEEKDAYS
import com.projectdelta.medsapp.R
import com.projectdelta.medsapp.Util.fromMilliSecondsToString
import com.projectdelta.medsapp.Util.fromMinutesToMilliSeconds
import com.projectdelta.medsapp.ViewModel.AddEventViewModel
import kotlinx.android.synthetic.main.activity_add_event.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class AddEventActivity : AppCompatActivity() {

	lateinit var eventViewModel: AddEventViewModel
	var id : Int = 0
	var cur_day : String = ""

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		eventViewModel = ViewModelProvider( this , ViewModelProvider.AndroidViewModelFactory.getInstance(this.application) ).get( AddEventViewModel::class.java )

		setContentView(R.layout.activity_add_event)

		id = intent.getIntExtra("ID" , -1)

		eventViewModel.getDataById( id ).observe(this , androidx.lifecycle.Observer { userData ->
			add_event_tw_today.text = userData.day
			cur_day = userData.day
		})
		val calendar = Calendar.getInstance()
		add_event_tw_display.text = fromMilliSecondsToString(calendar.get( Calendar.MILLISECOND ) + 0L )
		// create Chips
		for( day in WEEKDAYS ){
			val chip = Chip( add_event_cg_main.context )
			chip.apply {
				text = day
				isCheckable = true
				isClickable = true
				 isChecked = day == cur_day
			}
			add_event_cg_main.addView( chip )
		}

		// for time
		var time_select : Long = -1 // milliseconds
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
			if( time_select == -1L) {
				Toast.makeText(this , "Please fill all the cells !" , Toast.LENGTH_SHORT).show()
				return@setOnClickListener
			}

			val name = add_event_et_name.text.toString().trim().capitalize()
			val selectedDays = add_event_cg_main.checkedChipIds
			for( i in selectedDays.size - 1 downTo 0 )
				selectedDays[i] -= selectedDays[0]

			updateData( selectedDays , name , time_select )

			setResult( Activity.RESULT_OK)
			finish()
		}

		add_event_btn_cancel.setOnClickListener {
			setResult( Activity.RESULT_CANCELED )
			finish()
		}
	}

	private fun updateData(selectedDays : List<Int> , name : String , time : Long){
		val job = GlobalScope.launch {
			 selectedDays.forEach forE@{
				var userData = eventViewModel.getDataByIdMainThread(it)
				if( userData.list == null ){
					return@forE
				}
				userData.list.forEach {
					if (it.first == name && it.second == time) return@forE
				}
				var idx = userData.list.size;
				for (i in userData.list.indices) {
					if (time <= userData.list[i].second) {
						idx = i; break; }
				}
				userData.list.add(idx, Pair(name, time))
				eventViewModel.update(userData)
			}
		}
		GlobalScope.launch { job.join() }
	}

	override fun onDestroy() {
		super.onDestroy()
		add_event_cg_main.removeAllViews()
	}

}