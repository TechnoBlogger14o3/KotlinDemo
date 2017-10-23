package com.kotlin.demo.fragments

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.demo.AppSingleton
import com.kotlin.demo.AppSingleton.Companion.appPreference
import com.kotlin.demo.R
import com.kotlin.demo.databinding.FragmentHomeBinding
import com.kotlin.demo.supports.AppConstants
import com.kotlin.demo.supports.AppPreferences
import com.kotlin.demo.utility.MyValidations

/***
 * Created by Techno Blogger on 18/10/17.
 */
class FragmentHome : Fragment() {

    var strUserName: String = ""
    var strEmail: String = ""

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentHomeBinding: FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val rootView = fragmentHomeBinding.root

        AppSingleton.instance.activityInstance!!.setTitle(resources.getString(R.string.home))

        appPreference = (AppPreferences(context))
        val userName = AppSingleton.appPreference!!.userEmail

        // Implementing onClickListener on Button
        fragmentHomeBinding.btnNext.setOnClickListener {
            strUserName = fragmentHomeBinding.edtUser.text.toString()
            if (strUserName.length > 3) {
                val bundle = Bundle()
                bundle.putString(AppConstants.BundleKeys().strBundleUserName, strUserName)
                AppSingleton.instance.flowOrganization.replace(FragmentNextWithBundle(), bundle, true)
            } else {
                AppSingleton.instance.activityInstance!!.showSnackBar(rootView, AppConstants.Constants().strEnterValidUserName)
            }
        }

        fragmentHomeBinding.btnReset.setOnClickListener {
            fragmentHomeBinding.edtUser.setText("")
            AppSingleton.appPreference!!.userEmail = ""
        }

        fragmentHomeBinding.btnSave.setOnClickListener {
            strEmail = fragmentHomeBinding.edtEmail.text.toString()
            if (MyValidations().isValidEmail(strEmail)) {
                AppSingleton.appPreference!!.userEmail = strEmail
                AppSingleton.instance.activityInstance!!.showSnackBar(rootView, AppConstants.Constants().strUserNameSaved)
            } else {
                AppSingleton.instance.activityInstance!!.showSnackBar(rootView, AppConstants.Constants().strEnterValidEmail)
            }
        }

        return rootView
    }

}