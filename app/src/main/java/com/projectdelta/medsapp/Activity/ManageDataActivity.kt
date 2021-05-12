package com.projectdelta.medsapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.projectdelta.medsapp.data.UserDatabaseManager.nukeDatabase
import com.projectdelta.medsapp.R
import kotlinx.android.synthetic.main.activity_manage_data.*

class ManageDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_MedsApp)
        setContentView(R.layout.activity_manage_data)

        val parentView = findViewById<ConstraintLayout>(R.id.manage_data_CL)

        my_data_reset.setOnClickListener {
            val dialog = MaterialAlertDialogBuilder(this , R.style.AlertDialogTheme)
                .setTitle("Delete all my data ?")
                .setMessage("This process cannot be reversed")
                .setPositiveButton("Reset"){_ , _ ->
                    if(nukeDatabase( this))
                        Snackbar.make( parentView , "All data cleared!" , Snackbar.LENGTH_LONG ).show()
                    else
                        Snackbar.make( parentView , "Some error occurred!" , Snackbar.LENGTH_LONG ).show()
                }
                .setNegativeButton("Cancel") {_ , _ -> }
                .create()
            dialog.show()
        }
    }
}