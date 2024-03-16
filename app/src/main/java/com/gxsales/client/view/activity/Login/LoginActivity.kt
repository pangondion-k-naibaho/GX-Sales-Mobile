package com.gxsales.client.view.activity.Login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.gxsales.client.R
import com.gxsales.client.databinding.ActivityLoginBinding
import com.gxsales.client.model.Constants.PREFERENCES.Companion.TOKEN_KEY
import com.gxsales.client.model.Constants.PREFERENCES.Companion.USER_PREFERENCES
import com.gxsales.client.model.Constants.STATUS.Companion.STATUS_SUCCESS
import com.gxsales.client.model.dataclass.response.LoginResponse
import com.gxsales.client.view.activity.Dashboard.DashboardActivity
import com.gxsales.client.view.advanced_ui.InputTextView
import com.gxsales.client.view.advanced_ui.PopUpNotificationListener
import com.gxsales.client.view.advanced_ui.showPopupNotification
import com.gxsales.client.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private val TAG = LoginActivity::class.java.simpleName
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel by viewModels<LoginViewModel>()

    companion object{
        fun newIntent(context: Context): Intent = Intent(context, LoginActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBar?.hide()
        initView()
    }

    private fun initView(){
        binding.itvEmail.apply {
            setInputType(InputTextView.INPUT_TYPE.EMAIL)
            Log.d(TAG, "inputType itvEmail: ${binding.itvEmail.getInputType()}")
            setTitle(getString(R.string.tvTitle_Email))
            setListener(null)
        }

        binding.itvPassword.apply {
            setInputType(InputTextView.INPUT_TYPE.PASSWORD)
            Log.d(TAG, "inputType itvPassword: ${binding.itvPassword.getInputType()}")
            setTitle(getString(R.string.tvTitle_Password))
            setListener(object: InputTextView.InputViewListener{
                override fun onClickReveal() {
                    Log.d(TAG, "onClickReveal")
                    revealPassword()
                }
            })
        }

        binding.btnLogin.setOnClickListener {
            val retrievedEmail = binding.itvEmail.getText()
            val retrievedPassword = binding.itvPassword.getText()

            Log.d(TAG, "email: $retrievedEmail, password: $retrievedPassword")

            loginViewModel.loginUser(retrievedEmail, retrievedPassword)
        }

        loginViewModel.isLoading.observe(this@LoginActivity, {
            setForLoading(it)
        })

        loginViewModel.isFail.observe(this@LoginActivity, {
            if(it){
                binding.itvEmail.setOnError()
                binding.itvPassword.setOnError()
            }
        })

        loginViewModel.loginResponse.observe(this@LoginActivity, { userLoginResponse->
            setUpSuccessAction(userLoginResponse)
        })
    }

    private fun setUpSuccessAction(loginResponse: LoginResponse){
        Log.d(TAG, "token: ${loginResponse.token}")
        when(loginResponse.status){
            STATUS_SUCCESS ->{
                val sharedPreferences = this@LoginActivity.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString(TOKEN_KEY, loginResponse.token)
                editor.apply()
                if(editor.commit()){
                    setForPopUpDisplaying(true)
                    this@LoginActivity.showPopupNotification(
                        textTitle = getString(R.string.tvPopupTitle_Success),
                        textDesc = getString(R.string.tvPopupDesc_LoginSuccess),
                        backgroundImage = R.drawable.ic_tick_circle,
                        object: PopUpNotificationListener{
                            override fun onClickListener() {
                                this@LoginActivity.closeOptionsMenu()
                                setForPopUpDisplaying(false)
                                startActivity(DashboardActivity.newIntent(this@LoginActivity))
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                                finish()
                            }

                        }
                    )
                }
            }
            else ->{
                Toast.makeText(this@LoginActivity, "Login Error", Toast.LENGTH_SHORT).show()
                binding.itvEmail.setOnError()
                binding.itvPassword.setOnError()
            }
        }
    }

    fun setForLoading(isLoading: Boolean){
        if(isLoading){
            binding.loadingLayout.visibility = View.VISIBLE
            binding.pbLoading.visibility = View.VISIBLE
        }else{
            binding.loadingLayout.visibility = View.GONE
            binding.pbLoading.visibility = View.GONE
        }
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
}