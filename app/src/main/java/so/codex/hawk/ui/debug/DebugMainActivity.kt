package so.codex.hawk.ui.debug

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.debug_activity_layout.badge
import kotlinx.android.synthetic.main.debug_activity_layout.container
import so.codex.hawk.R
import so.codex.hawk.custom.views.badge.BadgeViewModel

class DebugMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.debug_activity_layout)
        container.clipToOutline = false
        container.clipChildren = false
        badge.update(BadgeViewModel("222k", 222000))
        container.clipToOutline = false
        container.clipChildren = false
        container.invalidate()
        container.invalidateOutline()
        container.requestLayout()
    }
}