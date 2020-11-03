package so.codex.hawk.custom.views

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.title_edit_text.view.*
import so.codex.hawk.R

/**
 *   Custom view for use in different screens
 *   of the application. This view is a vertically
 *   arranged [android.widget.TextView] and [android.widget.EditText].
 */
class TitleEditText(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    // Initializing internals on creation
    init {
        initComponent()
    }

    /**
     * Initializing internals on creation
     * The view initialization method on creation.
     */
    private fun initComponent() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.title_edit_text, this)
    }

    /**
     * @return [CharSequence] Method for getting text from [android.widget.EditText].
     */
    fun getText() = tv_title.text

    /**
     * Title setting method for [TitleEditText]
     * @param newTitle The method accepts any type that implements the [CharSequence] interface.
     *                 For example it can be of type String.
     */
    fun setTitle(newTitle: CharSequence) {
        tv_title.text = newTitle
    }

    /**
     * Method for setting the input type (email, password, and others) for the [android.widget.EditText].
     * For example, the combination (Kotlin language):
     * "[android.text.InputType.TYPE_CLASS_TEXT] or [android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD]"
     * will set the text password input with the display of characters as hidden.
     *
     * @param type The argument must be one of the android.text.InputType interface constants!
     */
    fun setInputType(type: Int) {
        edit_text.inputType = type
    }

    /**
     * The callback setter method to change the text in the [android.widget.EditText].
     * @param listener The method accepts an object that implements the [TextWatcher] interface.
     */
    fun addOnTextChangedListener(listener: TextWatcher) {
        edit_text.addTextChangedListener(listener)
    }
}