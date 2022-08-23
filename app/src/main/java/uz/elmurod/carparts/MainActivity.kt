package uz.elmurod.carparts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import uz.elmurod.carparts.databinding.ActivityMainBinding
import uz.elmurod.carparts.utils.MaskWatcher
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var auth: FirebaseAuth
    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        auth.setLanguageCode("uz")

        var currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(applicationContext, Home::class.java))
            finish()
        }
        val textWatcher = MaskWatcher.buildUzbPhoneNumberWatcher(getString(R.string.number_prefix))

        binding.login.setOnClickListener {
            login()
        }
        binding.numberInput.filters =
            arrayOf<InputFilter>(InputFilter.LengthFilter(textWatcher.getMaxLength()))
        binding.numberInput.addTextChangedListener(textWatcher)

        // Callback function for Phone Auth
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                startActivity(Intent(applicationContext, Home::class.java))
                finish()
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(applicationContext, "Failed", Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {

                Log.d("TAG", "onCodeSent:$verificationId")
                storedVerificationId = verificationId
                resendToken = token

                val intent = Intent(applicationContext, VerifyActivity::class.java)
                val phoneNumber = binding.numberInput.text.toString().replace(" ", "")
                val bundle=Bundle()
                bundle.putString("phone_number",phoneNumber)
                intent.putExtra("car_part", phoneNumber)
                intent.putExtra("storedVerificationId", storedVerificationId)
                intent.putExtra("resendToken",resendToken)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun login() {
        val phoneNumber = binding.numberInput.text.toString().replace(" ", "").trim()
        if (phoneNumber.length == 13) {
            sendVerificationCode(phoneNumber)
        } else {
            Toast.makeText(this, "Enter mobile number", Toast.LENGTH_SHORT).show()
        }
    }


    private fun sendVerificationCode(number: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }


    companion object {
        val TAG = "MainActivity"
    }
}