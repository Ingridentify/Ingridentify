package com.ingridentify.utils

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ingridentify.R

fun Fragment.hideBottomBar() {
    val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
    bottomNav.visibility = View.GONE
}

fun Fragment.showBottomBar() {
    val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
    bottomNav.visibility = View.VISIBLE
}

fun Fragment.hideActionBar() {
    (requireActivity() as androidx.appcompat.app.AppCompatActivity).supportActionBar?.hide()
}

fun Fragment.showActionBar() {
    (requireActivity() as androidx.appcompat.app.AppCompatActivity).supportActionBar?.show()
}

fun Fragment.enterFullscreen() {
    hideBottomBar()
    hideActionBar()
}

fun Fragment.exitFullscreen() {
    showBottomBar()
    showActionBar()
}