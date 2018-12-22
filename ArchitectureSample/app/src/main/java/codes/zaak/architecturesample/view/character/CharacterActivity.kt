package codes.zaak.architecturesample.view.character

import android.content.Context
import android.content.Intent
import android.os.Bundle
import codes.zaak.architecturesample.R
import codes.zaak.architecturesample.view.BaseActivity
import dagger.android.AndroidInjection

class CharacterActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)

        if (savedInstanceState == null) {
            val sagaId = intent.getIntExtra(EXTRA_SAGA_ID, 0)

            val characterFragment = CharacterFragment.create(sagaId)
            supportFragmentManager.beginTransaction().replace(R.id.container, characterFragment)
                .addToBackStack(CharacterFragment.TAG_CHARACTER_FRAGMENT).commit()
        }
    }

    companion object {
        const val EXTRA_SAGA_ID: String = "saga_id"

        fun newIntent(context: Context, sagaId: Int): Intent {
           return Intent(context, CharacterActivity::class.java).apply {
                putExtra(EXTRA_SAGA_ID, sagaId)

            }
        }
    }
}
