package com.gxsales.client.view.activity.Dashboard.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gxsales.client.R
import com.gxsales.client.databinding.FragmentLeadsBinding
import com.gxsales.client.view.activity.Dashboard.FragmentsDashboardCommunicator
import com.gxsales.client.view.adapter.ItemLeadAdapter
import com.gxsales.client.view.advanced_ui.InputSearchView
import com.gxsales.client.viewmodel.DashboardViewModel

class LeadsFragment : Fragment() {
    private val TAG = LeadsFragment::class.java.simpleName
    private var _binding: FragmentLeadsBinding?= null
    private val binding get() = _binding!!
    private var userToken = ""
    private lateinit var fdCommunicator: FragmentsDashboardCommunicator
    private val dashboardViewModel by viewModels<DashboardViewModel>()

    companion object{
        private const val DELIVERED_TOKEN = "DELIVERED_TOKEN"
        private const val DELIVERED_PROFILE = "DELIVERED_PROFILE"
        fun newInstance(token: String): LeadsFragment{
            val fragment = LeadsFragment()
            fragment.userToken = token

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
        fdCommunicator = activity as FragmentsDashboardCommunicator

        setUpView()

        return binding.root
    }

    private fun setUpView(){
        dashboardViewModel.getLeadsCollection(userToken)

        dashboardViewModel.isLoading.observe(this@LeadsFragment.requireActivity(),{
            if(it) fdCommunicator.startLoading() else fdCommunicator.stopLoading()
        })

        dashboardViewModel.isFail.observe(this@LeadsFragment.requireActivity(), {
            if(it) Log.d(TAG, "isFail")
        })

        dashboardViewModel.isUnauthorized.observe(this@LeadsFragment.requireActivity(), { isUnauthorized->
            Log.d(TAG, "isUnauthorized: $isUnauthorized")
            if(isUnauthorized == true) {
                fdCommunicator.setUnauthorizeWarning()
            }
        })

        dashboardViewModel.collectionLeadResponse.observe(this@LeadsFragment.requireActivity(), {leadsCollection->
            binding.apply {
                val rvAdapter = ItemLeadAdapter(leadsCollection.data!!.toMutableList())
                val rvLayoutManager = LinearLayoutManager(this@LeadsFragment.requireActivity())

                rvItemLeads.apply {
                    adapter = rvAdapter
                    layoutManager = rvLayoutManager
                }
            }
        })

        binding.apply {
            isvLeads.apply {
                setTextHelper(getString(R.string.tvHint_SearchLead))
                setListener(object: InputSearchView.InputSearchListener{
                    override fun onClickSearch() {
                        Toast.makeText(this@LeadsFragment.requireActivity(), "Search : ${getText()}", Toast.LENGTH_SHORT).show()
                    }

                    override fun onClearSearch() {
                        clearText()
                    }

                })
            }

            btnFilter.setOnClickListener {
                Log.d(TAG, "btn Filter Clicked")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}