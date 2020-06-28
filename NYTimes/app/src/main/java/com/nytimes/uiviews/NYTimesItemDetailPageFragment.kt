package com.nytimes.uiviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.nytimes.R
import com.example.nytimes.databinding.FragmentNytimesItemDetailPageBinding

class NYTimesItemDetailPageFragment : Fragment()
{
    private lateinit var navController: NavController
    var title: String? = null;
    var newsUrl: String? = null;
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var listNyitemsCellBinding: FragmentNytimesItemDetailPageBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.fragment_nytimes_item_detail_page, container, false)
        title = arguments?.getString("title") ?: ""
        newsUrl = arguments?.getString("url") ?: "https://www.google.com"
        return listNyitemsCellBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
        var backButton: ImageButton = view.findViewById<ImageButton>(R.id.nyitem_detail_back_button)
        backButton.setOnClickListener{
            navController?.navigate(R.id.action_NYTimesItemDetailPageFragment_to_NYTimesItemsListPageFragment)
        }
        view.findViewById<TextView>(R.id.nyitem_detail_page_title).text = title
        progressBar = view.findViewById<ProgressBar>(R.id.ctrlActivityIndicator)

        var webView = view.findViewById<WebView>(R.id.nyitem_detail_page_webView)
        webView.settings.setJavaScriptEnabled(true);
        webView.loadUrl(newsUrl);
        webView.webChromeClient = object : WebChromeClient(){
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)

                if(newProgress >= 100)
                {
                    progressBar.visibility = View.GONE
                }

            }
        }
        super.onViewCreated(view, savedInstanceState)
    }
}