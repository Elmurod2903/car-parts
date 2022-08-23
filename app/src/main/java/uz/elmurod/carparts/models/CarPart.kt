package uz.elmurod.carparts.models

import java.io.Serializable

class CarPart : Serializable {
    var name1: String? = null
    var name2: String? = null
    var price: String? = null
    var imgUrl: String? = null
    val id: Int? = null
    var cardCount: Int = 0


    constructor()
    constructor(name1: String?, name2: String?, price: String?, imgUrl: String?, cardCount: Int) {
        this.name1 = name1
        this.name2 = name2
        this.price = price
        this.imgUrl = imgUrl
        this.cardCount = cardCount
    }

}