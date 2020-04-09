package ie.wit.models

import android.os.Message
import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize
@IgnoreExtraProperties
@Parcelize
data class BugTrackingModel(
    var uid: String? = "",
    var title: String = "N/A",
    var descriptions: String = "N/A",
    var bugimportance: String = "N/A",
    var message: String = "the message",
    var upvotes: Int = 0,
    var email: String? = "Brendon@wit.ie")
    : Parcelable

{
    @Exclude
    fun toMap(): Map<String, Any?>{
        return mapOf(

            "uid" to uid,
            "title" to title,
            "descriptions" to descriptions,
            "bugimportance" to bugimportance,
            "message" to message,
            "upvote" to upvotes,
            "email" to email
        )
    }
}






