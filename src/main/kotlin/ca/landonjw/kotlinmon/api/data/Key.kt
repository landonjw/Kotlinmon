package ca.landonjw.kotlinmon.api.data

interface Key<T> {

    val token: String
    val value: T

}