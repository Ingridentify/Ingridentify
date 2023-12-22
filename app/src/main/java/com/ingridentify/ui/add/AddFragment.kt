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
import com.ingridentify.data.Result
import com.ingridentify.databinding.FragmentAddBinding
import com.ingridentify.ui.ViewModelFactory
import com.ingridentify.utils.FileUtils

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private val viewModel by viewModels<AddViewModel> { ViewModelFactory.getInstance(requireContext()) }
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
        binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.imageUri?.let { uriString: String ->
            viewModel.setImageUri(Uri.parse(uriString))
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

        binding.btnPredict.setOnClickListener {
            val imageUri = viewModel.imageUri.value

            if (imageUri == null) {
                showToast(getString(R.string.please_select_an_image_first))
                return@setOnClickListener
            }

            val imageFile = FileUtils.uriToFile(imageUri, requireContext())
            viewModel.predict(imageFile).observe(viewLifecycleOwner) { result: Result<String> ->
                when (result) {
                    is Result.Loading -> {
                        setLoading(true)
                    }

                    is Result.Success -> {
                        setLoading(false)
                        showToast(getString(R.string.hmm_looks_like_s, result.data))
                        getRecipe(result.data)
                    }

                    is Result.Error -> {
                        setLoading(false)
                        showToast(result.error)
                    }
                }
            }
        }

        viewModel.imageUri.observe(viewLifecycleOwner) { uri: Uri? ->
            binding.ivThumbnail.setImageURI(uri)
        }
    }

    private fun getRecipe(name: String) {
        viewModel.getRecipe(name).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    setLoading(true)
                }

                is Result.Success -> {
                    setLoading(false)
                    val toResultFragment = AddFragmentDirections.actionNavigationAddToNavigationRecipe(
                        name = name
                    )
                    requireView().findNavController().navigate(toResultFragment)
                }

                is Result.Error -> {
                    setLoading(false)
                    showToast(result.error)
                }
            }
        }
    }

    private fun showToast(message: String) = Toast.makeText(
        requireContext(), message, Toast.LENGTH_SHORT
    ).show()

    private fun setLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
            binding.btnPredict.isEnabled = false
            binding.btnPredict.text = ""
        } else {
            binding.progressBar.visibility = View.GONE
            binding.btnPredict.isEnabled = true
            binding.btnPredict.text = getString(R.string.predict)
        }
    }

    private fun checkPermission(): Boolean = ContextCompat.checkSelfPermission(
        requireContext(),
        CAMERA_PERMISSION
    ) == PackageManager.PERMISSION_GRANTED

    private fun startCamera() {
        val toCameraFragment = AddFragmentDirections.actionNavigationAddToCameraFragment()
        requireView().findNavController().navigate(toCameraFragment)
    }

    companion object {
        private const val CAMERA_PERMISSION = Manifest.permission.CAMERA
    }
}