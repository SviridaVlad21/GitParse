package com.example.parsegit

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserInfoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var mUserAvatar = itemView.findViewById<ImageView>(R.id.iv_avatar)
    var mUserLogin = itemView.findViewById<TextView>(R.id.tv_login)
    var mUserLocation = itemView.findViewById<TextView>(R.id.tv_location)
}