package com.example.instagram.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.R
import com.example.instagram.adapter.HomeAdapter
import com.example.instagram.manager.AuthManager
import com.example.instagram.manager.DatabaseManager
import com.example.instagram.manager.handler.DBPostHandler
import com.example.instagram.manager.handler.DBPostsHandler
import com.example.instagram.model.Post
import com.example.instagram.utils.DialogListener
import com.example.instagram.utils.Utils
import java.lang.RuntimeException


class HomeFragment : BaseFragment() {
    val TAG = HomeFragment::class.java.simpleName
    private var listner: HomeListner? = null
    private lateinit var recyclerView: RecyclerView
    var feeds = ArrayList<Post>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        initViews(view)
        return view
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (isVisibleToUser && feeds.size> 0){
            loadMyFeeds()
        }
    }

    /**
     * onAttach is for communoication od Fragments
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listner = if (context is HomeListner){
            context
        }else {
            throw RuntimeException("$context must implement HomeListner")
        }
    }

    /**
     * onDetach is for communoication od Fragments
     */
    override fun onDetach() {
        super.onDetach()
        listner = null
    }
    fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(activity, 1)

        val iv_camera = view.findViewById<ImageView>(R.id.iv_camera)
        iv_camera.setOnClickListener {
            listner!!.scrollToUpload()
        }

        loadMyFeeds()
    }

    private fun refreshAdapter(items: ArrayList<Post>) {
        val adapter = HomeAdapter(this, items)
        recyclerView.adapter = adapter
    }

    private fun loadMyFeeds() {
        showLoading(requireActivity())
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadFeeds(uid, object : DBPostsHandler {
            override fun onSuccess(posts: ArrayList<Post>) {
                dismissLoading()
                feeds.clear()
                feeds.addAll(posts)
                refreshAdapter(feeds)
            }

            override fun onError(e: Exception) {
                dismissLoading()
            }

        })
    }

    fun likeOrUnlikePost(post: Post) {
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.likeFeedPost(uid, post)
    }

    fun showDeleteDialog(post: Post) {
        Utils.dialogDouble(requireContext(), getString(R.string.str_delete_post), object : DialogListener{
            override fun onCallback(isChosen: Boolean) {
                if (isChosen){
                    deletePost(post)
                }
            }

        })
    }

    private fun deletePost(post: Post) {
        DatabaseManager.deletePost(post, object : DBPostHandler{
            override fun onSuccess(post: Post) {
                loadMyFeeds()
            }

            override fun onError(e: Exception) {

            }

        })
    }

    /**
     * This interface is created for communication with UploadFragment
     */
    interface HomeListner{
        fun scrollToUpload()
    }
}