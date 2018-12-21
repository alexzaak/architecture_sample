package codes.zaak.architecturesample.view

import android.os.Bundle
import codes.zaak.architecturesample.R
import codes.zaak.architecturesample.view.saga.SagaFragment
import dagger.android.AndroidInjection


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val sagaFragment = SagaFragment()
            supportFragmentManager.beginTransaction().replace(R.id.container, sagaFragment)
                .addToBackStack(null).commit()
        }
    }
}
