package com.example.pdf_viewer

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.pdf_viewer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val pickPdfLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                displayPdf(uri)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle PDF file opened from external app
        intent?.data?.let { uri ->
            displayPdf(uri)
        }

        binding.fabPickPdf.setOnClickListener {
            pickPdfFile()
        }
    }

    private fun pickPdfFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "application/pdf"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        pickPdfLauncher.launch(intent)
    }

    private fun displayPdf(uri: Uri) {
        binding.pdfView.fromUri(uri)
            .enableSwipe(true)
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .defaultPage(0)
            .load()
    }
}