package com.gxsales.client.view.activity.LoadScreen

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import com.gxsales.client.R
import com.gxsales.client.databinding.ActivityLoadscreenBinding
import com.gxsales.client.model.Constants.PREFERENCES.Companion.LOGIN_PREFERENCES
import com.gxsales.client.model.Constants.PREFERENCES.Companion.TOKEN_KEY
import com.gxsales.client.view.activity.Dashboard.DashboardActivity
import com.gxsales.client.view.activity.Login.LoginActivity

class LoadScreenActivity : ComponentActivity() {
    private val TAG = LoadScreenActivity::class.java.simpleName
    private lateinit var binding: ActivityLoadscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBar?.hide()
        setUpView()
    }

    private fun setUpView(){
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val sharedPreferences = this@LoadScreenActivity.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE)

            val retrievedToken =sharedPreferences.getString(TOKEN_KEY, null)
            Log.d(TAG, "retrievedToken : $retrievedToken")

            if(retrievedToken.isNullOrEmpty()){
                startActivity(
                    LoginActivity.newIntent(this@LoadScreenActivity)
                )
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                finish()
            }else{
                startActivity(
                    DashboardActivity.newIntent(this@LoadScreenActivity)
                )
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                finish()
            }

        }, 4000)
    }
}