package com.laskapi.myradio.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.laskapi.myradio.viewmodel.MViewModel

@Composable
fun RootComposable(viewModel:MViewModel = viewModel()) {
    Text("I am")
    LaunchedEffect(Unit) {
        viewModel.test()
    }
}