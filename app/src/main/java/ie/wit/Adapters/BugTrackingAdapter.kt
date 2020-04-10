package ie.wit.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.wit.R
import ie.wit.models.BugTrackingModel
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.card_bug.view.*
import kotlinx.android.synthetic.main.fragment_bug_tracking.view.*



interface BugListener {
    fun onBugClick(bugTracking: BugTrackingModel)
}

class BugTrackingAdapter constructor
    (var bugs: ArrayList<BugTrackingModel>, private val listener: BugListener, reportall : Boolean)

    : RecyclerView.Adapter<BugTrackingAdapter.MainHolder>() {

    val reportAll = reportall

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.card_bug,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val bugTracking = bugs[holder.adapterPosition]
        holder.bind(bugTracking, listener, reportAll)
    }

    override fun getItemCount(): Int = bugs.size

    fun removeAt(position: Int) {
        bugs.removeAt(position)
        notifyItemRemoved(position)
    }

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            bugTracking: BugTrackingModel, listener: BugListener, reportAll: Boolean

        ) {
            itemView.bugFormTitle.text = bugTracking.title
            itemView.bugDescription.text = bugTracking.descriptions
            itemView.bugFormNumber.text = bugTracking.bugimportance

            itemView.tag = bugTracking
            if(!reportAll)
                itemView.setOnClickListener { listener.onBugClick(bugTracking) }
            if(!bugTracking.profilepic.isEmpty()) {
                Picasso.get().load(bugTracking.profilepic.toUri())
                    //.resize(180, 180)
                    .transform(CropCircleTransformation())
                    .into(itemView.imageIcon)
            }
            else
                itemView.imageIcon.setImageResource(R.mipmap.ic_launcher_homer_round)
            }
            }
        }

