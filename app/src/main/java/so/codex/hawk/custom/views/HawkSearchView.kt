package so.codex.hawk.custom.views

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import androidx.appcompat.widget.SearchView
import androidx.core.view.updatePadding
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
     * @property listener current listener of the view
     */
    private var listener: SearchView.OnQueryTextListener? = null

    /**
     * Set listener for searching something
     *
     * @param listener current listener of the view
     */
    fun setOnQueryTextListener(listener: SearchView.OnQueryTextListener) {
        this.listener = listener
    }

    /**
     * Init block
     */
    init {
        val view = View.inflate(context, R.layout.search_view_layout, this)
        setOnApplyWindowInsetsListener { v, insets ->
            v.updatePadding(top = insets.systemWindowInsetTop)
            insets
        }

        queryEditText = view.findViewById(R.id.input_et)
        queryEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listener?.onQueryTextChange(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

}