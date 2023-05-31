package project.laundry.data

import android.app.Application

class App : Application() {
    companion object{
        lateinit var prefs:Prefs
    }

    override fun onCreate() {
        prefs = Prefs(applicationContext)
        prefs.buId=""
        prefs.uid=""
        prefs.userType=""
        super.onCreate()
    }
}