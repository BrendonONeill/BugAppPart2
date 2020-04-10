package ie.wit.main

import android.app.Application
import android.app.IntentService
import android.net.Uri
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference


import ie.wit.models.BugTrackingModel



class BugTrackingApp : Application() {




    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var storage: StorageReference
    lateinit var userImage: Uri

    override fun onCreate() {
        super.onCreate()

        Log.v("BugTracking","Bug Tracking App started")
    }
}