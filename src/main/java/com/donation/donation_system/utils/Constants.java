package com.donation.donation_system.utils;

public class Constants {
    public static final String MY_EMAIL = "phanquangduytvt@gmail.com";
    public static final int TOTAL_ITEMS_PER_PAGE = 8;
    public static final int ALL_ROLE = 0;
    public static final int ADMIN_ROLE = 1;
    public static final int USER_ROLE = 2;
    public static final int UNKNOWN_ROLE = -1;
    public static final String STATUS_NOTACTIVATED = "NotActivated";
    public static final String STATUS_ACTIVE = "Active";
    public static final String STATUS_INACTIVE = "Inactive";
    public static final String STATUS_BANNED = "Banned";
    public static final String UPPERCASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LOWERCASE_CHARS = "abcdefghijklmnopqrstuvwxyz";
    public static final String NUMBERS = "0123456789";
    public static final String SPECIAL_CHARS = "!@#$%^&*()-_=+[]{}|;:,.<>?";
    public static final String MAIL_TEMPLATE_BASE_NAME = "mail/MailMessages";
    public static final String MAIL_TEMPLATE_PREFIX = "/templates/";
    public static final String MAIL_TEMPLATE_SUFFIX = ".html";
    public static final String UTF_8 = "UTF-8";
    public static final String STATUS_ENABLE = "Enable";
    public static final String STATUS_DISABLE = "Disable";

    public static final String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static final String vnp_Returnurl = "/Donations/vnpay-payment";
    public static final String vnp_TmnCode = "S180CJNU";
    public static final String vnp_HashSecret = "GMXT6KNTL7ETG363XNKEJY6403LO81GT";
    public static final String vnp_apiUrl = "https://sandbox.vnpayment.vn/merchant_webapi/api/transaction";

    public static final String vnp_Version = "2.1.0";
    public static final String vnp_Command = "pay";
    public static final String orderType = "other";
    public static final String bankCode = "NCB";
    public static final String vnp_IpAddr = "127.0.0.1";
}
