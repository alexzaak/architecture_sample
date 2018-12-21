package codes.zaak.architecturesample.view.saga

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
import codes.zaak.architecturesample.view.character.CharacterActivity
import codes.zaak.architecturesample.viewmodel.AppViewModelFactory
import codes.zaak.architecturesample.viewmodel.SagaViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_saga.*
import timber.log.Timber
import javax.inject.Inject

class SagaFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory
    private lateinit var viewModel: SagaViewModel

    var adapter = SagaAdapter(ArrayList())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_saga, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.initObserver()
        this.initUi()
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)

        this.viewModel = ViewModelProviders.of(this, viewModelFactory).get(SagaViewModel::class.java)
        this.viewModel.loadSagaList()
    }

    override fun onDetach() {
        super.onDetach()
        this.viewModel.disposeElements()
    }

    private fun initObserver() {
        this.viewModel.result().observe(this, Observer {
            Timber.d(it.toString())
            it.let { result ->
                adapter.addSagaList(result)
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
        val gridLayoutManager = GridLayoutManager(this.context, 1)
        gridLayoutManager.orientation = RecyclerView.VERTICAL
        recycler.apply {
            setHasFixedSize(true)
            layoutManager = gridLayoutManager
            itemAnimator = DefaultItemAnimator()
        }

        refresh.setOnRefreshListener { this.viewModel.loadSagaList() }

        this.adapter.itemSelected().subscribe { it -> this.addFragment(it.id.toString()) }
    }

    private fun addFragment(sagaId: String) {
        startActivity(CharacterActivity.newIntent(this.requireContext(), sagaId))
    }

    private fun makeToast(message: String) {
        Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
    }
}
