package ie.wit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ie.wit.Adapters.BugListener
import ie.wit.Adapters.BugTrackingAdapter

import ie.wit.R
import ie.wit.models.BugTrackingModel

import ie.wit.utils.*
import kotlinx.android.synthetic.main.fragment_bug_report.view.*

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

        var query = FirebaseDatabase.getInstance()
            .reference.child("bugTrackings")

        var options = FirebaseRecyclerOptions.Builder<BugTrackingModel>()
            .setQuery(query, BugTrackingModel::class.java)
            .setLifecycleOwner(this)
            .build()

        root.recyclerView.adapter = BugTrackingAdapter(options,this)

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            BugAllReportsFragment().apply {
                arguments = Bundle().apply { }
            }
    }
}