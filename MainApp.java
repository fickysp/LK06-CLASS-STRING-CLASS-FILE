/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LK6;

/**
 *
 * @author FICKY
 */
import java.time.*;
import java.util.*;
import LK6.model.*;
import LK6.controller.*;

public class MainApp {

    private static final Scanner sc = new Scanner(System.in);
    private static final Controller ctrl = new Controller();

    public static void main(String[] args) {
        ctrl.loadData();

        System.out.println("========================================");
        System.out.println("          SISTEM PERPUSTAKAAN SMP       ");
        System.out.println("========================================");

        // Login maksimal 3x percobaan
        Pegawai pegawaiLogin = null;
        int percobaan = 0;
        while (percobaan < 3) {
            System.out.println("\n------------- LOGIN PEGAWAI ------------");
            System.out.print("NIP  : ");
            String nip = sc.nextLine().trim();
            System.out.print("Nama : ");
            String nama = sc.nextLine().trim();

            pegawaiLogin = ctrl.login(nip, nama);
            if (pegawaiLogin != null) {
                break;
            }
            percobaan++;
            System.out.println("Login gagal. Sisa percobaan: " + (3 - percobaan));
        }

        if (pegawaiLogin == null) {
            System.out.println("Login gagal 3 kali. Program ditutup.");
            return;
        }

        System.out.println("\nSelamat datang, " + pegawaiLogin.getName() + "!");
        menuUtama();
        sc.close();
    }

    // Menu Utama
    private static void menuUtama() {
        int pilihan;
        do {
            System.out.println("\n========================================");
            System.out.println("               MENU UTAMA               ");
            System.out.println("========================================");
            System.out.println(" 1. Kelola Data Siswa");
            System.out.println(" 2. Kelola Data Buku");
            System.out.println(" 3. Kelola Data Pegawai");
            System.out.println(" 4. Transaksi Peminjaman");
            System.out.println(" 5. Pengembalian Buku");
            System.out.println(" 6. Laporan");
            System.out.println(" 0. Keluar");
            System.out.println("========================================");
            System.out.print("Pilih: ");
            pilihan = bacaInt();
            switch (pilihan) {
                case 1 -> menuSiswa();
                case 2 -> menuBuku();
                case 3 -> menuPegawai();
                case 4 -> menuPeminjaman();
                case 5 -> menuPengembalian();
                case 6 -> menuLaporan();
                case 0 -> System.out.println("Terima kasih. Program selesai.");
                default -> System.out.println("Pilihan tidak valid!");
            }
        } while (pilihan != 0);
    }

    // Menu Siswa
    private static void menuSiswa() {
        int pilihan;
        do {
            System.out.println("\n----------- Kelola Data Siswa ----------");
            System.out.println(" 1. Tambah  \n 2. Lihat  \n 3. Edit  \n 4. Hapus  \n 0. Kembali");
            System.out.println("----------------------------------------");
            System.out.print("Pilih: ");
            pilihan = bacaInt();
            switch (pilihan) {
                case 1 -> {
                    System.out.print("\nNIS: "); String nis = sc.nextLine().trim();
                    System.out.print("Nama: "); String nama = sc.nextLine().trim();
                    System.out.print("Alamat: "); String alamat = sc.nextLine().trim();
                    // Membuat objek siswa baru berdasarkan input user
                    Siswa siswa = new Siswa(nama, nis, alamat);
                    ctrl.saveSiswa(siswa);
                }
                case 2 -> {
                    System.out.println("\n============= Daftar Siswa =============");
                    int no = 1;
                    for (Siswa s : ctrl.getAllSiswa()) {
                        System.out.println(no++ + ". [" + s.getNis() + "] " + s.getName() + " - " + s.getAlamat());
                    }
                }
                case 3 -> {
                    System.out.print("\nNIS yang diedit: "); String nis = sc.nextLine().trim();
                    System.out.print("Nama baru: "); String nama = sc.nextLine().trim();
                    System.out.print("Alamat baru: "); String alamat = sc.nextLine().trim();
                    ctrl.updateSiswa(nis, nama, alamat);
                }
                case 4 -> {
                    System.out.print("\nNIS yang dihapus: "); String nis = sc.nextLine().trim();
                    System.out.print("Konfirmasi? (y/n): ");
                    if (sc.nextLine().trim().equalsIgnoreCase("y")) {
                        ctrl.deleteSiswa(nis);
                    }
                    else {
                        System.out.println("Dibatalkan.");
                    }
                }
                case 0 -> {}
                default -> System.out.println("Pilihan tidak valid!");
            }
        } while (pilihan != 0);
    }

    // Menu Buku
    private static void menuBuku() {
        int pilihan;
        do {
            System.out.println("\n----------- Kelola Data Buku -----------");
            System.out.println(" 1. Tambah  \n 2. Lihat  \n 3. Edit  \n 4. Hapus  \n 0. Kembali");
            System.out.println("----------------------------------------");
            System.out.print("Pilih: ");
            pilihan = bacaInt();
            switch (pilihan) {
                case 1 -> {
                    System.out.print("\nKode: "); String kode = sc.nextLine().trim();
                    System.out.print("Judul: "); String judul = sc.nextLine().trim();
                    System.out.print("Jenis: "); String jenis = sc.nextLine().trim();
                    // Membuat objek buku baru
                    Buku buku = new Buku(kode, judul, jenis);
                    ctrl.saveBuku(buku);
                }
                case 2 -> {
                    System.out.println("\n============== Daftar Buku =============");
                    int no = 1;
                    for (Buku b : ctrl.getAllBuku()) {
                        String status = ctrl.cekKetersediaanBuku(b.getKode()) ? "[DIPINJAM]" : "[TERSEDIA]";
                        System.out.println(no++ + ". " + status + " [" + b.getKode() + "] " + b.getJudul() + " - " + b.getJenis());
                    }
                }
                case 3 -> {
                    System.out.print("\nKode yang diedit: "); String kode = sc.nextLine().trim();
                    System.out.print("Judul baru: "); String judul = sc.nextLine().trim();
                    System.out.print("Jenis baru: "); String jenis = sc.nextLine().trim();
                    ctrl.updateBuku(kode, judul, jenis);
                }
                case 4 -> {
                    System.out.print("\nKode yang dihapus: "); String kode = sc.nextLine().trim();
                    System.out.print("Konfirmasi? (y/n): ");
                    if (sc.nextLine().trim().equalsIgnoreCase("y")){
                        ctrl.deleteBuku(kode);
                    } else { 
                        System.out.println("Dibatalkan.");
                    }
                }
                case 0 -> {}
                default -> System.out.println("Pilihan tidak valid!");
            }
        } while (pilihan != 0);
    }

    // Menu Pegawai
    private static void menuPegawai() {
        int pilihan;
        do {
            System.out.println("\n--------- Kelola Data Pegawai ----------");
            System.out.println(" 1. Tambah  \n 2. Lihat  \n 3. Edit  \n 4. Hapus  \n 0. Kembali");
            System.out.println("----------------------------------------");
            System.out.print("Pilih: ");
            pilihan = bacaInt();
            switch (pilihan) {
                case 1 -> {
                    System.out.print("\nNIP: "); String nip = sc.nextLine().trim();
                    System.out.print("Nama: "); String nama = sc.nextLine().trim();
                    System.out.print("Tgl Lahir (yyyy-MM-dd): "); String tgl = sc.nextLine().trim();
                    try {
                        //membuat objek pegawai baru
                        Pegawai pegawai = new Pegawai(nama, nip, LocalDate.parse(tgl));
                        ctrl.savePegawai(pegawai);
                    }catch (Exception e) {
                        System.out.println("Format tanggal salah! Contoh: 2000-12-31"); 
                    }
                }
                case 2 -> {
                    System.out.println("\n============ Daftar Pegawai ============");
                    int no = 1;
                    for (Pegawai p : ctrl.getAllPegawai())
                        System.out.println(no++ + ". [" + p.getNip() + "] " + p.getName() + " - " + p.getTglLahir());
                }
                case 3 -> {
                    System.out.print("\nNIP yang diedit: "); String nip = sc.nextLine().trim();
                    System.out.print("Nama baru: "); String nama = sc.nextLine().trim();
                    System.out.print("Tgl Lahir baru (yyyy-MM-dd): "); String tgl = sc.nextLine().trim();
                    try {
                        ctrl.updatePegawai(nip, nama, LocalDate.parse(tgl));
                    }catch (Exception e) { 
                        System.out.println("Format tanggal salah! Contoh: 2000-12-31"); 
                    }
                }
                case 4 -> {
                    System.out.print("NIP yang dihapus: "); String nip = sc.nextLine().trim();
                    System.out.print("Konfirmasi? (y/n): ");
                    if (sc.nextLine().trim().equalsIgnoreCase("y")) {
                        ctrl.deletePegawai(nip);
                    } else {
                        System.out.println("Dibatalkan.");
                    }
                }
                case 0 -> {}
                default -> System.out.println("Pilihan tidak valid!");
            }
        } while (pilihan != 0);
    }

    // Menu Peminjaman
    private static void menuPeminjaman() {
        System.out.println("\n--------- Transaksi Peminjaman ---------");
        System.out.print("NIS Peminjam : "); String nis = sc.nextLine().trim();
        System.out.print("Kode Buku : "); String kode = sc.nextLine().trim();
        System.out.print("Tgl Pinjam (yyyy-MM-dd) : "); String tglP = sc.nextLine().trim();
        System.out.print("Tgl Kembali (yyyy-MM-dd) : "); String tglK = sc.nextLine().trim();
        try {
            LocalDate tglPinjam = tglP.isEmpty() ? LocalDate.now() : LocalDate.parse(tglP);
            LocalDate tglKembali = LocalDate.parse(tglK);
            if (tglKembali.isBefore(tglPinjam)) {
                System.out.println("Tanggal kembali tidak boleh sebelum tanggal pinjam!");
                return;
            }
            // Membuat objek peminjaman baru
            Peminjaman peminjaman = new Peminjaman("", nis, kode, tglPinjam, tglKembali, 0);
            ctrl.savePeminjaman(peminjaman);
        } catch (Exception e) {
            System.out.println("Format tanggal salah! Contoh: 2025-07-20");
        }
    }

    // Menu Pengembalian
    private static void menuPengembalian() {
        System.out.println("\n------------ Pengembalian Buku -------------");
        ctrl.laporanBukuBelumKembali();
        System.out.print("Kode Transaksi: ");
        ctrl.pengembalianBuku(sc.nextLine().trim());
    }

    // Menu Laporan
    private static void menuLaporan() {
        int pilihan;
        do {
            System.out.println("\n-------------- Laporan -----------------");
            System.out.println(" 1. Buku Belum Dikembalikan");
            System.out.println(" 2. Buku Jatuh Tempo");
            System.out.println(" 3. Semua Riwayat Peminjaman");
            System.out.println(" 0. Kembali");
            System.out.println("----------------------------------------");
            System.out.print("Pilih: ");
            pilihan = bacaInt();
            switch (pilihan) {
                case 1 -> ctrl.laporanBukuBelumKembali();
                case 2 -> ctrl.laporanBukuJatuhTempo();
                case 3 -> ctrl.laporanSemuaPeminjaman();
                case 0 -> {}
                default -> System.out.println("Pilihan tidak valid!");
            }
        } while (pilihan != 0);
    }

    // Membaca input pilihan dari Menu
    private static int bacaInt() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        }
        catch (NumberFormatException e) {
            return -1;
        }
    }
}
