package uz.elmurod.carparts

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import uz.elmurod.carparts.databinding.ActivityAddCarPartBinding
import uz.elmurod.carparts.models.CarPart
import com.karumi.dexter.PermissionToken

import com.karumi.dexter.listener.PermissionDeniedResponse

import com.karumi.dexter.listener.PermissionGrantedResponse

import com.karumi.dexter.listener.single.PermissionListener

import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionRequest


class AddCarPartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddCarPartBinding
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var firebaseStorage: FirebaseStorage
    lateinit var reference: StorageReference
    var imgUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCarPartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Dexter.withContext(this)
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) { /* ... */
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) { /* ... */
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) { /* ... */
                }
            }).check()
        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()
        reference = firebaseStorage.getReference("part_images")
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
        binding.saveAdd.setOnClickListener {
            val name1 = binding.partName1.text.toString()
            val name2 = binding.partName2.text.toString()
            val price = binding.price.text.toString()
            val carPart = CarPart(name1, name2, price, imgUrl,1)

            firebaseFirestore.collection("carpart")
                .add(carPart)
                .addOnSuccessListener {
                    Toast.makeText(this, "Information saved ${it.id}", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            finish()
        }
        binding.imageAddCarPart.setOnClickListener {
            getImageCount.launch("image/*")
        }

    }

    private var getImageCount =
        registerForActivityResult(ActivityResultContracts.CreateDocument()) { uri ->
            binding.imageAddCarPart.setImageURI(uri)
            val m = System.currentTimeMillis()
            val uploadTask = reference.child(m.toString()).putFile(uri)
            uploadTask.addOnSuccessListener {
                if (it.task.isSuccessful) {
                    val downloadUrl = it.metadata?.reference?.downloadUrl
                    downloadUrl?.addOnSuccessListener { imgUri ->
                        imgUrl = imgUri.toString()
                    }
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

            }


        }
}