package com.dunglv.appmusic.common

class MessageEventMusic {

    var type: Int = 0
    var uri: String =""
    var time: String =""
    var intValue = 0
    var booleanValue = false


    constructor(type: Int, booleanValue: Boolean) {
        this.type = type
        this.booleanValue = booleanValue
    }


    constructor( time: String,type: Int) {
        this.type = type
        this.time = time
    }

    constructor(type: Int, uri: String, intValue: Int) {
        this.type = type
        this.uri = uri
        this.intValue = intValue
    }
}