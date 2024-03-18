package com.gxsales.client.view.activity.Dashboard.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gxsales.client.R
import com.gxsales.client.databinding.FragmentHomeBinding
import com.gxsales.client.databinding.FragmentLeadsBinding
import com.gxsales.client.model.dataclass.response.ProfileResponse
import com.gxsales.client.view.activity.Dashboard.FragmentsDashboardCommunicator

class LeadsFragment : Fragment() {
    private var _binding: FragmentLeadsBinding?= null
    private val binding get() = _binding!!
    private var input = ""
    private lateinit var fdCommunicator: FragmentsDashboardCommunicator

    companion object{
        private const val DELIVERED_TOKEN = "DELIVERED_TOKEN"
        private const val DELIVERED_PROFILE = "DELIVERED_PROFILE"
        fun newInstance(token: String): LeadsFragment{
            val fragment = LeadsFragment()
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
        _binding = FragmentLeadsBinding.inflate(layoutInflater, container, false)
        setUpView()
        fdCommunicator = activity as FragmentsDashboardCommunicator

        return binding.root
    }

    private fun setUpView(){

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}