package kftsc.custapp;

/**
 * Created by kftsc on 1/15/2019.
 */

public class Customer {
    // build up database
    public abstract class CustomerInfo {
        public static final String CLIENT_COMPANY = "client_company";
        public static final String CLIENT_NAME = "client_name";
        public static final String PHONE_NUM = "phone_num";
        public static final String ADDRESS = "address";
        public static final String TABLE_NAME = "customer_info";
    }

    public abstract class GasPurchase {
        public static final String Buyer = "buyer_name";
        public static final String GAS_TYPE = "gas_type";
        public static final String GAS_CATEGORY = "gas_category";
        public static final String AMOOUNT = "amount";
        public static final String TIME = "time";
        public static final String TABLE_NAME = "purchase_infor";
    }
}
