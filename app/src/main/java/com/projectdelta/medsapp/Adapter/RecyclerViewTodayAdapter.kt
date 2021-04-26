package com.projectdelta.medsapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projectdelta.medsapp.Data.UserData
import com.projectdelta.medsapp.R
import kotlinx.android.synthetic.main.main_rec_today_layout.view.*

class RecyclerViewTodayAdapter( ) : RecyclerView.Adapter<RecyclerViewTodayAdapter.TodayViewHolder>( ) {

    lateinit var data : UserData

    inner class TodayViewHolder( itemView : View) : RecyclerView.ViewHolder( itemView )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayViewHolder {
        val view = LayoutInflater.from( parent.context ).inflate( R.layout.main_rec_today_layout , parent , false )
        return TodayViewHolder( view )
    }

    override fun getItemCount(): Int{
        if( this::data.isInitialized ) return  this.data.list.size
        return 0
    }

    override fun onBindViewHolder(holder: TodayViewHolder, position: Int) {
        holder.itemView.apply {
            main_rec_today_tw_list.text = data.list[ position ]
            main_rec_today_tw_time.text = data.timeList[position].toString()
        }
    }

    fun set( _data : UserData ){
        this.data = _data
        notifyDataSetChanged()
    }

}