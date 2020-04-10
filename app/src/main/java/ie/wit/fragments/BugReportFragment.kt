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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
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
    lateinit var loader : AlertDialog
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


        setSwipeRefresh()

        val swipeDeleteHandler = object : SwipeToDeleteCallback(activity!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = root.recyclerView.adapter as BugTrackingAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                deleteBug((viewHolder.itemView.tag as BugTrackingModel).uid)
                deleteUserBugTracking(app.auth.currentUser!!.uid,
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

    open fun setSwipeRefresh() {
        root.swiperefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                root.swiperefresh.isRefreshing = true
                getAllBugs(app.auth.currentUser!!.uid)
            }
        })
    }

    fun checkSwipeRefresh() {
        if (root.swiperefresh.isRefreshing) root.swiperefresh.isRefreshing = false
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

    fun deleteBug(uid: String?) {
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



    override fun onResume() {
        super.onResume()
        if(this::class == BugReportFragment::class)
            getAllBugs(app.auth.currentUser!!.uid)
    }

    fun getAllBugs(userId: String?) {
        loader = createLoader(activity!!)
        showLoader(loader, "Downloading Donations from Firebase")
        val bugsList = ArrayList<BugTrackingModel>()
        app.database.child("user-bugTrackings").child(userId!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    info("Firebase Donation error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    hideLoader(loader)
                    val children = snapshot.children
                    children.forEach {
                        val bug = it.
                            getValue<BugTrackingModel>(BugTrackingModel::class.java)

                        bugsList.add(bug!!)
                        root.recyclerView.adapter =
                            BugTrackingAdapter(bugsList, this@BugReportFragment, false)
                        root.recyclerView.adapter?.notifyDataSetChanged()
                        checkSwipeRefresh()

                        app.database.child("user-bugTrackings").child(userId)
                            .removeEventListener(this)
                    }
                }
            })
    }
}