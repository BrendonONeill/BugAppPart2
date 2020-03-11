package ie.wit.models

interface BugTrackingStore {
    fun findAll() : List<BugTrackingModel>
    fun findById(id: Long) : BugTrackingModel?
    fun create(bugTracking: BugTrackingModel)
    fun update(bugTracking: BugTrackingModel)
    fun delete(bugTracking: BugTrackingModel)

}