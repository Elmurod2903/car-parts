package uz.elmurod.carparts.utils

import android.net.Uri
import android.nfc.cardemulation.HostApduService
import com.orhanobut.hawk.Hawk
import uz.elmurod.carparts.models.CarPart
import uz.elmurod.carparts.models.CartModel

object PrefUtils {
    private const val PREF_FAVORITE = "pref_utils"
    private const val PREF_CART = "pref_cart"
    private const val PREF_TOKEN = "pref_token"
    private const val PREF_NAME = "pref_name"
    private const val PREF_GMAIL_NAME = "pref_gmail_name"
    private const val PREF_NUMBER = "pref_number"
    private const val PREF_FCM_TOKEN = "pref_fcm_token"
    private const val PREF_USER_IMAGE = "pref_user_image"




    fun setCards(item: CarPart) {
        val items = Hawk.get<ArrayList<CartModel>>(PREF_CART, arrayListOf())
        val cart = items.firstOrNull { it.product_id == item.id }
        if (cart != null) {
            if (item.cardCount > 0) {
                cart.count = item.cardCount
            } else {
                items.remove(cart)
            }
        } else {
            val newCart = item.id?.let { CartModel(it, item.cardCount) }
            if (newCart != null) {
                items.add(newCart)
            }

        }
        Hawk.put(PREF_CART, items)

    }

    fun getCartList(): ArrayList<CartModel> {
        return Hawk.get(PREF_CART, arrayListOf())

    }

    fun getCartCount(item: CarPart): Int {
        val items = Hawk.get<ArrayList<CartModel>>(PREF_CART, arrayListOf())
        return items.firstOrNull { it.product_id == item.id }?.count ?: 0
    }


}