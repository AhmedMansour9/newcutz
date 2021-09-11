package com.hadrmout.ui.register

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hadrmout.base.BaseViewModel
import com.hadrmout.data.DataCenterManager
import com.hadrmout.data.remote.model.AccountResponse
import com.hadrmout.data.remote.model.RegisterRequest
import com.hadrmout.utils.Resource
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class RegisterViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<RegisterNavigator>(dataCenterManager) {


    var customer: RegisterRequest = RegisterRequest()
    var registerRequest: RegisterRequest = RegisterRequest()
    private val _accountResponse = MutableLiveData<Resource<AccountResponse>>()
    val accountResponse: LiveData<Resource<AccountResponse>>
        get() = _accountResponse

    fun register() {
        registerRequest = customer
        if (!registerRequest.empty()) {
            _accountResponse.postValue(Resource.loading(null))
//            viewModelScope.launch {
            dataCenterManager.newAccount(registerRequest)
                .enqueue(object : Callback, retrofit2.Callback<AccountResponse> {
                    override fun onResponse(
                        call: Call<AccountResponse>,
                        response: Response<AccountResponse>
                    ) {
                        if (response.isSuccessful) {
                            if (response.body()?.status == true) {
                                _accountResponse.postValue(Resource.success(response.body()))

                            } else {
//                                var account = Gson().fromJson(
//                                    response.body()?.toString() ?: response.errorBody()?.string(),
//                                    AccountResponse::class.java
//                                )
                                _accountResponse.postValue(
                                    response.body()?.error?.let {
                                        Resource.error(
                                            it,
                                            null
                                        )
                                    }
                                )

                            }
                        } else {
                            var account = Gson().fromJson(
                                response.body()?.toString() ?: response.errorBody()?.string(),
                                AccountResponse::class.java
                            )
                            _accountResponse.postValue(
                                Resource.error(
                                    account.error.toString(),
                                    null
                                )
                            )
                        }

                    }

                    override fun onFailure(call: Call<AccountResponse>, t: Throwable) {
                        _accountResponse.postValue(Resource.error(t.message.toString(), null))
                    }
                })
        }
    }


    @Throws(JSONException::class)
    private fun getErrorBodyJSON(errorBody: String): JSONObject? {
        val key = "message"
        val errorBodyJson = JSONObject(errorBody)
        val messageArray = errorBodyJson.optJSONArray(key)
        if (messageArray != null) {
            val firstMessage = messageArray.optJSONObject(0)
            errorBodyJson.put(key, firstMessage.optString(key))
        } else {
            val messageObject = errorBodyJson.optJSONObject(key)
            if (messageObject != null) {
                val firstMessageKey = messageObject.keys().next()
                errorBodyJson.put(key, messageObject.optString(firstMessageKey))
            }
        }
        return errorBodyJson
    }
}

