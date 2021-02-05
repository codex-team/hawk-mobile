package so.codex.hawk.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.drawer
import kotlinx.android.synthetic.main.activity_main.project_recycler
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_main.view_navigation
import kotlinx.android.synthetic.main.activity_main.workspace_recycler
import kotlinx.android.synthetic.main.main_header.user_icon
import kotlinx.android.synthetic.main.main_header.username
import so.codex.hawk.R
import so.codex.hawk.custom.views.SquircleDrawable
import so.codex.hawk.ui.data.UiMainViewModel
import so.codex.hawk.ui.main.projectlist.ProjectAdapter
import so.codex.hawk.ui.main.workspacelist.WorkspaceAdapter
import timber.log.Timber

/**
 * Main application class.
 * Will be used only after successful authorization.
 */
class MainActivity : AppCompatActivity(), WorkspaceAdapter.OnWorkspaceClickListener {
    /**
     * @property projectAdapter for projects list.
     */
    private val projectAdapter = ProjectAdapter()

    /**
     * @property workspaceAdapter for workspace list.
     */
    private val workspaceAdapter = WorkspaceAdapter(this)

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
        initUi()
        viewModel.observeUiModels().observe(this, ::handleUiModels)
        viewModel.observeUiProjects().observe(this, ::handleUiProjects)
    }

    private fun handleUiProjects(projects: List<UiProject>) {
        projectAdapter.submitList(projects)
    }

    /**
     * Handle ui models from ViewModel, show different views by [model]
     *
     * @param model Representation of model with information to display in views
     */
    private fun handleUiModels(model: UiMainViewModel) {
        toolbar.title = model.title
        search.update(model.searchViewModel)
        if (model.showLoading) {
            // do some staff for showing loading
            Timber.i("show loading")
        } else {
            projectAdapter.submitList(model.projects)
            workspaceAdapter.submitList(model.workspaces)
            // Debug version user account
            val src = BitmapFactory.decodeResource(resources, R.drawable.auth_bg)
            user_icon.setImageDrawable(SquircleDrawable(src))
            username.text = "temnik15@bk.ru"
        }
    }

    /**
     * Screen initialization.
     */
    private fun initUi() {
        project_recycler.layoutManager = LinearLayoutManager(this)
        project_recycler.adapter = projectAdapter
        initToolbar()
        initDrawer()
    }


    /**
     * init custom toolbar
     */
    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            if (!drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.openDrawer(GravityCompat.START)
            }
        }
    }

    private fun initDrawer() {
        // This needs for show icon wihout black tint
        view_navigation.itemIconTintList = null
        workspace_recycler.layoutManager = LinearLayoutManager(this)
        workspace_recycler.adapter = workspaceAdapter
    }
}