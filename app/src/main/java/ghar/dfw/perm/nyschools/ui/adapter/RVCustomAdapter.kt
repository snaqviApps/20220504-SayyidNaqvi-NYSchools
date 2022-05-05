package ghar.dfw.perm.nyschools.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ghar.dfw.perm.R
import ghar.dfw.perm.nyschools.network.model.SchoolsResultRestApi
import ghar.dfw.perm.nyschools.network.model.ScoresResultRestApi
import ghar.dfw.perm.nyschools.utils.safeLet

class RVCustomAdapter(val schools: List<SchoolsResultRestApi>, val scores: List<ScoresResultRestApi>) :
  RecyclerView.Adapter<RVCustomAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(LayoutInflater.from(parent.context)
      .inflate(R.layout.card_view_city, parent, false))
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bindData(scores[position])
  }

  override fun getItemCount(): Int {
    return schools.size
  }

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var mTextViewSchoolName: TextView
    private var mTextViewCriticalReadingAvgScore: TextView
    private var mTextViewMathAvgScore: TextView
    private var mTextViewWritingAvgScore: TextView

    init {
      with(itemView) {
        mTextViewSchoolName = findViewById(R.id.mTextViewSchoolName)
        mTextViewMathAvgScore = findViewById<TextView?>(R.id.mTextViewMathAvgScore)
        mTextViewCriticalReadingAvgScore = findViewById(R.id.mTextViewCriticalReadingAvgScore)
        mTextViewWritingAvgScore = findViewById(R.id.mTextViewWritingAvgScore)
      }
    }

    fun bindData(score: ScoresResultRestApi) {

      // redundant check to make sure that data is not passed that is un-matched so item-view shouldn't be null
      mTextViewSchoolName.text = score.schoolName
      safeLet(score.mathScore, score.readingScore, score.writingScore) { math, reading, writing ->
        mTextViewMathAvgScore.visibility = View.VISIBLE
        mTextViewMathAvgScore.text = math

        mTextViewCriticalReadingAvgScore.visibility = View.VISIBLE
        mTextViewCriticalReadingAvgScore.text = reading

        mTextViewWritingAvgScore.visibility = View.VISIBLE
        mTextViewWritingAvgScore.text = writing
      }
    }

  }

}