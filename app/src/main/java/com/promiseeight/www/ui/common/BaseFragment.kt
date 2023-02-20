package com.promiseeight.www.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/*
    동일한 구조의 Fragment가 많을 것 같아 BaseFragment 추가했습니다.
*/
abstract class BaseFragment<B: ViewDataBinding> : Fragment() {
    private var _binding: B? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getFragmentBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
    }

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun removeItemDecorations(recyclerView: RecyclerView) {
        while(recyclerView.itemDecorationCount > 0){
            recyclerView.removeItemDecorationAt(0)
        }
    }

    fun showToast(message : String){
        Toast.makeText(requireContext(),message, Toast.LENGTH_SHORT).show()
    }
}