package com.ingridentify.ui.add

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.ingridentify.R
import com.ingridentify.databinding.FragmentAddBinding
import com.ingridentify.ui.ViewModelFactory

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AddViewModel> { ViewModelFactory.getInstance() }
    private val args: AddFragmentArgs by navArgs()

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

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { it?.let { uri ->
        viewModel.setImageUri(uri)
    }}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.imageUri?.let { uriString: String ->
            setImageUri(Uri.parse(uriString))
        }

        binding.btnCamera.setOnClickListener {
            if (!checkPermission()) {
                requestPermission.launch(CAMERA_PERMISSION)
            } else {
                startCamera()
            }
        }

        binding.btnGallery.setOnClickListener {
            galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        viewModel.imageUri.observe(requireActivity()) { uri: Uri? ->
            uri?.let(::setImageUri)
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

    private fun setImageUri(uri: Uri) {
        binding.ivThumbnail.setImageURI(uri)
    }

    companion object {
        private const val CAMERA_PERMISSION = Manifest.permission.CAMERA
    }
}