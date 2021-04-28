package com.lambton.mohitmvvmfirebase.updated

import BaseActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lambton.bikr.R
import com.lambton.bikr.databinding.FirebaseLoginBinding
import startNearByActivity
import startSignupActivity
import toast


class FireBaseLoginActivity : BaseActivity() {

    private lateinit var viewModel: FirebaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: FirebaseLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.firebase_login)

        viewModel = ViewModelProviders.of(this).get(FirebaseViewModel::class.java)
        binding.viewmodel = viewModel


        observeData()


    }

    private fun observeData() {

        //observe data
        viewModel.firebaseUser.observe(this, Observer {
            it?.let {
                toast(it.email)
              //  startHomeActivity()
            }
        })
        viewModel.validationError.observe(this, Observer {
            it?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
              //  toast(it)
                startNearByActivity()
            }
        })
        viewModel.loader.observe(this, Observer {
            if (it) {
                showProcessDialog(this, "Loading..")
            } else {
                hideProcessDialog()
            }
        })
        viewModel.gotoSign_up.observe(this, Observer {
            if (it) {
                startSignupActivity()
            }
        })
    }


}

