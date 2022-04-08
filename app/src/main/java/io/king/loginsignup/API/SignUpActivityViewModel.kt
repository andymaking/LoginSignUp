package io.king.loginsignup.API

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivityViewModel: ViewModel() {

    private var createNewUserLiveData: MutableLiveData<ResponseBody?> = MutableLiveData()

    fun getCreateNewUserObserver(): MutableLiveData<ResponseBody?> {
        return createNewUserLiveData
    }

    fun signup(userBody: UserBody){
        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        val registerInfo = retIn.registerUser(userBody)

        registerInfo.enqueue(object :
            Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                createNewUserLiveData.postValue(null)
//                Toast.makeText(
//                    this@MainActivity,
//                    t.message,
//                    Toast.LENGTH_SHORT
//                ).show()
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 201) {
                    createNewUserLiveData.postValue(response.body())
//                    Toast.makeText(this@MainActivity, "Registration success!", Toast.LENGTH_SHORT).show()

                }
                else{
                    createNewUserLiveData.postValue(null)
//                    Toast.makeText(this@MainActivity, "Registration failed!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}