package ie.wit.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ie.wit.R
import ie.wit.main.BugTrackingApp
import ie.wit.models.BugTrackingModel
import kotlinx.android.synthetic.main.fragment_bug_tracking.*

import kotlinx.android.synthetic.main.fragment_bug_tracking.view.*

class BugTrackingFragment : Fragment() {
    var bug = BugTrackingModel()
    var edit = false

    lateinit var app: BugTrackingApp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as BugTrackingApp




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_bug_tracking, container, false)
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

            app.bugTrackingsStore.create(BugTrackingModel(title = title, descriptions = description, bugimportance = bugNumber))


            val fragment = BugReportFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.homeFrame, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
            }
        }
    }



