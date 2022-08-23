package uz.elmurod.carparts

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import uz.elmurod.carparts.adapters.CarPartAdapter
import uz.elmurod.carparts.databinding.FragmentHomeBinding
import uz.elmurod.carparts.models.CarPart

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    lateinit var carPartAdapter: CarPartAdapter
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var list: ArrayList<CarPart>
    var carPart: CarPart? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = ArrayList()
        firebaseFirestore = FirebaseFirestore.getInstance()
        val bundle = Bundle()
        bundle.putSerializable("car", carPart)
        _binding!!.fabAddCarPart.setOnClickListener { view ->

            ColorStateList.valueOf(Color.WHITE)
            startActivity(Intent(context, AddCarPartActivity::class.java))
        }
        val colorStateList = ColorStateList.valueOf(Color.WHITE)
        _binding!!.fabAddCarPart.imageTintList = colorStateList

        firebaseFirestore.collection("carpart")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val result = it.result
                    result.forEach { queryDocumentSnapshot ->
                        carPart = queryDocumentSnapshot.toObject(CarPart::class.java)
                        list.add(carPart!!)

                    }
                    carPartAdapter = CarPartAdapter(list)
                    _binding!!.rvCarPart.adapter = carPartAdapter
                }
            }
    }

    companion object {

        fun newInstance() =
            HomeFragment()
    }


}