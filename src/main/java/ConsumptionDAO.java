import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsumptionDAO {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/fuelconsumption";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "DonGio024";

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public void insert(ConsumptionRecord record) {
        String sql = "INSERT INTO consumption_records(distance, fuel, consumption, language) VALUES(?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, record.getDistance());
            pstmt.setDouble(2, record.getFuel());
            pstmt.setDouble(3, record.getConsumption());
            pstmt.setString(4, record.getLanguage());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<ConsumptionRecord> getAllRecords() {
        String sql = "SELECT * FROM consumption_records";
        List<ConsumptionRecord> records = new ArrayList<>();

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                double distance = rs.getDouble("distance");
                double fuel = rs.getDouble("fuel");
                double consumption = rs.getDouble("consumption");
                String language = rs.getString("language");

                ConsumptionRecord record = new ConsumptionRecord(distance, fuel, consumption, language);
                records.add(record);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return records;
    }
}
