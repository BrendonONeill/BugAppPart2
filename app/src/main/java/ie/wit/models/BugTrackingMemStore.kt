package ie.wit.models

import android.util.Log

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class BugTrackingMemStore : BugTrackingStore {

    val bugTrackings = ArrayList<BugTrackingModel>()

    override fun findAll(): List<BugTrackingModel> {
        return bugTrackings
    }

    override fun findById(id:Long) : BugTrackingModel? {
        val foundBugTracking: BugTrackingModel? = bugTrackings.find { it.id == id }
        return foundBugTracking
    }

    override fun create(bugTracking: BugTrackingModel) {
        bugTracking.id = getId()
        bugTrackings.add(bugTracking)
        logAll()
    }

    override fun update(bugTracking: BugTrackingModel) {
        var foundBug: BugTrackingModel? = bugTrackings.find { p -> p.id == bugTracking.id }
        if (foundBug != null) {
            foundBug.title = bugTracking.title

            foundBug.bugimportance= bugTracking.bugimportance


            logAll()
        }
    }

    override fun delete(bugTracking: BugTrackingModel) {
        bugTrackings.remove(bugTracking)
        logAll()
    }

    fun logAll() {
        Log.v("BugTracking","** Bug Tracking Report List **")
        bugTrackings.forEach { Log.v("BugTracking","${it}") }
    }
}