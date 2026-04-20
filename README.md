# 📚 Sistem Manajemen Perpustakaan SMP (LK06)

Aplikasi *Command Line Interface* (CLI) berbasis Java untuk mengelola operasional perpustakaan sekolah. Sistem ini dibangun menggunakan arsitektur Object-Oriented Programming (OOP) dan memanfaatkan **File I/O (.txt)** sebagai media penyimpanan data (tanpa *database* eksternal).

## 🏗️ Struktur Proyek dan Alur Kerja

Proyek ini memisahkan logika bisnis, representasi data, dan penyimpanan ke dalam struktur direktori berikut:

```text
📁 LK6
 ┣ 📁 data/           # Tempat penyimpanan file seluruh object (.txt)
 ┣ 📁 model/          # Kumpulan class Entity/Master Data
 ┣ 📄 Controller.java # Core logic & File I/O handler
 ┗ 📄 Main.java       # User Interface (CLI) dan Main Method
```
### 1. Folder `model/` (Entity Classes)
Berisi *blueprint* atau cetakan objek dari entitas utama dalam sistem perpustakaan. Setiap *class* di dalam package ini bertugas sebagai representasi data:
* `Person.java`: Data master Person (Nama).
* `Buku.java`: Data master buku (Kode, Judul, Jenis).
* `Siswa.java`: Data master siswa turunan dari Person (NIS, Nama, Alamat).
* `Pegawai.java`: Data autentikasi dan master pegawai turunan dari Person (NIP, Nama, Tgl Lahir).
* `Peminjaman.java`: Data transaksi yang menghubungkan entitas di atas (Kode Transaksi, NIS, Kode Buku, Tanggal Pinjam, Tanggal Kembali, Status).

### 2. File `Controller.java` (Business Logic)
Bertindak sebagai "otak" dari aplikasi. Semua operasi logika dipusatkan di sini, meliputi:
* **CRUD File I/O**: Membaca (`BufferedReader`) dan menulis/menimpa (`BufferedWriter`) data dari/ke file teks.
* **Validasi Transaksi**: Mengecek ketersediaan buku, batas maksimum pinjaman (maks 2 buku), dan validitas siswa.
* **Filter Laporan**: Memproses `ArrayList` untuk menyortir data jatuh tempo dan buku yang belum dikembalikan.

### 3. Folder `data/` (Storage)
Direktori yang secara otomatis di-*generate* oleh sistem saat pertama kali dijalankan (`loadData()`). Menyimpan seluruh catatan dalam ekstensi `.txt` dengan *delimiter* (pemisah) berupa karakter *pipe* (`|`).

---

## ✨ Fitur Utama

1. **Autentikasi Pegawai**: Sistem *login* sederhana menggunakan validasi NIP pegawai.
2. **Manajemen Master Data**: Input, Edit, dan Hapus data buku, siswa, dan pegawai.
3. **Transaksi Peminjaman**: 
   * Pembuatan ID Transaksi unik otomatis (*auto-generate* berdasarkan waktu).
   * *Guard clause* / validasi ketat (maksimal pinjam 2 buku, status ketersediaan).
4. **Transaksi Pengembalian**: Mengubah status peminjaman (0 menjadi 1) dengan aman menggunakan teknik *read-modify-write* pada file teks.
5. **Pelaporan**: 
   * Daftar buku yang belum dikembalikan.
   * Kalkulasi keterlambatan otomatis (jatuh tempo) menggunakan API `java.time.temporal.ChronoUnit`.

---

## 🚀 Cara Menjalankan Program

1. *Compile* seluruh file `.java` di dalam *package* proyek.
2. Jalankan file `Main.java`.
3. Saat *prompt login* muncul, gunakan data *default* yang otomatis dibuat ketika data pegawai kosong/ belum ditambah:
   * **NIP Pegawai:** `001`
4. Program akan secara otomatis membuat folder `data/` beserta file `.txt` yang dibutuhkan jika belum tersedia di direktori lokal.

---

## ⚠️ Catatan Teknis

* **Delimiter**: Sistem menggunakan karakter *pipe* (`|`) sebagai pemisah kolom. Hindari penggunaan karakter `|` saat menginputkan data teks (seperti pada Judul Buku atau Alamat) agar proses pembacaan file (*parsing*) tidak mengalami *error*.
* **Format Tanggal**: Sistem menggunakan standar format kalender ISO-8601 (`YYYY-MM-DD`). Pastikan input tanggal selalu mengikuti format ini (contoh: 2026-04-20).
