package ie.wit.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.R
import ie.wit.models.BugTrackingModel
import kotlinx.android.synthetic.main.card_bug.view.*
import kotlinx.android.synthetic.main.fragment_bug_tracking.view.*





class BugTrackingAdapter constructor
    (var bugs: ArrayList<BugTrackingModel>)

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
        val bugTracking = bugs[holder.adapterPosition]
        holder.bind(bugTracking)
    }

    override fun getItemCount(): Int = bugs.size

    fun removeAt(position: Int) {
        bugs.removeAt(position)
        notifyItemRemoved(position)
    }

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            bugTracking: BugTrackingModel

        ) {
            itemView.bugFormTitle.text = bugTracking.title
            itemView.bugDescription.text = bugTracking.descriptions
            itemView.bugFormNumber.text = bugTracking.bugimportance
            itemView.imageIcon.setImageResource(R.mipmap.ic_launcher_round)

            }
        }
    }
