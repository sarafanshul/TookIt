package com.projectdelta.medsapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.projectdelta.medsapp.Activity.InfoActivity
import com.projectdelta.medsapp.Activity.SettingsActivity
import com.projectdelta.medsapp.Adapter.RecyclerViewMainAdapter
import com.projectdelta.medsapp.Adapter.RecyclerViewTodayAdapter
import com.projectdelta.medsapp.Data.UserDatabaseManager
import com.projectdelta.medsapp.Adapter.RecyclerItemClickListenr
import com.projectdelta.medsapp.Data.UserData
import com.projectdelta.medsapp.Util.fromMilliSecondsToString
import com.projectdelta.medsapp.Util.getDate
import com.projectdelta.medsapp.ViewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*

lateinit var mainViewModel: MainViewModel
lateinit var adapterToday : RecyclerViewTodayAdapter
lateinit var adapter : RecyclerViewMainAdapter


class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		mainViewModel = ViewModelProvider( this , ViewModelProvider.AndroidViewModelFactory.getInstance(this.application) ).get( MainViewModel::class.java )

		setContentView(R.layout.activity_main)

		UserDatabaseManager.context = applicationContext

		setTodayLayout()

		setMainLayout()

		main_nav_view.setNavigationItemSelectedListener {
			when( it.itemId ){
				R.id.main_menu_profile -> goProfile()
				R.id.main_menu_health -> goHealth()
				R.id.main_menu_recent -> goRecent()
				R.id.main_menu_notifications -> goSettings()
				R.id.main_menu_settings -> goSettings()
				R.id.main_menu_credits -> goHelp()
				R.id.main_menu_feedback -> goHelp()
			}
			true
		}
	}

	private fun setTodayLayout(){
		main_tw_month.text = YearMonth.now().format(DateTimeFormatter.ofPattern("MMMM"))
		val calender = Calendar.getInstance()
		val offset = calender.get( Calendar.ZONE_OFFSET ) + calender.get( Calendar.DST_OFFSET )
		val sinceMidnight = ( calender.timeInMillis + offset ) % (24 * 60 * 60 * 1000)



		adapterToday = RecyclerViewTodayAdapter()
		main_rec_today.adapter = adapterToday
		main_rec_today.layoutManager = LinearLayoutManager( this )
		mainViewModel.today.observe(this , androidx.lifecycle.Observer { data ->
			if(data == null) return@Observer
			adapterToday.set( data )
			main_tw_today.text = data.day
			main_tw_int.text = getDate().toString()
			for( pi in data.list ){
				if( pi.second >= sinceMidnight ){
					main_tw_next_name.text = pi.first
					main_tw_next_time.text = fromMilliSecondsToString(pi.second)
					break
				}
			}
		})
		main_cw_top.setOnClickListener {
			itemOnClickRecyclerViewToday(this@MainActivity , adapterToday.data)
		}
		main_rec_today.addOnItemTouchListener(
			RecyclerItemClickListenr(
				this,
				main_rec_main,
				object :
					RecyclerItemClickListenr.OnItemClickListener {
					override fun onItemClick(view: View, position: Int) {
						itemOnClickRecyclerViewToday(this@MainActivity, adapterToday.data) // because today = 0
					} // to Activity 2

					override fun onItemLongClick(view: View?, position: Int) {}
				}
			)
		)
	}

	private fun itemOnClickRecyclerViewToday( context: Context , data : UserData ){
		Intent( context , InfoActivity::class.java ).also {
			it.putExtra("DATA" , data)
			startActivity( it )
		}
	}

	private fun setMainLayout(){

		adapter = RecyclerViewMainAdapter()
		main_rec_main.adapter = adapter
		main_rec_main.layoutManager = LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL , false)
		main_rec_main.setScrollingTouchSlop( RecyclerView.TOUCH_SLOP_PAGING )

		val snapHelper = PagerSnapHelper()
		snapHelper.attachToRecyclerView( main_rec_main )

		mainViewModel.getAllExceptTodayByOrder.observe( this , androidx.lifecycle.Observer { data ->
			if(data == null) return@Observer
			adapter.set( data )
		} )

		main_rec_main.addOnItemTouchListener(
			RecyclerItemClickListenr(
				this,
				main_rec_main,
				object :
					RecyclerItemClickListenr.OnItemClickListener {
					override fun onItemClick(view: View, position: Int) {
						itemOnClickRecyclerView(this@MainActivity, position)
					} // to Activity 2

					override fun onItemLongClick(view: View?, position: Int) {
						itemLongClickRecyclerView(view, position)
					} // Changes Subtext
				}
			)
		)

	}

	private fun itemLongClickRecyclerView( view: View? , position: Int ){

	}

	private fun itemOnClickRecyclerView(context : Context, position: Int ){
		val curData = adapter.data[position]
		Intent( context , InfoActivity::class.java ).also {
			it.putExtra("DATA" , curData)
			startActivity( it )
		}
	}

	private fun goSettings() {
		Intent( this , SettingsActivity::class.java ).also{
			startActivity( it )
		}
	}
	private fun goHelp() {}
	private fun goRecent() {}
	private fun goHealth() {}
	private fun goProfile() {}
}