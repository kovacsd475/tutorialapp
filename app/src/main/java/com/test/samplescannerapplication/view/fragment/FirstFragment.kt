package com.test.samplescannerapplication.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.test.samplescannerapplication.R
import com.test.samplescannerapplication.databinding.FragmentFirstBinding
import com.test.samplescannerapplication.viewmodel.DiceViewModel
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private val TAG: String = FirstFragment::class.java.simpleName
    private var _binding: FragmentFirstBinding? = null
    private lateinit var viewModel: DiceViewModel
    private var thread: Thread? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel = ViewModelProvider(requireActivity())[DiceViewModel::class.java]
        viewModel = ViewModelProvider(this)[DiceViewModel::class.java]
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
        setupDiceRollButton()
        setupFistButton()
        observeUIState()
        activitySetting()
//        threadExample()
    }

    private fun setupDiceRollButton() {
        binding.buttonDiceRoll.setOnClickListener {
            viewModel.diceRoll()
        }
    }

    private fun setupFistButton() {
        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    private fun observeUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.diceRollUIState.collect { diceRoll ->
                    Log.d(TAG, "Diceroll : $diceRoll")
                    updateNumberOfRollsTextView(diceRoll.numberOfRolls)
                }
            }
        }
    }

    private fun updateNumberOfRollsTextView(numberOfRolls: Int) {
        binding.textviewFirst.text = getNumberOfRollsText(numberOfRolls)
    }

    private fun getNumberOfRollsText(numberOfRolls: Int): String {
        return resources.getString(R.string.number_of_dice_rolls_format_label, numberOfRolls)
    }

    private fun threadExample() {
        thread = Thread {
            try {
                binding.textviewFirst.text = "Before sleep"
                Thread.sleep(3000)

                activity?.runOnUiThread {
                    binding.textviewFirst.text = "Execute 1"
                    Log.d(TAG, "Execute 1")
                }

                Thread.sleep(10000)

                activity?.runOnUiThread {
                    binding.textviewFirst.text = "After sleep"
                    Log.d(TAG, "Execute 1")
                }

                Log.d(TAG, "Execute after UI update")
            } catch (e: InterruptedException) {
                e.printStackTrace()
                Log.e(TAG, "Thread interrupted")
            }

        }

        thread?.apply {
            name = "ScannerExampleThread"
            start()
        }
    }


    /**
     * Create buttons to launch demo activities.
     */
    private fun activitySetting() {
//        btnAutomaticBarcode.setOnClickListener(View.OnClickListener { // get the intent action string from AndroidManifest.xml
//            val barcodeIntent = Intent("android.intent.action.AUTOMATICBARCODEACTIVITY")
//            startActivity(barcodeIntent)
//        })

        _binding?.buttonClientBarcode?.setOnClickListener(View.OnClickListener { // get the intent action string from AndroidManifest.xml
            val barcodeIntent = Intent("android.intent.action.CLIENTBARCODEACTIVITY")
            startActivity(barcodeIntent)
        })

//        btnScannerSelectBarcode.setOnClickListener(View.OnClickListener { // get the intent action string from AndroidManifest.xml
//            val barcodeIntent = Intent(
//                "android.intent.action.SCANNERSELECTBARCODEACTIVITY"
//            )
//            startActivity(barcodeIntent)
//        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        thread?.interrupt()
    }
}