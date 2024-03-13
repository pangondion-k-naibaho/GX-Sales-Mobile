package com.gxsales.client.view.activity.Login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.gxsales.client.R
import com.gxsales.client.databinding.ActivityLoginBinding
import com.gxsales.client.model.Constants.LOGIN_STATUS.Companion.LOGIN_SUCCESS
import com.gxsales.client.model.Constants.PREFERENCES.Companion.LOGIN_PREFERENCES
import com.gxsales.client.model.Constants.PREFERENCES.Companion.TOKEN_KEY
import com.gxsales.client.model.dataclass.LoginResponse
import com.gxsales.client.view.activity.Dashboard.DashboardActivity
import com.gxsales.client.view.advanced_ui.InputTextView
import com.gxsales.client.view.advanced_ui.PopUpDialogListener
import com.gxsales.client.view.advanced_ui.showPopupDialog
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
            if(it) binding.loadingLayout.visibility = View.VISIBLE else binding.loadingLayout.visibility = View.GONE
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
        val sharedPreferences = this@LoginActivity.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(TOKEN_KEY, loginResponse.token)
        editor.apply()
        if(editor.commit()){
            binding.loadingLayout.visibility = View.GONE
            this@LoginActivity.showPopupDialog(
                textTitle = getString(R.string.tvPopupTitle_Success),
                textDesc = getString(R.string.tvPopupDesc_LoginSuccess),
                backgroundImage = R.drawable.ic_tick_circle,
                object: PopUpDialogListener{
                    override fun onClickListener() {
                        this@LoginActivity.closeOptionsMenu()
                        startActivity(DashboardActivity.newIntent(this@LoginActivity))
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                        finish()
                    }

                }
            )
        }
    }
}