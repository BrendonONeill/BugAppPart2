package ie.wit.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.squareup.picasso.Picasso
import ie.wit.R
import ie.wit.fragments.BugAllReportsFragment
import ie.wit.models.BugTrackingModel

import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.card_bug.view.*




interface BugListener {
    fun onBugClick(bugTracking: BugTrackingModel)
}

class BugTrackingAdapter(options: FirebaseRecyclerOptions<BugTrackingModel>,
     private val listener: BugListener?)
    : FirebaseRecyclerAdapter<BugTrackingModel,
        BugTrackingAdapter.BugViewHolder>(options){

class BugViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(bugTracking: BugTrackingModel, listener: BugListener) {
        with(bugTracking) {
            itemView.tag = bugTracking
            itemView.bugFormTitle.text = bugTracking.title
            itemView.bugDescription.text = bugTracking.descriptions
            itemView.bugFormNumber.text = bugTracking.bugimportance


            if(listener is BugAllReportsFragment)
                ; // Do Nothing, Don't Allow 'Clickable' Rows
            else
                itemView.setOnClickListener { listener.onBugClick(bugTracking) }

            if(bugTracking.isfavourite) itemView.imagefavourite.setImageResource(android.R.drawable.star_big_on)

            if(!bugTracking.profilepic.isEmpty()) {
                com.squareup.picasso.Picasso.get().load(bugTracking.profilepic.toUri())
                    //.resize(180, 180)
                    .transform(jp.wasabeef.picasso.transformations.CropCircleTransformation())
                    .into(itemView.imageIcon)
            }
            else
                itemView.imageIcon.setImageResource(ie.wit.R.mipmap.ic_launcher_homer_round)

        }
    }
}

override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BugViewHolder {

    return BugViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.card_bug, parent, false))
}

override fun onBindViewHolder(holder: BugViewHolder, position: Int, model: BugTrackingModel) {
    holder.bind(model,listener!!)
}

override fun onDataChanged() {
    // Called each time there is a new data snapshot. You may want to use this method
    // to hide a loading spinner or check for the "no documents" state and update your UI.
    // ...
}
}