package com.cutzegypt.ui.editprofile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navGraphViewModels
import com.bumptech.glide.Glide
import com.cutzegypt.R
import com.cutzegypt.base.BaseFragment
import com.cutzegypt.data.remote.model.MessageEvent
import com.cutzegypt.data.remote.model.ProfileResponse
import com.cutzegypt.databinding.FragmentEditProfileBinding
import com.cutzegypt.utils.SharedData
import com.cutzegypt.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import id.zelory.compressor.Compressor
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>(), EditProfileNavigator {
    var compressedFile1: File? = null

    override var idLayoutRes: Int = R.layout.fragment_edit_profile
    private val mViewModel: EditProfileViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory
    }
    private var token: String? = String()
    private var data: SharedData? = null
    private var GALLERY = 0
    var REQUEST_WRITE_PERMISSION: Int = 786
    var file: File? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data = SharedData(requireContext())
        mViewDataBinding.registerViewModel = mViewModel
        token = data?.getValue(SharedData.ReturnValue.STRING, "token")
        mViewModel.navigator = this
        setupObserver()
        setupObserverProfile()
    }

    private fun setupObserver() {
        mViewModel.defultResponse.observe(viewLifecycleOwner, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    dismissLoading()
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.updated),
                        Toast.LENGTH_SHORT
                    ).show()
                    EventBus.getDefault().postSticky(MessageEvent("profile"))

                }
                Status.LOADING -> {
                    showLoading()
                }
                Status.ERROR -> {
                    dismissLoading()
                }
            }
        })
    }

    fun setData(profile: ProfileResponse) {
        mViewDataBinding.EFullName.setText(profile.data?.name.toString())
        mViewDataBinding.EPhone.setText(profile.data?.phone.toString())
        mViewDataBinding.EEmail.setText(profile.data?.email.toString())
        Glide.with(requireActivity()).load(profile.data?.image)
            .error(R.drawable.ic_editimage).into(mViewDataBinding.Photo);

    }

    private fun setupObserverProfile() {
        mViewModel.getProfileResponse.observe(viewLifecycleOwner, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    dismissLoading()
                    it.data?.let { it1 -> setData(it1) }
                }
                Status.LOADING -> {
                    showLoading()
                }
                Status.ERROR -> {
                    dismissLoading()
                }
            }
        })
    }

    override fun onClickSaveChanges() {
        lifecycleScope.launch {
            compressedFile1 = file?.let { Compressor.compress(requireContext(), it) }
            mViewModel.editProfile(token!!, compressedFile1)
        }
    }

    override fun onClickImage() {
        val permission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        } else {
            GALLERY = 1
            choosePhotoFromGallary(GALLERY)

        }
    }


    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            REQUEST_WRITE_PERMISSION
        )
    }

    fun choosePhotoFromGallary(number: Int) {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(galleryIntent, number)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_WRITE_PERMISSION -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                } else {
                    choosePhotoFromGallary(GALLERY)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (GALLERY == 1) {
            if (data != null) {
                val contentURI = data!!.data
                val filePath = getRealPathFromURIPath(contentURI!!, requireActivity())
                file = File(filePath)
                try {
                    val bitmap =
                        MediaStore.Images.Media.getBitmap(context?.contentResolver, contentURI)
                    val path = saveImage(bitmap)
                    Glide.with(requireActivity()).load(file).into(mViewDataBinding.Photo);


                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }

        }


    }


    private fun getRealPathFromURIPath(contentURI: Uri, activity: Activity): String {
        val cursor = activity.contentResolver.query(contentURI, null, null, null, null)
        if (cursor == null) {
            return contentURI.getPath()!!
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            return cursor.getString(idx)
        }
    }


    fun saveImage(myBitmap: Bitmap): String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY
        )
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs()
        }

        try {
//            Log.d("heel",wallpaperDirectory.toString())
            val f = File(
                wallpaperDirectory, ((Calendar.getInstance()
                    .getTimeInMillis()).toString() + ".jpg")
            )
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(
                requireContext(),
                arrayOf(f.getPath()),
                arrayOf("image/jpeg"), null
            )
            fo.close()
//            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath())

            return f.getAbsolutePath()
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }

    companion object {
        private val IMAGE_DIRECTORY = "/demonuts"
    }

}