package uz.elmurod.carparts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import uz.elmurod.carparts.databinding.ActivityVerifyBinding
import java.util.concurrent.TimeUnit


class VerifyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerifyBinding
    private lateinit var auth: FirebaseAuth
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null
    private var storedVerificationId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        val phoneNumber = intent.getStringExtra("car_part")
        storedVerificationId = intent.getStringExtra("storedVerificationId")
        resendToken =
            intent.getParcelableExtra("resendToken")

        binding.verifyTv.text = getString(R.string.code_for_number).format(phoneNumber)
        binding.verifyBtn.setOnClickListener {
            var otp = binding.idOtp.text.toString().trim()
            if (!otp.isEmpty()) {
                val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId.toString(), otp
                )
                signInWithPhoneAuthCredential(credential)
            } else {
                Toast.makeText(this, "Enter verification code", Toast.LENGTH_SHORT).show()
            }
        }
        binding.resendCode.setOnClickListener {
            if (phoneNumber != null) {
                resendToken?.let { it1 -> resendCode(phoneNumber, it1) }
            }
        }

    }

    private fun resendCode(
        phoneNumber: String,
        resendToken: PhoneAuthProvider.ForceResendingToken
    ) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .setForceResendingToken(resendToken)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }


    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            startActivity(Intent(applicationContext, Home::class.java))
            finish()
//            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Toast.makeText(applicationContext, "Failed", Toast.LENGTH_LONG).show()
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            storedVerificationId = verificationId
            resendToken = token
            Log.d("TAG", "onCodeSent:$verificationId")
        }
    }


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(applicationContext, Home::class.java))
                    finish()
// ...
                } else {
// Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
// The verification code entered was invalid
                        Toast.makeText(this, "Invalid verification code", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

}