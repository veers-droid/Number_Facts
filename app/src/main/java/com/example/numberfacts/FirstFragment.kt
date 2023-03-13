package com.example.numberfacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.numberfacts.databinding.FragmentFirstBinding
import com.example.numberfacts.viewmodel.FirstFragmentViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val viewModel: FirstFragmentViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(FirstFragmentViewModel::class.java)) {
                    return FirstFragmentViewModel(requireActivity().application) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    //list for adapter
    private val storyList = ArrayList<String>()
    private lateinit var adapter: ArrayAdapter<String>

    companion object {
        const val CHOSEN_NUMBER = "0"
        const val FACT = "1"
        const val VIEW_MODEL_TYPE = "2"
        const val CHOSEN = 0
        const val RANDOM = 1
        const val HISTORY = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initViewModel()

        binding.inputNumber.addTextChangedListener {
            viewModel.number.value = it.toString().toIntOrNull()
        }

        binding.buttonFirst.setOnClickListener {
            if (viewModel.number.value == null) {
                Toast.makeText(context, "Please, enter number first", Toast.LENGTH_LONG).show()
            }
            else {
                val bundle = Bundle().apply {
                    //putting our number to transfer to next fragment
                    putInt(CHOSEN_NUMBER, viewModel.number.value!!)
                    //putting type of viewModel so that our fragment can create appropriate one
                    putInt(VIEW_MODEL_TYPE, CHOSEN)
                }
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
            }
        }

        binding.buttonRandom.setOnClickListener {
            val bundle = Bundle().apply {
                //putting type of viewModel so that our fragment can create appropriate one
                putInt(VIEW_MODEL_TYPE, RANDOM)
            }
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViewModel() {
        //getting history from db to our LiveData
        viewModel.getStory()

        viewModel.facts.observe(viewLifecycleOwner) {
            //refill listView
            storyList.clear()
            storyList.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }

    private fun initAdapter() {

        adapter = ArrayAdapter(requireContext(), R.layout.list_item, storyList)

        binding.story.adapter = adapter

        binding.story.setOnItemClickListener { parent, _, position, _ ->
            //separate number from its fact
            val itemList = (parent.getItemAtPosition(position) as String).split(" ", limit = 2)
            val number = itemList[0]
            val fact = itemList[1]

            val bundle = Bundle().apply {
                //putting our number to transfer to next fragment
                putString(CHOSEN_NUMBER, number)
                //putting our fact to transfer to next fragment
                putString(FACT, fact)
                //putting type of viewModel so that our fragment can create appropriate one
                putInt(VIEW_MODEL_TYPE, HISTORY)
            }
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
        }

    }
}
