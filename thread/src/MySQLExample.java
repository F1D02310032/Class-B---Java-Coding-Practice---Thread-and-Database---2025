
import java.sql.*;

/**
 * Contoh sederhana JDBC + MySQL: - Buat database 'contohdb' - Buat tabel
 * 'users' - Masukkan beberapa baris - Query dan tampilkan hasilnya
 *
 * Ganti DB_USER dan DB_PASSWORD sesuai environment Anda.
 */
public class MySQLExample {

    // Ganti sesuai environment
    private static final String DB_HOST = "localhost";
    private static final int DB_PORT = 3306;
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        try {
            // 0 Muat driver JDBC MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            String serverUrl = String.format(
                    "jdbc:mysql://%s:%d/?serverTimezone=UTC&allowPublicKeyRetrieval=true",
                    DB_HOST, DB_PORT
            );
            String dbName = "contohdb";
            String dbUrl = String.format(
                    "jdbc:mysql://%s:%d/%s?serverTimezone=UTC&allowPublicKeyRetrieval=true",
                    DB_HOST, DB_PORT, dbName
            );

            // 1) Koneksi ke server MySQL tanpa memilih database
            try (Connection conn = DriverManager.getConnection(serverUrl, DB_USER, DB_PASSWORD); Statement stmt = conn.createStatement()) {
                String createDbSql = "CREATE DATABASE IF NOT EXISTS " + dbName;
                stmt.executeUpdate(createDbSql);
            }

            // 2) Koneksi ke database yang baru dibuat
            try (Connection conn = DriverManager.getConnection(dbUrl, DB_USER, DB_PASSWORD)) {
                conn.setAutoCommit(false);

                // Buat tabel users jika belum ada
                try (Statement stmt = conn.createStatement()) {
                    String createTableSql = """
                        CREATE TABLE IF NOT EXISTS users (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            username VARCHAR(50) NOT NULL UNIQUE,
                            email VARCHAR(100),
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                        ) ENGINE=InnoDB;
                        """;
                    stmt.executeUpdate(createTableSql);
                }

                // Masukkan data
                String insertSql = "INSERT INTO users(username, email) VALUES (?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                    pstmt.setString(1, "johnDoe");
                    pstmt.setString(2, "john@example.com");
                    pstmt.executeUpdate();
                    pstmt.setString(1, "janeSmith");
                    pstmt.setString(2, "jane@example.com");
                    pstmt.executeUpdate();
                    pstmt.setString(1, "mikeBrown");
                    pstmt.setString(2, "mike@example.com");
                    pstmt.executeUpdate();
                    pstmt.setString(1, "susanClark");
                    pstmt.setString(2, "susan@example.com");
                    pstmt.executeUpdate();
                    pstmt.setString(1, "emmaWhite");
                    pstmt.setString(2, "emma@example.com");
                    pstmt.executeUpdate();
                    pstmt.setString(1, "oliverGreen");
                    pstmt.setString(2, "oliver@example.com");
                    pstmt.executeUpdate();
                    pstmt.setString(1, "noahBlack");
                    pstmt.setString(2, "noah@example.com");
                    pstmt.executeUpdate();
                    pstmt.setString(1, "avaGray");
                    pstmt.setString(2, "ava@example.com");
                    pstmt.executeUpdate();
                    pstmt.setString(1, "williamBlue");
                    pstmt.setString(2, "william@example.com");
                    pstmt.executeUpdate();
                    pstmt.setString(1, "sophiaRed");
                    pstmt.setString(2, "sophia@example.com");
                    pstmt.executeUpdate();
                    conn.commit();
                } catch (SQLException e) {
                    conn.rollback();
                    throw e;
                }
                // Tampilkan data
                String selectSql = "SELECT id, username, email, created_at FROM users ORDER BY id";
                try (PreparedStatement pstmt = conn.prepareStatement(selectSql); ResultSet rs = pstmt.executeQuery()) {
                    System.out.println("\nData di tabel users:");

                    System.out.println("\nDaftar Pengguna di Tabel 'users':");
                    System.out.println("------------------------------------------------------------");
                    System.out.printf("%-5s %-20s %-30s %-20s%n", "ID", "Username", "Email", "Created At");
                    System.out.println("------------------------------------------------------------");
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String username = rs.getString("username");
                        String email = rs.getString("email");
                        Timestamp ts = rs.getTimestamp("created_at");
                        System.out.printf("%-5d %-20s %-30s %-20s%n", id, username, email, ts);
                    }
                    System.out.println("------------------------------------------------------------");

                }
            }

        } catch (SQLException e) {
            System.err.println("Terjadi SQLException: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC tidak ditemukan: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
