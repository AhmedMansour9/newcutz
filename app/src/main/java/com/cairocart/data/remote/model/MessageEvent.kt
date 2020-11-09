package com.cairocart.data.remote.model

public class MessageEvent {
     var Message:String = ""
     lateinit var catmodel:CatModel
     constructor(Message: String) {
        this.Message = Message
    }
    constructor(catmodel:CatModel) {
        this.catmodel = catmodel
    }
}