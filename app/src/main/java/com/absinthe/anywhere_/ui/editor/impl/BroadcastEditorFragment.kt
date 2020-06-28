package com.absinthe.anywhere_.ui.editor.impl

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.absinthe.anywhere_.AnywhereApplication
import com.absinthe.anywhere_.R
import com.absinthe.anywhere_.adapter.card.ExtrasAdapter
import com.absinthe.anywhere_.constants.GlobalValues
import com.absinthe.anywhere_.databinding.EditorBroadcastBinding
import com.absinthe.anywhere_.databinding.LayoutHeaderExtrasBinding
import com.absinthe.anywhere_.model.ExtraBean
import com.absinthe.anywhere_.model.TYPE_STRING
import com.absinthe.anywhere_.model.database.AnywhereEntity
import com.absinthe.anywhere_.ui.editor.BaseEditorFragment
import com.absinthe.anywhere_.utils.AppUtils
import com.absinthe.anywhere_.utils.ShortcutsUtils
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

class BroadcastEditorFragment : BaseEditorFragment() {

    private lateinit var binding: EditorBroadcastBinding
    private val adapter = ExtrasAdapter()

    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = EditorBroadcastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView() {
        item.let {
            val extraBean: ExtraBean? = try {
                Gson().fromJson<ExtraBean>(it.param1, ExtraBean::class.java)
            } catch (e: JsonSyntaxException) {
                null
            }

            adapter.apply {
                animationEnable = true
                val headerBinding = LayoutHeaderExtrasBinding.inflate(layoutInflater)
                addHeaderView(headerBinding.root)

                headerBinding.ibAdd.setOnClickListener {
                    val item = ExtraBean.ExtraItem(TYPE_STRING, "", "")
                    addData(0, item)
                }
                setOnItemChildClickListener { _, view, position ->
                    if (view.id == R.id.ib_delete) {
                        removeAt(position)
                    }
                }
            }

            binding.apply {
                tietAppName.setText(it.appName)
                tietDescription.setText(it.description)
                rvExtras.apply {
                    adapter = this@BroadcastEditorFragment.adapter
                }
                extraBean?.apply {
                    tietIntentAction.setText(action)
                    tietIntentData.setText(data)
                    adapter.setList(extras)
                }
            }
        }
    }

    override fun tryingRun() {
        val ae = AnywhereEntity(item).apply {
            val extras = adapter.data.filter { it.key.isNotBlank() && it.value.isNotBlank() }
            val extraBean = ExtraBean(
                    action = binding.tietIntentAction.text.toString(),
                    data = binding.tietIntentData.text.toString(),
                    extras = extras
            )
            param1 = Gson().toJson(extraBean)
        }
        AppUtils.openAnywhereEntity(requireContext(), ae)
    }

    override fun doneEdit(): Boolean {
        if (binding.tietAppName.text.isNullOrBlank()) {
            binding.tilAppName.error = getString(R.string.bsd_error_should_not_empty)
            return false
        }

        val ae = AnywhereEntity(item).apply {
            appName = binding.tietAppName.text.toString()
            description = binding.tietDescription.text.toString()

            val extras = adapter.data.filter { it.key.isNotBlank() && it.value.isNotBlank() }
            val extraBean = ExtraBean(
                    action = binding.tietIntentAction.text.toString(),
                    data = binding.tietIntentData.text.toString(),
                    extras = extras
            )
            param1 = Gson().toJson(extraBean)
        }

        if (isEditMode && ae == item) return true

        if (isEditMode) {
            if (ae.appName != item.appName) {
                if (GlobalValues.shortcutsList.contains(ae.id)) {
                    if (AppUtils.atLeastNMR1()) {
                        ShortcutsUtils.updateShortcut(ae)
                    }
                }
            }
            AnywhereApplication.sRepository.update(ae)
        } else {
            AnywhereApplication.sRepository.insert(ae)
        }

        return true
    }
}