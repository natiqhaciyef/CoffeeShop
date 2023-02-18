package com.natiqhaciyef.coffeshop.ui.view.homescreen

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.natiqhaciyef.coffeshop.R
import com.natiqhaciyef.coffeshop.data.model.NotificationModel
import com.natiqhaciyef.coffeshop.databinding.FragmentNotificationBinding
import com.natiqhaciyef.coffeshop.ui.adapter.NotificationAdapter
import com.natiqhaciyef.coffeshop.ui.viewmodel.NotificationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_notification.view.*

@AndroidEntryPoint
class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    private lateinit var adapter: NotificationAdapter
    private var list = mutableListOf<NotificationModel>()
    private val viewModel: NotificationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.emptyText.visibility = View.GONE
        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.notificationLiveData.observe(viewLifecycleOwner) {
            it.data?.let {
                list = it.toMutableList()
                if(list.isEmpty())
                    binding.emptyText.visibility = View.VISIBLE
                else
                    binding.emptyText.visibility = View.GONE

                adapter = NotificationAdapter(list, requireContext())
                binding.recyclerNotificationView.adapter = adapter
                binding.recyclerNotificationView.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner){
            if (it)
                binding.loadingView.visibility = View.VISIBLE
            else
                binding.loadingView.visibility = View.GONE
        }
    }
}