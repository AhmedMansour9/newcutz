package com.gazr.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gazr.Model.AddFav_Response
import com.gazr.Model.AllProducts_Response
import com.gazr.Model.ProductDeal_Response
import com.gazr.Retrofit.ApiClient
import com.gazr.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class getAllProducts_ViewModel: ViewModel() {
    private var No_Product:Boolean=false

     var listProductsMutableLiveData: MutableLiveData<AllProducts_Response>? = null
    var listProductsMutableLiveDeals: MutableLiveData<ProductDeal_Response>? = null
    var listProductsMutableLiveAddFavourits: MutableLiveData<AddFav_Response>? = null

     fun getFilteredData(page:String,Lang:String,link:String,token:String?,name:String?,
                         cat_id:String?,sub_id:String?): LiveData<AllProducts_Response> {
        listProductsMutableLiveData = MutableLiveData<AllProducts_Response>()
        getProductsFiltered(page,Lang,link,token,name,cat_id,sub_id)
        return listProductsMutableLiveData as MutableLiveData<AllProducts_Response>
    }

     fun getData(Lang:String): LiveData<ProductDeal_Response> {
         listProductsMutableLiveDeals = MutableLiveData<ProductDeal_Response>()
        getDataValues(Lang)
        return listProductsMutableLiveDeals as MutableLiveData<ProductDeal_Response>
    }
     fun getLatest(page:String,Lang:String,link:String,token:String?): LiveData<AllProducts_Response> {
        listProductsMutableLiveData = MutableLiveData<AllProducts_Response>()
        getlatest(page,Lang,link,token)
        return listProductsMutableLiveData as MutableLiveData<AllProducts_Response>
    }

    fun getSimilar(page:String,Lang:String,link:String,token:String?,product_id:String): LiveData<AllProducts_Response> {
        listProductsMutableLiveData = MutableLiveData<AllProducts_Response>()
        getSimilarProducts(page,Lang,link,token,product_id)
        return listProductsMutableLiveData as MutableLiveData<AllProducts_Response>
    }

//    fun getFavouritProducts(Lang:String,token:String, context: Context): LiveData<Favourit_Response> {
//        listProductsMutableLiveFavourits = MutableLiveData<Favourit_Response>()
//        this.context = context
//        getFavourits(Lang,token)
//        return listProductsMutableLiveFavourits as MutableLiveData<Favourit_Response>
//    }
    fun getAddFavouritProducts(api:String,Lang:String,token:String): LiveData<AddFav_Response> {
        listProductsMutableLiveAddFavourits = MutableLiveData<AddFav_Response>()
        addFavouritProduct(api,Lang,token)
        return listProductsMutableLiveAddFavourits as MutableLiveData<AddFav_Response>
    }





     fun getProductsId(Lang:String,token:String): LiveData<AllProducts_Response> {
        listProductsMutableLiveData = MutableLiveData<AllProducts_Response>()
        getProductsid(Lang,token)
        return listProductsMutableLiveData as MutableLiveData<AllProducts_Response>
    }

    private fun getDataValues(Lang: String) {
        var map= HashMap<String,String>()
        map.put("lang",Lang)


        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.AllProducts(Lang)
        call?.enqueue(object : Callback, retrofit2.Callback<ProductDeal_Response> {
            override fun onResponse(call: Call<ProductDeal_Response>, response: Response<ProductDeal_Response>) {

                if (response.code() == 200) {
                    listProductsMutableLiveDeals?.setValue(response.body()!!)

                } else  {
                    listProductsMutableLiveDeals?.setValue(null)
                }
            }

            override fun onFailure(call: Call<ProductDeal_Response>, t: Throwable) {
                listProductsMutableLiveDeals?.setValue(null)

            }
        })
    }
    private fun getlatest(page:String,Lang: String,link:String,userId:String?) {
        var map= HashMap<String,String>()
        map.put("page",page)

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.BestSallingProducts(Lang,link,"Bearer "+userId,map)
        call?.enqueue(object : Callback, retrofit2.Callback<AllProducts_Response> {
            override fun onResponse(call: Call<AllProducts_Response>, response: Response<AllProducts_Response>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<AllProducts_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }

    private fun getSimilarProducts(page:String,Lang: String,link:String,userId:String?,product_id:String) {
        var map= HashMap<String,String>()
        map.put("page",page)
        map.put("product_id",product_id)

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.BestSallingProducts(Lang,link,"Bearer "+userId,map)
        call?.enqueue(object : Callback, retrofit2.Callback<AllProducts_Response> {
            override fun onResponse(call: Call<AllProducts_Response>, response: Response<AllProducts_Response>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<AllProducts_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }

//    private fun getFavourits(Lang: String,token:String) {
//        var map= HashMap<String,String>()
//        map.put("lang",Lang)
//
//        var service = ApiClient.getClient()?.create(Service::class.java)
//        val call = service?.FavouritProducts(map,"Bearer "+token)
//        call?.enqueue(object : Callback, retrofit2.Callback<Favourit_Response> {
//            override fun onResponse(call: Call<Favourit_Response>, response: Response<Favourit_Response>) {
//
//                if (response.code() == 200) {
//                    listProductsMutableLiveFavourits?.setValue(response.body()!!)
//
//                } else  {
//                    listProductsMutableLiveFavourits?.setValue(null)
//                }
//            }
//
//            override fun onFailure(call: Call<Favourit_Response>, t: Throwable) {
//                listProductsMutableLiveFavourits?.setValue(null)
//
//            }
//        })
//    }



    private fun getProductsid(Lang: String,token:String) {
        var map= HashMap<String,String>()
        map.put("userId",token)
        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.ProductsByCatId(Lang,map)
        call?.enqueue(object : Callback, retrofit2.Callback<AllProducts_Response> {
            override fun onResponse(call: Call<AllProducts_Response>, response: Response<AllProducts_Response>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<AllProducts_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }


    private fun addFavouritProduct(api:String,favorite_product:String,token: String) {
        var map= HashMap<String,String>()
        map.put("product_id",favorite_product)

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.AddFavouritProducts(api,map,"Bearer "+token)
        call?.enqueue(object : Callback, retrofit2.Callback<AddFav_Response> {
            override fun onResponse(call: Call<AddFav_Response>, response: Response<AddFav_Response>) {

                if (response.code() == 200) {
                    listProductsMutableLiveAddFavourits?.setValue(response.body()!!)

                } else  {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<AddFav_Response>, t: Throwable) {
                listProductsMutableLiveAddFavourits?.setValue(null)

            }
        })
    }

    private fun getProductsFiltered(page:String,Lang: String,link:String,userId:String?,name:String?,cat_id:String?,
                                    sub_id:String?) {
        var map= HashMap<String,String>()
        map.put("page",page)
        name?.let { map.put("name", it) }

        if (cat_id != null) {
            map.put("category_id",cat_id)
        }
        if (sub_id != null) {
            map.put("subcategory_id",sub_id)
        }
        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.FilterProducts(Lang,link,"Bearer "+userId,map)
        call?.enqueue(object : Callback, retrofit2.Callback<AllProducts_Response> {
            override fun onResponse(call: Call<AllProducts_Response>, response: Response<AllProducts_Response>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    No_Product=true
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<AllProducts_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }

    fun getStatus():Boolean{

        return No_Product
    }

}