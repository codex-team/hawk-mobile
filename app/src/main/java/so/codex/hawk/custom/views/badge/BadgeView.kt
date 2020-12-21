package so.codex.hawk.custom.views.badge

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.badge_layout.view.tv_badge_counter
import so.codex.hawk.R
import timber.log.Timber

class BadgeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    companion object {
        const val UNDEFINED_COUNT = -1
    }

    private var count: Int = UNDEFINED_COUNT
    private var text: String = ""

    init {
        inflate(context, R.layout.badge_layout, this)
        setBackgroundResource(R.drawable.badge_background)

        /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            outlineSpotShadowColor = ContextCompat.getColor(context, R.color.badgeShadowColor)
        }*/
    }

    fun update(model: BadgeViewModel) {
        visibility = if (model.text.isEmpty()) {
            Timber.w("Try to show badge without text")
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