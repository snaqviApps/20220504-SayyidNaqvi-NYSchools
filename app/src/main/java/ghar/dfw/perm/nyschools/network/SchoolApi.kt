package ghar.dfw.perm.nyschools.network

import ghar.dfw.perm.nyschools.network.model.SchoolsResultRestApi
import ghar.dfw.perm.nyschools.network.model.ScoresResultRestApi
import retrofit2.http.GET

const val SCHOOL_END_POINT = "s3k6-pzi2.json"
const val SCHOOL_SCORES_END_POINT = "f9bf-2cp4.json"

interface SchoolApi {

    @GET(SCHOOL_END_POINT)
    suspend fun getSchools(): List<SchoolsResultRestApi>

    @GET(SCHOOL_SCORES_END_POINT)
    suspend fun getScores(): List<ScoresResultRestApi>

}