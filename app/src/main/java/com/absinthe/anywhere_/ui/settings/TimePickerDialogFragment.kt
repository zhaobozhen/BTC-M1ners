package com.absinthe.anywhere_.ui.settings

import android.app.Dialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import com.absinthe.anywhere_.R
import com.absinthe.anywhere_.model.Const
import com.absinthe.anywhere_.model.GlobalValues
import com.absinthe.anywhere_.model.Settings
import com.absinthe.anywhere_.utils.manager.DialogStack
import com.absinthe.anywhere_.view.AnywhereDialogBuilder
import com.absinthe.anywhere_.view.AnywhereDialogFragment
import com.absinthe.anywhere_.view.ObservableTimePickerDialog
import com.absinthe.anywhere_.view.ObservableTimePickerDialog.OnCancelledListener
import com.absinthe.anywhere_.viewbuilder.entity.TimePickerBuilder
import com.google.android.material.button.MaterialButton
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class TimePickerDialogFragment : AnywhereDialogFragment() {

    private lateinit var mBuilder: TimePickerBuilder
    private val format = SimpleDateFormat("HH:mm", Locale.getDefault())

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mBuilder = TimePickerBuilder(requireContext())

        val start = Calendar.getInstance().apply {
            timeInMillis = GlobalValues.sAutoDarkModeStart
        }
        val end = Calendar.getInstance().apply {
            timeInMillis = GlobalValues.sAutoDarkModeEnd
        }

        if (GlobalValues.sAutoDarkModeStart == 0L) {
            mBuilder.btnStart.text = String.format(Locale.getDefault(), "%02d:%02d", 22, 0)
        } else {
            mBuilder.btnStart.text = String.format(Locale.getDefault(), "%02d:%02d", start[Calendar.HOUR_OF_DAY], start[Calendar.MINUTE])
        }
        if (GlobalValues.sAutoDarkModeEnd == 0L) {
            mBuilder.btnEnd.text = String.format(Locale.getDefault(), "%02d:%02d", 7, 0)
        } else {
            mBuilder.btnEnd.text = String.format(Locale.getDefault(), "%02d:%02d", end[Calendar.HOUR_OF_DAY], end[Calendar.MINUTE])
        }

        val listener = View.OnClickListener { v: View ->
            val timePickerDialog = ObservableTimePickerDialog(context,
                    OnTimeSetListener { _: TimePicker?, hourOfDay: Int, minute: Int ->
                        (v as MaterialButton).text = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute)
                        DialogStack.pop()
                    },
                    object : OnCancelledListener {
                        override fun onCancel() {
                            DialogStack.pop()
                        }
                    }, 0, 0, true)
            DialogStack.push(timePickerDialog)
        }
        mBuilder.btnStart.setOnClickListener(listener)
        mBuilder.btnEnd.setOnClickListener(listener)

        return AnywhereDialogBuilder(context).setView(mBuilder.root)
                .setTitle(R.string.dialog_set_dark_mode_period_title)
                .setPositiveButton(R.string.dialog_delete_positive_button) { _: DialogInterface?, _: Int ->
                    try {
                        format.parse(mBuilder.btnStart.text.toString())?.time?.let { GlobalValues.setsAutoDarkModeStart(it) }
                        format.parse(mBuilder.btnEnd.text.toString())?.time?.let { GlobalValues.setsAutoDarkModeEnd(it) }
                        Settings.setTheme(Const.DARK_MODE_AUTO)
                        GlobalValues.setsDarkMode(Const.DARK_MODE_AUTO)
                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }
                }
                .setNegativeButton(R.string.dialog_delete_negative_button, null)
                .create()
    }
}