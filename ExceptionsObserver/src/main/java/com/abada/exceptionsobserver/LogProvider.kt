package com.abada.exceptionsobserver

import android.content.ContentValues.TAG
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map


object LogProvider {
    var current = listOf<String>()
    var lastTag = ""
    val exceptionsFlow = flow {

        Log.i(TAG, "logCatOutput: start observing")
        Runtime.getRuntime().exec("logcat -D -v brief output *:E").inputStream.bufferedReader()
            .useLines { lines ->
                lines.forEach { line ->
                    var toEmit = line.trim()
                    //   if (line.first() in setOf('E', 'W', 'V', 'I', 'F', 'S', 'D') && line[1] =='/')
                    if (toEmit.isNotEmpty()) {
                        if (line.tag == lastTag && lastTag.isNotEmpty())
                            toEmit = toEmit.substring(toEmit.indexOf(":")+1)
                        lastTag = toEmit.tag.takeIf { it.isNotEmpty() } ?: lastTag
                        current = current + toEmit
                        emit(current + "\n")
                    }
                }
            }
    }.flowOn(Dispatchers.IO)
    val lastException = exceptionsFlow.map { it.filter { it.startsWith("E") } }
    fun clear() {
        current = listOf()
        Log.e("", "")
    }
}

val String.tag: String
    get() {
        return if (first() in setOf(
                'E',
                'W',
                'V',
                'I',
                'F',
                'S',
                'D'
            ) && contains(":")
        ) substring(0, indexOf(":"))
        else ""
    }