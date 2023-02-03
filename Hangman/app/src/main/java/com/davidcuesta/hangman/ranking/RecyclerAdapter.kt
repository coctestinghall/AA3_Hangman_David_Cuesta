package com.davidcuesta.hangman.ranking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.davidcuesta.hangman.R
import com.davidcuesta.hangman.utils.UserData

class RecyclerAdapter(private val userList: ArrayList<UserData>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.ranking_item, parent, false)
        return ViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.user.text = currentItem.username
        holder.sco.text = currentItem.score
        holder.ord.text = currentItem.order.toString()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val user: TextView = itemView.findViewById(R.id.nameText)
        val sco: TextView = itemView.findViewById(R.id.scoreTextBox)
        val ord: TextView = itemView.findViewById(R.id.orderText)


    }

}