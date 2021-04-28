package com.lambton.bikr.fragments

import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easywaylocation.EasyWayLocation
import com.example.easywaylocation.EasyWayLocation.LOCATION_SETTING_REQUEST_CODE
import com.example.easywaylocation.Listener
import com.lambton.bikr.R
import com.lambton.bikr.activity.RestaurentsActivity
import com.lambton.bikr.adapter.HomeAdapter
import com.lambton.bikr.models.Restaurants
import com.lambton.bikr.utils.DirectionFinder
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class HomeFragment : Fragment(), Listener {
    var easyWayLocation: EasyWayLocation? = null
var myLoc:Location? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: GridLayoutManager
    lateinit var recyclerAdapter: HomeAdapter
//    private lateinit var progressLayout: RelativeLayout
//    private lateinit var progressBar: ProgressBar
lateinit var tv_serch: TextView
    lateinit var et_postal: EditText


    val restaurantList = arrayListOf<Restaurants>()
    private var ratingComparator1 = Comparator<Restaurants> { res1, res2 ->

        if (res1.rating.compareTo(res2.rating, true) == 0) {
            // sort according to name if rating is same
            res1.name.compareTo(res2.name, true)
        } else {
            res1.rating.compareTo(res2.rating, true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.recyclerHome)

        layoutManager = GridLayoutManager(activity as Context, 2)
        setHasOptionsMenu(true)

        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Bikr"
//        loction.calculateDistance(startLatitude, startLongitude, endLatitude, endLongitude);
        et_postal= view.findViewById(R.id.et_postal)
        tv_serch = view.findViewById(R.id.tv_srch)
        tv_serch.isEnabled = false


//       var request = LocationRequest()
//        request.setInterval(10000)
//        request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
        easyWayLocation = EasyWayLocation(activity, true, this)


        tv_serch.setOnClickListener(View.OnClickListener {

            if(tv_serch.isEnabled){

//Log.e("LooP", "CC"+easyWayLocation.getLatitude().toString())
                if(myLoc!=null){

                    Toast.makeText(activity, "We are seaching....", Toast.LENGTH_LONG).show()


                    var i = Intent(activity, RestaurentsActivity::class.java)
             i.putExtra("current_lat",myLoc!!.latitude)
                    i.putExtra("current_long",myLoc!!.longitude)
                    startActivity(i)
                }else{

                    Toast.makeText(activity, "We are accessing your location...tap search again", Toast.LENGTH_LONG).show()
                    easyWayLocation = EasyWayLocation(activity, true, this)
                }

//                longi = easyWayLocation.getLongitude();
            }else{
                Toast.makeText(activity, "Enter valid Postal code ", Toast.LENGTH_LONG).show()

            }
        })
//        val regex = "^[A-Z][0-9][A-Z]?[0-9][A-Z][0-9]$"
//
//        val pattern: Pattern = Pattern.compile(regex)

        et_postal.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

                if(s.length==6){


                    if(isPostalCode(s)){

                        tv_serch.isEnabled = true
                        tv_serch.setBackgroundResource(R.drawable.yello_shape_bg)
                    }else{
                        tv_serch.isEnabled = false
                        tv_serch.setBackgroundResource(R.drawable.shape_bg)
                        Toast.makeText(activity, "Postal code wrong", Toast.LENGTH_LONG).show()
                    }


                }else{
                    tv_serch.isEnabled = false
                    tv_serch.setBackgroundResource(R.drawable.shape_bg)
                }
                //   tvSample.setText("Text is : "+s)
            }
        })


                                recyclerAdapter = HomeAdapter(activity as Context, restaurantList)
                                recyclerView.adapter = recyclerAdapter
                                recyclerView.layoutManager = layoutManager
//
        return view
    }
    //AIzaSyBuIHFyJ1aUuVNivchRxAOYmG_gxHGecmw
//

    fun isPostalCode(s: CharSequence) : Boolean{
        val regex = "^[A-Z][0-9][A-Z]?[0-9][A-Z][0-9]$"

        val pattern: Pattern = Pattern.compile(regex)
        val matcher: Matcher =   pattern.matcher(s)
        return  matcher.matches()
    }
    fun distanceFrom(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double {
        val earthRadius = 3958.75
        val dLat = Math.toRadians(lat2 - lat1.toDouble())
        val dLng = Math.toRadians(lng2 - lng1.toDouble())
        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1.toDouble())) * Math.cos(Math.toRadians(lat2.toDouble())) * Math.sin(dLng / 2) * Math.sin(dLng / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        val dist = earthRadius * c
        val meterConversion = 1609
        return (dist * meterConversion.toFloat()).toDouble()
    }
    private fun distance(
        lat1: Double,
        lon1: Double,
        lat2: Double,
        lon2: Double
    ): Double {
        val theta = lon1 - lon2
        var dist = (Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + (Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta))))
        dist = Math.acos(dist)
        dist = rad2deg(dist)
        dist = dist * 60 * 1.1515
        return dist
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sort_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_sort) {
            Collections.sort(restaurantList, ratingComparator1)
            restaurantList.reverse()
        }
        recyclerAdapter.notifyDataSetChanged()
        return super.onOptionsItemSelected(item)
    }

    override fun locationCancelled() {
//        Toast.makeText(activity, "Location Cacel", Toast.LENGTH_SHORT).show();
    }

    override fun locationOn() {
//        Toast.makeText(activity, "Location ON", Toast.LENGTH_SHORT).show();
    }

    override fun currentLocation(location: Location?) {
        val data = StringBuilder()
        data.append(location!!.latitude)
        myLoc = location
        data.append(" , ")
        data.append(location!!.longitude)
     //   Toast.makeText(activity, "Location ON "+data.toString(), Toast.LENGTH_SHORT).show();
        Log.e("locviooo ===", data.toString())
   //     latLong.setText(data)
     //   getLocationDetail.getAddress(location!!.latitude, location!!.longitude, "xyz")
//        location.

    }


    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            LOCATION_SETTING_REQUEST_CODE -> easyWayLocation!!.onActivityResult(
                resultCode
            )
        }
    }

    override fun onResume() {
        super.onResume()
        easyWayLocation!!.startLocation()

        easyWayLocation = EasyWayLocation(activity, true, this)
    }

     override fun onPause() {
        super.onPause()
        easyWayLocation!!.endUpdates()
    }
}