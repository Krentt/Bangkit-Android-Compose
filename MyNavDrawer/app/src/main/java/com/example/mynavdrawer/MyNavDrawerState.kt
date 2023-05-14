package com.example.mynavdrawer

import android.content.Context
import android.widget.Toast
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MyNavDrawerState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val drawerState: DrawerState,
    val snackbarHostState: SnackbarHostState,
    private val scope: CoroutineScope,
    private val context: Context
){
    @OptIn(ExperimentalMaterial3Api::class)
    fun onMenuClick(){
        scope.launch {
            drawerState.open()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun onItemSelected(title: String){
        scope.launch {
            drawerState.close()
            val snackbarResult = snackbarHostState.showSnackbar(
                message = context.resources.getString(R.string.coming_soon, title),
                actionLabel = context.resources.getString(R.string.subscribe_question)
            )
            if (snackbarResult == SnackbarResult.ActionPerformed){
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.subscribed_info),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun onBackPress(){
        if (drawerState.isOpen){
            scope.launch {
                drawerState.close()
            }
        }
    }
}
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun rememberMyNavDrawerState(
        snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
        drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
        coroutineScope: CoroutineScope = rememberCoroutineScope(),
        context: Context = LocalContext.current
    ): MyNavDrawerState =
        remember(snackbarHostState, drawerState, coroutineScope, context){
            MyNavDrawerState(drawerState, snackbarHostState, coroutineScope, context)
        }
