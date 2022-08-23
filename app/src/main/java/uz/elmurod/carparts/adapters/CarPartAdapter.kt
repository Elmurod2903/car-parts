package uz.elmurod.carparts.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import uz.elmurod.carparts.DetailActivity
import uz.elmurod.carparts.R
import uz.elmurod.carparts.databinding.ItemCarPartBinding
import uz.elmurod.carparts.models.CarPart
import uz.elmurod.carparts.utils.Constants

class CarPartAdapter(var list: List<CarPart>) : RecyclerView.Adapter<CarPartAdapter.VH>() {


    inner class VH(private val view: View) :
        RecyclerView.ViewHolder(view)
    {
        private val binding = ItemCarPartBinding.bind(view)


        fun onBind(carPart: CarPart) {
            view.setOnClickListener {
                val intent = Intent(it.context, DetailActivity::class.java)
                intent.putExtra(Constants.PRODUCT_DATA, carPart)
                it.context.startActivity(intent)
            }
            binding.tvName1.text = carPart.name1
            binding.tvPrice.text = "${carPart.price} $ "
            if (carPart.imgUrl != null) {
                Picasso.get().load(carPart.imgUrl).into(binding.imageCarPart)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val vh=
            LayoutInflater.from(parent.context).inflate(R.layout.item_car_part, parent, false)

        return VH(vh)

    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}