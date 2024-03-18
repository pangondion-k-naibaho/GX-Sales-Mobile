package com.gxsales.client.view.activity.Dashboard.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.gxsales.client.R
import com.gxsales.client.databinding.FragmentHomeBinding
import com.gxsales.client.model.Constants.DUMMY_DATA.Companion.getListItemSummary
import com.gxsales.client.model.dataclass.response.ProfileResponse
import com.gxsales.client.view.activity.Dashboard.FragmentsDashboardCommunicator
import com.gxsales.client.view.adapter.ItemSummaryAdapter
import com.gxsales.client.viewmodel.DashboardViewModel

class HomeFragment : Fragment() {
    private val TAG = HomeFragment::class.java.simpleName
    private var _binding: FragmentHomeBinding?= null
    private val binding get() = _binding!!
    private var token = ""
    private lateinit var fdCommunicator: FragmentsDashboardCommunicator
    private val dashboardViewModel by viewModels<DashboardViewModel>()
    private val retrievedUserProfile: ProfileResponse?= null

    companion object{
        private const val DELIVERED_TOKEN = "DELIVERED_TOKEN"
        private const val DELIVERED_PROFILE = "DELIVERED_PROFILE"
        fun newInstance(token: String): HomeFragment{
            val fragment = HomeFragment()
            fragment.token = token

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
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        fdCommunicator = activity as FragmentsDashboardCommunicator

        setUpView()

        return binding.root
    }

    private fun setUpView(){
        dashboardViewModel.getUserProfile(token)

        dashboardViewModel.isLoading.observe(this@HomeFragment.requireActivity(), {
            if(it) fdCommunicator.startLoading() else fdCommunicator.stopLoading()
        })

        dashboardViewModel.isFail.observe(this@HomeFragment.requireActivity(), {
            Log.d(TAG, "isFail: $it")
        })

        dashboardViewModel.isUnauthorized.observe(this@HomeFragment.requireActivity(), {
            fdCommunicator.setUnauthorizeWarning()
        })

        dashboardViewModel.userProfileResponse.observe(this@HomeFragment.requireActivity(), { profileResponse ->
            setUpProfileSection(profileResponse)
        })

        binding.apply {
            rvSummary.apply {
                val rvAdapter = ItemSummaryAdapter(getListItemSummary().toMutableList())
                val rvLayoutManager = GridLayoutManager(this@HomeFragment.requireActivity(), 2)

                adapter = rvAdapter
                layoutManager = rvLayoutManager
            }
        }
    }

    private fun setUpProfileSection(profileResponse: ProfileResponse){
//        Glide.with(this@HomeFragment.requireActivity())
//            .load(ContextCompat.getDrawable(this@HomeFragment.requireActivity(), R.drawable.dummy_photo))
//            .into(binding.ivUserProfile)

        binding.tvUserName.text = profileResponse.data!!.name
        binding.tvUserEmail.text = profileResponse.data.email
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}