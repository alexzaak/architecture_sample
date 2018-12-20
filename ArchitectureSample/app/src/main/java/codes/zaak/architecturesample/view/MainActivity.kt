package codes.zaak.architecturesample.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import codes.zaak.architecturesample.R
import codes.zaak.architecturesample.viewmodel.AppViewModelFactory
import codes.zaak.architecturesample.viewmodel.SagaViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject


class MainActivity : AppCompatActivity(), LifecycleOwner {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory
    private lateinit var viewModel: SagaViewModel

    var adapter = SagaAdapter(ArrayList())

    private lateinit var mLifecycleRegistry: LifecycleRegistry

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mLifecycleRegistry = LifecycleRegistry(this)
        mLifecycleRegistry.markState(Lifecycle.State.CREATED)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SagaViewModel::class.java)

        viewModel.loadSagaList()

        viewModel.result().observe(this, Observer {
            Timber.d(it.toString())
            it.let { result ->
                adapter.addSagaList(result)
                recycler.adapter = adapter
            }
        })

        viewModel.error().observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        viewModel.loader().observe(this, Observer {
            it.let { isLoading ->
                refresh.isRefreshing = isLoading
                Toast.makeText(this, isLoading.toString(), Toast.LENGTH_LONG).show()
            }
        })

        val gridLayoutManager = GridLayoutManager(this, 1)
        gridLayoutManager.orientation = RecyclerView.VERTICAL
        recycler.apply {
            setHasFixedSize(true)
            layoutManager = gridLayoutManager
            itemAnimator = DefaultItemAnimator()
        }

        refresh.setOnRefreshListener { this.viewModel.loadSagaList() }
    }

    public override fun onStart() {
        super.onStart()
        mLifecycleRegistry.markState(Lifecycle.State.STARTED)
    }

    override fun getLifecycle(): Lifecycle {
        return mLifecycleRegistry
    }

    override fun onDestroy() {
        viewModel.disposeElements()
        super.onDestroy()
    }
}
