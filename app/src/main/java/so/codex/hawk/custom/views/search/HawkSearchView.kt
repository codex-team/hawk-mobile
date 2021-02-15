package so.codex.hawk.custom.views.search

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import so.codex.hawk.R

/**
 * View for searching items
 */
class HawkSearchView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    /**
     * @property queryEditText view where user can search something
     */
    private val queryEditText: EditText

    /**
     * @property textChangeCallback current listener of the view
     */
    private var textChangeCallback: (String) -> Unit = {}

    /**
     * Init block and add listeners
     */
    init {
        val view = View.inflate(context, R.layout.search_view_layout, this)

        queryEditText = view.findViewById(R.id.edit_input)
        queryEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textChangeCallback(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    /**
     * Set listener for searching something
     *
     * @param textChange current listener of the view
     */
    fun setOnQueryTextListener(textChange: (String) -> Unit) {
        this.textChangeCallback = textChange
    }

    /**
     * Update field in view by [model]
     * @param model Model that contain information of search view
     */
    fun update(model: HawkSearchUiViewModel) {
        queryEditText.setText(model.text)
        queryEditText.hint = model.hint
        textChangeCallback = model.listener
    }
}