package codes.zaak.architecturesample.view.character


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import codes.zaak.architecturesample.R
import codes.zaak.architecturesample.viewmodel.AppViewModelFactory
import codes.zaak.architecturesample.viewmodel.CharacterViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_saga.*
import timber.log.Timber
import javax.inject.Inject


class CharacterFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory
    private lateinit var viewModel: CharacterViewModel

    var adapter = CharacterAdapter(ArrayList())

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

        val sagaId = arguments?.getString(ARG_SAGA_ID)

        this.viewModel = ViewModelProviders.of(this, viewModelFactory).get(CharacterViewModel::class.java)
        this.viewModel.loadCharacters(sagaId)
    }

    override fun onDetach() {
        super.onDetach()
        this.viewModel.disposeElements()
    }

    private fun initObserver() {
        this.viewModel.result().observe(this, Observer {
            Timber.d(it.toString())
            it.let { result ->
                adapter.addCharacterList(result)
                recycler.adapter = adapter
            }
        })

        this.viewModel.error().observe(this, Observer {
            Toast.makeText(this.context, it, Toast.LENGTH_LONG).show()
        })

        this.viewModel.loader().observe(this, Observer {
            it.let { isLoading ->
                refresh.isRefreshing = isLoading
                Toast.makeText(this.context, isLoading.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun initUi() {
        val gridLayoutManager = GridLayoutManager(this.context, 2)
        gridLayoutManager.orientation = RecyclerView.VERTICAL
        recycler.apply {
            setHasFixedSize(true)
            layoutManager = gridLayoutManager
            itemAnimator = DefaultItemAnimator()
        }

        refresh.setOnRefreshListener { this.viewModel.loadCharacters() }

    }

    companion object {

        const val TAG_CHARACTER_FRAGMENT: String = "character_fragment"
        private const val ARG_SAGA_ID: String = "saga_id"

        fun create(sagaId: String): CharacterFragment {
            val fragment = CharacterFragment()
            val args: Bundle = Bundle()
            args.putString(ARG_SAGA_ID, sagaId)
            fragment.arguments = args
            return fragment
        }
    }
}
