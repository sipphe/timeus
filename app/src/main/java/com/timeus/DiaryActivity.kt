package com.timeus

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.timeus.adapters.DiaryListAdapter
import com.timeus.models.Author
import com.timeus.models.Diary
import kotlinx.android.synthetic.main.add_diary_dialog.view.*
import java.time.LocalDateTime


class DiaryActivity : AppCompatActivity() {
    var db: FirebaseFirestore? = null
    var diaryList: MutableList<Diary> = ArrayList()
    var recyclerDiaries: RecyclerView? = null
    var fabAdd: FloatingActionButton? = null
    var layoutParent: ConstraintLayout? = null
    var buttonRefresh: ImageButton? = null
    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        buttonRefresh = findViewById(R.id.buttonRefresh)
        layoutParent = findViewById(R.id.parentLayout)
        recyclerDiaries = findViewById(R.id.recyclerDiaries)
        fabAdd = findViewById(R.id.fabAdd)

        FirebaseApp.initializeApp(this)
        loadDiaries()

        recyclerDiaries?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerDiaries?.adapter = DiaryListAdapter(this, diaryList)

        fabAdd?.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.add_diary_dialog, null)
            val builder = AlertDialog.Builder(this).setView(dialogView).setTitle("add diary")
            val alertDialog = builder.show()



            dialogView.buttonDone.setOnClickListener {
                dialogView.progressBar.visibility = View.VISIBLE
                if (connectionAvailable()) {
                    val newDiary = Diary(
                        dialogView.textInputHeading.text.toString(),
                        dialogView.textInputContent.text.toString(),
                        Author(
                            "AXeOaQ9Tjov6N4CKPPQo",
                            "annie",
                            "https://firebasestorage.googleapis.com/v0/b/united-bebop-229508.appspot.com/o/user_pic%2F84767166_2715808015161972_8470366344611627008_o.jpg?alt=media&token=7b12311d-47a3-4e1f-b930-849466703c1d"
                        ),
                        LocalDateTime.now().toString()
                    )
                    db!!.collection("diaries").document().set(newDiary).addOnSuccessListener {
                        dialogView.progressBar.visibility = View.INVISIBLE
                        showSnackBar("awe it's sent", Snackbar.LENGTH_LONG)
                        loadDiaries()
                        alertDialog.dismiss()

                    }.addOnFailureListener {
                        dialogView.progressBar.visibility = View.INVISIBLE
                        showSnackBar(
                            "eish this app is doing weird things, just try again quick",
                            Snackbar.LENGTH_LONG
                        )
                    }
                } else {
                    showSnackBar(
                        "check your connection, jy",
                        Snackbar.LENGTH_LONG
                    )
                    dialogView.progressBar.visibility = View.INVISIBLE
                }
            }
            dialogView.buttonCancel.setOnClickListener {
                alertDialog.dismiss()
            }


        }
        buttonRefresh!!.setOnClickListener {
            loadDiaries()
        }
    }

    private fun loadDiaries() {
        diaryList.clear()
        db = FirebaseFirestore.getInstance()
        if (connectionAvailable()) {
            db!!.collection("diaries").get().addOnSuccessListener { result ->
                for (document in result) {
                    var diary = document.toObject(Diary::class.java)
                    diaryList.add(0, diary)
                }
                diaryList.sortDescending()
                (recyclerDiaries?.adapter as DiaryListAdapter).notifyDataSetChanged()
            }
        } else {
            if (count == 0) {
                showSnackBar(
                    "lmao did you run out of data? \uD83D\uDE02",
                    Snackbar.LENGTH_LONG
                )
                count = 1
            } else {
                showSnackBar(
                    "loool broke boy! \uD83D\uDE02 \uD83D\uDE02 \uD83D\uDE02",
                    Snackbar.LENGTH_LONG
                )

                count = 0
            }
        }
    }

    private fun connectionAvailable(): Boolean {
        var connected = false
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        if (activeNetwork != null) {
            connected = if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                true
            } else {
                activeNetwork.type == ConnectivityManager.TYPE_MOBILE
            }
        }
        return connected
    }


    private fun showSnackBar(message: String?, length: Int?) {
        val contextView: View = findViewById(R.id.parentLayout)
        Snackbar.make(contextView, message!!, length!!).show()
    }

}

