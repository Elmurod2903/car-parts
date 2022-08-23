package uz.elmurod.carparts

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.squareup.picasso.Picasso
import uz.elmurod.carparts.databinding.ActivityDetailBinding
import uz.elmurod.carparts.models.CarPart
import uz.elmurod.carparts.utils.Constants
import uz.elmurod.carparts.utils.PrefUtils

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding

    private var item: CarPart? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.backButton.setOnClickListener {
            onBackPressed()
        }
        item = intent.getSerializableExtra(Constants.PRODUCT_DATA) as CarPart

        binding.tvTitle.text = item!!.name1
        binding.tvSealNumber.text = item!!.name2
        binding.tvDetailPrice.text = "${item!!.price} $ "
        if (item!!.imgUrl != null) {
            Picasso.get().load(item!!.imgUrl).into(binding.ivImage)
        }
        binding.btnCart.setOnClickListener {
            item!!.cardCount = 1
            PrefUtils.setCards(item!!)
            Toast.makeText(this, "Mahsulot savatga qo'shildi", Toast.LENGTH_SHORT).show()
            finish()
        }

    }


    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }
}