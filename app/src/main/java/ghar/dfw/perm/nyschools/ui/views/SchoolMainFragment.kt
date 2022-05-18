package ghar.dfw.perm.nyschools.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ghar.dfw.perm.databinding.FragmentSchoolMainBinding
import ghar.dfw.perm.nyschools.network.model.SchoolsResultRestApi
import ghar.dfw.perm.nyschools.network.model.ScoresResultRestApi
import ghar.dfw.perm.nyschools.ui.adapter.SchoolAdapter
import ghar.dfw.perm.nyschools.ui.viewmodel.SchoolsViewModel
import ghar.dfw.perm.nyschools.utils.safeLet


class SchoolMainFragment() : Fragment() {

  private var schoolMatchedList = ArrayList<SchoolsResultRestApi>()
  private var scoreMatchedList = ArrayList<ScoresResultRestApi>()
  private lateinit var schoolsViewModel: SchoolsViewModel
  private lateinit var binding: FragmentSchoolMainBinding

  override fun onCreateView(inflater: LayoutInflater,
                            container: ViewGroup?,
                            savedInstanceState: Bundle?): View {
    binding = FragmentSchoolMainBinding.inflate(layoutInflater)
    schoolsViewModel = ViewModelProvider(this)[SchoolsViewModel::class.java]

    setupRV()
    setupObservers()
    return binding.root
  }

  private fun setupRV() {
    binding.mRecyclerViewSchools.layoutManager = LinearLayoutManager(requireContext())
    binding.mainXmlViewModel = schoolsViewModel
  }

  private fun setupObservers() {

    schoolsViewModel.navigateToDetailsFragment.observe(viewLifecycleOwner, Observer {schoolsData ->
      schoolsData.let {
        this.findNavController().navigate(
         SchoolMainFragmentDirections.actionSchoolMainFragmentToDetailsFragment(it!!)
        )
      }
    })

    schoolsViewModel.schoolApiCallResponse.observe(viewLifecycleOwner) { schoolApiCallResponse ->
      when (schoolApiCallResponse) {
        is SchoolsViewModel.UIState.EmptyState -> {

        }
        is SchoolsViewModel.UIState.SuccessState -> {
          val schools = schoolApiCallResponse.schools
          val scores = schoolApiCallResponse.scores

          val sortedSchoolsList = schools?.sortedWith(compareBy { it.schoolName })
          val sortedScoresList = scores?.sortedWith(compareBy { it.schoolName })

          for (school in sortedSchoolsList!!) {
            sortedScoresList?.forEach {
              if (it.schoolName?.replace("\\s", "")?.lowercase()
                  .equals(school.schoolName?.replace("\\s", "")?.lowercase())) {
                schoolMatchedList.add(school)
                scoreMatchedList.add(it)
              }
            }
          }

          safeLet(schoolMatchedList, scoreMatchedList) { safeSchools, safeScores ->
            val adapter = SchoolAdapter(safeSchools, safeScores,
              SchoolAdapter.SchoolsNameListener { schoolName ->
                run {
                  schoolsViewModel.schoolClicked(safeSchools[0].schoolName)
                }
              })

            binding.mRecyclerViewSchools.adapter = adapter
          }
        }
        is SchoolsViewModel.UIState.ErrorState -> {}
      }
    }
  }

}