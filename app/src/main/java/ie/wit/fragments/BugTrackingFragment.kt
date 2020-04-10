package ie.wit.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog

import ie.wit.R
import ie.wit.main.BugTrackingApp
import ie.wit.models.BugTrackingModel
import ie.wit.utils.createLoader
import ie.wit.utils.hideLoader
import ie.wit.utils.showLoader
import kotlinx.android.synthetic.main.fragment_bug_tracking.*

import kotlinx.android.synthetic.main.fragment_bug_tracking.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class BugTrackingFragment : Fragment(), AnkoLogger {
    var bug = BugTrackingModel()
    var edit = false

    lateinit var app: BugTrackingApp
    lateinit var loader : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as BugTrackingApp




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_bug_tracking, container, false)
        loader = createLoader(activity!!)
        activity?.title = getString(R.string.action_bugTrackingForm)



        setButtonListener(root)
        return root;
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            BugTrackingFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    fun setButtonListener( layout: View) {
        layout.saveButton.setOnClickListener {
            val title = titlebug.text.toString()

            val description = DescriptionBox.text.toString()

            val bugNumber = if (BugRadio.checkedRadioButtonId == R.id.Bug1) "1"
            else if (BugRadio.checkedRadioButtonId == R.id.Bug2) "2"
            else if (BugRadio.checkedRadioButtonId == R.id.Bug3) "3"
            else if (BugRadio.checkedRadioButtonId == R.id.Bug4) "4"
            else if (BugRadio.checkedRadioButtonId == R.id.Bug5) "5" else "0"


            writeNewBugTracking(BugTrackingModel(title = title, descriptions = description, bugimportance = bugNumber, profilepic = app.userImage.toString(),
                email = app.auth.currentUser?.email))

            val fragment = BugReportFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.homeFrame, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
            }
        }


    fun writeNewBugTracking(bugTracking: BugTrackingModel) {
        // Create new donation at /donations & /donations/$uid
        showLoader(loader, "Adding Bug Tracking to Firebase")
        info("Firebase DB Reference : $app.database")
        val uid = app.auth.currentUser!!.uid
        val key = app.database.child("bugTrackings").push().key
        if (key == null) {
            info("Firebase Error : Key Empty")
            return
        }
        bugTracking.uid = key
        val donationValues = bugTracking.toMap()

        val childUpdates = HashMap<String, Any>()
        childUpdates["/bugTrackings/$key"] = donationValues
        childUpdates["/user-bugTrackings/$uid/$key"] = donationValues

        app.database.updateChildren(childUpdates)
        hideLoader(loader)
    }


}



