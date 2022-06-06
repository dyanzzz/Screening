package com.suitmedia.screeningtest.features.screenfour

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suitmedia.screeningtest.api.ResultsResponse
import com.suitmedia.screeningtest.data.Result
import com.suitmedia.screeningtest.features.screenone.ProfileEntity
import com.suitmedia.screeningtest.wrapper.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GuestViewModel @Inject constructor(private val repository: GuestRemoteRepository): ViewModel() {
    // === List
    private val _listGuest = MutableLiveData<Event<LiveData<Result<ResultsResponse<ProfileEntity>>>>>()
    val getListGuestItem: LiveData<Event<LiveData<Result<ResultsResponse<ProfileEntity>>>>> get() = _listGuest

    fun setListGuest(page: Int, perPage: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.Default) { repository.observeListGuest(page, perPage) }
            _listGuest.value = Event(result)
        }
    }
}