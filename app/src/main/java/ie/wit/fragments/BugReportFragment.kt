package ie.wit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.ItemTouchHelper

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ie.wit.Adapters.BugListener
import ie.wit.Adapters.BugTrackingAdapter

import ie.wit.R

import ie.wit.main.BugTrackingApp
import ie.wit.models.BugTrackingModel
import ie.wit.utils.*
import kotlinx.android.synthetic.main.fragment_bug_report.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


open class BugReportFragment : Fragment() , BugListener, AnkoLogger  {

    lateinit var app: BugTrackingApp

    lateinit var root: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as BugTrackingApp
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_bug_report, container, false)
        activity?.title = getString(R.string.action_bugReport)

        root.recyclerView.setLayoutManager(LinearLayoutManager(activity))


        var query = FirebaseDatabase.getInstance()
            .reference
            .child("user-bugTrackings").child(app.currentUser.uid)

        var options = FirebaseRecyclerOptions.Builder<BugTrackingModel>()
            .setQuery(query, BugTrackingModel::class.java)
            .setLifecycleOwner(this)
            .build()

        root.recyclerView.adapter = BugTrackingAdapter(options, this)

        val swipeDeleteHandler = object : SwipeToDeleteCallback(activity!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteBugTracking((viewHolder.itemView.tag as BugTrackingModel).uid)
                deleteUserBugTracking(app.currentUser!!.uid,
                    (viewHolder.itemView.tag as BugTrackingModel).uid)
            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(root.recyclerView)

        val swipeEditHandler = object : SwipeToEditCallback(activity!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onBugClick(viewHolder.itemView.tag as BugTrackingModel)
            }
        }
        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
        itemTouchEditHelper.attachToRecyclerView(root.recyclerView)

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            BugReportFragment().apply {
                arguments = Bundle().apply { }
            }
    }

    fun deleteUserBugTracking(userId: String, uid: String?) {
        app.database.child("user-bugTrackings").child(userId).child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.removeValue()
                    }
                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Donation error : ${error.message}")
                    }
                })
    }

    fun deleteBugTracking(uid: String?) {
        app.database.child("bugTrackings").child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.removeValue()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Donation error : ${error.message}")
                    }
                })
    }



    override fun onBugClick(bugTracking: BugTrackingModel) {
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.homeFrame, EditFragment.newInstance(bugTracking))
            .addToBackStack(null)
            .commit()
    }
}
