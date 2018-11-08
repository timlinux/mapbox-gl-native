package com.mapbox.mapboxsdk.location.utils

import android.content.Context
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import android.view.View
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.location.LocationComponent
import org.hamcrest.Matcher

class LocationComponentAction(private val mapboxMap: MapboxMap,
                                private val onPerformLocationComponentAction: OnPerformLocationComponentAction) : ViewAction {

  override fun getConstraints(): Matcher<View> {
    return isDisplayed()
  }

  override fun getDescription(): String {
    return javaClass.simpleName
  }

  override fun perform(uiController: UiController, view: View) {
    onPerformLocationComponentAction.onLocationComponentAction(
      mapboxMap.locationComponent,
      mapboxMap,
      uiController,
      view.context)
  }

  interface OnPerformLocationComponentAction {
    fun onLocationComponentAction(component: LocationComponent, mapboxMap: MapboxMap, uiController: UiController, context: Context)
  }
}