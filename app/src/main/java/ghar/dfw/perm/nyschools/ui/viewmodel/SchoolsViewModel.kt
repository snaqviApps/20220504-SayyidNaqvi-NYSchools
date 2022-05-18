package ghar.dfw.perm.nyschools.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ghar.dfw.perm.nyschools.constants.CoreConstants.Companion.MAX_TIME_OUT
import ghar.dfw.perm.nyschools.constants.CoreConstants.Companion.SCHOOL_BASE_URL
import ghar.dfw.perm.nyschools.di.DaggerComponentInjector
import ghar.dfw.perm.nyschools.di.NetworkModule
import ghar.dfw.perm.nyschools.network.SchoolApi
import ghar.dfw.perm.nyschools.network.model.SchoolsResultRestApi
import ghar.dfw.perm.nyschools.network.model.ScoresResultRestApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import retrofit2.Retrofit
import javax.inject.Inject

class SchoolsViewModel : ViewModel() {

    private val _schoolApiCallResponse = MutableLiveData<UIState>()
    val schoolApiCallResponse: LiveData<UIState>
        get() = _schoolApiCallResponse

    @Inject
    lateinit var retrofit: Retrofit

    var schoolApi: SchoolApi
    private val schoolInjector = DaggerComponentInjector
        .builder()
        .networkModule(NetworkModule(SCHOOL_BASE_URL))
        .build()

    init {
        schoolInjector.inject(this)
        schoolApi = retrofit.create(SchoolApi::class.java)
        networkCall()
    }

    private fun networkCall() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            withTimeout(MAX_TIME_OUT) {
                try {
                    val schools = schoolApi.getSchools()
                    val scores = schoolApi.getScores()
                    if (!schools.isNullOrEmpty() && !scores.isNullOrEmpty()) {
                        _schoolApiCallResponse.postValue(UIState.SuccessState(schools, scores)
                        )
                    } else {
                        _schoolApiCallResponse.postValue(UIState.ErrorState("No data retrieved"))
                    }

                } catch (exception: Exception) {
                    exception.message?.let {
                        _schoolApiCallResponse.postValue(UIState.ErrorState(it))
                    }
                }
            }
        }
    }

    fun schoolClicked(name: String?) {
        _navigateToDetailsFragment.value = name
    }

    private val _navigateToDetailsFragment = MutableLiveData<String?>()
    val navigateToDetailsFragment
        get() = _navigateToDetailsFragment

    sealed class UIState {
        object EmptyState : UIState()
        class SuccessState(
            val schools: List<SchoolsResultRestApi>?,
            val scores: List<ScoresResultRestApi>?
        ) : UIState()

        class ErrorState(val message: String) : UIState()
    }
}