package com.projectdelta.medsapp.Adapter

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projectdelta.medsapp.Data.UserData
import com.projectdelta.medsapp.R
import kotlinx.android.synthetic.main.main_rec_main_layout.view.*

class RecyclerViewMainAdapter() : RecyclerView.Adapter<RecyclerViewMainAdapter.MainViewHolder>( ) {

    lateinit var data : List<UserData>

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val _view = LayoutInflater.from( parent.context ).inflate(R.layout.main_rec_main_layout, parent , false )
        return MainViewHolder( _view )
    }

    override fun getItemCount(): Int {
        if( this::data.isInitialized ) return data.size // minus today
        return 0
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        var str = "" ; data[position].list.forEach { str += it.first + "\n" }
        holder.itemView.apply {
            rec_main_tw_1.text = data[position].day

            rec_main_tw_data.text = str

        }
    }

    fun set( _data : List<UserData> ){
        this.data = _data
        notifyDataSetChanged()
    }

    fun skipView(holder: MainViewHolder){
        holder.itemView.apply {
            rec_main_tw_1.visibility = View.GONE
        }
    }
}