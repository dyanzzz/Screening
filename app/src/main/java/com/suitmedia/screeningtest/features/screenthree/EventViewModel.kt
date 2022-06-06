package com.suitmedia.screeningtest.features.screenthree

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suitmedia.screeningtest.utils.DataDummy
import javax.inject.Inject

class EventViewModel @Inject constructor(): ViewModel() {
    // === List Event
    private val listEvent = MutableLiveData<ArrayList<EventEntity>>()

    fun getListEvent(): LiveData<ArrayList<EventEntity>> {
        println("##### getListEvent $listEvent")
        return listEvent
    }

    fun setListEvent() {
        val data = DataDummy.generateDummyEvent()

        listEvent.postValue(data)
    }
}