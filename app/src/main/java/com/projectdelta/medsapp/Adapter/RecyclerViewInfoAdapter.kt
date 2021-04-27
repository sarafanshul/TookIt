package com.projectdelta.medsapp.Adapter

import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginLeft
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textview.MaterialTextView
import com.projectdelta.medsapp.Data.UserData
import com.projectdelta.medsapp.Data.UserDatabaseManager
import com.projectdelta.medsapp.R
import com.projectdelta.medsapp.Util.fromMilliSecondsToString
import kotlinx.android.synthetic.main.activity_info_rv_main_layout.view.*

class RecyclerViewInfoAdapter() : RecyclerView.Adapter<RecyclerViewInfoAdapter.InfoViewHolder>() {

	lateinit var data : UserData
	lateinit var context : Context

	inner class InfoViewHolder( itemView : View) : RecyclerView.ViewHolder( itemView )

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
		val _view = LayoutInflater.from( parent.context ).inflate(R.layout.activity_info_rv_main_layout , parent , false )
		return InfoViewHolder( _view )
	}

	override fun getItemCount(): Int {
		if( this::data.isInitialized ) return this.data.list.size
		return 0
	}

	override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
		holder.itemView.apply {
			info_rv_layout_tw_name.text = data.list[position]
			info_rv_layout_tw_time.text = fromMilliSecondsToString(data.timeList[position])
		}
	}

	fun set( newData : UserData ){
		this.data = newData
		notifyDataSetChanged()
	}

	fun deleteOneOrAll(viewHolder: RecyclerView.ViewHolder){

		val title = MaterialTextView(context)
		title.apply {
			text = "Remove item"
			setPaddingRelative(55 , 30, 0, 0)
//			gravity = Gravity.CENTER_HORIZONTAL
			textSize = (resources.getDimension(R.dimen.textMedium))
		}
		val dialog = MaterialAlertDialogBuilder(context)
			.setCustomTitle(title)
			.setMessage("Do you want to remove current item for one day or everyday ?")
			.setPositiveButton("ALL"){_ ,_ -> deleteAll(viewHolder) }
			.setNegativeButton("ONCE"){_ ,_ -> deleteOne(viewHolder) }
			.setNeutralButton("CANCEL"){_ ,_ ->}
			.create()

		dialog.show()
	}

	private var removed_pos = 0
	private var removed_item : String = "" ; private var removed_time : Long = 0
	fun deleteOne( viewHolder: RecyclerView.ViewHolder ){
		// cache
		removed_pos = viewHolder.adapterPosition
		removed_item = this.data.list[removed_pos]
		removed_time = this.data.timeList[removed_pos]

		this.data.list.removeAt(removed_pos)
		this.data.timeList.removeAt(removed_pos)
		notifyItemRemoved( removed_pos )

		Snackbar.make( viewHolder.itemView , "$removed_item Removed." ,Snackbar.LENGTH_LONG ).apply {
			setAction("UNDO") {
				data.list.add(removed_pos, removed_item)
				data.timeList.add(removed_pos , removed_time)
				notifyItemInserted(removed_pos)
			}
//			anchorView = viewHolder.itemView.rootView.findViewById(R.id.activity_detailed_info_fab_extract) // for on top of efab
		}.show()

	}

	fun deleteAll( viewHolder: RecyclerView.ViewHolder ){
		removed_pos = viewHolder.adapterPosition
		removed_item = this.data.list[removed_pos]
		removed_time = this.data.timeList[removed_pos]

		UserDatabaseManager.deleteAllInstances(removed_item , removed_time)
	}

}