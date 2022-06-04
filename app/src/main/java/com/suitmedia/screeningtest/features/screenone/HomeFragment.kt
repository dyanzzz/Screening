package com.suitmedia.screeningtest.features.screenone

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.suitmedia.screeningtest.R
import com.suitmedia.screeningtest.databinding.FragmentHomeBinding
import com.suitmedia.screeningtest.di.Injectable
import com.suitmedia.screeningtest.utils.SnackBarCustom
import com.suitmedia.screeningtest.utils.Tools.hideKeyboard
import timber.log.Timber
import java.io.ByteArrayOutputStream

class HomeFragment: Fragment(), Injectable {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val _pictureId = 123
    private val _permissionCode = 1000
    private var permissionGranted = false

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
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(cameraIntent, _pictureId)
                }
            }

            checkPalindrome.setOnClickListener {
                hideKeyboard()
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
                    } else {
                        resultPalindrome.text = "not from input text palindrome"
                    }
                }
            }
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == _pictureId && data != null) {
            val photo = data.extras!!["data"] as Bitmap?

            binding.profileImage.setImageBitmap(photo)
        }
    }

    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.contentResolver,
            inImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }

    private fun requestPermission() {
        Timber.e("Request permission clicked!!!")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissionGranted = if (PermissionChecker.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PermissionChecker.PERMISSION_DENIED) {
                //permission was not enabled
                val permission = arrayOf(Manifest.permission.CAMERA)

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