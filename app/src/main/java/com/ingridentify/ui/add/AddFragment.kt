package com.ingridentify.ui.add

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.ingridentify.R
import com.ingridentify.databinding.FragmentAddBinding

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private val requestPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            startCamera()
        } else {
            Toast.makeText(
                requireContext(), getString(R.string.camera_permission_denied),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCamera.setOnClickListener {
            if (!checkPermission()) {
                requestPermission.launch(CAMERA_PERMISSION)
            } else {
                startCamera()
            }
        }
    }

    private fun checkPermission(): Boolean = ContextCompat.checkSelfPermission(
        requireContext(),
        CAMERA_PERMISSION
    ) == PackageManager.PERMISSION_GRANTED

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun startCamera() {
        val toCameraFragment = AddFragmentDirections.actionNavigationAddToCameraFragment()
        requireView().findNavController().navigate(toCameraFragment)
    }

    companion object {
        private const val CAMERA_PERMISSION = Manifest.permission.CAMERA
    }
}