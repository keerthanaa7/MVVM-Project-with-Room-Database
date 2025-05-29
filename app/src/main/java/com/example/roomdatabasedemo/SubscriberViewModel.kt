package com.example.roomdatabasedemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRepository)  : ViewModel() {
    val subscribers = repository.subscribers
    val inputNmae = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()

    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate(){
        val name = inputNmae.value!!
        val email = inputEmail.value!!
        insert(subscriber = Subscriber(0, name, email))
        inputNmae.value=""
        inputEmail.value=""

    }
    fun clearOrDelete() {
        clearAll()
    }

    fun insert(subscriber: Subscriber){
        viewModelScope.launch {
            repository.insert(subscriber)
        }
    }

    fun update(subscriber: Subscriber){
        viewModelScope.launch {
            repository.update(subscriber)
        }
    }

    fun delete(subscriber: Subscriber){
        viewModelScope.launch {
            repository.delete(subscriber)
        }
    }

    fun clearAll(){
        viewModelScope.launch {
            repository.deleteAll()
        }
    }
}