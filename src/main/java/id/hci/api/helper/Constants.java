package id.hci.api.helper;

public class Constants {

    public static final int CHECKOUT_ACTIVE = 1;
    public static final int CHECKOUT_DELETED = 3;
    public static final int CHECKOUT_CHANGED_TO_TRANSACTION = 4;

    public static final int TRANSACTION_STATUS_CREATED = 1;
    public static final int TRANSACTION_STATUS_SUCCESS = 2;
    public static final int TRANSACTION_STATUS_FAILED = 3;
    public static final int TRANSACTION_STATUS_PENDING = 4;
    public static final int TRANSACTION_STATUS_PAYLATER = 9;

    public static final String ORDER_PREFIX = "IV-";
    public static final String TRANSID_PREFIX = "TR-";

}
