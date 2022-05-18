package ghar.dfw.perm.nyschools.ui.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ghar.dfw.perm.R
import ghar.dfw.perm.databinding.FragmentDetailsBinding
import ghar.dfw.perm.databinding.FragmentSchoolMainBinding
import ghar.dfw.perm.nyschools.ui.viewmodel.SchoolsViewModel
import ghar.dfw.perm.nyschools.utils.safeLet


/**
 * A simple [Fragment] subclass.
 * Use the [DetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailsFragment : Fragment() {

  private lateinit var detailsBinding: FragmentDetailsBinding
  private lateinit var detailsFromSchoolViewModel: SchoolsViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

  }

  override fun onCreateView(inflater: LayoutInflater,
                            container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {

    detailsBinding = FragmentDetailsBinding.inflate(layoutInflater)
    detailsFromSchoolViewModel = ViewModelProvider(requireActivity())[SchoolsViewModel::class.java]

    getDetails()
    return detailsBinding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

//    val schoolNameReceived = DetailsFragmentArgs.fromSavedStateHandle()
  }

  private fun getDetails() : Unit {

    detailsFromSchoolViewModel.schoolApiCallResponse.observe(viewLifecycleOwner) { schoolsScoreResponse ->
      when (schoolsScoreResponse) {
        is SchoolsViewModel.UIState.EmptyState ->  {}

        is SchoolsViewModel.UIState.SuccessState -> {
          val scores = schoolsScoreResponse.scores
          Toast.makeText(context, "Hi details: ${scores?.toList()}", Toast.LENGTH_SHORT).show()

          val sortedScoresList = scores?.sortedWith(compareBy { it.schoolName })

//    safeLet(score.mathScore, score.readingScore, score.writingScore) { math, reading, writing ->
//
//        detailsBinding.mTextViewMathAvgScore.visibility = View.VISIBLE
//        detailsBinding.mTextViewMathAvgScore.text = math
//
//        detailsBinding.mTextViewCriticalReadingAvgScore.visibility = View.VISIBLE
//        detailsBinding.mTextViewCriticalReadingAvgScore.text = reading
//
//        detailsBinding.mTextViewWritingAvgScore.visibility = View.VISIBLE
//        detailsBinding.mTextViewWritingAvgScore.text = writing
//      }


        }
        is SchoolsViewModel.UIState.ErrorState -> {}

      }
    }

  }
}