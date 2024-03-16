package com.gxsales.client.view.activity.Dashboard

import android.app.SearchManager.OnCancelListener
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.compose.ui.res.booleanResource
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationBarView
import com.gxsales.client.R
import com.gxsales.client.databinding.ActivityDashboardBinding
import com.gxsales.client.databinding.ActivityDashboardE1Binding
import com.gxsales.client.databinding.ActivityDashboardE2Binding
import com.gxsales.client.databinding.BottomsheetLayoutBinding
import com.gxsales.client.model.Constants.PREFERENCES.Companion.TOKEN_KEY
import com.gxsales.client.model.Constants.PREFERENCES.Companion.USER_PREFERENCES
import com.gxsales.client.model.Constants.STATUS.Companion.STATUS_SUCCESS
import com.gxsales.client.model.Extensions.Companion.dpToPx
import com.gxsales.client.model.dataclass.response.ProfileResponse
import com.gxsales.client.view.activity.Login.LoginActivity
import com.gxsales.client.view.advanced_ui.PopUpDialogListener
import com.gxsales.client.view.advanced_ui.PopUpNotificationListener
import com.gxsales.client.view.advanced_ui.showPopupDialog
import com.gxsales.client.view.advanced_ui.showPopupNotification
import com.gxsales.client.viewmodel.DashboardViewModel

class DashboardActivity : AppCompatActivity() {
    private val TAG = DashboardActivity::class.java.simpleName
    private lateinit var binding: ActivityDashboardBinding
    private val dashboardViewModel by viewModels<DashboardViewModel>()
    private lateinit var sharedPreferences: SharedPreferences
    private var userToken: String? = null

    private var retrievedProfileResponse: ProfileResponse?= null
    private var previousIdMenu: Int?= null

    companion object{
        fun newIntent(context: Context): Intent = Intent(context, DashboardActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setUpView()
        initView()
    }

    private fun initView(){
        sharedPreferences = this@DashboardActivity.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)
        userToken = sharedPreferences.getString(TOKEN_KEY, null)

        dashboardViewModel.getUserProfile(userToken!!)

        dashboardViewModel.isLoading.observe(this@DashboardActivity, {
            setUpLoading(it)
        })

        dashboardViewModel.isFail.observe(this@DashboardActivity, {
            setUpFail(it)
        })

        dashboardViewModel.isUnauthorized.observe(this@DashboardActivity, {
            if(it) setUpUnauthorized()
        })

        dashboardViewModel.userProfileResponse.observe(this@DashboardActivity, {profileResponse->
            setUserProfile(profileResponse)
        })

        binding.apply {
            bnvDashboard.apply {
                selectedItemId = R.id.menu_home
                previousIdMenu = R.id.menu_home
                setOnItemSelectedListener(object: NavigationBarView.OnItemSelectedListener{
                    override fun onNavigationItemSelected(item: MenuItem): Boolean {
                        when(item.itemId){
                            R.id.menu_home ->{
                                Log.d(TAG, "menu_home")
                                previousIdMenu = R.id.menu_home
                            }
                            R.id.menu_leads ->{
                                Log.d(TAG, "menu_leads")
                                previousIdMenu = R.id.menu_leads
                            }
                            R.id.menu_shop ->{
                                Log.d(TAG, "menu_shop")
                                previousIdMenu = R.id.menu_shop
                            }
                            R.id.menu_account ->{
                                setUpBottomSheet()
                            }
                        }
                        return true
                    }
                })
            }
        }

    }

    private fun setUserProfile(profileResponse: ProfileResponse){
        retrievedProfileResponse = profileResponse
    }

    private fun setUpLoading(isLoading: Boolean){
        if(isLoading){
            binding.loadingLayout.visibility = View.VISIBLE
            binding.pbLoading.visibility = View.VISIBLE
        }else{
            binding.loadingLayout.visibility = View.GONE
            binding.pbLoading.visibility = View.GONE
        }
    }

    private fun setUpUnauthorized(){
        Log.d(TAG, "Unauthorized")
        setForPopUpDisplaying(true)
        this@DashboardActivity.showPopupNotification(
            getString(R.string.tvPopupTitle_Unauthorized),
            getString(R.string.tvPopupDesc_Unauthorized),
            R.drawable.ic_checklist_green,
            object: PopUpNotificationListener{
                override fun onClickListener() {
                    this@DashboardActivity.closeOptionsMenu()
                    val editor = sharedPreferences.edit()
                    editor.remove(TOKEN_KEY)
                    editor.apply()
                    if(editor.commit()){
                        startActivity(LoginActivity.newIntent(this@DashboardActivity))
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                        finish()
                    }
                }
            }
        )
    }

    fun setForPopUpDisplaying(isDisplaying: Boolean){
        if(isDisplaying){
            binding.loadingLayout.visibility = View.VISIBLE
            binding.pbLoading.visibility = View.GONE
        }else{
            binding.loadingLayout.visibility = View.GONE
            binding.pbLoading.visibility = View.GONE
        }
    }

    private fun setUpFail(isFail: Boolean){
        binding.loadingLayout.visibility = View.GONE
        if(isFail){
            Toast.makeText(this@DashboardActivity, "Failed to get Data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpBottomSheet(){
        val bottomSheetDialog = BottomSheetDialog(this@DashboardActivity)
        val mBottomSheetDialog = BottomsheetLayoutBinding.inflate(layoutInflater, null, false)

        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            600.dpToPx(this@DashboardActivity) // Convert dp to pixels
        )
        mBottomSheetDialog.root.layoutParams = layoutParams

        bottomSheetDialog.setContentView(mBottomSheetDialog.root)

        try {
            mBottomSheetDialog.tvProfileName.text = retrievedProfileResponse!!.data!!.name ?: getString(R.string.tvDummy_Name)
            mBottomSheetDialog.tvProfileEmail.text = retrievedProfileResponse!!.data!!.email ?: getString(R.string.tvDummy_Email)
        } catch (e: NullPointerException) {
            mBottomSheetDialog.tvProfileName.text = getString(R.string.tvDummy_Name)
            mBottomSheetDialog.tvProfileEmail.text = getString(R.string.tvDummy_Email)
        }

        mBottomSheetDialog.btnLogout.setOnClickListener {
            Log.d(TAG, "Logout")
            bottomSheetDialog.dismiss()
            dashboardViewModel.logOutUser(userToken!!)

            dashboardViewModel.userLogoutResponse.observe(this@DashboardActivity, {logOutResponse->
                when(logOutResponse.status){
                    STATUS_SUCCESS ->{
                        val editor = sharedPreferences.edit()
                        editor.remove(TOKEN_KEY)
                        editor.apply()
                        if(editor.commit()){
                            setForPopUpDisplaying(true)
                            showPopupNotification(
                                getString(R.string.tvPopupTitle_Success),
                                getString(R.string.tvPopupDesc_LogoutSuccess),
                                R.drawable.ic_checklist_green,
                                object: PopUpNotificationListener{
                                    override fun onClickListener() {
                                        this@DashboardActivity.closeOptionsMenu()
                                        startActivity(LoginActivity.newIntent(this@DashboardActivity))
                                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                                        finish()
                                    }
                                }
                            )
                        }
                    }
                    else ->{
                        Toast.makeText(this@DashboardActivity, "Failed to Log Out", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }

        bottomSheetDialog.setOnCancelListener(object: DialogInterface.OnCancelListener{
            override fun onCancel(dialog: DialogInterface?) {
                binding.bnvDashboard.selectedItemId = previousIdMenu!!
            }
        })

//        binding.loadingLayout.apply {
//            visibility = View.VISIBLE
//            background = ColorDrawable(ContextCompat.getColor(this@DashboardActivity, R.color.black))
//        }
//
//        binding.pbDashboard.visibility = View.GONE

        bottomSheetDialog.show()
    }


}