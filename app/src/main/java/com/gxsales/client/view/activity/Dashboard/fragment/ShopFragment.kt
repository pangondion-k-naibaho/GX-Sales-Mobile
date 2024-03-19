package com.gxsales.client.view.activity.Dashboard.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.gxsales.client.R
import com.gxsales.client.databinding.FragmentShopBinding
import com.gxsales.client.model.Constants.DUMMY_DATA.Companion.getListItemShop
import com.gxsales.client.view.activity.Dashboard.FragmentsDashboardCommunicator
import com.gxsales.client.view.adapter.ItemShopAdapter
import com.gxsales.client.view.advanced_ui.InputSearchView

class ShopFragment : Fragment() {
    private val TAG = ShopFragment::class.java.simpleName
    private var _binding: FragmentShopBinding?= null
    private val binding get() = _binding!!
    private var input = ""
    private lateinit var fdCommunicator: FragmentsDashboardCommunicator

    companion object{
        private const val DELIVERED_TOKEN = "DELIVERED_TOKEN"
        private const val DELIVERED_PROFILE = "DELIVERED_PROFILE"
        fun newInstance(token: String): ShopFragment{
            val fragment = ShopFragment()
            fragment.input = token

            val bundle = Bundle()
            bundle.putString(DELIVERED_TOKEN, token)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentShopBinding.inflate(layoutInflater, container, false)
        fdCommunicator = activity as FragmentsDashboardCommunicator
        setUpView()

        return binding.root
    }

    private fun setUpView(){
        Log.d(TAG, "setUpView")
        binding.apply {
            isvShop.apply {
                setTextHelper(getString(R.string.tvHint_SearchShop))
                setListener(object:InputSearchView.InputSearchListener{
                    override fun onClickSearch() {
                        Toast.makeText(this@ShopFragment.requireActivity(), "Search: ${getText()}", Toast.LENGTH_SHORT).show()
                    }

                    override fun onClearSearch() {
                        clearText()
                    }
                })
            }



            rvItemShop.apply {
                val rvAdapter = ItemShopAdapter(getListItemShop().toMutableList())
                val rvLayoutManager = LinearLayoutManager(this@ShopFragment.requireActivity())

                layoutManager = rvLayoutManager
                adapter = rvAdapter
                setHasFixedSize(true)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}