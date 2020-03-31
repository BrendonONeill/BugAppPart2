package ie.wit.main

import android.app.Application
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import ie.wit.models.BugTrackingMemStore
import ie.wit.models.BugTrackingModel
import ie.wit.models.BugTrackingStore


class BugTrackingApp : Application() {

    lateinit var bugTrackingsStore: BugTrackingStore

    var bugs = ArrayList<BugTrackingModel>()
    lateinit var auth: FirebaseAuth

    override fun onCreate() {
        super.onCreate()
        bugTrackingsStore = BugTrackingMemStore()
        Log.v("BugTracking","Bug Tracking App started")
    }
}