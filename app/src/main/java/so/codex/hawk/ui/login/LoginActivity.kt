package so.codex.hawk.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_login.auth_form
import kotlinx.android.synthetic.main.activity_login.btn_enter
import kotlinx.android.synthetic.main.activity_login.edit_text_email
import kotlinx.android.synthetic.main.activity_login.edit_text_password
import so.codex.hawk.R
import so.codex.hawk.domain.login.LoginEvent
import so.codex.hawk.notification.domain.NotificationContainer
import so.codex.hawk.ui.main.MainActivity

/**
 * Class for the login screen
 */
class LoginActivity : AppCompatActivity() {
    /**
     * @property loginViewModel for handling view logic.
     */
    private val loginViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(LoginViewModel::class.java)
    }

    /**
     * @property notificationContainer A container that delegates logic for processing and displaying
     * notifications
     */
    private val notificationContainer = NotificationContainer()

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
        setListeners()
        loginViewModel
            .observeLoginEvent()
            .observe(this) {
                loginEventHandling(it)
            }
    }

    /**
     * Attach container for notification
     */
    override fun onStart() {
        super.onStart()
        notificationContainer.attach(this)
    }

    /**
     * Detach container for notification
     */
    override fun onStop() {
        super.onStop()
        notificationContainer.detach()
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

    /**
     * A method for setting various handlers on view elements.
     */
    private fun setListeners() {
        // Login button
        btn_enter.setOnClickListener {
            val email = edit_text_email.text.toString()
            val password = edit_text_password.text.toString()
            loginViewModel.login(email, password)
        }
    }

    /**
     * Method to handle the login event.
     *
     * @param event One of the logon events. For a complete list of events, see [LoginEvent].
     */
    private fun loginEventHandling(event: LoginEvent) {
        when (event) {
            LoginEvent.SUCCESSFUL_LOGIN -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            LoginEvent.LOGIN_ERROR -> {
                // show error
            }
            LoginEvent.INTERNET_ERROR -> {
                // show error
            }
        }
    }
}
