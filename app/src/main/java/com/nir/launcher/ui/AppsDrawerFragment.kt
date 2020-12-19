package com.nir.launcher.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nir.launcher.R
import com.nir.launcher.model.AppData
import com.nir.launcher.viewmodel.AppDrawerViewModel
import com.nir.library.CustomLauncher
import org.koin.android.viewmodel.ext.android.sharedViewModel
import java.util.Locale

class AppsDrawerFragment : Fragment() {

    private lateinit var appsAdapter: AppsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: EditText
    private var appList = mutableListOf<AppData>()
    private lateinit var filteredList: MutableList<AppData>

    private val viewModel: AppDrawerViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_apps_drawer, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        searchView = view.findViewById(R.id.search_Name)

        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        val itemDecor = DividerItemDecoration(context, RecyclerView.VERTICAL)
        recyclerView.addItemDecoration(itemDecor)

        setEventListener()
        registerInstallReceiver()
        registerUninstallReceiver()
        registerObservers()
        viewModel.getAppLaunchers()
    }

    private fun registerObservers() {
        viewModel.appLiveData.observe(viewLifecycleOwner, registerAppDataObserver())
    }

    private fun registerAppDataObserver() = Observer<List<AppData>> { list ->
            appList = list as MutableList<AppData>
            setupRecyclerView(appList)
    }

    private fun setEventListener() {
        searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString() != "") {
                    filteredList = mutableListOf()
                    for (item in appList) {
                        if (item.appName.toLowerCase(Locale.getDefault())
                                .contains(s.toString().toLowerCase(Locale.getDefault()))
                        ) {
                            filteredList.add(item)
                        }
                    }
                    setupRecyclerView(filteredList)
                } else {
                    setupRecyclerView(appList)
                }
            }

            override fun afterTextChanged(s: Editable?) = Unit
        })
    }

    private fun setupRecyclerView(list: List<AppData>) {
        appsAdapter = AppsAdapter(context, list)
        recyclerView.adapter = appsAdapter
        appsAdapter.notifyDataSetChanged()
    }

    private fun registerInstallReceiver() {
        CustomLauncher.registerInstallReceiver(context) { packageName ->
            viewModel.getAppLaunchers()
            Toast.makeText(context, "USER INSTALL : $packageName", Toast.LENGTH_SHORT).show()
        }
    }

    private fun registerUninstallReceiver() {
        CustomLauncher.registerUninstallReceiver(context) { packageName ->
            viewModel.getAppLaunchers()
            Toast.makeText(context, "USER UNINSTALL : $packageName", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        CustomLauncher.unRegisterReceivers(context)
    }
}