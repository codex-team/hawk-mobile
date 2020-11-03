package so.codex.hawk.custom.views

import android.content.Context
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.title_edit_text.view.*
import so.codex.hawk.R

/**
 * Custom view for use in different screens
 * of the application. This view is a vertically
 * arranged [android.widget.TextView] and [android.widget.EditText].
 *
 * This view supports setting attributes from xml.
 * The following attributes are available:
 *      1. android:text - sets title.
 *      2. android:textColor - sets title color.
 *      3. android:inputType - sets inputType for EditText.
 */
class TitleEditText(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    /**
     * @property DEFAULT_TITLE_COLOR_RESOURCE contains the color ID from the application resources.
     *                                        This color will be set if there is no attribute to set
     *                                        the color in the xml markup.
     */
    private val DEFAULT_TITLE_COLOR_RESOURCE = R.color.colorDefaultTitleEditText

    // Initializing internals on creation
    init {
        initComponent(context, attrs)
    }

    /**
     * Initializing internal  components of view.
     *
     * @param context This parameter is supplied by the system when creating a view.
     *                It must be provided from the init block.
     * @param attrs This parameter is supplied by the system when creating a view.
     *              It must be provided from the init block. The parameter contains
     *              many attributes set in the xml view.
     */
    private fun initComponent(context: Context, attrs: AttributeSet) {
        //inflating root view
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.title_edit_text, this)
        //Getting the set attributes in the xml
        val arrayAttrs = context.obtainStyledAttributes(
            attrs,
            R.styleable.TitleEditText,
            EditorInfo.TYPE_NULL,
            EditorInfo.TYPE_NULL
        )

        /* Setting attributes for TextView
           The defTitleColor variable specifies the default title color.
         */
        val defTitleColor = ContextCompat.getColor(context, DEFAULT_TITLE_COLOR_RESOURCE)
        val title = arrayAttrs.getString(R.styleable.TitleEditText_android_text) ?: ""
        val titleColor =
            arrayAttrs.getInt(R.styleable.TitleEditText_android_textColor, defTitleColor)
        tv_title.text = title
        tv_title.setTextColor(titleColor)

        /* Setting attributes for EditText
           If there is no attribute in xml for setting inputType,
           it will be installed InputType.TYPE_CLASS_TEXT.
         */
        val inputType = arrayAttrs.getInt(
            R.styleable.TitleEditText_android_inputType,
            InputType.TYPE_CLASS_TEXT
        )
        edit_text.inputType = inputType
        //Releasing Attributes
        arrayAttrs.recycle()
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