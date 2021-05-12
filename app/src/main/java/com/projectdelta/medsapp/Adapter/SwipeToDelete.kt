package com.projectdelta.medsapp.adapter

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.projectdelta.medsapp.R


// https://www.youtube.com/watch?v=eEonjkmox-0
class SwipeToDelete(
		var adapter : RecyclerViewInfoAdapter
) : ItemTouchHelper.SimpleCallback( 0 , ItemTouchHelper.LEFT  ) { // ADD "or ItemTouchHelper.RIGHT" for right swipe
	override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
		TODO("Implement On Move ?")
		return false // because dragDirs : 0 (only Left , Right)
	}

	// called when swiped
	override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
		adapter.deleteOneOrAll(viewHolder)
	}

	// for Child Draw
	private lateinit var deleteIcon : Drawable
	private var swipeBackgroundDelete : ColorDrawable = ColorDrawable( Color.parseColor( "#B00020" ) )
	override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

		val itemView = viewHolder.itemView
		deleteIcon = ContextCompat.getDrawable( recyclerView.context ,
				R.drawable.ic_delete
		)!!

		val iconMargin_delete = ( itemView.height - deleteIcon.intrinsicHeight ) / 2

		if( dX > 0) { // Swipe Right

		}else { // Swipe Left
			swipeBackgroundDelete.setBounds( itemView.right + dX.toInt() , itemView.top , itemView.right , itemView.bottom )
			deleteIcon.setBounds(itemView.right - iconMargin_delete - deleteIcon.intrinsicHeight , itemView.top + iconMargin_delete ,
					itemView.right - iconMargin_delete ,itemView.bottom - iconMargin_delete )
			swipeBackgroundDelete.draw( c )

		}

		if( dX > 0 ){

		}
		else{
			c.clipRect(itemView.right + dX.toInt() , itemView.top , itemView.right , itemView.bottom)
			deleteIcon.draw( c )
		}

		super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
	} // https://www.youtube.com/watch?v=eEonjkmox-0
}