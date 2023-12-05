package com.ingridentify.ui.add

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.common.util.concurrent.ListenableFuture
import com.ingridentify.R
import com.ingridentify.databinding.FragmentCameraBinding

class CameraFragment : Fragment() {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!
    private var imageCapture: ImageCapture = ImageCapture.Builder().build()
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)

        enterFullscreen()

        return binding.root
    }

    private fun enterFullscreen() {
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav.visibility = View.GONE

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    private fun exitFullscreen() {
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav.visibility = View.VISIBLE

        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }

    override fun onResume() {
        super.onResume()
        startCamera()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        exitFullscreen()
    }

    private fun startCamera() {
        val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview: Preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding.previewView.surfaceProvider)
            }

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.failed_to_start_camera),
                    Toast.LENGTH_SHORT
                ).show()
                Log.e(TAG, "startCamera: ${e.message}", e)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    companion object {
        private const val TAG = "ScanFragment"
    }
}