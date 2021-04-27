package com.projectdelta.medsapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projectdelta.medsapp.Data.UserData
import com.projectdelta.medsapp.R
import com.projectdelta.medsapp.Util.fromMilliSecondsToString
import kotlinx.android.synthetic.main.activity_info_rv_main_layout.view.*

class RecyclerViewInfoAdapter() : RecyclerView.Adapter<RecyclerViewInfoAdapter.InfoViewHolder>() {

    lateinit var data : UserData

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

}