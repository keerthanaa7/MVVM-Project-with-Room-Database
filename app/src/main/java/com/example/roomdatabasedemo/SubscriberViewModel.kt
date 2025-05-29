package com.example.roomdatabasedemo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel() {
    val subscribers = repository.subscribers
    val inputNmae = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()
    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete: Subscriber

    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate() {
        if (isUpdateOrDelete) {
            update(subscriberToUpdateOrDelete)
            subscriberToUpdateOrDelete.name = inputNmae.value!!
            subscriberToUpdateOrDelete.email = inputEmail.value!!
            update(subscriberToUpdateOrDelete)
        } else {
            val name = inputNmae.value!!
            val email = inputEmail.value!!
            insert(subscriber = Subscriber(0, name, email))
            inputNmae.value = ""
            inputEmail.value = ""
        }


    }

    fun clearOrDelete() {
        if (isUpdateOrDelete) {
            delete(subscriberToUpdateOrDelete)
        } else {
            clearAll()
        }

    }

    fun insert(subscriber: Subscriber) {
        viewModelScope.launch {
            val newrowid = repository.insert(subscriber)
            if (newrowid > -1) {
                Log.d("SubscriberViewModel", "inserted success")
            } else {
                Log.d("SubscriberViewModel", "inserted failure")
            }
        }
    }

    fun update(subscriber: Subscriber) {
        viewModelScope.launch(Dispatchers.IO) {
            var noofrows:Int = repository.update(subscriber)
            withContext(Dispatchers.Main) {
                if (noofrows > 0) {
                    inputNmae.value = ""
                    inputEmail.value = ""
                    isUpdateOrDelete = false
                    subscriberToUpdateOrDelete = subscriber
                    saveOrUpdateButtonText.value = "save"
                    clearAllOrDeleteButtonText.value = "Clear All"
                    Log.d("SubscriberViewModel", "updated success")
                } else {
                    Log.d("SubscriberViewModel", "updated failure")
                }

            }
        }
    }

    fun delete(subscriber: Subscriber) {
        viewModelScope.launch(Dispatchers.IO) {
           val rowsdeleted =  repository.delete(subscriber)
            withContext(Dispatchers.Main) {
                inputNmae.value = ""
                inputEmail.value = ""
                isUpdateOrDelete = false
                subscriberToUpdateOrDelete = subscriber
                saveOrUpdateButtonText.value = "save"
                clearAllOrDeleteButtonText.value = "Clear All"
            }
        }

    }

    fun clearAll() {
        viewModelScope.launch {
            val rowsdeleted = repository.deleteAll()
        }
    }

    fun initUpdateAndDelete(subscriber: Subscriber) {
        inputNmae.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"


    }
}