package com.example.tugaspraktikum10
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.tugaspraktikum10.resto.MenuResto
import com.example.tugaspraktikum10.resto.MenuRestoRepository

class MenuRestoViewModel(application: Application) : AndroidViewModel(application)
{
    private var repository: MenuRestoRepository =
        MenuRestoRepository(application)
    private var allMenu: LiveData<List<MenuResto>> = repository.getAllMenus()
    fun insert(menu: MenuResto) {
        repository.insert(menu)
    }
    fun update(menu: MenuResto) {
        repository.update(menu)
    }
    fun delete(menu: MenuResto) {
        repository.delete(menu)
    }
    fun deleteAllMenus() {
        repository.deleteAllMenus()
    }
    fun getAllMenus(): LiveData<List<MenuResto>> {
        return allMenu
    }
}
