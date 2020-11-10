package com.cy.testapp.Activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import com.cy.testapp.R
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_location.*

class LocationActivity : Activity() {
    private val TAG = "LocationActivity"

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        val locationRequest = LocationRequest.create().apply {
            interval = 500
            fastestInterval = 500
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
            expirationTime = 60 * 1000
        }
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult?) {
                super.onLocationResult(p0)
                p0?.let {
                    Log.e(TAG, "onLocationResult: $p0")
                    fusedLocationProviderClient.removeLocationUpdates(this)
                } ?: run {
                    Log.e(TAG, "onLocationResult: null")
                }
            }

            override fun onLocationAvailability(p0: LocationAvailability?) {
                super.onLocationAvailability(p0)
                p0?.let {
                    Log.e(TAG, "onLocationAvailability: $p0")
                } ?: run {
                    Log.e(TAG, "onLocationAvailability: null")
                }
            }
        }
        bt0.setOnClickListener {
            if (true) {
                requestLocation()
                return@setOnClickListener
            }
            val locationSettingsRequestBuilder =
                LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

            LocationServices.getSettingsClient(this)
                .checkLocationSettings(locationSettingsRequestBuilder.build()).run {
                    addOnSuccessListener {
                        Log.e(TAG, "SuccessListener")
                        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                            it?.run {
                                Log.e(TAG, "location: ${it.latitude}-${it.longitude}")
                            } ?: run {
                                Log.e(TAG, "location: null")
                                fusedLocationProviderClient.requestLocationUpdates(
                                    locationRequest,
                                    locationCallback,
                                    Looper.getMainLooper()
                                )
                            }
                        }
                    }
                    addOnFailureListener { exception ->
                        Log.e(TAG, "FailureListener: $exception")
                        if (exception is ResolvableApiException) {
                            // Location settings are not satisfied, but this can be fixed
                            // by showing the user a dialog.
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                exception.startResolutionForResult(
                                    this@LocationActivity,
                                    123
                                )
                            } catch (sendEx: IntentSender.SendIntentException) {
                                // Ignore the error.
                            }
                        }
                    }
                }
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        val criteria = Criteria().apply {
            accuracy = Criteria.ACCURACY_COARSE
            isAltitudeRequired = false
            isBearingRequired = false
            isCostAllowed = true
            powerRequirement = Criteria.POWER_HIGH
        }
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var provider = locationManager.getBestProvider(criteria, true)
        provider = LocationManager.NETWORK_PROVIDER
        val locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location?) {
                location?.let {
                    Log.e(TAG, "onLocationChanged: $location")
                    locationManager.removeUpdates(this)
                } ?: run {
                    Log.e(TAG, "onLocationChanged: null")
                }
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                Log.e(TAG, "onStatusChanged: $provider-$status-$extras")
            }

            override fun onProviderEnabled(provider: String?) {
                Log.e(TAG, "onProviderEnabled: $provider")
            }

            override fun onProviderDisabled(provider: String?) {
                Log.e(TAG, "onProviderDisabled: $provider")
            }

        }
        Log.e(TAG, "getLastKnownLocation1: ${locationManager.getLastKnownLocation(provider)}")
        locationManager.requestLocationUpdates(provider, 1000, 0f, locationListener)
        Log.e(TAG, "getLastKnownLocation2: ${locationManager.getLastKnownLocation(provider)}")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e(TAG, "onActivityResult: $requestCode $resultCode $data")
    }
}