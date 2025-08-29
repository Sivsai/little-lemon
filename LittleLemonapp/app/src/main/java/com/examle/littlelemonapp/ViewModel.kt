package com.examle.littlelemonapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.ViewModelProvider

// Assume 'MenuDao' and 'MenuItemEntity' are already defined
class MenuViewModel(menuDao: MenuDao) : ViewModel() {

    // This gets a Flow of lists from the database and converts it into a StateFlow
    // The UI will collect this StateFlow.
    val menuItems: StateFlow<List<MenuItemEntity>> = menuDao.getAllMenuItems()
        .stateIn(
            scope = viewModelScope,
            // Start collecting when the UI is visible and stop 5 seconds after it's gone
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList() // Show an empty list while data is loading
        )
}

class MenuViewModelFactory(private val menuDao: MenuDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MenuViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MenuViewModel(menuDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}