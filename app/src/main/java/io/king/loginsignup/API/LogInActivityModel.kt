package io.king.loginsignup.API

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogInActivityModel: ViewModel() {

    private var logInLiveData: MutableLiveData<ResponseBody?> = MutableLiveData()

    fun getLogInUserObserver(): MutableLiveData<ResponseBody?> {
        return logInLiveData
    }

    fun signIn(signInBody: SignInBody){
        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        val signInInfo = retIn.signin(signInBody)

        signInInfo.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                logInLiveData.postValue(null)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200) {
                    logInLiveData.postValue(response.body())
                } else {
                    logInLiveData.postValue(null)
                }
            }
        })
    }


}