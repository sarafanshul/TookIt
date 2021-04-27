package com.projectdelta.medsapp.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AlertDialogLayout
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.projectdelta.medsapp.Adapter.RecyclerViewInfoAdapter
import com.projectdelta.medsapp.Constant.DAYS
import com.projectdelta.medsapp.Data.UserData
import com.projectdelta.medsapp.R
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

		info_btn_add.setOnClickListener {
			val dialogLayout = layoutInflater.inflate(R.layout.full_dialog , null)
			val dialog = AlertDialog.Builder(this , R.style.fullScreenAlertDialog)
				.setView(dialogLayout)
				.setPositiveButton("ADD"){_ , _ -> }
				.setNegativeButton("Cancel"){_ , _ -> }
				.create()
			dialog.show()
		}

	}

	private fun setView( ){
		adapter = RecyclerViewInfoAdapter()
		info_rv_main.adapter = adapter
		info_rv_main.layoutManager = LinearLayoutManager( this )

		infoViewModel.getDataById(id).observe(this , androidx.lifecycle.Observer { data ->
			adapter.set(data)
			info_tw_day.text = data.day
		})

	}
}