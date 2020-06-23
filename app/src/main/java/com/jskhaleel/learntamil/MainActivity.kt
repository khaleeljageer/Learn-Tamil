package com.jskhaleel.learntamil

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jskhaleel.learntamil.ui.main.MainFragment
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }


        Dexter.withContext(baseContext)
            .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {

                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {

                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {

                }
            }).check()
    }

    override fun onResume() {
        super.onResume()

        copyTrainedData()
    }

    private fun copyTrainedData() {
        val data = getExternalFilesDir("/tessdata")
        if (data!!.exists()) {
            data.mkdir()
        }

        val files = assets.list("tessdata/")
        if (files != null) {
            for (file in files) {
                val newPath = data.absolutePath + File.separator + file
                if (!File(newPath).exists()) {
                    val `in` = assets.open("tessdata/$file")
                    val out: OutputStream = FileOutputStream(newPath)
                    val buff = ByteArray(1024)
                    var len: Int
                    while (`in`.read(buff).also { len = it } > 0) {
                        out.write(buff, 0, len)
                    }
                    `in`.close()
                    out.close()
                }
                Log.d("Khaleel", "newPath $newPath")
            }
        }
    }
}