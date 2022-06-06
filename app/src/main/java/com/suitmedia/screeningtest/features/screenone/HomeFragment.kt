package com.suitmedia.screeningtest.features.screenone

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.content.PermissionChecker
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.suitmedia.screeningtest.R
import com.suitmedia.screeningtest.databinding.FragmentHomeBinding
import com.suitmedia.screeningtest.di.Injectable
import com.suitmedia.screeningtest.utils.CameraUtil
import com.suitmedia.screeningtest.utils.SnackBarCustom
import com.suitmedia.screeningtest.utils.Tools.hideKeyboard
import timber.log.Timber
import java.io.File
import java.io.IOException

class HomeFragment: Fragment(), Injectable {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val _pictureId = 123
    private val _permissionCode = 1000
    private var permissionGranted = false
    private var fileName: String = ""
    private var filePath: String = ""
    private var tvName: String = ""
    private var tvPalindrome: String = ""
    private lateinit var profileEntity: ProfileEntity
    private var isPalindrome = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        context ?: return binding.root

        binding.apply {
            profileImage.setOnClickListener {
                requestPermission()
                if (permissionGranted) {
                    capturePhoto()
                }
            }

            nextScreen.setOnClickListener {
                hideKeyboard()
                tvName = name.text.toString()
                tvPalindrome = palindrome.text.toString()

                if (tvName.isEmpty()) {
                    SnackBarCustom.snackBarIconInfo(
                        root,
                        layoutInflater,
                        resources,
                        root.context,
                        "Name Must be fill",
                        R.drawable.ic_close,
                        R.color.red_500
                    )
                    name.requestFocus()
                } else if (tvPalindrome.isEmpty()) {
                    SnackBarCustom.snackBarIconInfo(
                        root,
                        layoutInflater,
                        resources,
                        root.context,
                        "Palindrome Must be fill",
                        R.drawable.ic_close,
                        R.color.red_500
                    )
                    palindrome.requestFocus()
                } else if (fileName.isEmpty()) {
                    SnackBarCustom.snackBarIconInfo(
                        root,
                        layoutInflater,
                        resources,
                        root.context,
                        "Photo profile Must be fill",
                        R.drawable.ic_close,
                        R.color.red_500
                    )
                } else {
                    checkPalindrome()
                    if(isPalindrome) {
                        profileEntity = ProfileEntity(0, tvName, "fistName", "lastName", "email", "avatar", fileName, filePath)
                        val direction =
                            HomeFragmentDirections.actionNavigationHomeToNavigationDashboard(
                                profileEntity
                            )
                        it.findNavController().navigate(direction)
                    }
                }
            }

            checkPalindrome.setOnClickListener {
                hideKeyboard()
                checkPalindrome()
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Timber.e(fileName)
        Timber.e(filePath)
        Timber.e(tvName)

        if(filePath.isNotEmpty()) {
            Timber.e(filePath.toUri().toString())
            binding.profileImage.setImageURI(filePath.toUri())
        }
    }

    private fun capturePhoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    CameraUtil.createImageFile(requireContext())
                } catch (ex: IOException) {
                    Toast.makeText(requireContext(), "Gagal ambil photo, silahkan coba lagi", Toast.LENGTH_SHORT).show()
                    Timber.e(ex.message)
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "com.suitmedia.screeningtest.fileprovider",
                        it
                    )
                    fileName = it.name
                    filePath = it.absolutePath
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, _pictureId)
                }
            }
        }
    }

    private fun checkPalindrome() {
        binding.apply {
            val textPalindrome = palindrome.text
            if(textPalindrome.isNullOrEmpty()) {
                SnackBarCustom.snackBarIconInfo(root, layoutInflater, resources, root.context, "PLease Input Text to Check Palindrome", R.drawable.ic_close, R.color.red_500)
                palindrome.requestFocus()
            } else {
                resultPalindrome.visibility = View.VISIBLE
                var reverseString = ""
                val textLength = textPalindrome.toString().length

                for (i in (textLength - 1) downTo 0) {
                    reverseString += textPalindrome[i]
                }

                if (textPalindrome.toString() == reverseString) {
                    resultPalindrome.text = "sentences is palindrome"
                    isPalindrome = true
                } else {
                    resultPalindrome.text = "sentences is not palindrome"
                    isPalindrome = false
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == _pictureId) {
                processCapturedPhoto()
            }
        }
    }

    private fun processCapturedPhoto() {
        if (filePath.isEmpty()) {
            Toast.makeText(requireContext(), "Gagal ambil photo, silahkan coba lagi", Toast.LENGTH_SHORT).show()
            return
        }

        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            val f = File(filePath)
            mediaScanIntent.data = Uri.fromFile(f)
            requireContext().sendBroadcast(mediaScanIntent)
            val imageUri = mediaScanIntent.data

            binding.profileImage.setImageURI(imageUri)
        }
    }

    private fun requestPermission() {
        Timber.e("Request permission clicked!!!")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissionGranted = if (
                PermissionChecker.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PermissionChecker.PERMISSION_DENIED
                || PermissionChecker.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_DENIED) {
                //permission was not enabled
                val permission = arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                )

                //show popup to request permission
                requestPermissions(permission, _permissionCode)
                false
            } else {
                SnackBarCustom.snackBarIconInfo(binding.root, layoutInflater, resources, requireContext(), "Permission granted", R.drawable.ic_error_outline, R.color.blue_500)
                //permission already granted
                true
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //called when user presses ALLOW or DENY from Permission Request Popup
        when (requestCode) {
            _permissionCode -> {
                permissionGranted = if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission from popup was granted
                    true
                } else {
                    //permission from popup was denied
                    SnackBarCustom.snackBarIconInfo(binding.root, layoutInflater, resources, requireContext(), "Permission denied", R.drawable.ic_close, R.color.red_500)
                    false
                }
            }
        }
    }
}