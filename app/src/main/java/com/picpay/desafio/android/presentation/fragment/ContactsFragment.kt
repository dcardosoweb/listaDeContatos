package com.picpay.desafio.android.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.StringUtils
import com.picpay.desafio.android.R
import com.picpay.desafio.android.data.service.response.GetUsersResponse
import com.picpay.desafio.android.data.service.response.asListModel
import com.picpay.desafio.android.databinding.FragmentContactsBinding
import com.picpay.desafio.android.presentation.adapter.UserListAdapter
import com.picpay.desafio.android.presentation.viewModel.UserContactsViewModel
import com.picpay.desafio.android.utils.Result
import com.picpay.desafio.android.utils.gone
import com.picpay.desafio.android.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

//TODO verificar o motivo do binding não estar funcionando com o baseFragment
class ContactsFragment : Fragment() {
    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserContactsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            recyclerView.adapter = UserListAdapter()
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
        setupObservers()
    }

    private fun setupObservers(){
        with(viewModel){
            statusGetUsersFromServer.observe(viewLifecycleOwner){ status ->
                checkStatusGetUsersFromServer(status)
            }
            getUsersFromServer()
        }
    }

    private fun checkStatusGetUsersFromServer(status: Result<List<GetUsersResponse>>) {
        with(binding) {
            when (status) {
                is Result.InProgress -> {
                    recyclerView.gone()
                    infoMessage.gone()
                    progressBar.visible()
                }
                is Result.Success -> {
                    progressBar.gone()
                    val contactList = status.data

                    if(contactList.isNullOrEmpty()){
                        infoMessage.text = StringUtils.getString(R.string.empty_contact_list)
                        infoMessage.visible()
                        progressBar.gone()
                    }else {
                        var adapter = UserListAdapter()
                        adapter.submitList(contactList.asListModel())
                        recyclerView.adapter = adapter
                        recyclerView.layoutManager = LinearLayoutManager(context)
                        recyclerView.visible()
                    }
                }
                is Result.Error -> {
                    val exception = status.exception
                    if(exception.message.isNullOrEmpty()){
                        infoMessage.text = StringUtils.getString(R.string.generic_error_message)
                    }else{
                        infoMessage.text = exception.message
                    }
                    infoMessage.visible()
                    progressBar.gone()
                }
                else -> {
                }
            }
        }
    }
}