package com.example.instagram.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.R
import com.example.instagram.adapter.FavoriteAdapter
import com.example.instagram.adapter.HomeAdapter
import com.example.instagram.manager.AuthManager
import com.example.instagram.manager.DatabaseManager
import com.example.instagram.manager.handler.DBPostHandler
import com.example.instagram.manager.handler.DBPostsHandler
import com.example.instagram.model.Post
import com.example.instagram.utils.DialogListener
import com.example.instagram.utils.Utils

class FavoriteFragment : BaseFragment() {
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        initViews(view)
        return view
    }

    fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(activity, 1)

        loadLikedFeeds()
    }

    fun likeOrUnLikePost(post: Post){
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.likeFeedPost(uid, post)

        loadLikedFeeds()
    }

    private fun loadLikedFeeds() {
        showLoading(requireActivity())
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadLikeFeeds(uid, object : DBPostsHandler{
            override fun onSuccess(posts: ArrayList<Post>) {
                dismissLoading()
                refreshAdapter(posts)
            }

            override fun onError(e: Exception) {
                dismissLoading()
            }

        })
    }

    private fun refreshAdapter(items: ArrayList<Post>) {
        val adapter = FavoriteAdapter(this, items)
        recyclerView.adapter = adapter
    }

    fun showDeleteDialog(post: Post) {
        Utils.dialogDouble(requireContext(), getString(R.string.str_delete_post), object :
            DialogListener {
            override fun onCallback(isChosen: Boolean) {
                if (isChosen){
                    deletePost(post)
                }
            }

        })
    }

    private fun deletePost(post: Post) {
        DatabaseManager.deletePost(post, object : DBPostHandler {
            override fun onSuccess(post: Post) {
                loadLikedFeeds()
            }

            override fun onError(e: Exception) {

            }

        })
    }

}