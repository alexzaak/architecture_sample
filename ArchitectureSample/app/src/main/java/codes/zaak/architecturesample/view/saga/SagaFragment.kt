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
import codes.zaak.architecturesample.viewmodel.SagaListViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_saga.*
import timber.log.Timber
import javax.inject.Inject

class SagaFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory
    private lateinit var listViewModel: SagaListViewModel

    var adapter = SagaAdapter()

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

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)

        this.listViewModel = ViewModelProviders.of(this, viewModelFactory).get(SagaListViewModel::class.java)
        this.listViewModel.loadSagaList()
    }

    override fun onDetach() {
        super.onDetach()
        this.listViewModel.disposeElements()
    }

    private fun initObserver() {
        this.listViewModel.result().observe(this, Observer {
            Timber.d(it.toString())
            it.let { result ->
                adapter.addSagaList(result)
                recycler.adapter = adapter
            }
        })

        this.listViewModel.error().observe(this, Observer {
            Toast.makeText(this.context, it, Toast.LENGTH_LONG).show()
        })

        this.listViewModel.loader().observe(this, Observer {
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

        refresh.setOnRefreshListener { this.listViewModel.loadSagaList() }

        this.adapter.itemSelected().subscribe { it -> this.addFragment(it.id) }
    }

    private fun addFragment(sagaId: Int) {
        startActivity(CharacterActivity.newIntent(this.requireContext(), sagaId))
    }

    private fun makeToast(message: String) {
        Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
    }

    companion object {
         const val TAG: String = "saga_fragment"
    }
}
