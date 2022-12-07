package com.example.architecturetraining.designPattern.behavioral.observer

import android.content.Context
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

interface PublisherInterface {

    fun subscribe(subscriber: Subscriber)
    fun unsubscribe(subscriber: Subscriber): Boolean
    fun mainBusinessLogic()
    fun <T, R> notifySubscriber(publisherStatus: PublisherStatus<T, R>)
}

class Publisher : PublisherInterface {

    private var subscribers = mutableListOf<Subscriber>()

    override fun subscribe(subscriber: Subscriber) {
        subscribers.add(subscriber)
    }

    override fun unsubscribe(subscriber: Subscriber): Boolean {
        return subscribers.remove(subscriber)
    }

    override fun <T, R> notifySubscriber(publisherStatus: PublisherStatus<T, R>) {
        subscribers.map { it.update(publisherStatus) }
    }

    override fun mainBusinessLogic() {
        runBlocking {
            while (true) {
                var count = (1..44324).random()
                notifySubscriber(PublisherStatus.StatusA("counter", count.toString()))
                delay(1000)
                notifySubscriber(PublisherStatus.StatusB(count))
                delay(1000)
                notifySubscriber(PublisherStatus.StatusA("ewrwrer", 34242))
            }
        }
    }
}

interface SubscriberInterface {
    fun <T, R> update(publisherStatus: PublisherStatus<T, R>)
}

class Subscriber : SubscriberInterface {

    override fun <T, R> update(publisherStatus: PublisherStatus<T, R>) {
        when (publisherStatus) {
            is PublisherStatus.StatusA<*> -> {
                when (publisherStatus.value) {
                    is Int -> println("Status Int" + publisherStatus.value)
                    is String -> println("Status String" + publisherStatus.value)
                }
            }
            is PublisherStatus.StatusB -> {
                publisherStatus.value
            }
        }
    }
}

fun main() {
    val publisher = Publisher()
    val subscriber1 = Subscriber()
    val subscriber2 = Subscriber()
    publisher.subscribe(subscriber1)
    publisher.subscribe(subscriber2)
    publisher.mainBusinessLogic()
    publisher.unsubscribe(subscriber1)
}