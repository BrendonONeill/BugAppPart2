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
    var profilepic: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var isfavourite: Boolean = false,
    var email: String? = "Brendon@wit.ie")
    : Parcelable

{
    @Exclude
    fun toMap(): Map<String, Any?>{
        return mapOf(

            "uid" to uid,
            "title" to title,
            "profilepic" to profilepic,
            "descriptions" to descriptions,
            "bugimportance" to bugimportance,
            "message" to message,
            "latitude" to latitude,
            "longitude" to longitude,
            "email" to email,
            "isfavourite" to isfavourite

        )
    }
}






