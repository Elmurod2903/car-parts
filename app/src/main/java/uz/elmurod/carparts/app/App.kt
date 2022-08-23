package uz.elmurod.carparts.app

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.orhanobut.hawk.Hawk

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build()
    }
}