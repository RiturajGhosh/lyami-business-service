package com.lyami.v1.controller.payment;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/razorpay")
public class RazorpayController {

    @Value("${razorpay.key_id}")
    private String razorpayKeyId;

    @Value("${razorpay.key_secret}")
    private String razorpayKeySecret;

    private RazorpayClient client;

    @PostConstruct
    public void init() throws Exception {
        this.client = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
    }

    @PostMapping("/createOrder")
    public ResponseEntity<Map<String, Object>> createOrder(
            @RequestParam Double amount, @RequestParam String currency) {
        try {
            JSONObject options = new JSONObject();
            options.put("amount", amount * 100); // amount in the smallest currency unit (paise)
            options.put("currency", currency);
            options.put("receipt", "order_rcptid_11");

            Order order = client.Orders.create(options);

            Map<String, Object> response = new HashMap<>();
            response.put("id", order.get("id"));
            response.put("amount", order.get("amount"));
            response.put("currency", order.get("currency"));
            response.put("status", order.get("status"));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/paymentVerification")
    public ResponseEntity<String> paymentVerification(@RequestBody Map<String, Object> data) {
        try {
            String razorpayOrderId = (String) data.get("razorpay_order_id");
            String razorpayPaymentId = (String) data.get("razorpay_payment_id");
            String razorpaySignature = (String) data.get("razorpay_signature");

            boolean isValid = verifySignature(razorpayOrderId, razorpayPaymentId, razorpaySignature);

            if (isValid) {
                return ResponseEntity.ok("Payment verified successfully");
            } else {
                return ResponseEntity.status(400).body("Payment verification failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal server error");
        }
    }

    private boolean verifySignature(String orderId, String paymentId, String signature) throws Exception {
        String data = orderId + "|" + paymentId;
        String generatedSignature = hmacSHA256(data, razorpayKeySecret);
        return generatedSignature.equals(signature);
    }

    private String hmacSHA256(String data, String key) throws Exception {
        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        sha256Hmac.init(secretKey);
        byte[] hash = sha256Hmac.doFinal(data.getBytes());
        return new String(Hex.encodeHex(hash));
    }
}
