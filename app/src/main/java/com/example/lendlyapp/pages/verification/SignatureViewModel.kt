package com.example.lendlyapp.pages.verification

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignatureViewModel @Inject constructor() : ViewModel() {

    val points = mutableStateListOf<Offset>()

    fun onDragStart(offset: Offset) {
        points.add(offset)
    }

    fun onDrag(offset: Offset) {
        points.add(offset)
    }

    fun onDragEnd() {
        points.add(Offset.Unspecified)
    }

    fun clearSignature() {
        points.clear()
    }
}
