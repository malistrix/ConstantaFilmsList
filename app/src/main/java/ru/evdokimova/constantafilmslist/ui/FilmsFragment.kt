package ru.evdokimova.constantafilmslist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.evdokimova.constantafilmslist.R
import ru.evdokimova.constantafilmslist.databinding.FragmentFilmsBinding
import ru.evdokimova.constantafilmslist.utils.Resource


@AndroidEntryPoint
class FilmsFragment : Fragment() {

    private var _binding: FragmentFilmsBinding? = null
    private val mBinding get() = requireNotNull(_binding)

    private val viewModel by viewModels<FilmsViewModel>()

    private val filmsAdapter = FilmsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmsBinding.inflate(inflater, container, false)
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initObserveFilmsLiveData()
        initSwipeRefresh()
    }

    private fun initSwipeRefresh() {
        mBinding.swipeRefresh.setOnRefreshListener {
            viewModel.updateFilmsLiveData()
        }
    }

    private fun initAdapter() {
        filmsAdapter.setOnItemClickListener {
            Toast.makeText(
                requireContext(),
                getString(R.string.filmPressed, it.title),
                Toast.LENGTH_SHORT
            )
                .show()
        }
        mBinding.filmsRv.adapter = filmsAdapter
    }

    private fun initObserveFilmsLiveData() {
        viewModel.filmsLiveData.observe(viewLifecycleOwner) { filmsResource ->
            when (filmsResource) {
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), getText(R.string.loading), Toast.LENGTH_SHORT)
                        .show()
                }
                is Resource.Success -> {
                    Toast.makeText(requireContext(), getText(R.string.ready), Toast.LENGTH_SHORT)
                        .show()
                    filmsAdapter.submitData(filmsResource.data!!)
                    filmsAdapter.notifyDataSetChanged()
                    mBinding.swipeRefresh.isRefreshing = false
                }
                is Resource.Error -> {
                    mBinding.swipeRefresh.isRefreshing = false
                    Toast.makeText(
                        requireContext(),
                        filmsResource.message ?: getText(R.string.error),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
    }
}