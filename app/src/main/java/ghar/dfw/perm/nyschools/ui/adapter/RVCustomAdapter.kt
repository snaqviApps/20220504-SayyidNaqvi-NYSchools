package ghar.dfw.perm.nyschools.ui.adapter

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IntegerRes
import androidx.core.text.isDigitsOnly
import androidx.recyclerview.widget.RecyclerView
import ghar.dfw.perm.R
import ghar.dfw.perm.nyschools.constants.CoreConstants
import ghar.dfw.perm.nyschools.constants.CoreConstants.Companion.NO_SCORE_AVAILABLE
import ghar.dfw.perm.nyschools.network.model.SchoolsResultRestApi
import ghar.dfw.perm.nyschools.network.model.ScoresResultRestApi
import ghar.dfw.perm.nyschools.utils.safeLet

class RVCustomAdapter(val schools: List<SchoolsResultRestApi>,
                      val scores: List<ScoresResultRestApi>) :
  RecyclerView.Adapter<RVCustomAdapter.ViewHolder>() {


  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(LayoutInflater.from(parent.context)
      .inflate(R.layout.card_view_city, parent, false))
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bindData(schools[position], scores[position])
  }

  override fun getItemCount(): Int {
    return schools.size
  }

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var mTextViewSchoolName: TextView
    private var mLinearLayoutContainerSubItem: View
    private var mTextViewCriticalReadingAvgScore: TextView
    private var mTextViewMathAvgScore: TextView
    private var mTextViewWritingAvgScore: TextView

    private var mTextViewMathAvgScoreLabel: TextView
    private var mTextViewWritingAvgScoreLabel: TextView
    private var mTextViewCriticalReadingAvgScoreLabel: TextView

    init {
      with(itemView) {
        mTextViewSchoolName = findViewById(R.id.mTextViewSchoolName)
        mLinearLayoutContainerSubItem = findViewById(R.id.mLinearLayoutContainerSubItem)

        mTextViewCriticalReadingAvgScore = findViewById(R.id.mTextViewCriticalReadingAvgScore)

        mTextViewCriticalReadingAvgScoreLabel =
          findViewById(R.id.mTextViewCriticalReadingAvgScoreLabel)

        mTextViewMathAvgScore = findViewById<TextView?>(R.id.mTextViewMathAvgScore)
        mTextViewMathAvgScoreLabel = findViewById(R.id.mTextViewMathAvgScoreLabel)

        mTextViewWritingAvgScore = findViewById(R.id.mTextViewWritingAvgScore)
        mTextViewWritingAvgScoreLabel = findViewById(R.id.mTextViewWritingAvgScoreLabel)

      }.also {
        mTextViewMathAvgScore.setRawInputType(2)
        mTextViewCriticalReadingAvgScore.setRawInputType(2)
        mTextViewWritingAvgScore.setRawInputType(2)
      }
    }

    fun bindData(uiDataClass: SchoolsResultRestApi, score: ScoresResultRestApi) {

      // redundant check to make sure that data is not passed that is un-matched so item-view shouldn't be null
          mTextViewSchoolName.text = score.schoolName
          when (score.mathScore!!.isNotEmpty() && score.mathScore!!.isDigitsOnly()) {
            true -> {
              mTextViewMathAvgScore.visibility = View.VISIBLE
              mTextViewMathAvgScore.text = score.mathScore
            }
            else -> {
              mTextViewMathAvgScore.text = NO_SCORE_AVAILABLE
            }
          }

          when (score.readingScore!!.isNotEmpty() && score.readingScore!!.isDigitsOnly()) {
            true -> {
              mTextViewCriticalReadingAvgScore.visibility = View.VISIBLE
              mTextViewCriticalReadingAvgScore.text = score.readingScore
            }
            else -> {
              mTextViewCriticalReadingAvgScore.text = NO_SCORE_AVAILABLE
            }
          }

          when ((score.writingScore!!.isNotEmpty()) && score.writingScore!!.isDigitsOnly()) {
            true -> {
              mTextViewWritingAvgScore.visibility = View.VISIBLE
              mTextViewWritingAvgScore.text = score.writingScore
            }
            else -> {
              mTextViewWritingAvgScore.text = NO_SCORE_AVAILABLE
            }
          }

    }

  }

}