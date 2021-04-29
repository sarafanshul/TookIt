package com.projectdelta.medsapp.Activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.projectdelta.medsapp.Adapter.RecyclerViewInfoAdapter
import com.projectdelta.medsapp.Adapter.SwipeToDelete
import com.projectdelta.medsapp.Data.UserData
import com.projectdelta.medsapp.R
import com.projectdelta.medsapp.ViewModel.InfoViewModel
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {

	lateinit var adapter: RecyclerViewInfoAdapter
	lateinit var infoViewModel: InfoViewModel
	var id : Int = 0
	val REQUEST_CODE = 101

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

			Intent( this , AddEventActivity::class.java ).also{
				it.putExtra("ID" , id)
				startActivityForResult(it , REQUEST_CODE)
			}
		}

	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if( resultCode == Activity.RESULT_OK ){
			Snackbar.make( info_cl , "New Medicine Added !" , Snackbar.LENGTH_LONG ).show()
		}
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