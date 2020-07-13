package com.example.parsegit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RecyclerAdapter  : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val userList = ArrayList<User>()

    fun addUser(newUser : User){
        userList.add(newUser)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view =  layoutInflater.inflate(R.layout.user_info_holder, parent, false)
        return UserInfoHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(userList.isNotEmpty()){
            val userHolder = holder as UserInfoHolder
            userHolder.mUserLogin.text = userList[position].login
            userHolder.mUserLocation.text = userList[position].location

            Picasso.get().load(userList[position].avatar_url).into(userHolder.mUserAvatar)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun deleteItem(position : Int){
        userList.removeAt(position)
        notifyItemRemoved(position)
    }
}
