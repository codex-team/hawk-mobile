package so.codex.hawk.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.drawer
import kotlinx.android.synthetic.main.activity_main.project_recycler
import kotlinx.android.synthetic.main.activity_main.search
import kotlinx.android.synthetic.main.activity_main.swipeToRefresh
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_main.view_navigation
import kotlinx.android.synthetic.main.activity_main.workspace_recycler
import so.codex.hawk.R
import so.codex.hawk.ui.data.UiMainViewModel
import so.codex.hawk.ui.data.UiProject
import so.codex.hawk.ui.data.UiWorkspace
import so.codex.hawk.ui.main.lists.projectlist.ProjectAdapter
import so.codex.hawk.ui.main.lists.workspacelist.WorkspaceAdapter
import timber.log.Timber

/**
 * Main application class.
 * Will be used only after successful authorization.
 */
class MainActivity : AppCompatActivity() {
    /**
     * @property projectAdapter for projects list.
     */
    private val projectAdapter = ProjectAdapter()

    /**
     * @property workspaceAdapter for workspace list.
     */
    private val workspaceAdapter = WorkspaceAdapter()

    /**
     * ViewModel handle models from business logic and convert to ui models
     */
    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
    }

    private val drawerViewModel: DrawerViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DrawerViewModel::class.java)
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
        mainViewModel.observeUiMainData().observe(this, ::handleUiModels)
        mainViewModel.observeUiProjects().observe(this, ::handleUiProjects)
        mainViewModel.observeSearchUiViewModel().observe(this) {
            search.update(it)
        }
        drawerViewModel.observeUiWorkspace().observe(this, ::handleUiWorkspaces)
    }

    private fun handleUiProjects(projects: List<UiProject>) {
        projectAdapter.submitList(projects)
    }

    private fun handleUiWorkspaces(workspaces: List<UiWorkspace>) {
        workspaceAdapter.submitList(workspaces)
    }

    /**
     * Handle ui models from ViewModel, show different views by [model]
     *
     * @param model Representation of model with information to display in views
     */
    private fun handleUiModels(model: UiMainViewModel) {
        toolbar.title = model.title
        if (model.showLoading) {
            Timber.i("show loading")
        } else {
            Timber.i("show result")
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
        swipeToRefresh.setOnRefreshListener {
            // Debug version
            mainViewModel.submitEvent(MainViewModel.UiEvent.Refresh)
            swipeToRefresh.postDelayed(
                {
                    swipeToRefresh.isRefreshing = false
                },
                1000
            )
        }
    }

    /**
     * Init custom toolbar.
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

    /**
     * Init drawer with navigation view.
     */
    private fun initDrawer() {
        // This needs for show icon without black tint
        view_navigation.itemIconTintList = null
        workspace_recycler.layoutManager = LinearLayoutManager(this)
        workspace_recycler.adapter = workspaceAdapter
    }
}