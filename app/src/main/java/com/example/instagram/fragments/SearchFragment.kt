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
import com.example.instagram.manager.AuthManager
import com.example.instagram.manager.DBFollowHandler
import com.example.instagram.manager.DatabaseManager
import com.example.instagram.manager.handler.DBUserHandler
import com.example.instagram.manager.handler.DBUsersHandler
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

   /* private fun loadUsers(): ArrayList<User> {
       DatabaseManager.loadUsers(object : DBUsersHandler{
           override fun onSuccess(users: ArrayList<User>) {
               items.clear()
               items.addAll(users)
               refreshAdapter(items)
           }

           override fun onError(e: Exception) {

           }

       })
        return items
    }*/

    fun followOurUnfollow(to: User) {
        val uid = AuthManager.currentUser()!!.uid
        if (!to.isFollow){
            followUser(uid, to)
        }else {
            unFollowUser(uid, to)
        }
    }

    private fun followUser(uid: String, to: User) {
        DatabaseManager.loadUser(uid, object : DBUserHandler{
            override fun onSuccess(me: User?) {
                DatabaseManager.followUser(me!!, to, object : DBFollowHandler{
                    override fun onSuccess(isFollowed: Boolean) {
                        to.isFollow = true
                       DatabaseManager.storePostsToMyFeed(uid, to)
                    }

                    override fun onError(e: java.lang.Exception) {
                    }
                })
            }
            override fun onError(e: Exception) {

            }

        })
    }

    private fun unFollowUser(uid: String, to: User) {
        DatabaseManager.loadUser(uid, object : DBUserHandler{
            override fun onSuccess(me: User?) {
                DatabaseManager.unFollowUser(me!!, to, object : DBFollowHandler{
                    override fun onSuccess(isFollowed: Boolean) {
                        to.isFollow = false
                        DatabaseManager.removePostsToMyFeed(uid, to)
                    }

                    override fun onError(e: java.lang.Exception) {
                    }
                })
            }

            override fun onError(e: Exception) {

            }

        })
    }

    private fun loadUsers() {
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadUsers(object : DBUsersHandler {
            override fun onSuccess(users: ArrayList<User>) {
                DatabaseManager.loadFollowing(uid, object : DBUsersHandler {
                    override fun onSuccess(following: ArrayList<User>) {
                        items.clear()
                        items.addAll(mergedUsers(uid, users, following))
                        refreshAdapter(items)
                    }

                    override fun onError(e: Exception) {

                    }
                })
            }

            override fun onError(e: Exception) {

            }
        })
    }

    private fun mergedUsers(uid: String, users: ArrayList<User>, following: ArrayList<User>): Collection<User> {
        val items = ArrayList<User>()
        for (u in users){
            val user = u
            for (f in following){
                if (u.uid == f.uid){
                    user.isFollow = true
                }
            }
            if (uid != user.uid){
                items.add(user)
            }
        }
        return items
    }
}