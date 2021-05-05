package ca.landonjw.kotlinmon.common.util.collections

class ImmutableArray<T>(private vararg val values: T) {

    operator fun get(index: Int) = values[index]

}

inline fun <reified T> immutableArrayOf(vararg values: T) = ImmutableArray(*values)