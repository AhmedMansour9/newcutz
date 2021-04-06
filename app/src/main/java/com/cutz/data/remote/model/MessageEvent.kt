package com.cutz.data.remote.model

 class MessageEvent {
     var Message:String = ""
      var cat_Id:String?= String()
      var brand_Id:String?= String()
      var min_Price:String?= String()
      var max_Price:String?= String()
    lateinit  var catmodel:CatModel
     lateinit  var addcoupon:AddCouponResponse.Data

     constructor(Message: String) {
        this.Message = Message
    }
    constructor(catmodel:CatModel) {
        this.catmodel = catmodel
    }
    constructor(catmodel:CatModel,Message: String) {
        this.Message = Message
        this.catmodel = catmodel

    }
     constructor(catmodel:AddCouponResponse.Data,Message: String) {
         this.Message = Message
         this.addcoupon = catmodel

     }
     constructor(Message: String,cat_Id: String?,brand_Id: String?,min_Price: String?,max_Price: String?) {
         this.Message = Message
         this.cat_Id = cat_Id
         this.brand_Id = brand_Id
         this.min_Price = min_Price
         this.max_Price = max_Price

     }
}