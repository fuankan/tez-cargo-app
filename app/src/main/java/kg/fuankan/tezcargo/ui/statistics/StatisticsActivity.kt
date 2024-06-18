package kg.fuankan.tezcargo.ui.statistics

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.design2.chili2.extensions.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kg.fuankan.tezcargo.data.models.AccountingType
import kg.fuankan.tezcargo.databinding.ActivityStatisticsBinding
import kg.fuankan.tezcargo.domain.model.DeliveryEvent
import kg.fuankan.tezcargo.extensions.collectFlow
import kg.fuankan.tezcargo.ui.base.BaseActivity
import androidx.browser.customtabs.CustomTabsIntent

@AndroidEntryPoint
class StatisticsActivity : BaseActivity<StatisticsVM, ActivityStatisticsBinding>(
    StatisticsVM::class.java,
    ActivityStatisticsBinding::inflate
) {

    private var shouldLaunchUrl = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collectFlows()
        setupViews()
    }

    private fun setupViews() {
        with(vb) {
            icvAboutDrivers.setOnSingleClickListener {
                vm.getAccounting(AccountingType.DRIVER_BASIC.name)
            }

            icvAboutDriversDeliveries.setOnSingleClickListener {
                vm.getAccounting(AccountingType.DRIVER_DELIVERIES.name)
            }

            icvAboutDeliveries.setOnSingleClickListener {
                vm.getAccounting(AccountingType.DELIVERY.name)
            }

            icvAboutStorage.setOnSingleClickListener {
                vm.getAccounting(AccountingType.STORAGE.name)
            }

            ivBack.setOnSingleClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    override fun collectFlows() {
        super.collectFlows()
        collectFlow(vm.event) {
            when (it) {
                is DeliveryEvent.AccountingFetched -> {
                    it.url?.let { url ->
                        shouldLaunchUrl = true
                        openUrl(url)
                    }
                }
                else -> {}
            }
        }
    }

    private fun openUrl(url: String) {
        if (shouldLaunchUrl) {
            shouldLaunchUrl = false
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            customTabsIntent.launchUrl(this, Uri.parse(url))
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, StatisticsActivity::class.java))
        }
    }
}