package com.projectdelta.medsapp.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TimePicker
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AlertDialogLayout
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.ChipGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.projectdelta.medsapp.Adapter.RecyclerViewInfoAdapter
import com.projectdelta.medsapp.Adapter.SwipeToDelete
import com.projectdelta.medsapp.Constant.DAYS
import com.projectdelta.medsapp.Data.UserData
import com.projectdelta.medsapp.R
import com.projectdelta.medsapp.Util.fromMinutesToMilliSeconds
import com.projectdelta.medsapp.ViewModel.InfoViewModel
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {

	lateinit var adapter: RecyclerViewInfoAdapter
	lateinit var infoViewModel: InfoViewModel
	var id : Int = 0

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		infoViewModel = ViewModelProvider( this , ViewModelProvider.AndroidViewModelFactory.getInstance(this.application) ).get( InfoViewModel::class.java )

		setContentView(R.layout.activity_info)

		val _data = intent.getSerializableExtra("DATA") as UserData
		id = _data.id

		setView( )

		var itemTouchHelper = ItemTouchHelper(
				SwipeToDelete(
						adapter
				)
		)
		itemTouchHelper.attachToRecyclerView( info_rv_main )


		info_btn_add.setOnClickListener {
			val dialogLayout = layoutInflater.inflate(R.layout.full_dialog , null)
			val dialog = AlertDialog.Builder(this , R.style.fullScreenAlertDialog)
				.setView(dialogLayout)
				.setPositiveButton("ADD"){_ , _ ->
					getData(dialogLayout)
				}
				.setNegativeButton("Cancel"){_ , _ -> }
				.create()
			dialog.show()
		}

	}

	private fun getData( view : View){
		val full_dialog_et_name = view.findViewById<EditText>(R.id.full_dialog_et_name)
		val timePicker2 = view.findViewById<TimePicker>(R.id.timePicker2)
		val full_dialog_cg = view.findViewById<ChipGroup>(R.id.full_dialog_cg)

		val minutes = timePicker2.hour * 60L + timePicker2.minute
		val name = full_dialog_et_name.text.toString()
		val selected = full_dialog_cg.checkedChipIds
		var ids = mutableListOf<Int>()
		selected.forEach {
			when( it ){
				R.id.full_dialog_cg_sunday -> ids.add( 0 )
				R.id.full_dialog_cg_monday -> ids.add( 1 )
				R.id.full_dialog_cg_tuesday -> ids.add( 2 )
				R.id.full_dialog_cg_wednesday -> ids.add( 3 )
				R.id.full_dialog_cg_thursday -> ids.add( 4 )
				R.id.full_dialog_cg_friday -> ids.add( 5 )
				R.id.full_dialog_cg_saturday -> ids.add( 6 )
			}
		}

		ids.forEach {
			infoViewModel.getDataById( it ).observe( this@InfoActivity , androidx.lifecycle.Observer { data ->
				data.list.forEach {
					if(it == name) return@Observer
				}
				data.list.add( name )
				data.timeList.add( fromMinutesToMilliSeconds(minutes) )
				infoViewModel.update( data )
			} )
		}

		Log.d( "MYDATA" , " ${minutes}\n${name}\n${selected.toString()} " )


	}

	private fun setView( ){
		adapter = RecyclerViewInfoAdapter()
		info_rv_main.adapter = adapter
		adapter.context = this
		info_rv_main.layoutManager = LinearLayoutManager( this )

		infoViewModel.getDataById(id).observe(this , androidx.lifecycle.Observer { data ->
			adapter.set( data )
			info_tw_day.text = data.day
		})

	}

	override fun onStop() {
		super.onStop()
		infoViewModel.update( adapter.data )
	}

	override fun onPause() {
		super.onPause()
		infoViewModel.update( adapter.data )
	}

	override fun onDestroy() {
		super.onDestroy()
		infoViewModel.update( adapter.data )
	}
}