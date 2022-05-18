package ghar.dfw.perm.nyschools.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ghar.dfw.perm.databinding.CardViewCityBinding
import ghar.dfw.perm.nyschools.network.model.SchoolsResultRestApi
import ghar.dfw.perm.nyschools.network.model.ScoresResultRestApi

open class SchoolAdapter(val schools: List<SchoolsResultRestApi>, val scores: List<ScoresResultRestApi>, val clickListener: SchoolsNameListener) :
  RecyclerView.Adapter<SchoolAdapter.SchoolsMainViewHolder>() {

  val schoolIn = schools

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolsMainViewHolder {

    val layoutInflater = LayoutInflater.from(parent.context)
    val binding = CardViewCityBinding.inflate(layoutInflater)
    return SchoolsMainViewHolder(binding)
  }

  override fun onBindViewHolder(holder: SchoolsMainViewHolder, position: Int) {
    holder.bindData(schools[position], clickListener)
  }

  override fun getItemCount(): Int {
    return schools.size
  }


   class SchoolsMainViewHolder(private val binding : CardViewCityBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindData(school: SchoolsResultRestApi, clickListener: SchoolsNameListener) {
        binding.clickListener = clickListener
        binding.mTextViewSchoolName.text = school.schoolName
    }
  }

   class SchoolsNameListener(val clickListener : (school : String) ->  Unit){


//     val receivedSchool = SchoolAdapter.schoolIn

    fun onClick(clickedschool: SchoolsResultRestApi) = clickListener(clickedschool.schoolName!!)
  }

}

