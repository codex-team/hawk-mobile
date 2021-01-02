package so.codex.hawk.ui.debug

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.debug_activity_layout.badge
import kotlinx.android.synthetic.main.debug_activity_layout.container
import so.codex.hawk.R
import so.codex.hawk.custom.views.badge.BadgeViewModel
import so.codex.hawk.utils.ShortNumberUtils

class DebugMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.debug_activity_layout)
        container.clipToOutline = false
        container.clipChildren = false
        val count = 222000L
        badge.update(BadgeViewModel(ShortNumberUtils.convert(count), count))
        container.clipToOutline = false
        container.clipChildren = false
        container.invalidate()
        container.invalidateOutline()
        container.requestLayout()
    }
}