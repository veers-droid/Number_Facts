package com.example.numberfacts

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.numberfacts.databinding.FragmentSecondBinding
import com.example.numberfacts.viewmodel.SecondFragmentViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var chosenNumber: Int? = null

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: SecondFragmentViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(SecondFragmentViewModel::class.java)) {
                    return SecondFragmentViewModel(requireActivity().application) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            if (it.getInt(FirstFragment.VIEW_MODEL_TYPE) == FirstFragment.CHOSEN) {
                chosenNumber = it.getInt(FirstFragment.CHOSEN_NUMBER)
                binding.chosenNumber.append(" $chosenNumber")
                initViewModelWithChosenNumber()
                if (savedInstanceState == null) {
                    viewModel.getFactAboutNumber()
                }
            } else if (it.getInt(FirstFragment.VIEW_MODEL_TYPE) == FirstFragment.RANDOM) {
                initViewModelWithRandomNumber()
                if (savedInstanceState == null) {
                    viewModel.getRandomFact()
                }
            } else {
                val number = it.getString(FirstFragment.CHOSEN_NUMBER)
                val fact = it.getString(FirstFragment.FACT)
                initViewModelFromHistory(number, fact)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViewModelWithChosenNumber() {
        viewModel.number.value = chosenNumber
        viewModel.fact.observe(viewLifecycleOwner) {
            binding.numberFact.append(it)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initViewModelWithRandomNumber() {
        viewModel.number.observe(viewLifecycleOwner) {
            binding.chosenNumber.text = "Your number is $it"
        }
        viewModel.fact.observe(viewLifecycleOwner) {
            binding.numberFact.append(it)
        }
    }

    private fun initViewModelFromHistory(number: String?, fact: String?) {
        viewModel.number.observe(viewLifecycleOwner) {
            binding.chosenNumber.append(" $it")
        }
        viewModel.fact.observe(viewLifecycleOwner) {
            binding.numberFact.append(it)
        }
        viewModel.number.value = number?.toIntOrNull()
        viewModel.fact.value = fact
    }
}
