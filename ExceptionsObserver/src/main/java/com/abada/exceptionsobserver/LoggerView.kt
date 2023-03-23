package com.abada.exceptionsobserver

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.abada.flyView.FlyViewScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine


@Composable
fun FlyViewScope.ExceptionObserverView(
    modifier: Modifier = Modifier
) {
    val search = remember { MutableStateFlow("") }
    val searchText by search.collectAsState()
    var isExtended by remember { mutableStateOf(true) }
    Surface(
        modifier = modifier, color = Color.Red
    ) {
        Column {
            Row {
                IconButton(onClick = { isExtended = !isExtended }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Collapse"
                    )
                }
                IconButton(onClick = removeView) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
                IconButton(onClick = LogProvider::clear) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Clear")
                }
                TextField(value = searchText, onValueChange = { search.value = it })
            }
            if (isExtended) {
                val currentFlow by remember {
                    LogProvider.exceptionsFlow.combine(search) { logs, search ->
                        logs.filter { if (search != "") it.contains(search) else true }
                    }
                }.collectAsState(initial = listOf())
                var selected by remember { mutableStateOf<Int?>(null) }
                LazyColumn(
                ) {
                    itemsIndexed(currentFlow) { index, it ->
                        Text(text = it,
                            modifier = Modifier
                                .clickable { selected = index }
                                .background(
                                    if (selected == index) Color.Gray else Color.Transparent
                                )
                                .scrollable(
                                    rememberScrollState(), Orientation.Horizontal
                                ),
                            color = Color.Black)
                    }
                }
            }
        }
    }
}