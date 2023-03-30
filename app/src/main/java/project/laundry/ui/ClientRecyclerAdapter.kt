package project.laundry.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import project.laundry.data.dataclass.ClientData
import project.laundry.databinding.ClientRecyclerItemBinding

class ClientRecyclerAdapter(private val ctx: Context, private val myClients : List<ClientData>) : RecyclerView.Adapter<ClientRecyclerAdapter.ViewHolder>(){
    class ViewHolder(private val binding : ClientRecyclerItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(ctx: Context, client : ClientData){
            binding.linearLayout.setOnClickListener {
                val intent = Intent(ctx, ClientDetailActivity::class.java)
                intent.putExtra("client", client)
                ctx.startActivity(intent)
            }
            binding.clientName.text = client.clientName
            binding.clientLaundry.text = client.laundry.toString()
            binding.clientDate.text = client.registerDate
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ClientRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val client = myClients[position]
        holder.bind(ctx, client)
    }

    override fun getItemCount(): Int {
        return myClients.size
    }
}