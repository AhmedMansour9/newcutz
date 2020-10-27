package com.cairocart.Fragments


import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import com.cairocart.Model.Blogs_Response
import com.cairocart.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_details_articatle_.view.*

/**
 * A simple [Fragment] subclass.
 */
class DetailsArticatle_Fragment : Fragment() {
    var bundle= Bundle()
    lateinit var details: Blogs_Response.Data
    lateinit var root:View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_details_articatle_, container, false)

        showDetails()

        return root
    }
    fun  showDetails(){
        bundle = requireArguments()
        details = bundle.getParcelable("articitalItem")!!
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            root.T_Descrption.setText(
                    Html.fromHtml(
                            details.description,
                            Html.FROM_HTML_MODE_LEGACY  )
            )
        } else {
            root.T_Descrption.setText(HtmlCompat.fromHtml(details.description!!, 0));

        }
        root.T_Descrption.movementMethod= ScrollingMovementMethod()
        root.T_title.text=details.title
        Picasso.get()
                .load( details.image)
                .resize(500,500)
                .into(root.Img_Detail)

    }

}
