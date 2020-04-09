package ie.wit.main

import android.app.Application
import android.app.IntentService
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import ie.wit.api.BugTrackingService

import ie.wit.models.BugTrackingModel



class BugTrackingApp : Application() {

    lateinit var bugTrackingService: BugTrackingService

    var bugTrackings = ArrayList<BugTrackingModel>()
    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference

    override fun onCreate() {
        super.onCreate()
        bugTrackingService = BugTrackingService.create()
        Log.v("BugTracking","Bug Tracking App started")
    }
}