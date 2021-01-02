package so.codex.hawk.custom.views.badge

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.badge_layout.view.tv_badge_counter
import so.codex.hawk.R
import timber.log.Timber

/**
 * View for showing short number of something. View have rounded background
 */
class BadgeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    companion object {
        /**
         * Default value for count. If count was equals [UNDEFINED_COUNT] then view should be gone.
         */
        const val UNDEFINED_COUNT = -1L
    }

    /**
     * This is number that to display in text
     */
    private var count: Long = UNDEFINED_COUNT

    /**
     * Display short number with suffix of count
     */
    private var text: String = ""

    /**
     * Inflate xml in view and set background from resource
     */
    init {
        inflate(context, R.layout.badge_layout, this)
        setBackgroundResource(R.drawable.badge_background)
    }

    /**
     * Update view by [BadgeViewModel] for update current information. If model have Default count,
     * then view is gone
     * @param model Model for displaying information
     */
    fun update(model: BadgeViewModel) {
        visibility = if (model.text.isEmpty() || model.count == UNDEFINED_COUNT) {
            Timber.w("Try to show badge without text or count is undefined")
            View.GONE
        } else {
            View.VISIBLE
        }
        count = model.count
        text = model.text
        tv_badge_counter.text = text
        tv_badge_counter.setTextColor(ContextCompat.getColor(context, model.textColor))
    }
}