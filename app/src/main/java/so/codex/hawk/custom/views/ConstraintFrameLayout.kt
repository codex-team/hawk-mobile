package so.codex.hawk.custom.views

import android.content.Context
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import so.codex.hawk.R

/**
 * Modified FrameLayout that can constrain itself in size.
 *
 * @param context This parameter is supplied by the system when creating a view.
 *                It must be provided from the init block.
 * @param attrs This parameter is supplied by the system when creating a view.
 *              It must be provided from the init block. The parameter contains
 *              many attributes set in the xml view.
 * @param defStyleAttr An attribute in the current theme that contains a reference to a style
 *                     resource that supplies default values for the view.
 *                     Can be 0 to not look for defaults.
 */
class ConstraintFrameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    /**
     * @property maxWidth Limiting the maximum width.
     */
    private val maxWidth: Int

    /**
     * @property maxHeight Limiting the maximum height.
     */
    private val maxHeight: Int

    /**
     * @property minWidth Limiting the minimum width.
     */
    private val minWidth: Int

    /**
     * @property minHeight  Limiting the minimum height.
     */
    private val minHeight: Int

    /**
     * @property NO_VALUE variable stub. Used if no limit is specified.
     */
    private val NO_VALUE = -1

    /**
     * Initialization method. Used to get the given constraints from the attributes.
     */
    init {
        val arrayAttrs = context.obtainStyledAttributes(
            attrs,
            R.styleable.ConstraintFrameLayout,
            EditorInfo.TYPE_NULL,
            EditorInfo.TYPE_NULL
        )
        minWidth =
            arrayAttrs.getDimensionPixelSize(R.styleable.ConstraintFrameLayout_minWidth, NO_VALUE)
        minHeight =
            arrayAttrs.getDimensionPixelSize(R.styleable.ConstraintFrameLayout_minHeight, NO_VALUE)
        maxWidth =
            arrayAttrs.getDimensionPixelSize(R.styleable.ConstraintFrameLayout_maxWidth, NO_VALUE)
        maxHeight =
            arrayAttrs.getDimensionPixelSize(R.styleable.ConstraintFrameLayout_maxHeight, NO_VALUE)
        arrayAttrs.recycle()
    }

    /**
     * @see onMeasure method description in android.view.View.
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val prefWidth = MeasureSpec.getSize(widthMeasureSpec)
        val prefHeight = MeasureSpec.getSize(heightMeasureSpec)
        val width =
            if (minWidth != NO_VALUE && prefWidth < minWidth) {
                minWidth
            } else if (maxWidth != NO_VALUE && prefWidth > maxWidth) {
                maxWidth
            } else {
                prefWidth
            }
        val height =
            if (minHeight != NO_VALUE && prefHeight < minHeight) {
                minHeight
            } else if (maxHeight != NO_VALUE && prefHeight > maxHeight) {
                maxHeight
            } else {
                prefHeight
            }
        super.onMeasure(
            MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST),
            MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST)
        )
    }
}
