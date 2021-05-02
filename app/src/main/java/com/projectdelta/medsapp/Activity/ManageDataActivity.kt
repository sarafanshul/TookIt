package com.projectdelta.medsapp.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.projectdelta.medsapp.Data.UserDatabaseManager.nukeDatabase
import com.projectdelta.medsapp.R
import kotlinx.android.synthetic.main.activity_manage_data.*

class ManageDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_data)

        my_data_reset.setOnClickListener {
            val dialog = MaterialAlertDialogBuilder(this , R.style.AlertDialogTheme)
                .setTitle("Delete all my data ?")
                .setMessage("This process cannot be reversed")
                .setPositiveButton("Reset"){_ , _ ->
                    nukeDatabase( this)
                }
                .setNegativeButton("Cancel") {_ , _ -> }
                .create()
            dialog.show()
        }
    }
}