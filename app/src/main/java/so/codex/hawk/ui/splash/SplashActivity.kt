package so.codex.hawk.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import so.codex.hawk.R
import so.codex.hawk.domain.splash.SplashEvent
import so.codex.hawk.ui.login.LoginActivity
import so.codex.hawk.ui.main.MainActivity

/**
 * Application launch class.
 */
class SplashActivity : AppCompatActivity() {
    /**
     * @property splashViewModel for handling view logic.
     */
    private val splashViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(SplashViewModel::class.java)
    }

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
        setContentView(R.layout.activity_splash)
        splashViewModel
            .observeSplashEvent()
            .observe(this) {
                eventResponse(it)
            }
        splashViewModel.checkingSessionFreshness()
    }

    /**
     * Method to handle the splash event.
     *
     * @param event One of the events on the splash screen.
     *
     * For a complete list of events, see [SplashEvent].
     */
    private fun eventResponse(event: SplashEvent) {
        when (event) {
            SplashEvent.SESSION_EXPIRED -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            SplashEvent.SESSION_ACTIVE -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            SplashEvent.NO_INTERNET -> {
                // show error and btn reload
            }
        }
    }
}
