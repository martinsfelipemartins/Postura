package br.com.postura.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import br.com.postura.R
import kotlinx.android.synthetic.main.welcome_dialog.button_understand

class WelcomeDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dialog?.window?.setBackgroundDrawableResource(R.drawable.corner_dialog)
        return inflater.inflate(R.layout.welcome_dialog, container, false)
    }

    override fun onStart() {
        super.onStart()
        closeDialog()
    }

    private fun closeDialog() {
        button_understand.setOnClickListener {
            dismiss()
        }
    }
}
