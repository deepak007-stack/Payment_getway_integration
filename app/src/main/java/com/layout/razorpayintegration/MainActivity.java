package com.layout.razorpayintegration;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

import static java.lang.Math.round;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {

    private EditText amount;
    private Button btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        amount = (EditText) findViewById(R.id.amount);
        btnPay = (Button) findViewById(R.id.btn_pay);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startPayment();
            }
        });


        Checkout.preload(getApplicationContext());
    }

    public void startPayment() {

        String samount = amount.getText().toString();

        int tamount = Math.round(Float.parseFloat(samount) * 100);

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_ozOIizB631XLgF");

        checkout.setImage(R.drawable.payment_logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Android Developer");
            options.put("description", "We can bulid your future..");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", tamount);
            options.put("prefill.email", "tyron11199@gmail.com");
            options.put("prefill.contact", "8949329470");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch (Exception e) {
            Log.e("tag", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {

        Toasty.success(getApplicationContext(), "Payment Successful ", Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void onPaymentError(int i, String s) {

        Toasty.error(getApplicationContext(), "Payment Unsuccessful !", Toast.LENGTH_SHORT, true).show();
    }
}