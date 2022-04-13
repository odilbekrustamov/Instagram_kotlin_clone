package com.example.instagram.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.R
import com.example.instagram.adapter.SearchAdapter
import com.example.instagram.model.User

/**
 * inSearchActivity, all registered users can be found by searching keyword and followed.
 */

class SearchFragment : BaseFragment() {
    val TAG = SearchFragment::class.java.simpleName
    lateinit var rv_search: RecyclerView
    var items = ArrayList<User>()
    var users = ArrayList<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        initViews(view)
        return view
    }

    fun initViews(view: View) {
        rv_search = view.findViewById(R.id.rv_search)
        rv_search.layoutManager = GridLayoutManager(activity, 1)
        val et_search = view.findViewById<EditText>(R.id.et_search)
        et_search.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val keyword = s.toString().trim()
                usersByKeyword(keyword)
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
        loadUsers()
        refreshAdapter(items)
    }

    private fun refreshAdapter(items: ArrayList<User>) {
        val adapter = SearchAdapter(this, items)
        rv_search.adapter = adapter
    }

    private fun usersByKeyword(keyword: String) {
        if (keyword.isEmpty())
            refreshAdapter(items)

        users.clear()
        for (user in items)
            if (user.fullname.toLowerCase().startsWith(keyword.toLowerCase()))
                users.add(user)
        refreshAdapter(users)
    }

    private fun loadUsers(): ArrayList<User> {
        items = ArrayList()
        items.add(User("Odilbek_79", "odiljon@gmail.com"))
        items.add(User("Alijon", "odiljon@gmail.com"))
        items.add(User("Siroj", "odiljon@gmail.com"))
        items.add(User("Javron", "odiljon@gmail.com"))
        items.add(User("Jonibek", "odiljon@gmail.com"))
        items.add(User("Ma'rufjon", "odiljon@gmail.com"))
        items.add(User("SaidAhmad", "odiljon@gmail.com"))
        items.add(User("Asliddin", "odiljon@gmail.com"))

        return items
    }
}