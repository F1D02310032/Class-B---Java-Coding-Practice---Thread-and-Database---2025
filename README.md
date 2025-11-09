# Class-B---Java-Coding-Practice---Thread-and-Database---2025
TUGAS THREAD KELAS B

# MySQLExample

Program ini adalah contoh sederhana penggunaan JDBC (Java Database Connectivity) untuk berinteraksi dengan database MySQL. Program ini mencakup langkah-langkah dasar seperti membuat database, membuat tabel, memasukkan data, dan membaca data dari tabel.

## Fitur Program

1. **Membuat Database**: Program akan membuat database bernama `contohdb` jika belum ada.
2. **Membuat Tabel**: Program akan membuat tabel bernama `users` di dalam database `contohdb` jika belum ada.
3. **Memasukkan Data**: Program akan memasukkan beberapa baris data ke dalam tabel `users`.
4. **Menampilkan Data**: Program akan membaca dan menampilkan data dari tabel `users` di konsol.

## Struktur Tabel `users`

Tabel `users` memiliki struktur sebagai berikut:

| Kolom       | Tipe Data          | Keterangan                          |
|-------------|--------------------|-------------------------------------|
| `id`        | `INT`              | Primary key, auto increment         |
| `username`  | `VARCHAR(50)`      | Tidak boleh null, harus unik        |
| `email`     | `VARCHAR(100)`     | Email pengguna                      |
| `created_at`| `TIMESTAMP`        | Waktu pembuatan data, default saat ini |

## Cara Kerja Program

1. **Koneksi ke MySQL Server**: Program pertama-tama terhubung ke server MySQL menggunakan kredensial yang diberikan.
2. **Membuat Database**: Jika database `contohdb` belum ada, program akan membuatnya.
3. **Koneksi ke Database**: Program kemudian terhubung ke database `contohdb`.
4. **Membuat Tabel**: Jika tabel `users` belum ada, program akan membuat tabel tersebut.
5. **Memasukkan Data**: Program memasukkan beberapa data pengguna ke dalam tabel `users`.
6. **Menampilkan Data**: Program membaca data dari tabel `users` dan menampilkannya di konsol dalam format tabel.

## Output Program

Setelah program dijalankan, data dari tabel `users` akan ditampilkan di konsol dalam format berikut:

Daftar Pengguna di Tabel 'users':

ID Username Email Created At

1 johnDoe john@example.com 2023-10-01 12:00:00 2 janeSmith jane@example.com 2023-10-01 12:01:00 ...

contoh/ss output:

<img width="790" height="462" alt="image" src="https://github.com/user-attachments/assets/2c9ca4da-2c99-431b-b50c-00669f6f0e51" />



## Persyaratan

- **Java Development Kit (JDK)**: Versi 8 atau lebih baru.
- **MySQL Server**: Pastikan MySQL Server berjalan di `localhost` pada port `3306`.
- **Driver JDBC MySQL**: Program menggunakan driver `com.mysql.cj.jdbc.Driver`. Pastikan driver ini tersedia di classpath.

## Konfigurasi

Sebelum menjalankan program, pastikan untuk menyesuaikan kredensial database di bagian berikut:

```java
private static final String DB_USER = "root";       // Ganti dengan username MySQL Anda
private static final String DB_PASSWORD = "";      // Ganti dengan password MySQL Anda

🔍 Penjelasan Kode

1. Konstanta (Constants)

private static final String DB_HOST = "localhost";
private static final int DB_PORT = 3306;
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "";


Ini adalah parameter koneksi. Sesuaikan nilai ini jika server MySQL Anda memiliki konfigurasi yang berbeda.

2. Memuat Driver

Class.forName("com.mysql.cj.jdbc.Driver");


Baris ini secara eksplisit memuat kelas driver MySQL ke dalam memori, yang mendaftarkannya ke DriverManager.

3. Membuat Database (Blok try Pertama)

String serverUrl = String.format(
        "jdbc:mysql://%s:%d/?serverTimezone=UTC&allowPublicKeyRetrieval=true",
        DB_HOST, DB_PORT
);
String dbName = "contohdb";
// ...
try (Connection conn = DriverManager.getConnection(serverUrl, DB_USER, DB_PASSWORD); Statement stmt = conn.createStatement()) {
    String createDbSql = "CREATE DATABASE IF NOT EXISTS " + dbName;
    stmt.executeUpdate(createDbSql);
}


Blok try-with-resources ini digunakan untuk memastikan Connection dan Statement ditutup secara otomatis.

Kita terhubung ke server MySQL terlebih dahulu (perhatikan URL jdbc:mysql://localhost:3306/ tanpa nama database) untuk menjalankan perintah CREATE DATABASE IF NOT EXISTS contohdb.

4. Operasi Tabel (Blok try Kedua)

Blok ini menghubungkan ke database contohdb yang spesifik.

String dbUrl = String.format(
        "jdbc:mysql://%s:%d/%s?serverTimezone=UTC&allowPublicKeyRetrieval=true",
        DB_HOST, DB_PORT, dbName
);
try (Connection conn = DriverManager.getConnection(dbUrl, DB_USER, DB_PASSWORD)) {
    // ...
}


a. Transaksi

conn.setAutoCommit(false);


Kita menonaktifkan auto-commit. Ini berarti perubahan SQL (seperti INSERT) tidak akan langsung disimpan ke database. Kita memulai sebuah transaksi.

b. Membuat Tabel

String createTableSql = """
    CREATE TABLE IF NOT EXISTS users (
        id INT AUTO_INCREMENT PRIMARY KEY,
        username VARCHAR(50) NOT NULL UNIQUE,
        email VARCHAR(100),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    ) ENGINE=InnoDB;
    """;
stmt.executeUpdate(createTableSql);


Perintah ini membuat tabel users jika belum ada. Kode ini menggunakan Text Blocks (fitur Java 15+) untuk SQL yang lebih mudah dibaca.

c. Memasukkan Data (INSERT) dengan PreparedStatement

String insertSql = "INSERT INTO users(username, email) VALUES (?, ?)";
try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
    // ... setString & executeUpdate berulang kali ...
    conn.commit(); // Menyimpan semua perubahan
} catch (SQLException e) {
    conn.rollback(); // Membatalkan semua perubahan jika ada error
    throw e;
}


Kita menggunakan PreparedStatement karena lebih aman (mencegah SQL Injection) dan efisien untuk kueri yang diulang.

Jika semua 10 executeUpdate() berhasil, conn.commit() akan menyimpan semua perubahan tersebut ke database secara permanen.

Jika salah satu saja gagal (misalnya, username duplikat), program akan masuk ke blok catch, dan conn.rollback() akan membatalkan semua 10 percobaan INSERT, sehingga database kembali ke keadaan sebelum try ini dimulai.

d. Mengambil Data (SELECT)

String selectSql = "SELECT id, username, email, created_at FROM users ORDER BY id";
try (PreparedStatement pstmt = conn.prepareStatement(selectSql); 
     ResultSet rs = pstmt.executeQuery()) {
    // ...
    while (rs.next()) {
        // ... ambil data dari rs
        System.out.printf("%-5d %-20s %-30s %-20s%n", id, username, email, ts);
    }
}


pstmt.executeQuery() menjalankan kueri SELECT dan mengembalikan hasilnya dalam objek ResultSet.

while (rs.next()) digunakan untuk mengulang (iterasi) setiap baris data yang ditemukan.

rs.getInt("id"), rs.getString("username"), dll., digunakan untuk mengambil nilai dari kolom berdasarkan namanya.
