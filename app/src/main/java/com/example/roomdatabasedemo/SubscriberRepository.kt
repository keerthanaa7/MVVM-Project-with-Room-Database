package com.example.roomdatabasedemo

class SubscriberRepository(private val dao:SubscriberDAO) {

    val subscribers = dao.getAllSubscribers()
    suspend fun insert(subscriber: Subscriber):Long{
        return dao.insertSubscriber(subscriber)
    }

    suspend fun update(subscriber: Subscriber):Int{
        return dao.updateSubsriber(subscriber)
    }
    suspend fun delete(subscriber: Subscriber):Int{
        return dao.deleteSubscriber(subscriber)
    }

    suspend fun deleteAll():Int{
        return dao.deleteAll()
    }

}