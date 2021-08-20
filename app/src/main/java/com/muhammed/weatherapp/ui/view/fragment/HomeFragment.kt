package com.muhammed.weatherapp.ui.view.fragment

import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import com.muhammed.weatherapp.R
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
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    var lat: Double = 0.0
    var lon: Double = 0.0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }
    override fun onStart() {
        super.onStart()

        if (!checkPermissions()) {
            requestPermissions()
        } else {
            getLastLocation()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        setObserver()
    }

    private fun setObserver() {
        homeViewModel.getWeather(lat,lon)
        homeViewModel.weather.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
                    Log.d(TAG, "setObserver: Loading")

                }
                Status.SUCCESS -> {
                    Log.d(TAG, "setObserver: Success")
                    binding.homeData=HomeItemViewState(it.data!!)

                }
                Status.ERROR -> {
                    Log.d(TAG, "setObserver: Error")
                }
            }
        })
    }



    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        fusedLocationClient.lastLocation
            .addOnCompleteListener { taskLocation ->
                if (taskLocation.isSuccessful && taskLocation.result != null) {
                    val location = taskLocation.result
                    location.latitude = lat
                    location.longitude = lon
                    Log.d(TAG, "getLastLocation: ${location.latitude}")
                    Log.d(TAG, "getLastLocation: ${location.longitude}")
                } else {
                    Log.w(TAG, "getLastLocation:exception", taskLocation.exception)
                }
            }
    }

    private fun checkPermissions() =
        ActivityCompat.checkSelfPermission(
            requireActivity(),
            ACCESS_COARSE_LOCATION
        ) == PERMISSION_GRANTED

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(ACCESS_COARSE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE
        )
    }

    private fun showSnackbar(
        snackStrId: Int,
        actionStrId: Int = 0,
        listener: View.OnClickListener? = null
    ) {
        val snackbar = Snackbar.make(
            this.requireView(), getString(snackStrId),
            LENGTH_INDEFINITE
        )
        if (actionStrId != 0 && listener != null) {
            snackbar.setAction(getString(actionStrId), listener)
        }
        snackbar.show()
    }

    private fun requestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                ACCESS_COARSE_LOCATION
            )
        ) {

            Toast.makeText(
                requireContext(),
                "Sana daha doğru bilgi vermek için konumunu bizimle paylaşman gerek",
                Toast.LENGTH_SHORT
            ).show()

        } else {
            startLocationPermissionRequest()
        }

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
                        ) ==
                                PackageManager.PERMISSION_GRANTED)
                    ) {
                        Toast.makeText(
                            requireContext(),
                            "İzin Verildi",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                } else {
                    Toast.makeText(requireContext(), "İzin  Verilmedi", Toast.LENGTH_SHORT)
                        .show()
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
