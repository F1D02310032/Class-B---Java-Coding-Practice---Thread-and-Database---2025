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


## Persyaratan

- **Java Development Kit (JDK)**: Versi 8 atau lebih baru.
- **MySQL Server**: Pastikan MySQL Server berjalan di `localhost` pada port `3306`.
- **Driver JDBC MySQL**: Program menggunakan driver `com.mysql.cj.jdbc.Driver`. Pastikan driver ini tersedia di classpath.

## Konfigurasi

Sebelum menjalankan program, pastikan untuk menyesuaikan kredensial database di bagian berikut:

```java
private static final String DB_USER = "root";       // Ganti dengan username MySQL Anda
private static final String DB_PASSWORD = "";      // Ganti dengan password MySQL Anda
