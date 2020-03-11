package ie.wit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.Adapters.BugTrackingAdapter
import ie.wit.Adapters.DeleteListener
import ie.wit.R
import ie.wit.main.BugTrackingApp
import ie.wit.models.BugTrackingModel
import kotlinx.android.synthetic.main.fragment_bug_report.view.*


class BugReportFragment : Fragment(), DeleteListener {

    lateinit var app: BugTrackingApp

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
        root.recyclerView.adapter = BugTrackingAdapter(app.bugTrackingsStore.findAll(),this)

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            BugReportFragment().apply {
                arguments = Bundle().apply { }
            }
    }

    override fun onDeleteClick(bugTracking: BugTrackingModel) {
        app.bugTrackingsStore.delete(bugTracking.copy())

        val fragment = BugReportFragment()
        val fragmentManager = activity!!.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.homeFrame, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }


}