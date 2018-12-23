package codes.zaak.architecturesample.view.character


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import codes.zaak.architecturesample.R
import codes.zaak.architecturesample.repository.model.response.Saga
import codes.zaak.architecturesample.viewmodel.AppViewModelFactory
import codes.zaak.architecturesample.viewmodel.CharacterViewModel
import codes.zaak.architecturesample.viewmodel.SagaViewModel
import com.squareup.picasso.Picasso
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_character.*
import timber.log.Timber
import javax.inject.Inject


class CharacterFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory
    private lateinit var characterViewModel: CharacterViewModel
    private lateinit var sagaViewModel: SagaViewModel

    private var adapter = CharacterAdapter()
    private var sagaId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_character, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.initObserver()
        this.initUi()
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)

        this.sagaId = arguments?.getInt(ARG_SAGA_ID, 0)!!

        this.characterViewModel = ViewModelProviders.of(this, viewModelFactory).get(CharacterViewModel::class.java)
        this.sagaViewModel = ViewModelProviders.of(this, viewModelFactory).get(SagaViewModel::class.java)
        this.characterViewModel.loadCharacters(this.sagaId)
        this.sagaViewModel.loadSaga(this.sagaId)
    }

    override fun onDetach() {
        super.onDetach()
        this.characterViewModel.disposeElements()
    }

    private fun initObserver() {
        this.characterViewModel.result().observe(this, Observer {
            Timber.d(it.toString())
            it.let { result ->
                adapter.addCharacterList(result)
                recycler.adapter = adapter
            }
        })

        this.characterViewModel.error().observe(this, Observer {
            Toast.makeText(this.context, it, Toast.LENGTH_LONG).show()
        })

        this.characterViewModel.loader().observe(this, Observer {
            it.let { isLoading ->
                refresh.isRefreshing = isLoading
                Toast.makeText(this.context, isLoading.toString(), Toast.LENGTH_LONG).show()
            }
        })

        this.sagaViewModel.result().observe(this, Observer {
            it.let { saga ->
                Picasso.get().load(saga.image).into(header_image)
                this.setupToolBar(saga)
            }
        })
    }

    private fun setupToolBar(saga: Saga) {
        (this.activity as AppCompatActivity).apply {
            setSupportActionBar(main_toolbar)
            supportActionBar?.title = saga.name
        }
    }

    private fun initUi() {
        val gridLayoutManager = GridLayoutManager(this.context, 2)
        gridLayoutManager.orientation = RecyclerView.VERTICAL
        recycler.apply {
            setHasFixedSize(true)
            layoutManager = gridLayoutManager
            itemAnimator = DefaultItemAnimator()
        }

        refresh.setOnRefreshListener { this.characterViewModel.loadCharacters(this.sagaId) }
    }

    companion object {
        const val TAG_CHARACTER_FRAGMENT: String = "character_fragment"
        private const val ARG_SAGA_ID: String = "saga_id"

        fun create(sagaId: Int): CharacterFragment {
            val fragment = CharacterFragment()
            val args: Bundle = Bundle()
            args.putInt(ARG_SAGA_ID, sagaId)
            fragment.arguments = args
            return fragment
        }
    }
}
