package com.syahputrareno975.kartupersediaanlibrary.dialog

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.syahputrareno975.kartupersediaanlibrary.R

class DialogEditValue {
    lateinit var context: Context
    lateinit var titleValue : String
    lateinit var value : String
    lateinit var onEdit : (String) -> Unit

    constructor(context: Context, title: String, value: String, onEdit: (String) -> Unit) {
        this.context = context
        this.titleValue = title
        this.value = value
        this.onEdit = onEdit
    }


    fun dialog(){

        val v = (context as Activity).layoutInflater.inflate(R.layout.dialog_edit_value,null)
        val dialog = AlertDialog.Builder(context)
            .create()

        val title:TextView = v.findViewById(R.id.title_edit_value)
        title.text = titleValue

        val edit : EditText = v.findViewById(R.id.value_edit)
        edit.setText(value)
        edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                value = edit.text.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        val ok : Button = v.findViewById(R.id.button_edit)
        ok.setOnClickListener {
            if (value.isEmpty()){
                Toast.makeText(context,"Please fill all Form!",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            onEdit.invoke(value)
            dialog.dismiss()
        }

        val cancel : Button = v.findViewById(R.id.button_cancel)
        cancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setView(v)
        dialog.setCancelable(false)
        dialog.show()
    }
}