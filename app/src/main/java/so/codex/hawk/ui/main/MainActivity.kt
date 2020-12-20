package so.codex.hawk.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.search_view
import so.codex.hawk.R
import so.codex.hawk.domain.main.MainEvent
import so.codex.hawk.custom.views.search.HawkSearchViewModel
import timber.log.Timber

/**
 * Main application class.
 * Will be used only after successful authorization.
 */
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
    }

    /**
     * The method is designed to initialize the activity (setting the root view element
     * and other ui elements).
     *
     * @param savedInstanceState This argument is supplied by the system.
     *                           An instance of the Bundle class is used to restore the required
     *                           values when re-creating an activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        search_view.update(
            HawkSearchViewModel(
                hint = getString(R.string.search_hint),
                text = "Hello",
            ) {
                Timber.i("text changed $it")
            }
        )

        viewModel.observeMainEvent().observe(this) {
            renderEvent(it)
        }
    }

    private fun renderEvent(event: MainEvent) {
        when (event) {
            is MainEvent.WorkspacesSuccessEvent -> {
                // do some staff too
            }
        }
    }
}
