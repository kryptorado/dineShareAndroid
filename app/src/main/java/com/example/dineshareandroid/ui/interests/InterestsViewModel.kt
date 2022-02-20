package com.example.dineshareandroid.ui.interests

import androidx.lifecycle.*
import com.amplifyframework.datastore.generated.model.Interest
import com.example.dineshareandroid.backend.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InterestsViewModel: ViewModel() {
    val updateSuccess = MutableLiveData<Boolean>()

    val interests: LiveData<List<Interest>> = liveData {
        val interests = UserData.getListOfInterests()
        emit(interests)
    }

    fun updateInterests(interests: MutableList<Interest>) {
        viewModelScope.launch  {
            val isSuccess = withContext(Dispatchers.IO) {
                UserData.updateInterests(interests)
            }
            updateSuccess.value = isSuccess
        }
    }
}