package so.codex.hawk

import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.auth_form

/**
 * Class for the login screen
 */
class LoginActivity : AppCompatActivity() {
    /**
     * The method is designed to initialize the activity (setting the root view
     * element and other ui elements).
     *
     * @param savedInstanceState This argument is supplied by the system.
     *                           An instance of the Bundle class is used to restore the required
     *                           values when re-creating an activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setAuthFormWidth()
    }

    /**
     * The method of adjusting the authorization form depending on the user's screen size.
     */
    private fun setAuthFormWidth() {
        val defaultWidth = resources.getInteger(R.integer.auth_form_default_width)
        val authWidth = (resources.displayMetrics.density * defaultWidth).toInt()
        auth_form.post {
            if (auth_form.width > authWidth) {
                auth_form.layoutParams =
                    FrameLayout.LayoutParams(
                        authWidth,
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        Gravity.CENTER_HORIZONTAL
                    )
            }
        }
    }
}
