package com.example.mohitmvvmfirebase.ui.activity.emp_posts

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lambton.bikr.R
import com.lambton.bikr.activity.DelievryDetailActivity
import com.lambton.bikr.activity.RestaurentsActivity
import com.lambton.bikr.firebase.Restaurant_model
import com.mikhaellopez.circularimageview.CircularImageView


/**
 * Created by ThinkSoft on 20/12/2017.
 */
class RestuarntRecyclerAdapter(contacts: ArrayList<Restaurant_model>, contx: Context, listener: RestaurentsActivity, currentLat: Double, currentLong: Double) : RecyclerView.Adapter<RestuarntRecyclerAdapter.RecyclerViewHolder>() {
    var  currentLatii:Double = currentLat
    var  currentLongi:Double = currentLong

    override fun onBindViewHolder(holder: RecyclerViewHolder, p1: Int) {

//        if(!isEmpty) {
            var user: Restaurant_model = filteredlistContacts[p1]


        holder!!.textViewName.text = user.name
        holder!!. textViewEmail.text = user.address
        holder!!.tvDistance.text = user.distance.toString() + " Km"
        holder!!.tv_loc.text = user.description

        Glide.with(ctx)
                .load(user.image)
                .apply(RequestOptions.circleCropTransform().placeholder(R.drawable.ic_uservbv).error(
                        R.drawable.ic_uservbv))
                .into(holder!!.user_profile_photo)//binding?.userProfilePhoto



        holder!!.tv_apply.setOnClickListener(View.OnClickListener {

//            if(user.appliedPosts!!.size>0){
                var i = Intent(ctx, DelievryDetailActivity::class.java)
               i.putExtra("rest_data",user)
            i.putExtra("distance",user.distance)
            i.putExtra("current_lat",currentLatii)
            i.putExtra("current_long",currentLongi)
                listenerContact.startActivity(i)
//
        })

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerViewHolder {

    return RecyclerViewHolder(LayoutInflater.from(p0!!.context).inflate(
        R.layout.list_layout_rest, p0,
            false))
}



    // var isEmpty = false

    private var listContacts: ArrayList<Restaurant_model> = contacts
    private var filteredlistContacts: ArrayList<Restaurant_model> = contacts

    private var ctx: Context = contx
   private var listenerContact: RestaurentsActivity = listener

//    interface OnItemClickListener {
//        fun onItemClick(contact:Add_post)
//    }

    interface AdapterList_Listner {
        fun onFavChange(isFav: Boolean, position: Int)
        // var arrayList = ArrayList<Add_post>()
    }

    override fun getItemCount(): Int {
      //  var i: Int = filteredlistContacts.size
//        if(isEmpty){
//            return 1
//        }else {
            return filteredlistContacts.size
      //  }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }







    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


//        var ll_main =  itemView.findViewById(R.id.ll_main) as LinearLayout
        var textViewName = itemView.findViewById(R.id.tv_list_title) as TextView
        var textViewEmail = itemView.findViewById(R.id.tv_list_email) as TextView
        var tvDistance = itemView.findViewById(R.id.tvDistance) as TextView
        var tv_loc = itemView.findViewById(R.id.tv_loc) as TextView
        var tv_apply = itemView.findViewById(R.id.tv_apply) as TextView


       var  user_profile_photo = itemView.findViewById(R.id.user_profile) as CircularImageView

    }


}