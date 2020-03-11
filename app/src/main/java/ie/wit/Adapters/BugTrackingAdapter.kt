package ie.wit.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.R
import ie.wit.models.BugTrackingModel
import kotlinx.android.synthetic.main.card_bug.view.*
import kotlinx.android.synthetic.main.fragment_bug_tracking.view.*



interface DeleteListener {
    fun onDeleteClick(bugTracking: BugTrackingModel)
}

class BugTrackingAdapter constructor
    (private var bugTrackings: List<BugTrackingModel>,
     private val dlistener: DeleteListener)

    : RecyclerView.Adapter<BugTrackingAdapter.MainHolder>() {

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
        val bugTracking = bugTrackings[holder.adapterPosition]
        holder.bind(bugTracking, dlistener)
    }

    override fun getItemCount(): Int = bugTrackings.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            bugTracking: BugTrackingModel,
            dlistener: DeleteListener
        ) {
            itemView.bugFormTitle.text = bugTracking.title
            itemView.bugDescription.text = bugTracking.descriptions
            itemView.bugFormNumber.text = bugTracking.bugimportance
            itemView.imageIcon.setImageResource(R.mipmap.ic_launcher_round)
            itemView.bugDelete.setOnClickListener {
                (dlistener.onDeleteClick(bugTracking))
            }
        }
    }
}