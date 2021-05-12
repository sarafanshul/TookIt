package com.projectdelta.medsapp.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textview.MaterialTextView
import com.projectdelta.medsapp.adapter.RecyclerViewInfoAdapter
import com.projectdelta.medsapp.adapter.SwipeToDelete
import com.projectdelta.medsapp.data.UserData
import com.projectdelta.medsapp.R
import com.projectdelta.medsapp.libary.StatesRecyclerViewAdapter
import com.projectdelta.medsapp.viewModel.InfoViewModel
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {

	lateinit var adapter: RecyclerViewInfoAdapter
	lateinit var infoViewModel: InfoViewModel
	var id : Int = 0
	val REQUEST_CODE = 101

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		infoViewModel = ViewModelProvider( this , ViewModelProvider.AndroidViewModelFactory.getInstance(this.application) ).get( InfoViewModel::class.java )

		setTheme(R.style.Theme_MedsApp)
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


		info_fab_add.setOnClickListener {

			Intent( this , AddEventActivity::class.java ).also{
				it.putExtra("ID" , id)
				it.putExtra("DAY" , info_tw_day.text.toString())
				startActivityForResult(it , REQUEST_CODE)
			}
		}

		info_btn_back.setOnClickListener {
			finish()
		}

	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if( resultCode == Activity.RESULT_OK ){
			Snackbar.make( info_cl , "New event added !" , Snackbar.LENGTH_LONG ).apply {
				anchorView = info_fab_add
			}.show()
		}
	}

	private fun setView( ){

		info_rv_main.layoutManager = LinearLayoutManager( this )
		adapter = RecyclerViewInfoAdapter()
		val emptyView = layoutInflater.inflate(R.layout.empty_view_layout , info_rv_main , false)
		emptyView.findViewById<MaterialTextView>(R.id.empty_view_text_view).setTextColor( R.attr.theme_text )
		val statesRecyclerViewAdapter = StatesRecyclerViewAdapter( adapter , emptyView , emptyView , emptyView )
		info_rv_main.adapter = statesRecyclerViewAdapter
		adapter.context = this@InfoActivity
		info_rv_main.addItemDecoration( DividerItemDecoration( info_rv_main.context , DividerItemDecoration.VERTICAL ))

		infoViewModel.getDataById(id).observe(this , androidx.lifecycle.Observer { data ->
			if( data.list.isEmpty() ) statesRecyclerViewAdapter.state = StatesRecyclerViewAdapter.STATE_EMPTY
			else statesRecyclerViewAdapter.state = StatesRecyclerViewAdapter.STATE_NORMAL
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