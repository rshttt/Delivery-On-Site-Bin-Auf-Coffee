package com.example.deliveryonsitebinaufcoffee

import androidx.annotation.DrawableRes

class NavItems {
    data class NavIcons(@DrawableRes val iconResId: Int)

    val icons: List<NavIcons>

    init {
        icons = setIcon()
    }

    private fun setIcon(): List<NavIcons> {
        return listOf(
            NavIcons(R.drawable.homegray),
            NavIcons(R.drawable.lovegray),
            NavIcons(R.drawable.notegray),
            NavIcons(R.drawable.profilegray)
        )
    }
}