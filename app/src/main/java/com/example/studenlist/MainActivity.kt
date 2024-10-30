package com.example.studenlist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.Normalizer
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    private lateinit var studentAdapter: StudentAdapter
    private val students = listOf(
        Student("Dương Trung Kiên", "20215067"),
        Student("Nguyen Van Kiên", "20210000"),
        Student("Đinh Văn Luận", "20215083"),
        Student("Test", "07"),
        // Thêm các sinh viên khác
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val searchEditText: EditText = findViewById(R.id.searchEditText)

        studentAdapter = StudentAdapter(students)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = studentAdapter

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val keyword = s.toString()
                if (keyword.length > 2) {
                    val filteredList = students.filter {
                        removeDiacritics(it.name).contains(removeDiacritics(keyword), ignoreCase = true) || it.mssv.contains(keyword)
                    }
                    studentAdapter.updateList(filteredList)
                } else {
                    studentAdapter.updateList(students)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun removeDiacritics(str: String): String {
        val normalizedString = Normalizer.normalize(str, Normalizer.Form.NFD)
        val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
        return pattern.matcher(normalizedString).replaceAll("")
    }
}