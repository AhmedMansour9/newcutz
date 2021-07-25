package com.cutzegypt.ui.detailsproduct

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cutzegypt.R
import com.cutzegypt.adapter.*
import com.cutzegypt.base.BaseDialogFragment
import com.cutzegypt.data.remote.model.*
import com.cutzegypt.databinding.FragmentDetailsProductBinding
import com.cutzegypt.ui.addreview.AddReviewFragment
import com.cutzegypt.ui.congratulition.CongratulitionCartActivity
import com.cutzegypt.ui.login.LoginActivity
import com.cutzegypt.utils.SharedData
import com.cutzegypt.utils.Status
import com.smarteist.autoimageslider.SliderAnimations
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


@AndroidEntryPoint
class DetailsProductFragment : BaseDialogFragment<FragmentDetailsProductBinding>(),
    DetailsProductNavigtor {
    override var idLayoutRes: Int = R.layout.fragment_details_product
    private lateinit var details: ProductsResponse.Data
    private var bundle = Bundle()
    private val productViewModel: DetailsProductViewModel by viewModels()
    private val revwiesAdapter = ReviwesAdapter()
    private var data: SharedData? = null
    private var token: String? = String()
    private var type: String="per_unit"
    private var weightId: String? = String()

    private var counter: Int = 1
    private var totalPrice: Int = 1


    var total: Int = 0
    var addetional=""
    var listAddetional :MutableList<Int>?= mutableListOf()
    lateinit var productsGridAdapter: RelatedProductAdapter
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()
        initAdapter()
        getData()
        initUI()
        initSlider()
        getReviwes()
        initInformation()
        checkStock()
        subscribeAddFavourit()
        subscriberemoveFavourit()
        subscribeAddToCart()
        onClickAddCart()
        getRelatedProducts()
        subscribeRelatedProduct()
        setVisablityData()
        onClickRadioButton()

    }

    private fun subscribeRelatedProduct() {
        productViewModel.relatedResponse.observe(this, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    setData(it.data?.data as MutableList<ProductsResponse.Data>)
                }
                Status.LOADING -> {

                }

                Status.ERROR -> {
                }
            }
        })

    }

    private fun setData(data: MutableList<ProductsResponse.Data>) {
        mViewDataBinding.TRelated.visibility = View.VISIBLE
        mViewDataBinding.recyclerProductsRelated.visibility = View.VISIBLE
        productsGridAdapter.setDeveloperList(data)

    }

    fun initAdapter() {
        productsGridAdapter = RelatedProductAdapter(productData = object :
            RelatedProductAdapter.ProductItemListener {
            override fun itemClicked(productData: ProductsResponse.Data?) {
                details = productData!!
                weightId=null
                counter=1
                totalPrice = details.finalPrice.toInt()
                mViewDataBinding.model = details
                total = details.finalPrice.toInt()
                mViewDataBinding.TLastPrice.text = ""
                mViewDataBinding.TQuantity.text="1"
                initSlider()
                getReviwes()
                initInformation()
                checkStock()
                getRelatedProducts()
            }

        })

    }

    private fun getRelatedProducts() {
        productViewModel.getRelated(details.id.toString(), token)
    }

    private fun getWeights() {
        if (details.weights.size > 0) {
            var additonaldapter = SizesAdapter(object : SizesAdapter.ItemListener{
                override fun itemClicked(productData: ProductsResponse.Data.Weights) {
                    counter=1
                    total = productData.price.toInt()
                    totalPrice = productData.price.toInt()
                    weightId=productData.id.toString()
                    total=productData.price.toInt()
                    totalPrice=productData.price.toInt()
                    mViewDataBinding.TPrice.text = total.toString() + resources.getString(R.string.currency)

                }
            })
            additonaldapter.setList(details.weights)
            mViewDataBinding.sizesRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            mViewDataBinding.sizesRecyclerView.adapter = additonaldapter

        } else {
            mViewDataBinding.LinarWeights.visibility = View.GONE
        }


    }

    private fun subscribeAddToCart() {
        productViewModel.addCartResponse.observe(this, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    EventBus.getDefault().postSticky(MessageEvent("cart"))
                    dismissLoading()
                    startActivity(Intent(requireContext(), CongratulitionCartActivity::class.java))

                }
                Status.LOADING -> {
                    showLoading()
                }

                Status.ERROR -> {
                    dismissLoading()
                    error(resources.getString(R.string.error), it.message.toString())

                }
            }
        })
    }

    private fun init() {
        productViewModel.navigator = this
        mViewDataBinding.viewmodel = productViewModel
        data = SharedData(requireContext())
        token = data?.getValue(SharedData.ReturnValue.STRING, "token")

    }


    private fun subscribeAddFavourit() {
        productViewModel.addFavouritResponse.observe(this, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.add_favourit),
                        Toast.LENGTH_SHORT
                    ).show()
                    details.isFavoirte == 1
                    mViewDataBinding.ImgFavourit.setImageResource(R.drawable.ic_favourit)
                    dismissLoading()
                }
                Status.LOADING -> {
                    showLoading()

                }

                Status.ERROR -> {
                    dismissLoading()
                }
            }
        })
    }

    private fun subscriberemoveFavourit() {
        productViewModel.removeFavouritResponse.observe(this, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.remove_favourit),
                        Toast.LENGTH_SHORT
                    ).show()
                    details.isFavoirte == 0
                    mViewDataBinding.ImgFavourit.setImageResource(R.drawable.ic_emptyfavourit)
                    dismissLoading()
                }
                Status.LOADING -> {
                    showLoading()

                }

                Status.ERROR -> {
                    dismissLoading()
                }
            }
        })
    }

    fun checkIsFavourit(postion: Int) {
        if (postion == 1) {
            token?.let { it1 -> productViewModel.removeFavourit(details.id.toString(), it1) }
        } else {
            token?.let { it1 -> productViewModel.addFavourit(details.id.toString(), it1) }
        }

    }


    private fun getReviwes() {
        if (details.rates.size > 0) {
            mViewDataBinding.TNoReviwes.visibility = View.GONE
            mViewDataBinding.reviwesRecyclerView.visibility = View.VISIBLE
            revwiesAdapter.setDeveloperList(details.rates)
        } else {
            mViewDataBinding.TNoReviwes.visibility = View.VISIBLE
            mViewDataBinding.reviwesRecyclerView.visibility = View.GONE
        }
    }

    private fun initUI() {
        mViewDataBinding.reviwesRecyclerView.setHasFixedSize(true)
        mViewDataBinding.reviwesRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.reviwesRecyclerView.adapter = revwiesAdapter

        mViewDataBinding.recyclerProductsRelated.isMotionEventSplittingEnabled = false
        mViewDataBinding.recyclerProductsRelated.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        mViewDataBinding.recyclerProductsRelated.adapter = productsGridAdapter

    }


    private fun initInformation() {
        if (details.instructions.size > 0) {
            var informationAdapter = InformationAdapter(details.instructions)
            mViewDataBinding.moreinformationRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            mViewDataBinding.moreinformationRecyclerView.adapter = informationAdapter
        } else {
            mViewDataBinding.TInstractions.visibility = View.VISIBLE
            mViewDataBinding.moreinformationRecyclerView.visibility = View.GONE
        }

    }


    private fun getData() {
        bundle = this.requireArguments()
        details = bundle.getParcelable("item")!!
        mViewDataBinding.model = details
        total = details.finalPrice.toInt()
        totalPrice = details.finalPrice.toInt()
        if(details.weights.size>0){
            mViewDataBinding.LinarWeights.visibility=View.VISIBLE
        }
        
    }
   fun setVisablityData(){
       if(details.country.isNullOrEmpty()){
           mViewDataBinding.RelaCountry.visibility=View.GONE
       }
       if(details.serveNumber==null){
           mViewDataBinding.Serve.visibility=View.GONE
       }
       if(details.bBQ.equals("no")){
           mViewDataBinding.RelaBbq.visibility=View.GONE
       }
       if(details.panSearing.equals("no")){
           mViewDataBinding.RelaPanSearing.visibility=View.GONE
       }
       if(details.chilled.equals("no")){
           mViewDataBinding.RelaChilled.visibility=View.GONE
       }
       if(details.frozen.equals("no")){
           mViewDataBinding.RelaFrozen.visibility=View.GONE
       }

       if(details.bBQ.equals("no") && details.panSearing.equals("no") && details.panSearing.equals("no") && details.chilled.equals("no")
           && details.frozen.equals("no")){
           mViewDataBinding.Vieww2.visibility=View.GONE
       }

   }

    private fun checkStock() {
        if (details.stock > 0) {
            mViewDataBinding.imgTrue.setImageResource(R.drawable.img_true)
            mViewDataBinding.Stock.text = resources.getString(R.string.in_stock)
        } else {
            mViewDataBinding.imgTrue.setImageResource(R.drawable.img_false)
            mViewDataBinding.Stock.text = resources.getString(R.string.out_stock)
        }
    }

    private fun initSlider() {
        val productsSliderAdapter = SliderProductAdapter(details.images)
        mViewDataBinding.sliderView.setSliderAdapter(productsSliderAdapter)
        mViewDataBinding.sliderView.startAutoCycle();
        mViewDataBinding.sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
    }


    fun toggleArrow(view: View): Boolean {
        return if (view.rotation == 0f) {
            view.animate().setDuration(200).rotation(180f)
            true
        } else {
            view.animate().setDuration(200).rotation(0f)
            false
        }
    }

    fun onClickAddCart() {
        mViewDataBinding.BtnCart.setOnClickListener() {

            addAuthCart()

        }

    }
//    private fun checkRadioButton(){
//        var id: Int = mViewDataBinding.radios.getCheckedRadioButtonId()
//        val radioButton: View = mViewDataBinding.radios.findViewById(id)
//        val radioId = mViewDataBinding.radios.indexOfChild(radioButton)
//        val btn = mViewDataBinding.radios.getChildAt(radioId) as RadioButton
//        val selection = btn.text as String
//        if (selection == "1 kg"  || selection == "1 كيلو") {
//            type="per_unit"
//            total=total*2
//            mViewDataBinding.TPrice.text = total.toString() + resources.getString(R.string.currency)
//        }else {
//            type="gram"
//            total=total/2
//            mViewDataBinding.TPrice.text = total.toString() + resources.getString(R.string.currency)
//        }
//
//    }

    fun onClickRadioButton(){

        mViewDataBinding.LinearKilos.setOnCheckedChangeListener(
             { group, checkedId ->
                val radio: RadioButton = mViewDataBinding.LinearKilos.findViewById(checkedId)
                if (radio.text .equals("1 kg") || radio.text .equals("1 كيلو")) {
                    type = "per_unit"
                    total = total * 2
                    mViewDataBinding.TPrice.text = total.toString() + resources.getString(R.string.currency)
                } else {
                    type = "gram"
                    total = total / 2
                    mViewDataBinding.TPrice.text =
                        total.toString() + resources.getString(R.string.currency)
                }
            })
    }

    fun addAuthCart() {
        if(!listAddetional.isNullOrEmpty()){
            listAddetional?.forEachIndexed { index, i ->


                var L_Postion:Int=listAddetional!!.size-1
                if (L_Postion == index) {
                    addetional = addetional + listAddetional?.get(index)
                } else {
                    addetional = addetional + listAddetional?.get(index) + ","
                }
            }
        }
        var request = RequestAddToCartResponse(
            counter,
            details.id.toString()
            ,addetional
        )
        token?.let { productViewModel.addToCart(it, request) }
    }

    private fun isLogin(): Boolean {
        token = data?.getValue(SharedData.ReturnValue.STRING, "token")
        if (token.isNullOrEmpty()) {
            return false
        }
        return true
    }


    override fun onClickAddReview() {
        if (isLogin()) {
            val newDialogFragment = AddReviewFragment()
            val bundle2 = Bundle()
            bundle2.putParcelable("item", details)
            newDialogFragment.arguments = bundle2
            val transaction: FragmentTransaction =
                requireActivity().supportFragmentManager.beginTransaction()
            newDialogFragment.show(transaction, "New_Dialog_Fragment")
        } else {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }


    }

    override fun onClickToggleReviwes() {
        var checked = toggleArrow(mViewDataBinding.btToggleReviews)
        if (checked) {
            mViewDataBinding.lytExpandReviews.visibility = View.VISIBLE
        } else {
            mViewDataBinding.lytExpandReviews.visibility = View.GONE
        }
    }

    override fun onClickAddFavourit() {
        token = data?.getValue(SharedData.ReturnValue.STRING, "token")
        checkIsFavourit(details.isFavoirte)

    }


    override fun onClickMore() {
        var checked = toggleArrow(mViewDataBinding.btToggleMore)
        if (checked) {
            mViewDataBinding.lytExpandMore.visibility = View.VISIBLE
        } else {
            mViewDataBinding.lytExpandMore.visibility = View.GONE
        }
    }

    override fun onClickDescrption() {
        var checked = toggleArrow(mViewDataBinding.btToggleDescription)
        if (checked) {
            mViewDataBinding.lytExpandDescription.visibility = View.VISIBLE
        } else {
            mViewDataBinding.lytExpandDescription.visibility = View.GONE
        }
    }

    override fun onClickMinus() {
        checkMinCart()
    }

    override fun onCLickFinish() {
        activity?.supportFragmentManager?.popBackStack()
    }

    override fun onClickAddetional() {
        var checked = toggleArrow(mViewDataBinding.btSizes)
        if (checked) {
            mViewDataBinding.lytExpandSizes.visibility = View.VISIBLE
        } else {
            mViewDataBinding.lytExpandSizes.visibility = View.GONE
        }
    }

    override fun onClickExpirment() {
        var checked = toggleArrow(mViewDataBinding.btToggleExpirement)
        if (checked) {
            mViewDataBinding.lytExpandExpiremnt.visibility = View.VISIBLE
        } else {
            mViewDataBinding.lytExpandExpiremnt.visibility = View.GONE
        }
    }

    override fun onClickPlus() {
        checkMaxCart()
    }

    @SuppressLint("SetTextI18n")
    fun checkMaxCart() {
        val countValue: Int = details.stock
        val defult = mViewDataBinding.TQuantity.text.toString().toInt()
        if (countValue > defult) {
            counter++
            var price=total * counter
            mViewDataBinding.TQuantity.text = counter.toString()
//            total = total + price
            mViewDataBinding.TPrice.text = price.toString() + resources.getString(R.string.currency)
        } else {
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.max_cart) + " " + details.stock,
                Toast.LENGTH_SHORT
            ).show()
            mViewDataBinding.ImgPlus.isEnabled = false
        }

    }

    @SuppressLint("SetTextI18n")
    fun checkMinCart() {
        if (counter > 1) {
            mViewDataBinding.ImgPlus.isEnabled = true
            counter--
            var price=total * counter
            mViewDataBinding.TQuantity.text = counter.toString()
//            total = total - price
            mViewDataBinding.TPrice.text = price.toString() + resources.getString(R.string.currency)
        }
    }


    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    fun onMessageEvent(messsg: MessageEvent) {/* Do something */
        if (messsg.Message.equals("login")) {
            token = data?.getValue(SharedData.ReturnValue.STRING, "token")
        }
    };
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

    }
}