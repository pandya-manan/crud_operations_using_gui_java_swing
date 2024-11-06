package receiver;
public class ReceiverDBProperties {

    private static final String dbURL = "jdbc:postgresql://localhost:5432/overseas_transport_management_system";
    private static final String dbUsername = "postgres";
    private static final String dbPassword = "12345678";

    public static String getDbURL() {
        return dbURL;
    }

    public static String getDbUsername() {
        return dbUsername;
    }

    public static String getDbPassword() {
        return dbPassword;
    }
}
