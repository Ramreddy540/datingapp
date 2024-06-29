package com.example.otpverification;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import org.json.JSONObject;

public class MonthlyCharges extends AppCompatActivity implements PaymentResultListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_charges); // Ensure you use the correct layout file

        Button choosePlanButton1 = findViewById(R.id.button1);
        Button choosePlanButton2 = findViewById(R.id.button2);

        choosePlanButton1.setOnClickListener(v -> startPayment("50000")); // Example amount in paise (500.00 INR)
        choosePlanButton2.setOnClickListener(v -> startPayment("100000")); // Example amount in paise (1000.00 INR)
    }

    public void startPayment(String amount) {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_AlruDQh4nj5yr9"); // Replace with your key ID

        checkout.setImage(R.drawable.ic_emoji); // Ensure this drawable exists

        final MonthlyCharges activity = this;

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Merchant Name");
            options.put("description", "Reference No. #123456");
            options.put("order_id", "order_DBJOWzybf0sJbb"); // Replace with a valid order ID
            options.put("image", "http://example.com/image/rzp.jpg");
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", amount); // Pass amount in currency subunits
            options.put("prefill.email", "gaurav.kumar@example.com");
            options.put("prefill.contact", "9988776655");

            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Successful: " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int code, String response) {
        Toast.makeText(this, "Payment failed: " + response, Toast.LENGTH_SHORT).show();
    }
}
