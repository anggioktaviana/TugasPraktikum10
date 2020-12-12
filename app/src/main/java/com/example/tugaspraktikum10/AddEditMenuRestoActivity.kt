package com.example.tugaspraktikum10

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_menu.*

class AddEditMenuRestoActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "com.example.tugaspraktikum10.EXTRA_ID"
        const val EXTRA_NAMA = "com.example.tugaspraktikum10.EXTRA_NAMA"
        const val EXTRA_DESKRIPSI = "com.example.tugaspraktikum10.EXTRA_DESKRIPSI"
        const val EXTRA_RATE = "com.example.tugaspraktikum10.EXTRA_RATE"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_menu)
        number_picker_rate.minValue = 1
        number_picker_rate.maxValue = 5
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_black_24dp)
        if (intent.hasExtra(EXTRA_ID)) {
            title = "Edit Menu"
            edit_text_nama.setText(intent.getStringExtra(EXTRA_NAMA))
            edit_text_deskripsi.setText(intent.getStringExtra(EXTRA_DESKRIPSI))
            number_picker_rate.value = intent.getIntExtra(EXTRA_RATE, 1)
        } else {
            title = "Tambah Menu"
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menuresto, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.save_note -> {
                saveNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun saveNote() {
        if (edit_text_nama.text.toString().trim().isBlank() || edit_text_deskripsi.text.toString().trim().isBlank()) {
            Toast.makeText(this, "Menu kosong!", Toast.LENGTH_SHORT).show()
            return
        }
        val data = Intent().apply {
            putExtra(EXTRA_NAMA, edit_text_nama.text.toString())
            putExtra(EXTRA_DESKRIPSI, edit_text_deskripsi.text.toString())
            putExtra(EXTRA_RATE, number_picker_rate.value)
            if (intent.getIntExtra(EXTRA_ID, -1) != -1) {
                putExtra(EXTRA_ID, intent.getIntExtra(EXTRA_ID, -1))
            }
        }
        setResult(Activity.RESULT_OK, data)
        finish()
    }
}