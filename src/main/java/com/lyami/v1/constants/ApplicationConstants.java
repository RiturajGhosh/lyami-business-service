package com.lyami.v1.constants;

public class ApplicationConstants {

    public static final String ADMIN_ROLE_AUTHORIZER = "hasRole('ADMIN')";
    public static final String MODERATOR_ROLE_AUTHORIZER = "hasRole('MODERATOR')";
    public static final String USER_ROLE_AUTHORIZER = "hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')";
    public static final String STAY_USER_ROLE_AUTHORIZER = "hasRole('HOSTEL') or hasRole('HOTEL')";
    public static Long OTP_EXPIRY_DURATION_MS = Long.valueOf(5 * 60000);
    public static String OTP_MAIL_SUBJECT = "Lyami mail verification OTP";
    public static String OTP_MAIL_HTML = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "    <title>OTP Email</title>\n" +
            "    <style>\n" +
            "        /* Add your CSS styles here for a beautiful email layout */\n" +
            "        body {\n" +
            "            font-family: Arial, sans-serif;\n" +
            "            background-color: #f4f4f4;\n" +
            "        }\n" +
            "        .container {\n" +
            "            max-width: 600px;\n" +
            "            margin: 0 auto;\n" +
            "            padding: 20px;\n" +
            "            background-color: #fff;\n" +
            "            border-radius: 5px;\n" +
            "            box-shadow: 0 0 10px rgba(0,0,0,0.1);\n" +
            "        }\n" +
            "        .header {\n" +
            "            background-color: #007bff;\n" +
            "            color: #fff;\n" +
            "            text-align: center;\n" +
            "            padding: 10px;\n" +
            "            border-radius: 5px 5px 0 0;\n" +
            "        }\n" +
            "        .content {\n" +
            "            padding: 20px;\n" +
            "        }\n" +
            "        .otp {\n" +
            "            font-size: 24px;\n" +
            "            font-weight: bold;\n" +
            "            color: #007bff;\n" +
            "        }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <div class=\"container\">\n" +
            "        <div class=\"header\">\n" +
            "            <h2>One-Time Password (OTP) Email</h2>\n" +
            "        </div>\n" +
            "        <div class=\"content\">\n" +
            "            <p>Dear User,</p>\n" +
            "            <p>Your one-time password (OTP) for authentication is:</p>\n" +
            "            <p class=\"otp\">{{otp}}</p> <!-- Replace with your actual OTP value -->\n" +
            "            <p>Please use this OTP to access your account.</p>\n" +
            "            <p>If you did not request this OTP, please ignore this email.</p>\n" +
            "            <p>Thank you for using our services.</p>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "</body>\n" +
            "</html>\n";

    public static String ACCESS_DENIED_EXCEPTION_MSG = "Access Denied##401";
}
