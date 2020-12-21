package so.codex.hawk.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.btn_refresh
import kotlinx.android.synthetic.main.activity_main.search_view
import so.codex.hawk.R
import so.codex.hawk.custom.views.search.HawkSearchViewModel
import timber.log.Timber

/**
 * Main application class.
 * Will be used only after successful authorization.
 */
class MainActivity : AppCompatActivity() {

    /**
     * ViewModel handle models from business logic and convert to ui models
     */
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

        btn_refresh.setOnClickListener {
            viewModel.submitEvent(MainViewModel.UiEvent.Refresh)
        }

        viewModel.observeUiModels().observe(this, ::handleUiModels)
    }

    /**
     * Handle ui models from ViewModel, show different views by [model]
     *
     * @param model Representation of model with information to display in views
     */
    private fun handleUiModels(model: UiMainViewModel) {
        if (model.showLoading) {
            // do some staff for showing loading
            Timber.i("show loading")
        } else {
            // do some staff for workspace
            model.workspaces.forEach {
                Timber.i("workspace: $it")
            }
        }
    }
}