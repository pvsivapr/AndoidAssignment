package com.nytimes.uiviews

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.nytimes.MainActivity
import com.example.nytimes.R
import com.nytimes.localdb.NYLocalDBModel
import com.nytimes.localdb.NYTimesLocalService
import com.nytimes.localmodels.NYTimesLocalItemModel
import com.nytimes.networking.NYItemsListModel
import com.nytimes.networking.NYTimesService
import retrofit2.Call
import retrofit2.Response
import java.time.Instant
import java.util.*
import kotlin.collections.ArrayList

class NYTimesItemsListPageFragment : Fragment()
{
    private var swipeRefreshLayout: SwipeRefreshLayout? =null
    private var title: TextView? = null

    private lateinit var navController: NavController
    private lateinit var nYItemsListPageViewModel: NYTimesItemsListPageViewModel

    var newsType: String? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_nytimes_items_list_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        navController = Navigation.findNavController(view)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        nYItemsListPageViewModel = ViewModelProvider(this).get(NYTimesItemsListPageViewModel::class.java)
        nYItemsListPageViewModel?.nyItemsLocalDisplayModelList?.observe(viewLifecycleOwner, androidx.lifecycle.Observer<List<NYTimesLocalItemModel>>{
            println("data obtained")
            if(it !== null) {
                loadAdapter(it)
            }
            swipeRefreshLayout?.isRefreshing = false
        })
        nYItemsListPageViewModel?.nyTimesItemDetail?.observe(viewLifecycleOwner, androidx.lifecycle.Observer<NYTimesLocalItemModel>{
            if(it !== null) {
                try {
                    var nyItemBundle = Bundle().apply {
                        putString("title", it.title)
                        putString("byline", it.byline)
                        putString("url", it.url)
                    }
                    navController.navigate(R.id.action_NYTimesItemsListPageFragment_to_NYTimesItemDetailPageFragment, nyItemBundle)
                }
                catch (ex: Exception)
                {
                    ex.printStackTrace()
                }
            }
        })
        loadViews()
        super.onActivityCreated(savedInstanceState)
    }

    private fun loadAdapter(nyItemsListModel: List<NYTimesLocalItemModel>)
    {
        try {
            val nyTimesListRecyclerView = view?.findViewById<View>(R.id.nyitems_list_view) as RecyclerView
            val nyTimesItemsListPageAdaptor = NYTimesItemsListAdapter(nyItemsListModel, nYItemsListPageViewModel!!)
            nyTimesListRecyclerView.setHasFixedSize(true)
            nyTimesListRecyclerView.layoutManager = LinearLayoutManager(activity)// as RecyclerView.LayoutManager?
            nyTimesListRecyclerView.adapter = nyTimesItemsListPageAdaptor
        }
        catch (ex: Exception)
        {
            ex.printStackTrace()
        }
    }

    fun loadViews()
    {
        try
        {
            swipeRefreshLayout = view?.findViewById<SwipeRefreshLayout>(R.id.nyitems_swipe_list_view)
            swipeRefreshLayout?.setOnRefreshListener {
                nYItemsListPageViewModel?.GetNYItems(newsType!!)
            }
            val menuHamburgerImageButton = view?.findViewById<ImageButton>(R.id.imagebutton_menu_hamburger)
            menuHamburgerImageButton?.setOnClickListener{
                onImageButtonTapped(it)
            }
            val menuHamburgerSearchButton = view?.findViewById<ImageButton>(R.id.imagebutton_search_icon)
            menuHamburgerSearchButton?.setOnClickListener{
                onImageButtonTapped(it)
            }
            val menuOptionsImageButton = view?.findViewById<ImageButton>(R.id.imagebutton_menu_vertical_icon)
            menuOptionsImageButton?.setOnClickListener{
                onImageButtonTapped(it)
            }
            loadDynamicData()
        }
        catch (ex: Exception)
        {
            ex.printStackTrace()
        }
    }

    fun onImageButtonTapped(view: View)
    {
        val imageButtonView = view as ImageButton
        try {
            when (imageButtonView.id) {
                (R.id.imagebutton_menu_hamburger) -> {
                    activity?.finish()
                }
                (R.id.imagebutton_search_icon) -> {
                    val text = "Functionality is under development"
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(activity, text, duration)
                    toast.show()
                }
                (R.id.imagebutton_menu_vertical_icon) -> {
                    loadDynamicData()
                }
                else -> {
                }
            }
        }
        catch (ex: Exception)
        {
            println(ex)
        }
    }

    fun loadDynamicData()
    {
        swipeRefreshLayout?.isRefreshing = true
        var types: Array<String> = arrayOf("arts", "automobiles", "books", "business", "fashion", "food", "health", "home", "insider", "magazine", "movies", "nyregion", "obituaries", "opinion", "politics", "realestate", "science", "sports", "sundayreview", "technology", "theater", "t-magazine", "travel", "upshot", "us", "world")
        newsType = types.random()
        title = view?.findViewById<TextView>(R.id.nyitems_diaplay_page_title)
        title?.text = newsType
        nYItemsListPageViewModel?.GetNYItems(newsType!!)
    }
}

class NYTimesItemsListPageViewModel (application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext


    var nyItemsLocalDisplayModelList = MutableLiveData<List<NYTimesLocalItemModel>>()
    //var nyTimesItems = MutableLiveData<NYItemsListModel>()
    var nyTimesItemDetail = MutableLiveData<NYTimesLocalItemModel>()
//    val appContext: Application = application

//    val context = paren;

    fun GetNYItems(newsType: String)
    {
        try {
            var callNYTimesService = NYTimesService.create().getNYArtsItems(newsType)
            callNYTimesService.enqueue(object : retrofit2.Callback<NYItemsListModel> {
                override fun onFailure(call: Call<NYItemsListModel>, t: Throwable) {
                    println("This is a failed service call ${t.message}")
                    callNYTimesService.cancel()
                    getFromLocal()
                }

                override fun onResponse(call: Call<NYItemsListModel>, response: Response<NYItemsListModel>) {
                    var nyTimesResponse: NYItemsListModel? = response.body()
                    var nyTimesItemsList = nyTimesResponse
                    if (nyTimesItemsList != null) {
                        println("Data is loaded")
                        var nyItemsLocalDisplayModelTempList = ArrayList<NYTimesLocalItemModel>()
                        nyTimesItemsList.results.forEach{
                            var imageUrl: String = " "
                            var imageFormat: String = " "
                            var imageType: String = " "
                            it.multimedia.forEach{
                                if(it.format == "Standard Thumbnail")
                                {
                                    imageUrl = it.url
                                    imageFormat = it.format
                                    imageType = it.type
                                }
                            }
                            val nyItemsLocalDisplayModelItem = NYTimesLocalItemModel(
                                title = it.title,
                                byline = it.byline,
                                section = it.section,
                                subSection = it.subSection,
                                url = it.url,
                                itemType = it.itemType,

                                createdDate = it.createdDate,
                                publishedDate = it.publishedDate,
                                updatedDate = it.updatedDate,

                                imageUrl = imageUrl,
                                imageFormat = imageFormat,
                                imageType = imageType
                            )
                            nyItemsLocalDisplayModelTempList.add(nyItemsLocalDisplayModelItem)
                            SaveToLocal(nyItemsLocalDisplayModelItem)
                        }
                        nyItemsLocalDisplayModelList.postValue(nyItemsLocalDisplayModelTempList)
                        //nyTimesItems.postValue(nyTimesItemsList)
                    } else {
                        println("ny Times: is null going with Local db")
                        getFromLocal()
                    }
                    callNYTimesService.cancel()
                }
            })
        }
        catch(ex: Exception)
        {
            println(ex.stackTrace)
        }
    }

    fun SaveToLocal(nyTimesLocalItemModel : NYTimesLocalItemModel)
    {
        try {
            val nyLocalDBModel = NYLocalDBModel(
                nid = Date.from(Instant.now()).time.toInt(),
                section = nyTimesLocalItemModel.section,
                subSection = nyTimesLocalItemModel.section,
                title = nyTimesLocalItemModel.section,
                byline = nyTimesLocalItemModel.section,
                itemType = nyTimesLocalItemModel.section,
                url = nyTimesLocalItemModel.section,

                createdDate = nyTimesLocalItemModel.section,
                publishedDate = nyTimesLocalItemModel.section,
                updatedDate = nyTimesLocalItemModel.section,

                imageUrl = nyTimesLocalItemModel.section,
                imageFormat = nyTimesLocalItemModel.section,
                imageType = nyTimesLocalItemModel.section
            )
            val values = NYTimesLocalService.getInstance(context).nyTimesLocalDBService()
                .insertNYItem(nyLocalDBModel)
        }
        catch (ex: Exception)
        {
            ex.printStackTrace()
        }
    }

    fun getFromLocal()
    {
        try {
            val values = NYTimesLocalService.getInstance(context).nyTimesLocalDBService()
                .getLocalNYItemsList()

            if (values != null) {
                var nyItemsLocalDisplayModelTempList = ArrayList<NYTimesLocalItemModel>()

                values.forEach {
                    val nyItemsLocalDisplayModelItem = NYTimesLocalItemModel(
                        title = it.title!!,
                        byline = it.byline!!,
                        section = it.section!!,
                        subSection = it.subSection!!,
                        url = it.url!!,
                        itemType = it.itemType!!,

                        createdDate = it.createdDate!!,
                        publishedDate = it.publishedDate!!,
                        updatedDate = it.updatedDate!!,

                        imageUrl = it.imageUrl!!,
                        imageFormat = it.imageFormat!!,
                        imageType = it.imageType!!
                    )
                    nyItemsLocalDisplayModelTempList.add(nyItemsLocalDisplayModelItem)
                }
                nyItemsLocalDisplayModelList.postValue(nyItemsLocalDisplayModelTempList)
            }
            else
            {
                nyItemsLocalDisplayModelList.postValue(null)
            }
        }catch (ex: Exception)
        {
            ex.printStackTrace()
            nyItemsLocalDisplayModelList.postValue(null)
        }
    }

    fun getNYTimesItemsLocalDB(context: Context)
    {
        try
        {
            val items = NYTimesLocalService.getInstance(context).nyTimesLocalDBService().getLocalNYItemsList()
        }
        catch (ex: Exception)
        {
            ex.printStackTrace()
        }
    }



    fun OnForwardButtonClicked(item: NYTimesLocalItemModel)
    {
        if(item != null)
        {
            nyTimesItemDetail.postValue(item)
        }
        else{
            println("item is null")
        }
    }
}