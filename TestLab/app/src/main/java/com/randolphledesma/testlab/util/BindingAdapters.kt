package com.randolphledesma.testlab.util

import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.elevation.ElevationOverlayProvider

@BindingAdapter("elevationOverlay")
fun View.bindElevationOverlay(previousElevation: Float, elevation: Float) {
    if (previousElevation == elevation) return
    val color = ElevationOverlayProvider(context)
        .compositeOverlayWithThemeSurfaceColorIfNeeded(elevation)
    setBackgroundColor(color)
}
