package feodorbal.network;

public class NetworkVariables {
    private static String Pubkey;
    private static String Prikey;
    private static String ServerPublicKey;
    private static String AESKEY;
    private static String SALT;

    public static String getAESKEY() {
        return AESKEY;
    }

    public static String getPrikey() {
        return Prikey;
    }

    public static String getPubkey() {
        return Pubkey;
    }

    public static String getSALT() {
        return SALT;
    }

    public static String getServerPublicKey() {
        return ServerPublicKey;
    }

    public static void setAESKEY(String AESKEY) {
        NetworkVariables.AESKEY = AESKEY;
    }

    public static void setPrikey(String prikey) {
        Prikey = prikey;
    }

    public static void setPubkey(String pubkey) {
        Pubkey = pubkey;
    }

    public static void setSALT(String SALT) {
        NetworkVariables.SALT = SALT;
    }


}
