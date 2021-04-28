package com.lambton.bikr.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lambton.bikr.R
import com.lambton.bikr.models.Restaurants
class HomeAdapter(val context: Context, private val itemList: ArrayList<Restaurants>) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtResName: TextView = view.findViewById(R.id.txtRestaurantName)
        val txtCostPerPerson: TextView = view.findViewById(R.id.txtCostPerPerson)
        val imgRes: ImageView = view.findViewById(R.id.imgRestaurantImage)
        val txtRate: TextView = view.findViewById(R.id.txtRating)
        val llContent: LinearLayout = view.findViewById(R.id.llContent)
        val imgFavourite: ImageView = view.findViewById(R.id.imgFavourite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_home_single_element, parent, false)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 5;//itemList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
//
        holder.llContent.setOnClickListener {
//            val intent = Intent(context, DetailActivity::class.java)
//            intent.putExtra("res_id", res.userId)
//            intent.putExtra("name", res.name)
//            context.startActivity(intent)
        }
    }

}