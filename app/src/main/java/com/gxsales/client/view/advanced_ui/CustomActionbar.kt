package com.gxsales.client.view.advanced_ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.gxsales.client.R
import com.gxsales.client.databinding.CustomActionbarLayoutBinding

class CustomActionbar: ConstraintLayout {
    private lateinit var binding: CustomActionbarLayoutBinding
    private var actionbarListener: ActionbarListener?= null

    private val TAG = CustomActionbar::class.java.simpleName

    enum class ACTIONBAR_TYPE{
        SHOPNLEAD, ADD_LEAD
    }

    constructor(context: Context):super(context){
        init(context, null)
    }

    constructor(context: Context, attributeSet: AttributeSet):super(context, attributeSet){
        init(context, attributeSet)
    }

    private fun init(context: Context, attributeSet: AttributeSet?){
        binding = CustomActionbarLayoutBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.custom_actionbar_layout, this, true)
        )

        binding.btnLeft.setOnClickListener {
            actionbarListener?.onButtonLeftClicked()
        }

        binding.btnRight.setOnClickListener {
            actionbarListener?.onButtonRightClicked()
        }
    }

    fun setActionbarType(actionBarType: ACTIONBAR_TYPE){
        when(actionBarType){
            ACTIONBAR_TYPE.SHOPNLEAD ->{
                setButtonRightInvisible()
            }
            ACTIONBAR_TYPE.ADD_LEAD ->{
                setButtonRightVisible()
            }
        }
    }

    fun setTitle(input: String){
        binding.tvTitle.text = input
    }

    fun setListener(listener: ActionbarListener){
        actionbarListener = listener
    }

    fun setButtonRightVisible(){
        binding.btnRight.visibility = View.VISIBLE
    }

    fun setButtonRightInvisible(){
        binding.btnRight.visibility = View.GONE
    }

    fun setActionbarVisible(){
        binding.root.visibility = View.VISIBLE
    }

    fun setActionbarInvisible(){
        binding.root.visibility = View.GONE
    }

    interface ActionbarListener{
        fun onButtonLeftClicked(){}
        fun onButtonRightClicked(){}
    }
}