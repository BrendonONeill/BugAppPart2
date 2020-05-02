package ie.wit.main

import android.app.Application
import android.app.IntentService
import android.location.Location
import android.net.Uri
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference


import ie.wit.models.BugTrackingModel



class BugTrackingApp : Application() {




    lateinit var currentUser: FirebaseUser
    lateinit var database: DatabaseReference
    lateinit var storage: StorageReference
    lateinit var googleSignInClient: GoogleSignInClient

    lateinit var userImage: Uri
    lateinit var currentLocation : Location
    lateinit var locationClient : FusedLocationProviderClient
    lateinit var mMap : GoogleMap
    lateinit var marker : Marker

    override fun onCreate() {
        super.onCreate()

        Log.v("BugTracking","Bug Tracking App started")
    }
}