package com.gxsales.client.view.activity.AddLead

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.gxsales.client.R
import com.gxsales.client.databinding.ActivityAddLeadBinding
import com.gxsales.client.view.advanced_ui.CustomActionbar

class AddLeadActivity : AppCompatActivity(), FragmentsAddLeadCommunicator{
    private val TAG = AddLeadActivity::class.java.simpleName
    private lateinit var binding : ActivityAddLeadBinding

    companion object{
        fun newIntent(context: Context): Intent = Intent(context, AddLeadActivity::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddLeadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView(){
        binding.cabAddLead.apply {
            setTitle(getString(R.string.tvToolbarTitle_AddLeads))
            setActionbarType(CustomActionbar.ACTIONBAR_TYPE.ADD_LEAD)
            setListener(object: CustomActionbar.ActionbarListener{
                override fun onButtonLeftClicked() {
                    super.onButtonLeftClicked()
                    finish()
                    //checking condition if one or some element has already filled in the fragment will be added
                }

                override fun onButtonRightClicked() {
                    super.onButtonRightClicked()
                    Toast.makeText(this@AddLeadActivity, "This feature will be added soon", Toast.LENGTH_SHORT).show()
                }
            })
        }

    }

    override fun startLoading() {
        Log.d(TAG, "startLoading")
        //Will be added soon
    }

    override fun stopLoading() {
        Log.d(TAG, "stopLoading")
        //Will be added soon
    }

    override fun setUnauthorizeWarning() {
        Log.d(TAG, "setUnauthorizeWarning")
        //Will be added soon
    }
}