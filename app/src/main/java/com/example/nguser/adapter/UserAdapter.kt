package com.example.nguser.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import com.example.nguser.R
import com.example.nguser.data.model.User

class UserAdapter(private val context: Context, private val users: MutableList<User>, private val onItemClickListener: OnItemClickListener): BaseAdapter() {

    private lateinit var textName: TextView
    private lateinit var textEmail: TextView
    private lateinit var textPhone: TextView

    class DiffCallback : DiffUtil.ItemCallback<User>(){
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

    }

    override fun getCount(): Int {
        return users.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("SetTextI18n", "ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        convertView = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false)

        textName = convertView.findViewById(R.id.txt_name)
        textEmail = convertView.findViewById(R.id.txt_email)
        textPhone = convertView.findViewById(R.id.txt_phone)

        textName.text = users[position].name
        textEmail.text = "Email : " +  users[position].email
        textPhone.text = "Phone : " + users[position].mobile

        convertView.setOnClickListener {
            onItemClickListener.onItemClick(users[position])
        }

        return convertView
    }

    interface OnItemClickListener{
        fun onItemClick(user: User)
    }

}