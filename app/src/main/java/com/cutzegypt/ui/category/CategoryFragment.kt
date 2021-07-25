package com.cutzegypt.ui.category

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.cutzegypt.R
import com.cutzegypt.adapter.CategoryAdapter
import com.cutzegypt.adapter.SubCategoriesAdapter
import com.cutzegypt.base.BaseFragment
import com.cutzegypt.data.remote.model.SectonsResponse
import com.cutzegypt.databinding.CategoryFragmentBinding
import com.cutzegypt.ui.bottomnavigate.BottomNavigateActivity
import com.cutzegypt.ui.nointernet.NoInternertActivity
import com.cutzegypt.utils.SharedData
import com.cutzegypt.utils.isConnected
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CategoryFragment : BaseFragment<CategoryFragmentBinding>(), CategoriesNavigator {

    override var idLayoutRes: Int = R.layout.category_fragment
    private var data: SharedData? = null
    lateinit var items: SectonsResponse.Data
    lateinit var bundle: Bundle
    var type: String? = String()
    private val mViewModel: CategoriesViewModel by viewModels()
    var listParts: MutableList<SectonsResponse.Data.Category.Parts>? = mutableListOf()
    var list: MutableList<SectonsResponse.Data.Category.SubCategory>? = mutableListOf()
    private lateinit var catAdapter : CategoryAdapter

    private fun onClickItem(productData: SectonsResponse.Data.Category) {
        if (type.equals("section")) {
            if (productData.id == 11 || productData.id == 12 || productData.id == 13 || productData.id == 15 || productData.id == 16
                || productData.id == 19 || productData.id == 20 || productData.id == 28 || productData.id == 14) {
                checkAnimals(productData.id!!)
            } else {
                hideBef()
                hideVeel()
                hideLamp()
                hideFish()
                hideDuck()
                hideChecken()
                hideOstric()
                hideShrimp()
            }
        }
    }

    private var subCategoryAdapter =
        SubCategoriesAdapter(object : SubCategoriesAdapter.CategoryItemListener {
            override fun itemClicked(productData: SectonsResponse.Data.Category.SubCategory) {
                val bundle = Bundle()
                bundle.putString("type","sub")
                bundle.putParcelable("cat", productData)
                Navigation.findNavController(mViewDataBinding.root)
                    .navigate(R.id.action_T_Categories_to_productsById, bundle);
            }
        })

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewDataBinding.registerViewModel = mViewModel
        mViewModel.navigator = this
        init()
        initAdapter()
        initRecycle()
        getData()
//        setGravity()
    }

    private fun initAdapter() {
        catAdapter = CategoryAdapter(requireContext(),object : CategoryAdapter.CategoryItemListener {
            override fun itemClicked(productData: SectonsResponse.Data.Category) {
                onClickItem(productData)
                list = productData.subCategories
                listParts=productData.parts
                list?.let { subCategoryAdapter.setList(it) }
            }
        })
    }

    private fun setGravity() {
        if(BottomNavigateActivity.Lang.equals("ar")){
            mViewDataBinding.recyclerCategroies.layoutDirection=View.LAYOUT_DIRECTION_RTL
        }
        if(BottomNavigateActivity.Lang.equals("ar")){
            if  (type.equals("section")){
                mViewDataBinding.recyclerSubCategroies.layoutDirection=View.LAYOUT_DIRECTION_RTL
            }
        }
    }

    private fun getData() {
        bundle = requireArguments()
        items = bundle.getParcelable("data")!!
        items.categories?.let { catAdapter.setList(it) }
        type = bundle.getString("type")
    }

    private fun checkAnimals(postion: Int) {
        if (postion == 11) {
            mViewDataBinding.recyclerSubCategroies.visibility = View.GONE
            mViewDataBinding.ImgBeef.visibility = View.VISIBLE
            mViewDataBinding.RelaBeef.visibility = View.VISIBLE
            mViewDataBinding.ImgVeel.visibility = View.GONE
            mViewDataBinding.ImgLamp.visibility = View.GONE
            mViewDataBinding.RelaLamb.visibility = View.GONE
            mViewDataBinding.ImgFish.visibility = View.GONE
            mViewDataBinding.RelaFish.visibility = View.GONE
            mViewDataBinding.ImgDuck.visibility = View.GONE
            mViewDataBinding.RelaDuck.visibility = View.GONE
            mViewDataBinding.ImgChicken.visibility = View.GONE
            mViewDataBinding.RelaChicken.visibility = View.GONE
            mViewDataBinding.ImgOstrich.visibility = View.GONE
            mViewDataBinding.RelaOstrich.visibility = View.GONE
            mViewDataBinding.ImgShramp.visibility = View.GONE
            mViewDataBinding.RelaShramp.visibility = View.GONE
            mViewDataBinding.ImgTurkey.visibility = View.GONE
            mViewDataBinding.RelaTurkey.visibility = View.GONE
        }

        if (postion == 12) {
            mViewDataBinding.ImgBeef.visibility = View.GONE
            mViewDataBinding.RelaBeef.visibility = View.GONE
            mViewDataBinding.recyclerSubCategroies.visibility = View.GONE
            mViewDataBinding.ImgVeel.visibility = View.VISIBLE
            mViewDataBinding.RelaVeel.visibility = View.VISIBLE
            mViewDataBinding.ImgLamp.visibility = View.GONE
            mViewDataBinding.RelaLamb.visibility = View.GONE
            mViewDataBinding.ImgFish.visibility = View.GONE
            mViewDataBinding.RelaFish.visibility = View.GONE
            mViewDataBinding.ImgDuck.visibility = View.GONE
            mViewDataBinding.RelaDuck.visibility = View.GONE
            mViewDataBinding.ImgChicken.visibility = View.GONE
            mViewDataBinding.RelaChicken.visibility = View.GONE
            mViewDataBinding.ImgOstrich.visibility = View.GONE
            mViewDataBinding.RelaOstrich.visibility = View.GONE
            mViewDataBinding.ImgShramp.visibility = View.GONE
            mViewDataBinding.RelaShramp.visibility = View.GONE
            mViewDataBinding.ImgTurkey.visibility = View.GONE
            mViewDataBinding.RelaTurkey.visibility = View.GONE
        }
        if (postion == 13) {
            mViewDataBinding.ImgBeef.visibility = View.GONE
            mViewDataBinding.RelaBeef.visibility = View.GONE
            mViewDataBinding.recyclerSubCategroies.visibility = View.GONE
            mViewDataBinding.ImgVeel.visibility = View.GONE
            mViewDataBinding.RelaVeel.visibility = View.GONE
            mViewDataBinding.ImgLamp.visibility = View.VISIBLE
            mViewDataBinding.RelaLamb.visibility = View.VISIBLE
            mViewDataBinding.ImgFish.visibility = View.GONE
            mViewDataBinding.RelaFish.visibility = View.GONE
            mViewDataBinding.ImgDuck.visibility = View.GONE
            mViewDataBinding.RelaDuck.visibility = View.GONE
            mViewDataBinding.ImgChicken.visibility = View.GONE
            mViewDataBinding.RelaChicken.visibility = View.GONE
            mViewDataBinding.ImgOstrich.visibility = View.GONE
            mViewDataBinding.RelaOstrich.visibility = View.GONE
            mViewDataBinding.ImgShramp.visibility = View.GONE
            mViewDataBinding.RelaShramp.visibility = View.GONE
            mViewDataBinding.ImgTurkey.visibility = View.GONE
            mViewDataBinding.RelaTurkey.visibility = View.GONE
        }
        if (postion == 16) {
            mViewDataBinding.ImgBeef.visibility = View.GONE
            mViewDataBinding.RelaBeef.visibility = View.GONE
            mViewDataBinding.recyclerSubCategroies.visibility = View.GONE
            mViewDataBinding.ImgVeel.visibility = View.GONE
            mViewDataBinding.RelaVeel.visibility = View.GONE
            mViewDataBinding.ImgLamp.visibility = View.GONE
            mViewDataBinding.RelaLamb.visibility = View.GONE
            mViewDataBinding.ImgFish.visibility = View.VISIBLE
            mViewDataBinding.RelaFish.visibility = View.VISIBLE
            mViewDataBinding.ImgDuck.visibility = View.GONE
            mViewDataBinding.RelaDuck.visibility = View.GONE
            mViewDataBinding.ImgChicken.visibility = View.GONE
            mViewDataBinding.RelaChicken.visibility = View.GONE
            mViewDataBinding.ImgOstrich.visibility = View.GONE
            mViewDataBinding.RelaOstrich.visibility = View.GONE
            mViewDataBinding.ImgShramp.visibility = View.GONE
            mViewDataBinding.RelaShramp.visibility = View.GONE
            mViewDataBinding.ImgTurkey.visibility = View.GONE
            mViewDataBinding.RelaTurkey.visibility = View.GONE
        }

        if (postion == 19) {
            mViewDataBinding.ImgBeef.visibility = View.GONE
            mViewDataBinding.RelaBeef.visibility = View.GONE
            mViewDataBinding.recyclerSubCategroies.visibility = View.GONE
            mViewDataBinding.ImgVeel.visibility = View.GONE
            mViewDataBinding.RelaVeel.visibility = View.GONE
            mViewDataBinding.ImgLamp.visibility = View.GONE
            mViewDataBinding.RelaLamb.visibility = View.GONE
            mViewDataBinding.ImgFish.visibility = View.GONE
            mViewDataBinding.RelaFish.visibility = View.GONE
            mViewDataBinding.ImgDuck.visibility = View.VISIBLE
            mViewDataBinding.RelaDuck.visibility = View.VISIBLE
            mViewDataBinding.ImgChicken.visibility = View.GONE
            mViewDataBinding.RelaChicken.visibility = View.GONE
            mViewDataBinding.ImgOstrich.visibility = View.GONE
            mViewDataBinding.RelaOstrich.visibility = View.GONE
            mViewDataBinding.ImgShramp.visibility = View.GONE
            mViewDataBinding.RelaShramp.visibility = View.GONE
            mViewDataBinding.ImgTurkey.visibility = View.GONE
            mViewDataBinding.RelaTurkey.visibility = View.GONE
        }

        if (postion == 20) {
            mViewDataBinding.ImgBeef.visibility = View.GONE
            mViewDataBinding.RelaBeef.visibility = View.GONE
            mViewDataBinding.recyclerSubCategroies.visibility = View.GONE
            mViewDataBinding.ImgVeel.visibility = View.GONE
            mViewDataBinding.RelaVeel.visibility = View.GONE
            mViewDataBinding.ImgLamp.visibility = View.GONE
            mViewDataBinding.RelaLamb.visibility = View.GONE
            mViewDataBinding.ImgFish.visibility = View.GONE
            mViewDataBinding.RelaFish.visibility = View.GONE
            mViewDataBinding.ImgDuck.visibility = View.GONE
            mViewDataBinding.RelaDuck.visibility = View.GONE
            mViewDataBinding.ImgChicken.visibility = View.VISIBLE
            mViewDataBinding.RelaChicken.visibility = View.VISIBLE
            mViewDataBinding.ImgOstrich.visibility = View.GONE
            mViewDataBinding.RelaOstrich.visibility = View.GONE
            mViewDataBinding.ImgShramp.visibility = View.GONE
            mViewDataBinding.RelaShramp.visibility = View.GONE
            mViewDataBinding.ImgTurkey.visibility = View.GONE
            mViewDataBinding.RelaTurkey.visibility = View.GONE
        }

        if (postion == 15) {
            mViewDataBinding.ImgBeef.visibility = View.GONE
            mViewDataBinding.RelaBeef.visibility = View.GONE
            mViewDataBinding.recyclerSubCategroies.visibility = View.GONE
            mViewDataBinding.ImgVeel.visibility = View.GONE
            mViewDataBinding.RelaVeel.visibility = View.GONE
            mViewDataBinding.ImgLamp.visibility = View.GONE
            mViewDataBinding.RelaLamb.visibility = View.GONE
            mViewDataBinding.ImgFish.visibility = View.GONE
            mViewDataBinding.RelaFish.visibility = View.GONE
            mViewDataBinding.ImgDuck.visibility = View.GONE
            mViewDataBinding.RelaDuck.visibility = View.GONE
            mViewDataBinding.ImgChicken.visibility = View.GONE
            mViewDataBinding.RelaChicken.visibility = View.GONE
            mViewDataBinding.ImgOstrich.visibility = View.VISIBLE
            mViewDataBinding.RelaOstrich.visibility = View.VISIBLE
            mViewDataBinding.ImgShramp.visibility = View.GONE
            mViewDataBinding.RelaShramp.visibility = View.GONE
            mViewDataBinding.ImgTurkey.visibility = View.GONE
            mViewDataBinding.RelaTurkey.visibility = View.GONE
        }

        if (postion == 28) {
            mViewDataBinding.ImgBeef.visibility = View.GONE
            mViewDataBinding.RelaBeef.visibility = View.GONE
            mViewDataBinding.recyclerSubCategroies.visibility = View.GONE
            mViewDataBinding.ImgVeel.visibility = View.GONE
            mViewDataBinding.RelaVeel.visibility = View.GONE
            mViewDataBinding.ImgLamp.visibility = View.GONE
            mViewDataBinding.RelaLamb.visibility = View.GONE
            mViewDataBinding.ImgFish.visibility = View.GONE
            mViewDataBinding.RelaFish.visibility = View.GONE
            mViewDataBinding.ImgDuck.visibility = View.GONE
            mViewDataBinding.RelaDuck.visibility = View.GONE
            mViewDataBinding.ImgChicken.visibility = View.GONE
            mViewDataBinding.RelaChicken.visibility = View.GONE
            mViewDataBinding.ImgOstrich.visibility = View.GONE
            mViewDataBinding.RelaOstrich.visibility = View.GONE
            mViewDataBinding.ImgShramp.visibility = View.VISIBLE
            mViewDataBinding.RelaShramp.visibility = View.VISIBLE
            mViewDataBinding.ImgTurkey.visibility = View.GONE
            mViewDataBinding.RelaTurkey.visibility = View.GONE
        }

        if (postion == 14) {
            mViewDataBinding.ImgBeef.visibility = View.GONE
            mViewDataBinding.RelaBeef.visibility = View.GONE
            mViewDataBinding.recyclerSubCategroies.visibility = View.GONE
            mViewDataBinding.ImgVeel.visibility = View.GONE
            mViewDataBinding.RelaVeel.visibility = View.GONE
            mViewDataBinding.ImgLamp.visibility = View.GONE
            mViewDataBinding.RelaLamb.visibility = View.GONE
            mViewDataBinding.ImgFish.visibility = View.GONE
            mViewDataBinding.RelaFish.visibility = View.GONE
            mViewDataBinding.ImgDuck.visibility = View.GONE
            mViewDataBinding.RelaDuck.visibility = View.GONE
            mViewDataBinding.ImgChicken.visibility = View.GONE
            mViewDataBinding.RelaChicken.visibility = View.GONE
            mViewDataBinding.ImgOstrich.visibility = View.GONE
            mViewDataBinding.RelaOstrich.visibility = View.GONE
            mViewDataBinding.ImgShramp.visibility = View.GONE
            mViewDataBinding.RelaShramp.visibility = View.GONE
            mViewDataBinding.ImgTurkey.visibility = View.VISIBLE
            mViewDataBinding.RelaTurkey.visibility = View.VISIBLE

        }
    }

    private fun hideLamp() {
        mViewDataBinding.recyclerSubCategroies.visibility = View.VISIBLE
        mViewDataBinding.ImgLamp.visibility = View.GONE
        mViewDataBinding.RelaLamb.visibility = View.GONE
    }
    private fun hideFish() {
        mViewDataBinding.recyclerSubCategroies.visibility = View.VISIBLE
        mViewDataBinding.ImgFish.visibility = View.GONE
        mViewDataBinding.RelaFish.visibility = View.GONE
    }

    private fun hideBef() {
        mViewDataBinding.recyclerSubCategroies.visibility = View.VISIBLE
        mViewDataBinding.ImgBeef.visibility = View.GONE
        mViewDataBinding.RelaBeef.visibility = View.GONE
    }

    private fun hideVeel() {
        mViewDataBinding.recyclerSubCategroies.visibility = View.VISIBLE
        mViewDataBinding.ImgVeel.visibility = View.GONE
        mViewDataBinding.RelaVeel.visibility = View.GONE
    }
    private fun hideDuck(){
        mViewDataBinding.ImgDuck.visibility = View.GONE
        mViewDataBinding.RelaDuck.visibility = View.GONE
        mViewDataBinding.recyclerSubCategroies.visibility = View.VISIBLE
    }

    private fun hideChecken(){
        mViewDataBinding.ImgChicken.visibility = View.GONE
        mViewDataBinding.RelaChicken.visibility = View.GONE
        mViewDataBinding.recyclerSubCategroies.visibility = View.VISIBLE
    }
    private fun hideOstric(){
        mViewDataBinding.ImgOstrich.visibility = View.GONE
        mViewDataBinding.RelaOstrich.visibility = View.GONE
        mViewDataBinding.recyclerSubCategroies.visibility = View.VISIBLE
    }
    private fun hideShrimp(){
        mViewDataBinding.ImgShramp.visibility = View.GONE
        mViewDataBinding.RelaShramp.visibility = View.GONE
        mViewDataBinding.recyclerSubCategroies.visibility = View.VISIBLE
    }
    private fun initRecycle() {
        mViewDataBinding.recyclerCategroies.setHasFixedSize(true)
        mViewDataBinding.recyclerCategroies.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        mViewDataBinding.recyclerCategroies.adapter = catAdapter

        mViewDataBinding.recyclerSubCategroies.setHasFixedSize(true)
        mViewDataBinding.recyclerSubCategroies.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        mViewDataBinding.recyclerSubCategroies.adapter = subCategoryAdapter
    }

    private fun init() {
        data = SharedData(requireContext())

    }


    override fun onStart() {
        super.onStart()
        checkInternet()
    }

    fun checkInternet() {
        if (!requireContext().isConnected) {
            startActivity(Intent(requireContext(), NoInternertActivity::class.java))
        }
    }

    override fun onClickNeck() {
        listParts?.get(5)?.let { openProducts(it) }

    }

    override fun onClickRib() {
        listParts?.get(1)?.let { openProducts(it) }

    }

    override fun onClickBirsket() {
        listParts?.get(7)?.let { openProducts(it) }
    }

    override fun onClickShank() {
        listParts?.get(8)?.let { openProducts(it) }

    }

    override fun onClickFlank() {
        list?.get(11)?.let { openProducts(it) }

    }

    override fun onClickRound() {
        listParts?.get(0)?.let { openProducts(it) }
    }

    override fun onClickChuck() {
        listParts?.get(6)?.let { openProducts(it) }

    }

    override fun onClickBottomSillirion() {
        list?.get(14)?.let { openProducts(it) }

    }
    override fun onClickHead() {
        listParts?.get(2)?.let { openProducts(it) }
    }

    override fun onClickBottomTongue() {
        listParts?.get(3)?.let { openProducts(it) }
    }

    override fun onClickBottomCheek() {
        listParts?.get(4)?.let { openProducts(it) }
    }

    override fun onClickPlate() {
        listParts?.get(9)?.let { openProducts(it) }
    }

    override fun onClickShortLion() {
        listParts?.get(10)?.let { openProducts(it) }
    }

    override fun onClickTenderLion() {
        listParts?.get(13)?.let { openProducts(it) }
    }

    override fun onClickTail() {
        listParts?.get(15)?.let { openProducts(it) }
    }

    override fun onClickBeefRump() {
        listParts?.get(17)?.let { openProducts(it) }
    }

    override fun onClickBeefSilverside() {
        listParts?.get(18)?.let { openProducts(it) }
    }

    override fun onClickBeefTopside() {
        listParts?.get(19)?.let { openProducts(it) }
    }


    override fun onClickVealNeck() {
        listParts?.get(6)?.let { openProducts(it) }
    }

    override fun onClickVealRib() {
        listParts?.get(4)?.let { openProducts(it) }
    }

    override fun onClickVealBirsket() {
        listParts?.get(2)?.let { openProducts(it) }
    }

    override fun onClickVealShank() {
        listParts?.get(3)?.let { openProducts(it) }
    }

    override fun onClickVealFlank() {
        listParts?.get(8)?.let { openProducts(it) }
    }

    override fun onClickVealRound() {
        listParts?.get(9)?.let { openProducts(it) }
    }

    override fun onClickVealHead() {
        listParts?.get(0)?.let { openProducts(it) }

    }

    override fun onClickVealShortLion() {
        listParts?.get(5)?.let { openProducts(it) }
    }

    override fun onClickVealSilrion() {
        listParts?.get(7)?.let { openProducts(it) }
    }

    override fun onClickVealChuck() {
        listParts?.get(1)?.let { openProducts(it) }
    }

    override fun onClickVealRump() {
        listParts?.get(10)?.let { openProducts(it) }
    }

    override fun onClickVealLiver() {
        listParts?.get(11)?.let { openProducts(it) }
    }

    override fun onClickVealTopside() {
        listParts?.get(12)?.let { openProducts(it) }
    }

    override fun onClickVealSilverside() {
        listParts?.get(13)?.let { openProducts(it) }
    }


    override fun onClickLambNeck() {
        listParts?.get(2)?.let { openProducts(it) }
    }

    override fun onClickLambRib() {
        listParts?.get(11)?.let { openProducts(it) }
    }

    override fun onClickLambBreast() {
        listParts?.get(4)?.let { openProducts(it) }
    }

    override fun onClickLambShank() {
        listParts?.get(10)?.let { openProducts(it) }
    }

    override fun onClickLambFlank() {
        listParts?.get(7)?.let { openProducts(it) }
    }

    override fun onClickLambShoulder() {
        listParts?.get(3)?.let { openProducts(it) }
    }

    override fun onClickLambLoin() {
        listParts?.get(6)?.let { openProducts(it) }
    }

    override fun onClickLambSirLoin() {
        listParts?.get(8)?.let { openProducts(it) }
    }

    override fun onClickLambLeg() {
        listParts?.get(9)?.let { openProducts(it) }
    }



    override fun onClickfishHead() {
        listParts?.get(0)?.let { openProducts(it) }

    }

    override fun onClickfishKama() {
        listParts?.get(1)?.let { openProducts(it) }
    }

    override fun onClickfishblackmeat() {
        listParts?.get(2)?.let { openProducts(it) }
    }

    override fun onClickfishTail() {
        listParts?.get(3)?.let { openProducts(it) }
    }

    override fun onClickfishTailMeat() {
        listParts?.get(4)?.let { openProducts(it) }
    }

    override fun onClickfishCaviar() {
        listParts?.get(5)?.let { openProducts(it) }
    }

    override fun onClickfishAdbomenMeat() {
        listParts?.get(6)?.let { openProducts(it) }
    }




    override fun onClickDuckHead() {
        listParts?.get(0)?.let { openProducts(it) }
    }

    override fun onClickDuckNeck() {
        listParts?.get(1)?.let { openProducts(it) }
    }

    override fun onClickDuckBreast() {
        listParts?.get(2)?.let { openProducts(it) }
    }

    override fun onClickDuckWing() {
        listParts?.get(3)?.let { openProducts(it) }
    }

    override fun onClickDuckback() {
        listParts?.get(4)?.let { openProducts(it) }
    }

    override fun onClickDuckTenderlion() {
        listParts?.get(5)?.let { openProducts(it) }
    }

    override fun onClickDuckTip() {
        listParts?.get(6)?.let { openProducts(it) }
    }

    override fun onClickDuckDrumstick() {
        listParts?.get(7)?.let { openProducts(it) }
    }

    override fun onClickDuckTail() {
        listParts?.get(8)?.let { openProducts(it) }
    }



    override fun onClickChickenNeck() {
        listParts?.get(0)?.let { openProducts(it) }
    }

    override fun onClickChickenBreast() {
        listParts?.get(1)?.let { openProducts(it) }
    }

    override fun onClickChickenLeg() {
        listParts?.get(2)?.let { openProducts(it) }
    }

    override fun onClickChickenBack() {
        listParts?.get(3)?.let { openProducts(it) }
    }

    override fun onClickChickenThight() {
        listParts?.get(4)?.let { openProducts(it) }
    }

    override fun onClickChickenWing() {
        listParts?.get(5)?.let { openProducts(it) }
    }





    override fun onClickOstrichHead() {
        listParts?.get(0)?.let { openProducts(it) }
    }

    override fun onClickOstrichNeck() {
        listParts?.get(1)?.let { openProducts(it) }
    }

    override fun onClickOstrichTopFilet() {
        listParts?.get(2)?.let { openProducts(it) }
    }

    override fun onClickOstrichMoon() {
        listParts?.get(3)?.let { openProducts(it) }
    }

    override fun onClickOstrichPearl() {
        listParts?.get(4)?.let { openProducts(it) }
    }

    override fun onClickOstrichOyster() {
        listParts?.get(5)?.let { openProducts(it) }
    }

    override fun onClickOstrichTips() {
        listParts?.get(6)?.let { openProducts(it) }
    }

    override fun onClickOstrichFan() {
        listParts?.get(7)?.let { openProducts(it) }
    }

    override fun onClickOstrichDrumOuter() {
        listParts?.get(8)?.let { openProducts(it) }
    }

    override fun onClickOstrichEyeFilet() {
        listParts?.get(9)?.let { openProducts(it) }
    }

    override fun onClickOstrichouterstripFilet() {
        listParts?.get(10)?.let { openProducts(it) }
    }

    override fun onClickOstrichTenderLoin() {
        listParts?.get(11)?.let { openProducts(it) }
    }

    override fun onClickOstrichEyeTail() {
        listParts?.get(12)?.let { openProducts(it) }
    }

    override fun onClickOstrichEyeLeg() {
        listParts?.get(13)?.let { openProducts(it) }
    }

    override fun onClickSharpHead() {
        listParts?.get(0)?.let { openProducts(it) }

    }

    override fun onClickSharpTail() {
        listParts?.get(1)?.let { openProducts(it) }

    }

    override fun onClickTurkeyBack() {
        listParts?.get(0)?.let { openProducts(it) }
    }

    override fun onClickTurkeyTail() {
        listParts?.get(1)?.let { openProducts(it) }
    }

    override fun onClickTurkeyWing() {
        listParts?.get(2)?.let { openProducts(it) }
    }

    override fun onClickTurkeyTip() {
        listParts?.get(3)?.let { openProducts(it) }

    }

    override fun onClickTurkeyThight() {
        listParts?.get(4)?.let { openProducts(it) }
    }

    override fun onClickTurkeyTenderloin() {
        listParts?.get(5)?.let { openProducts(it) }
    }

    override fun onClickTurkeyDrumstick() {
        listParts?.get(6)?.let { openProducts(it) }
    }

    override fun onClickTurkeyBreast() {
        listParts?.get(7)?.let { openProducts(it) }
    }

    override fun onClickTurkeyNick() {
        listParts?.get(8)?.let { openProducts(it) }
    }

    override fun onClickTurkeyHead() {
        listParts?.get(9)?.let { openProducts(it) }

    }


    fun openProducts(productData: SectonsResponse.Data.Category.Parts) {
        val bundle = Bundle()
        bundle.putString("type","parts")
        bundle.putParcelable("cat", productData)
        Navigation.findNavController(mViewDataBinding.root)
            .navigate(R.id.action_T_Categories_to_productsById, bundle);
    }

    fun openProducts(productData: SectonsResponse.Data.Category.SubCategory) {
        val bundle = Bundle()
        bundle.putString("type","sub")
        bundle.putParcelable("cat", productData)
        Navigation.findNavController(mViewDataBinding.root)
            .navigate(R.id.action_T_Categories_to_productsById, bundle);

    }
}