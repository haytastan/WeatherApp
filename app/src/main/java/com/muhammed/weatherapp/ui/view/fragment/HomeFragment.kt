package com.muhammed.weatherapp.ui.view.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.muhammed.weatherapp.databinding.FragmentHomeBinding
import com.muhammed.weatherapp.ui.viewmodel.HomeViewModel
import com.muhammed.weatherapp.util.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val homeViewModel: HomeViewModel by viewModels()
    var latiude = ""
    var longitude = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkSelfPermission()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        obtieneLocalizacion()
        setObserver()
    }

    private fun checkSelfPermission() {

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !==
            PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
                )
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
                )
            }
        }
    }

    private fun obtieneLocalizacion() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                latiude = location?.latitude.toString()
                longitude = location?.longitude.toString()
                homeViewModel.latt = latiude
                homeViewModel.lonn = longitude



                Log.d(TAG, "latiude85: ${location?.latitude}")
                Log.d(TAG, "longitude86: ${location?.longitude}")
                Log.d(TAG, "longitude87: ${latiude}")
                Log.d(TAG, "longitude88: ${longitude}")
                Log.d(TAG, "obtieneLocalizacion: ${homeViewModel.latt}")
                Log.d(TAG, "obtieneLocalizacion: ${homeViewModel.lonn} ")

            }
    }

    private fun setObserver() {

        homeViewModel.getWeather().observe(viewLifecycleOwner, {
            when (it.status) {

                Status.LOADING -> {
                    Log.d(TAG, "setObserver: Loading")

                }
                Status.SUCCESS -> {
                    it.data.let {
                        binding.homeData = it?.let { it1 -> HomeItemViewState(it1) }
                        Log.d(TAG, "setObserver: Success")

                    }
                }
                Status.ERROR -> {
                    Log.d(TAG, "setObserver: Error")

                }

            }
        })

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    if ((ContextCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) ===
                                PackageManager.PERMISSION_GRANTED)
                    ) {
                        Toast.makeText(requireContext(), "Permission Granted", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}