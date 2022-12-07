package com.example.architecturetraining.designPattern.behavioral.observer

/**
 *
 */
sealed class PublisherStatus<T, out R>(data: String? = null) {
    class StatusA<T>(val key: String? = null, val value: T? = null) :
        PublisherStatus<Any, Any>()

    class StatusB<out R>(val value: R? = null) : PublisherStatus<Any, R>()
}