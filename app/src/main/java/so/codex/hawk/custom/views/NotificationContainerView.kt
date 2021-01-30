package so.codex.hawk.custom.views

import android.app.Activity
import android.content.Context
import android.graphics.Outline
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.transition.Fade
import android.transition.TransitionManager
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewOutlineProvider
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import so.codex.hawk.R
import so.codex.hawk.notification.model.NotificationModel
import timber.log.Timber

/**
 * Container of notification, that can show notification by updating model of notification
 * [NotificationModel].
 *
 * Simple extension of [FrameLayout] for displaying notification.
 */
class NotificationContainerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    /**
     * @property density Screen density for calculating size of margins, padding and etc.
     */
    private val density = context.resources.displayMetrics.density

    /**
     * @property notificationHandler The handler that can processing delayed task for hide
     *                               notification.
     */
    private val notificationHandler = Handler(Looper.getMainLooper())

    /**
     * @property DEFAULT_SHOW_PERIOD Period of displaying a single notification (in milliseconds).
     */
    private val DEFAULT_SHOW_PERIOD = 5000L

    /**
     * @property currentNotification contains the currently displayed notification.
     */
    private var currentNotification: FrameLayout? = null

    /**
     * Calculate margins and padding for container.
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val params = layoutParams
        if (statusBarIsVisible(context)) {
            if (params is MarginLayoutParams) {
                params.setMargins(0.toDp(), getStatusBarHeight(), 0.toDp(), 0.toDp())
            }
        }
        setPadding(16.toDp())

        clipToPadding = false
        clipChildren = false
    }

    /**
     * Check if we don't placed container under status bar by checking attribute.
     * [WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS]
     *
     * @param context Use for getting attributes from [android.view.Window].
     *
     * @return return true if we cannot place items under status bar else false.
     */
    private fun statusBarIsVisible(context: Context): Boolean {
        Timber.e("#info $fitsSystemWindows")
        return if (!fitsSystemWindows) {
            val activity = if (context is Fragment && context.activity != null)
                context.activity
            else if (context is Activity) {
                context
            } else
                null
            activity?.let {
                (
                    it.window.attributes.flags
                        and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    ) == WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            } ?: false
        } else {
            fitsSystemWindows
        }
    }

    /**
     * Get height of status bar.
     * @return Height of status bar in pixel.
     */
    private fun getStatusBarHeight(): Int {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            return getDimenInDp(resourceId)
        }
        return 0
    }

    /**
     * Get size in dp from dimen resources.
     *
     * @param dimenRes resource from dimens.
     *
     * @return size from resource in pixel.
     */
    private fun getDimenInDp(@DimenRes dimenRes: Int): Int =
        context.resources.getDimensionPixelSize(dimenRes)

    /**
     * Create notification and inflate the container with the contents by [model].
     */
    fun updateNotification(model: NotificationModel) {
        Timber.i("New notification received.")
        val notification = createNotificationView(model)
        if (currentNotification != null) {
            Timber.e("Forced replacement of notification!")
            hideNotification()
        }
        TransitionManager.beginDelayedTransition(this, Fade())
        addView(notification)
        notificationHandler.postDelayed(::hideNotification, DEFAULT_SHOW_PERIOD)
        visibility = View.VISIBLE
        currentNotification = notification
    }

    /**
     * Internal function for hide notification by time or click on them.
     */
    private fun hideNotification() {
        if (currentNotification != null) {
            notificationHandler.removeCallbacksAndMessages(null)
            TransitionManager.beginDelayedTransition(this, Fade())
            removeView(currentNotification)
            visibility = View.GONE
            currentNotification = null
        }
    }

    /**
     * Generate layout params for text in notification.
     * @return layout params for text.
     */
    private fun getNotificationTextLayoutParams(): LayoutParams {
        return LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
    }

    /**
     * Setup layout params for container of notification.
     */
    private fun FrameLayout.setupLayoutParams() {
        layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
        setPadding(16.toDp())
    }

    /**
     * Extension for converting from size in dp to pixels
     * @return size in pixels
     */
    private fun Int.toDp(): Int = (this * density).toInt()

    /**
     * Extension for converting from size in dp to pixels
     * @return size in pixels
     */
    private fun Int.toDpFloat() = this * density

    /**
     * Method for creating and configuring a notification view.
     *
     * @param model Notification data model.
     *
     * @return ready-made notification view to display.
     */
    private fun createNotificationView(model: NotificationModel): FrameLayout {
        val notification = FrameLayout(context)
        notification.setupLayoutParams()
        notification.setBackgroundColor(
            ContextCompat.getColor(
                context,
                R.color.notificationBackgroundColor
            )
        )
        notification.elevation = 8.toDpFloat()
        notification.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                val rect = Rect()
                view.getDrawingRect(rect)
                outline.setRoundRect(rect, 10.toDpFloat())
            }
        }
        notification.clipToOutline = true
        val messageView = createMessageView(model)
        notification.addView(messageView)
        notification.setOnClickListener {
            hideNotification()
        }
        return notification
    }

    /**
     * Method for creating and configuring a message TextView.
     *
     * @param model Notification data model.
     *
     * @return ready-made TextView message to insert into the parent.
     */
    private fun createMessageView(model: NotificationModel): TextView {
        val message = TextView(context)
        message.text = model.text
        message.typeface = ResourcesCompat.getFont(context, R.font.roboto_medium)
        message.setTextColor(ContextCompat.getColor(context, R.color.notificationTextErrorColor))
        message.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14F)
        message.layoutParams = getNotificationTextLayoutParams()
        message.gravity = Gravity.CENTER
        return message
    }
}
