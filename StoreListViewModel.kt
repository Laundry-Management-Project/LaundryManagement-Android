package project.laundry.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.laundry.data.dataclass.Store
import project.laundry.data.repository.Repository

class StoreListViewModel : ViewModel() {
    val stores : MutableLiveData<ArrayList<Store>> = MutableLiveData()

    private val rep = Repository()
    fun loadStores(uid : String, userType:String){
        rep.getStores(userType, uid) { response ->
            response?.let{
                stores.value=it.stores
            }
        }
    }
    fun addStoreItem(item: Store){
//        mStoreListItems.add(item)
//        stores.value = mStoreListItems
//        Log.d("addStore_StoreList", item.toString())
    }
}