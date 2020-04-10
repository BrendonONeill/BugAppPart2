package ie.wit.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
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
import ie.wit.models.BugTrackingModel

import ie.wit.utils.*
import kotlinx.android.synthetic.main.fragment_bug_report.view.*

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class BugAllReportsFragment : BugReportFragment(),
    BugListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_bug_report, container, false)
        activity?.title = getString(R.string.menu_report_all)

        root.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        setSwipeRefresh()

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            BugAllReportsFragment().apply {
                arguments = Bundle().apply { }
            }
    }

    override fun setSwipeRefresh() {
        root.swiperefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                root.swiperefresh.isRefreshing = true
                getAllUsersDonations()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getAllUsersDonations()
    }

    fun getAllUsersDonations() {
        loader = createLoader(activity!!)
        showLoader(loader, "Downloading All Users Donations from Firebase")
        val bugsList = ArrayList<BugTrackingModel>()
        app.database.child("bugTrackings")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    info("Firebase Donation error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    hideLoader(loader)
                    val children = snapshot.children
                    children.forEach {
                        val donation = it.
                            getValue<BugTrackingModel>(BugTrackingModel::class.java)

                        bugsList.add(donation!!)
                        root.recyclerView.adapter =
                            BugTrackingAdapter(bugsList, this@BugAllReportsFragment,true)
                        root.recyclerView.adapter?.notifyDataSetChanged()
                        checkSwipeRefresh()

                        app.database.child("bugTrackings").removeEventListener(this)
                    }
                }
            })
    }
}