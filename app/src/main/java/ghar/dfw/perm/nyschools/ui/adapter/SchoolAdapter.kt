package ghar.dfw.perm.nyschools.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ghar.dfw.perm.databinding.CardViewCityBinding
import ghar.dfw.perm.nyschools.network.model.SchoolsResultRestApi
import ghar.dfw.perm.nyschools.network.model.ScoresResultRestApi
import kotlin.properties.Delegates

private lateinit var schoolIn: List<SchoolsResultRestApi>
private var schoolInInt by Delegates.notNull<Int>()

open class SchoolAdapter(val schools: List<SchoolsResultRestApi>, val scores: List<ScoresResultRestApi>, val clickListener: SchoolsNameListener) :
  RecyclerView.Adapter<SchoolAdapter.SchoolsMainViewHolder>() {

  init {
    schoolIn = schools

  }
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolsMainViewHolder {

    val layoutInflater = LayoutInflater.from(parent.context)
    val binding = CardViewCityBinding.inflate(layoutInflater)
    return SchoolsMainViewHolder(binding)
  }

  override fun onBindViewHolder(holder: SchoolsMainViewHolder, position: Int) {

    holder.bindData(schools[position], clickListener, position - 8)
  }

  override fun getItemCount(): Int {
    return schools.size
  }


   class SchoolsMainViewHolder(private val binding : CardViewCityBinding) : RecyclerView.ViewHolder(binding.root) {


    fun bindData(school: SchoolsResultRestApi, clickListener: SchoolsNameListener, i: Int) {
        binding.clickListener = clickListener
        binding.mTextViewSchoolName.text = school.schoolName
        schoolInInt = i
    }
  }

  class SchoolsNameListener(val clickListener : (school : String) ->  Unit){

//    fun onClick(schoolClicked : SchoolsResultRestApi) = clickListener(schoolClicked.schoolName!!)
    fun onClick() = clickListener(schoolIn[schoolInInt].schoolName!!)


  }

}

