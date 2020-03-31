package ie.wit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.ItemTouchHelper

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ie.wit.Adapters.BugTrackingAdapter

import ie.wit.R
import ie.wit.main.BugTrackingApp
import ie.wit.models.BugTrackingModel
import ie.wit.utils.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.fragment_bug_report.view.*


class BugReportFragment : Fragment() {

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
        var root = inflater.inflate(R.layout.fragment_bug_report, container, false)


        root.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        root.recyclerView.adapter = BugTrackingAdapter(app.bugTrackingsStore.findAll() as ArrayList<BugTrackingModel>)

        val swipeDeleteHandler = object : SwipeToDeleteCallback(activity!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = root.recyclerView.adapter as BugTrackingAdapter
                adapter.removeAt(viewHolder.adapterPosition)

            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(root.recyclerView)
        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            BugReportFragment().apply {
                arguments = Bundle().apply { }
            }
    }

    


}