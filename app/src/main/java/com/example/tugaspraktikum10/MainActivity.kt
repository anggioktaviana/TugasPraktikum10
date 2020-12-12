package com.example.tugaspraktikum10
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugaspraktikum10.resto.MenuResto
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val ADD_MENU_REQUEST = 1
        const val EDIT_MENU_REQUEST = 2
    }
    private lateinit var menurestoViewModel: MenuRestoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonAddNote.setOnClickListener {
            startActivityForResult(
                Intent(this, AddEditMenuRestoActivity::class.java),
                ADD_MENU_REQUEST
            )
        }
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        val adapter = MenuRestoAdapter()
        recycler_view.adapter = adapter

        menurestoViewModel = ViewModelProviders.of(this).get(MenuRestoViewModel::class.java)
        menurestoViewModel.getAllMenus().observe(this, Observer<List<MenuResto>> {
            adapter.submitList(it)
        })
        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                menurestoViewModel.delete(adapter.getMenuRestoAt(viewHolder.adapterPosition))
                Toast.makeText(baseContext, "Menu dihapus!", Toast.LENGTH_SHORT).show()
            }
        }
        ).attachToRecyclerView(recycler_view)
        adapter.setOnItemClickListener(object : MenuRestoAdapter.OnItemClickListener {
            override fun onItemClick(note: MenuResto) {
                val intent = Intent(baseContext, AddEditMenuRestoActivity::class.java)
                intent.putExtra(AddEditMenuRestoActivity.EXTRA_ID, note.id)
                intent.putExtra(AddEditMenuRestoActivity.EXTRA_NAMA, note.nama)
                intent.putExtra(AddEditMenuRestoActivity.EXTRA_DESKRIPSI, note.deskripsi)
                intent.putExtra(AddEditMenuRestoActivity.EXTRA_RATE, note.rate)
                startActivityForResult(intent, EDIT_MENU_REQUEST)
            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.delete_all_notes -> {
                menurestoViewModel.deleteAllMenus()
                Toast.makeText(this, "Semua sudah dihapus!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_MENU_REQUEST && resultCode == Activity.RESULT_OK) {
            val newNote = MenuResto(
                data!!.getStringExtra(AddEditMenuRestoActivity.EXTRA_NAMA),
                data.getStringExtra(AddEditMenuRestoActivity.EXTRA_DESKRIPSI),
                data.getIntExtra(AddEditMenuRestoActivity.EXTRA_RATE, 1)
            )
            menurestoViewModel.insert(newNote)
            Toast.makeText(this, "Menu disimpan!", Toast.LENGTH_SHORT).show()
        } else if (requestCode == EDIT_MENU_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(AddEditMenuRestoActivity.EXTRA_ID, -1)
            if (id == -1) {
                Toast.makeText(this, "Pembaharuan gagal!", Toast.LENGTH_SHORT).show()
            }
            val updateNote = MenuResto(
                data!!.getStringExtra(AddEditMenuRestoActivity.EXTRA_NAMA),
                data.getStringExtra(AddEditMenuRestoActivity.EXTRA_DESKRIPSI),
                data.getIntExtra(AddEditMenuRestoActivity.EXTRA_RATE, 1)
            )
            updateNote.id = data.getIntExtra(AddEditMenuRestoActivity.EXTRA_ID, -1)
            menurestoViewModel.update(updateNote)
        } else {
            Toast.makeText(this, "Menu tidak disimpan!", Toast.LENGTH_SHORT).show()
        }
    }
}
