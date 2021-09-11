package com.hadrmout.ui.detailsproduct

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hadrmout.R
import com.hadrmout.adapter.RelatedProductAdapter
import com.hadrmout.adapter.SizesAdapter
import com.hadrmout.adapter.SliderProductAdapter
import com.hadrmout.base.BaseDialogFragment
import com.hadrmout.data.remote.model.MessageEvent
import com.hadrmout.data.remote.model.ProductsResponse
import com.hadrmout.data.remote.model.RequestAddToCartResponse
import com.hadrmout.databinding.FragmentDetailsProductBinding
import com.hadrmout.ui.addreview.AddReviewFragment
import com.hadrmout.ui.congratulition.CongratulitionCartActivity
import com.hadrmout.ui.login.LoginActivity
import com.hadrmout.utils.SharedData
import com.hadrmout.utils.Status
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
    private var data: SharedData? = null
    private var token: String? = String()
    private var type: String = "per_unit"
    private var weightId: String? = null
    private var counter: Int = 1
    private var totalPrice: Double = 1.0


    var total: Double = 0.0
    var addetional = ""
    var listAddetional: MutableList<Int>? = mutableListOf()
    lateinit var productsGridAdapter: RelatedProductAdapter
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()
        initAdapter()
        getData()
        initUI()
        getAddetional()
        initSlider()
        checkStock()
        subscribeAddFavourit()
        subscriberemoveFavourit()
        subscribeAddToCart()
        onClickAddCart()


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
                counter = 1
                totalPrice = details.finalPrice.toDouble()
                mViewDataBinding.model = details
                total = details.finalPrice.toDouble()
                mViewDataBinding.TLastPrice.text = ""
                mViewDataBinding.TQuantity.text = "1"
                initSlider()
                checkStock()
            }

        })

    }

    private fun getRelatedProducts() {
        productViewModel.getRelated(details.id.toString(), token)
    }

    private fun getAddetional() {
        val additonaldapter = SizesAdapter(object : SizesAdapter.ItemListener {
            override fun itemClicked(productData: ProductsResponse.Data.Addition) {
                if (listAddetional.isNullOrEmpty()) {
                    listAddetional?.add(productData.id)
                } else {
                    listAddetional?.forEachIndexed { index, s ->
                        if (listAddetional?.get(index)!!.equals(productData.id)) {
                            listAddetional?.removeAt(index)
                        }
                    }
                    listAddetional?.add(productData.id)
                }


            }
        })
        if (details.additions.size>0) {
            additonaldapter.setList(details.additions as MutableList<ProductsResponse.Data.Addition>)
        }
        mViewDataBinding.additonalRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        mViewDataBinding.additonalRecyclerView.adapter = additonaldapter

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
                    details.isFavoirte = true
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
                    details.isFavoirte = false
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

    fun checkIsFavourit(postion: Boolean) {
        if (postion) {
            token?.let { it1 -> productViewModel.removeFavourit(details.id.toString(), it1) }
        } else {
            token?.let { it1 -> productViewModel.addFavourit(details.id.toString(), it1) }
        }

    }




    private fun initUI() {

        mViewDataBinding.recyclerProductsRelated.isMotionEventSplittingEnabled = false
        mViewDataBinding.recyclerProductsRelated.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        mViewDataBinding.recyclerProductsRelated.adapter = productsGridAdapter

    }


    @SuppressLint("SetTextI18n")
    private fun getData() {
        bundle = this.requireArguments()
        details = bundle.getParcelable("item")!!
        mViewDataBinding.model = details
        total = details.finalPrice.toDouble()
        totalPrice = details.finalPrice.toDouble()
        mViewDataBinding.TTotalPrices.text =
            totalPrice.toString() + " " + resources.getString(R.string.currency)
        setWeights(details.weights)
        checkVisableData()

    }

    private fun checkVisableData() {
        if(details.additions.size <= 0){
            mViewDataBinding.additonal.visibility=View.GONE
            mViewDataBinding.additonalRecyclerView.visibility=View.GONE
        }
        if(details.weights.size <= 0){
            mViewDataBinding.weights.visibility=View.GONE
            mViewDataBinding.SCountry.visibility=View.GONE
        }
    }

    private fun setWeights(weights: List<ProductsResponse.Data.Weights>) {
        val adapter =
            ArrayAdapter(requireContext(), R.layout.custom_spinner, weights)
        adapter.setDropDownViewResource(R.layout.custom_dropdown)
        mViewDataBinding.spinnerAdapter = adapter

        mViewDataBinding.SCountry.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    var title = mViewDataBinding.SCountry.selectedItem.toString();
                    weights.forEachIndexed { index, weig ->
                        if (title.equals(weights.get(index).title)) {
                            mViewDataBinding.TTotalPrices.text = weights.get(index).price.toString()+ " " + resources.getString(R.string.currency)
                            weightId = weights.get(index).id.toString()
                        }
                    }


                }

            }

//            mViewModel.itemPositionCountry.observe(this, Observer { postion ->
//                if (mViewModel.itemPositionCountry.value != null) {
//                    listCities?.clear()
//                    mViewDataBinding.spinnerAdapterCities?.notifyDataSetChanged()
//                    adapter.getItem(postion)?.id
//                    country_Id = "EG"
//                    bindingAdapterCities(adapter.getItem(postion)?.city)
//                    mViewModel.itemPositionCountry.value = null
//                }
//            })

    }

    private fun checkStock() {
//        if (details.stock > 0) {
//            mViewDataBinding.imgTrue.setImageResource(R.drawable.img_true)
//            mViewDataBinding.Stock.text = resources.getString(R.string.in_stock)
//        } else {
//            mViewDataBinding.imgTrue.setImageResource(R.drawable.img_false)
//            mViewDataBinding.Stock.text = resources.getString(R.string.out_stock)
//        }
    }

    private fun initSlider() {
        val productsSliderAdapter = SliderProductAdapter(details.images as MutableList<String>)
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
            weightId,
            listAddetional
        )
        token?.let { productViewModel.addToCart(it, request, details.id.toString()) }
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



    override fun onClickAddFavourit() {
        token = data?.getValue(SharedData.ReturnValue.STRING, "token")
        checkIsFavourit(details.isFavoirte)

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


    override fun onClickPlus() {
        checkMaxCart()
    }

    @SuppressLint("SetTextI18n")
    fun checkMaxCart() {
//        val countValue: Int = details.stock
        val defult = mViewDataBinding.TQuantity.text.toString().toInt()
//        if (countValue > defult) {
        counter++
        var price = total * counter
        mViewDataBinding.TQuantity.text = counter.toString()
//            total = total + price
        mViewDataBinding.TTotalPrices.text =
            price.toString() + resources.getString(R.string.currency)
//        } else {
//            Toast.makeText(
//                requireContext(),
//                resources.getString(R.string.max_cart) + " " + details.stock,
//                Toast.LENGTH_SHORT
//            ).show()
//            mViewDataBinding.ImgPlus.isEnabled = false
//        }

    }

    @SuppressLint("SetTextI18n")
    fun checkMinCart() {
        if (counter > 1) {
            mViewDataBinding.ImgPlus.isEnabled = true
            counter--
            var price = total * counter
            mViewDataBinding.TQuantity.text = counter.toString()
//            total = total - price
//            mViewDataBinding.TTotalPrices.text =
//                price.toString() + resources.getString(R.string.currency)
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