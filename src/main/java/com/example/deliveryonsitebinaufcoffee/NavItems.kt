package com.example.deliveryonsitebinaufcoffee

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

class NavItems {
    data class NavIcons(val icon: ImageVector)

    val icons: List<NavIcons>

    init {
        icons = setIcon()
    }

    private fun setIcon(): List<NavIcons> {
        return listOf(
            NavIcons(Icons.Default.Home),
            NavIcons(Icons.Default.Favorite),
            NavIcons(Icons.Default.Menu),
            NavIcons(Icons.Default.Person)
        )
    }
}