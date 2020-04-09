package ie.wit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import ie.wit.R
import ie.wit.main.BugTrackingApp
import ie.wit.models.BugTrackingModel
import ie.wit.utils.createLoader
import ie.wit.utils.hideLoader
import kotlinx.android.synthetic.main.fragment_edit.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class EditFragment : Fragment(), AnkoLogger {

    lateinit var app: BugTrackingApp
    lateinit var loader : AlertDialog
    lateinit var root: View
    var editBugs: BugTrackingModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as BugTrackingApp

        arguments?.let {
            editBugs = it.getParcelable("editbugs")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_edit, container, false)
        activity?.title = getString(R.string.action_edit)
        loader = createLoader(activity!!)



        root.editTitle.setText(editBugs!!.title)
        root.editDescription.setText(editBugs!!.descriptions)
        root.editImportance.setText(editBugs!!.bugimportance)



        root.editUpdateButton.setOnClickListener {

            updateBugData()
            updateBugTracking(editBugs!!.uid, editBugs!!)
            updateUserBugTracking(app.auth.currentUser!!.uid,
                editBugs!!.uid, editBugs!!)

        }
        return root
    }


    companion object {
        @JvmStatic
        fun newInstance(donation: BugTrackingModel) =
            EditFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("editbugs",donation)
                }
            }
    }


    fun updateBugData() {
        editBugs!!.title = root.editTitle.text.toString()
        editBugs!!.descriptions = root.editDescription.text.toString()

    }

    fun updateUserBugTracking(userId: String, uid: String?, bugTracking: BugTrackingModel) {
        app.database.child("user-bugTrackings").child(userId).child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.setValue(bugTracking)
                        activity!!.supportFragmentManager.beginTransaction()
                            .replace(R.id.homeFrame, BugReportFragment.newInstance())
                            .addToBackStack(null)
                            .commit()
                        hideLoader(loader)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Donation error : ${error.message}")
                    }
                })
    }

    fun updateBugTracking(uid: String?, bugTracking: BugTrackingModel) {
        app.database.child("bugTrackings").child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.setValue(bugTracking)
                        hideLoader(loader)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Donation error : ${error.message}")
                    }
                })
    }
}