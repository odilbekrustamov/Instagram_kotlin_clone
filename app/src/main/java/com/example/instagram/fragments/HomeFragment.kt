package com.example.instagram.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.instagram.R
import java.lang.RuntimeException

class HomeFragment : BaseFragment() {
    val TAG = HomeFragment::class.java.simpleName
    private var listner: HomeListner? = null

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

        val iv_camera = view.findViewById<ImageView>(R.id.iv_camera)
        iv_camera.setOnClickListener {
            listner!!.scrollToUpload()
        }
    }

    /**
     * This interface is created for communication with UploadFragment
     */
    interface HomeListner{
        fun scrollToUpload()
    }
}