
package com.lambton.bikr.signup

import FirebaseNetworkCallBack
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.lambton.bikr.firebase.FirebaseRepository


class SignupViewModel : ViewModel() {

    //email and password for the input
    var name: String? = null
    var email: String? = null
    var password: String? = null
    var type: Int? = null
    var mFile_viewModel: Uri? = null
var emp: Boolean = false
    var seek: Boolean = false
    var JobSeek: Boolean = false
    var rb1: Boolean = false
    var rb2: Boolean = false
    var rb3: Boolean = false
    var gender: String? = null
    var genderID: Int? = null
    var dob: String? = null
    var city: String? = null
    var zipcode: String? = null
    val apiLoader = MutableLiveData<Boolean>()
    val mFirebaseUser = MutableLiveData<FirebaseUser>()
    val mGeneralError = MutableLiveData<String>()
    val repository = FirebaseRepository()
    val muserCreated = MutableLiveData<String>()
    var image_pick_picker = MutableLiveData<Boolean>()


    val user by lazy {
      //  repository.currentUser()
    }


    fun goToLogin(view: View) {

//        Intent(view.context, FireBaseLoginActivity::class.java).also {
//
//            view.context.startActivity(it)

//        }
    }
//

    // perform to signup


    fun signup() {
        apiLoader.value = true
Log.e("gende val",type.toString())
        //2131230939
      if(emp){
          JobSeek = false
      }else if(seek){
          JobSeek = true
      }

       if(rb1){
           gender = "Male"
       }else if(rb2 ){
           gender = "Female"
       }else{
           gender = "Other"
       }
//
        //2131230941

        //validating email and password
        if (name.isNullOrEmpty() || email.isNullOrEmpty() || password.isNullOrEmpty()) {
            mGeneralError.postValue("Invalid email or password")
            apiLoader.value = false
            return
        }


    }


    fun image_picker() {
        image_pick_picker.value = true
    }
}