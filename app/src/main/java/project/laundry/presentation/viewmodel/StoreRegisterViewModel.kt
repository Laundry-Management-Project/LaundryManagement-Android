package project.laundry.presentation.owner

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.laundry.data.dataclass.AddStoreDto
import project.laundry.data.dataclass.StoreListItems
import project.laundry.data.repository.Repository

class StoreRegisterViewModel : ViewModel() {

    val storeList : MutableLiveData<StoreListItems> = MutableLiveData()
    private val rep = Repository()

    fun addStore(uid : String, addStoreDto: AddStoreDto) {

        rep.postOwnerData(uid, addStoreDto) {response ->
            if(response!=null){
                storeList.value = response
                Log.d("addStore", response.toString())
            }
            else{

            }
        }
    }
}