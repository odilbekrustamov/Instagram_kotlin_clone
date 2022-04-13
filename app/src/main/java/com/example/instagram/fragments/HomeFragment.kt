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
import com.example.instagram.model.Post
import java.lang.RuntimeException


class HomeFragment : BaseFragment() {
    val TAG = HomeFragment::class.java.simpleName
    private var listner: HomeListner? = null
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        initViews(view)
        return view
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

        refreshAdapter(loadPost())
    }

    private fun refreshAdapter(items: ArrayList<Post>) {
        val adapter = HomeAdapter(this, items)
        recyclerView.adapter = adapter
    }

    private fun loadPost(): ArrayList<Post> {
        val items = ArrayList<Post>()
        items.add(Post("https://images.unsplash.com/photo-1519295918781-d590afd8e95d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80"))
        items.add(Post("https://images.unsplash.com/photo-1498758536662-35b82cd15e29?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=388&q=80"))
        items.add(Post("https://images.unsplash.com/photo-1637979909766-ccf55518a928?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=534&q=80"))
        return items
    }

    /**
     * This interface is created for communication with UploadFragment
     */
    interface HomeListner{
        fun scrollToUpload()
    }
}