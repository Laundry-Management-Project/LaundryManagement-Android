package project.laundry.presentation.owner

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.laundry.data.dataclass.StoreListItems

class StoreListViewModel : ViewModel() {
    val storeListItems : MutableLiveData<List<StoreListItems>> = MutableLiveData()
    private val mStoreListItems : ArrayList<StoreListItems> = ArrayList()

    fun load(items:List<StoreListItems>){
        storeListItems.value=items
    }
    fun addStoreItem(item:StoreListItems){
        mStoreListItems.add(item)
        storeListItems.value = mStoreListItems
        Log.d("addStore_StoreList", item.toString())
    }
}