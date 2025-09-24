package com.yudha.listuser.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yudha.listuser.R
import com.yudha.listuser.data.model.User

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    
    private var users = listOf<User>()
    
    fun submitList(newUsers: List<User>) {
        users = newUsers
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }
    
    override fun getItemCount(): Int = users.size
    
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvUserId: TextView = itemView.findViewById(R.id.tvUserId)
        private val tvUserName: TextView = itemView.findViewById(R.id.tvUserName)
        private val tvUserEmail: TextView = itemView.findViewById(R.id.tvUserEmail)
        
        fun bind(user: User) {
            tvUserId.text = "ID: ${user.id}"
            tvUserName.text = user.name
            tvUserEmail.text = user.email
        }
    }
}