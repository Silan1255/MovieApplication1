package com.example.movieapplication1.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.movieapplication1.utils.CustomToast
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    // region variables
    lateinit var binding: VB

    @Inject
    lateinit var customToast: CustomToast
    //endregion

    //region fragment lifecycle
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentContext = requireContext()
    }

    //endregion

    //region tools

    abstract fun getViewBinding(): VB


    fun intent(targetActivity: Class<*>) {
        val intent = Intent(requireContext(), targetActivity)
        startActivity(intent)
    }

    fun intent(targetActivity: Class<*>, bundle: Bundle) {
        val intent = Intent(requireContext(), targetActivity)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    fun selectFile(fileRequestCode: Int) {

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                23423
            )
        } else {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "file/*"
            val mimeTypes = arrayOf("application/pdf")
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(intent, fileRequestCode)
        }
    }

    fun convertMultipartFile(
        file: File
    ): MultipartBody.Part {
        val requestFile: RequestBody = file.asRequestBody("file/*".toMediaType())
        return MultipartBody.Part.createFormData("file", file.name, requestFile)
    }
    //endregion
}