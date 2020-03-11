package ie.wit.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BugTrackingModel(var id: Long = 0,
                            var title: String = "",
                            var descriptions: String = "",
                            var bugimportance: String = "",
                            var image: String = "") : Parcelable


