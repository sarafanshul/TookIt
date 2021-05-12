package com.projectdelta.medsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textview.MaterialTextView
import com.projectdelta.medsapp.data.UserData
import com.projectdelta.medsapp.data.UserDatabaseManager
import com.projectdelta.medsapp.R
import com.projectdelta.medsapp.util.fromMilliSecondsToString
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
			info_rv_layout_tw_name.text = data.list[position].first.capitalize()
			info_rv_layout_tw_time.text = fromMilliSecondsToString(data.list[position].second)
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
			textSize = (resources.getDimension(R.dimen.textMedium))
		}
		val dialog = MaterialAlertDialogBuilder(context , R.style.AlertDialogTheme)
			.setCustomTitle(title)
			.setMessage("Do you want to remove current item for one day or everyday ?")
			.setPositiveButton("ALL"){_ ,_ -> deleteAll(viewHolder) }
			.setNegativeButton("ONCE"){_ ,_ -> deleteOne(viewHolder) }
			.setNeutralButton("CANCEL"){_ ,_ -> notifyDataSetChanged() } // swipe glitch
			.create()

		dialog.show()
	}

	private var removed_pos = 0
	private var removed_item : String = "" ; private var removed_time : Long = 0
	fun deleteOne( viewHolder: RecyclerView.ViewHolder ){
		// cache
		removed_pos = viewHolder.adapterPosition
		removed_item = this.data.list[removed_pos].first
		removed_time = this.data.list[removed_pos].second

		this.data.list.removeAt(removed_pos)

		notifyItemRemoved( removed_pos )

		Snackbar.make( viewHolder.itemView , "${removed_item.capitalize()} Removed." ,Snackbar.LENGTH_LONG ).apply {
			setAction("UNDO") {
				data.list.add(removed_pos, Pair( removed_item , removed_time ))
				notifyItemInserted(removed_pos)
			}
			animationMode = Snackbar.ANIMATION_MODE_SLIDE
			anchorView = viewHolder.itemView.rootView.findViewById(R.id.info_fab_add) // for on top of efab
		}.show()

	}

	fun deleteAll( viewHolder: RecyclerView.ViewHolder ){
		removed_pos = viewHolder.adapterPosition
		removed_item = this.data.list[removed_pos].first
		removed_time = this.data.list[removed_pos].second

		UserDatabaseManager.deleteAllInstances(context , removed_item , removed_time)

		Snackbar.make( viewHolder.itemView , "${removed_item.capitalize()} Removed from everywhere ! " ,Snackbar.LENGTH_LONG ).apply {
			animationMode = Snackbar.ANIMATION_MODE_SLIDE
			anchorView = viewHolder.itemView.rootView.findViewById(R.id.info_fab_add) // for on top of efab
		}.show()
	}

}