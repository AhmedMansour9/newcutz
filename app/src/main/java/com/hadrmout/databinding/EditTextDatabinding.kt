package com.hadrmout.databinding

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Build
import android.text.Html
import android.util.Patterns
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import com.hadrmout.R
import java.util.regex.Pattern


class EditTextDatabinding {
    companion object {
        @BindingAdapter("emailVaild")
        @JvmStatic
        fun emailVaid(editText: TextInputLayout, msg: String) {
            editText.editText?.doAfterTextChanged {
                if (!Patterns.EMAIL_ADDRESS.matcher(editText.editText?.text.toString()).matches()) {
                    editText.error = msg
                } else {
                    editText.error = null
                }
            }

        }

        @BindingAdapter("passwordVaild")
        @JvmStatic
        fun passwordValid(editText: TextInputLayout, msg: String) {
            val passwordRegex = "^" + "(?=\\S+$)" + ".{7,}"

            editText.editText?.doAfterTextChanged {
                if (!Pattern.compile(passwordRegex).matcher(editText.editText?.text.toString())
                        .matches()
                ) {
                    editText.error = msg
                } else {
                    editText.error = null
                }
            }
        }

        @BindingAdapter("passwordloginValid")
        @JvmStatic
        fun passwordloginValid(editText: TextInputLayout, msg: String) {
            val passwordRegex = "^" + "(?=\\S+$)" + ".{7,}"

            editText.editText?.doAfterTextChanged {
                if (!Pattern.compile(passwordRegex).matcher(editText.editText?.text.toString())
                        .matches()
                ) {
                    editText.error = msg
                } else {
                    editText.error = null
                }
            }
        }

        @BindingAdapter("imageUrl")
        @JvmStatic
        fun loadImage(view: ImageView, url: String?) {
                Glide.with(view.context)
                    .load(url)
                    .error(R.drawable.img_fake)
                    .into(view)
        }

        @BindingAdapter("imagedrawableUrl")
        @JvmStatic
        fun loaddrawableImage(view: ImageView, url: Int) {
            Glide.with(view.context)
                .load(url)
                .into(view)
        }


        @BindingAdapter("checkFavourit")
        @JvmStatic
        fun checkFavourit(view: ImageView, status: Boolean) {
            if (status) {
                view.setImageResource(R.drawable.ic_emptyfavourit)
            } else {
                view.setImageResource(R.drawable.ic_favourit)

            }
        }

        @BindingAdapter("review")
        @JvmStatic
        fun review(view: RatingBar, raiting: Double) {
            view.rating= raiting.toFloat()
        }


        @BindingAdapter("descrption")
        @JvmStatic
        fun descrption(view: TextView, Data: String?) {
            if(!Data.isNullOrEmpty()){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    view.setText(
                        Html.fromHtml(
                            Data,
                            Html.FROM_HTML_MODE_LEGACY
                        )
                    )
                } else {
                    view.setText(HtmlCompat.fromHtml(Data!!, 0));

                }
            }

        }

        @SuppressLint("SetTextI18n")
        @BindingAdapter(value = ["bind:price"], requireAll = false)
        @JvmStatic
        fun price(view: TextView, Data: String?) {
            //        holder.dateTextView.setText(dateUtils.format(new Date(order.getCreatedDate() * 1000)));

//        holder.orderStatusTextView.setText();
            var total: String = String.format(
                "%.2f",
                Data?.toDouble()
            )
            view.setText(
                total + " " + view.resources.getString(R.string.currency)
            )

        }

        @SuppressLint("SetTextI18n")
        @BindingAdapter(value = ["bind:ratee"], requireAll = false)
        @JvmStatic
        fun ratee(view: TextView, Data: Double) {
            view.setText(
                Data.toString()
            )
        }

        @SuppressLint("SetTextI18n")
        @BindingAdapter(value = ["bind:min"], requireAll = false)
        @JvmStatic
        fun min(view: TextView, Data: String) {
            view.setText(
                Data + "  " + view.resources.getString(R.string.min)
            )
        }

        @SuppressLint("SetTextI18n")
        @BindingAdapter(value = ["bind:weight"], requireAll = false)
        @JvmStatic
        fun weight(view: TextView, Data: String?) {
            //        holder.dateTextView.setText(dateUtils.format(new Date(order.getCreatedDate() * 1000)));

//        holder.orderStatusTextView.setText();

            view.setText(
                Data
            )

        }


        @SuppressLint("SetTextI18n")
        @BindingAdapter(value = ["bind:BeforePrice", "bind:FinalPrice"], requireAll = false)
        @JvmStatic
        fun textPrice(textPrice: TextView, BeforePrice: String, FinalPrice: String) {
            val finalPrice = FinalPrice.toDouble()
            val orignalPrice = BeforePrice.toDouble()


            if (finalPrice < orignalPrice) {
                textPrice.isVisible = true
//                textPrice.text =
//                    BeforePrice + " " + textPrice.resources.getString(R.string.currency)
                var result = String.format(
                    "%.2f",
                    BeforePrice.toDouble()
                )
                textPrice.setText(
                    result +" "+ textPrice.context.resources.getString(R.string.currency)
                )
                textPrice.setPaintFlags(textPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
            }

        }


        @BindingAdapter(value = ["bind:BeforePrice2", "bind:FinalPrice2"], requireAll = false)
        @JvmStatic
        fun textSalePrice(textPrice2: TextView?, BeforePrice: String?, FinalPrice: String?) {

            if (!BeforePrice.isNullOrEmpty() && !FinalPrice.isNullOrEmpty()) {
                if (BeforePrice.toDouble() > FinalPrice.toDouble()) {
                    textPrice2?.isVisible = true
                    textPrice2?.setText(
                        String.format(
                            "%.2f",
                            FinalPrice?.toDouble()
                        )
                    )
                    textPrice2?.text =
                        BeforePrice + " " + textPrice2?.resources?.getString(R.string.currency)
                    textPrice2?.setPaintFlags(textPrice2.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
                }
            }
        }


        @BindingAdapter("raitingBar")
        @JvmStatic
        fun raitingbar(view: RatingBar?, rating: Int) {
            if(view!=null){
                val rate = rating.toFloat()
                view.rating = rate
            }
        }



    }




    }
