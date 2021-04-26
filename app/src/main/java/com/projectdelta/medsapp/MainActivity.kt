package com.projectdelta.medsapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.projectdelta.medsapp.Adapter.RecyclerViewMainAdapter
import com.projectdelta.medsapp.Adapter.RecyclerViewTodayAdapter
import com.projectdelta.medsapp.Util.RecyclerItemClickListenr
import com.projectdelta.medsapp.ViewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

lateinit var mainViewModel: MainViewModel
lateinit var adapterToday : RecyclerViewTodayAdapter
lateinit var adapter : RecyclerViewMainAdapter


class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		mainViewModel = ViewModelProvider( this , ViewModelProvider.AndroidViewModelFactory.getInstance(this.application) ).get( MainViewModel::class.java )

		setContentView(R.layout.activity_main)

		setTodayLayout()

		setMainLayout()

		main_rec_main.addOnItemTouchListener(
			RecyclerItemClickListenr(this , main_rec_main , object : RecyclerItemClickListenr.OnItemClickListener {
				override fun onItemClick(view: View, position: Int) {
					itemOnClickRecyclerView(this@MainActivity, position)
				} // to Activity 2
				override fun onItemLongClick(view: View?, position: Int) {
					itemLongClickRecyclerView(view , position)
				} // Changes Subtext
				}
			)
		)

	}

	private fun setTodayLayout(){
		adapterToday = RecyclerViewTodayAdapter()
		main_rec_today.adapter = adapterToday
		main_rec_today.layoutManager = LinearLayoutManager( this )
		mainViewModel.today.observe(this , androidx.lifecycle.Observer { data ->
			adapterToday.set( data )
			main_tw_today.text = data.day
		})
	}

	private fun setMainLayout(){
		adapter = RecyclerViewMainAdapter()
		main_rec_main.adapter = adapter
		main_rec_main.layoutManager = LinearLayoutManager(this)
		mainViewModel.getAllExceptTodayByOrder.observe( this , androidx.lifecycle.Observer { data ->
			adapter.set( data )
		} )
	}

	private fun itemLongClickRecyclerView( view: View? , position: Int ){
		Toast.makeText(this , "Clicked " , Toast.LENGTH_SHORT).show()
	}

	private fun itemOnClickRecyclerView(context : Context, position: Int ){
		Toast.makeText(this , "Clicked " , Toast.LENGTH_SHORT).show()
	}

}