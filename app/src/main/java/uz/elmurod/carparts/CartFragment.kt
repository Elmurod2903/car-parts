package uz.elmurod.carparts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import uz.elmurod.carparts.adapters.CartAdapter
import uz.elmurod.carparts.databinding.FragmentCartBinding
import uz.elmurod.carparts.databinding.FragmentProfileBinding
import uz.elmurod.carparts.models.CarPart
import uz.elmurod.carparts.utils.Constants
import uz.elmurod.carparts.utils.PrefUtils
import java.io.Serializable


class CartFragment : Fragment() {
    private lateinit var _binding: FragmentCartBinding
    private val binding get() = _binding
    lateinit var list: ArrayList<CarPart>
    private var item: CarPart? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments
        if (bundle!=null){
            item= bundle.getSerializable("car") as CarPart?
        }

//        list = ArrayList()
//        item?.let { list.add(it) }
//        val adapter = CartAdapter(list)
//        _binding.rvCart.adapter = adapter
        _binding.btnMakeOrder.setOnClickListener {
            Toast.makeText(requireContext(), "Buyurtmagiz qabul qilindi", Toast.LENGTH_SHORT)
                .show()

            PrefUtils.getCartList().map { it.product_id }
        }
    }

    companion object {
        fun newInstance() = CartFragment()
    }
}