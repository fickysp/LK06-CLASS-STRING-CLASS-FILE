/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LK6.controller;

/**
 *
 * @author FICKY
 */
import java.io.*;
import LK6.model.*;
import java.time.*;
import java.time.format.*;
import java.time.temporal.*;
import java.util.ArrayList;

public class Controller {

    //folder
    private final String FOLDER = "data";
    //nama file
    private final String[] FILE_PATH = {"siswa.txt", "pegawai.txt", "buku.txt", "peminjaman.txt"};

    //method untuk membuat folder atau data
    public void loadData() {
        File folder = new File(FOLDER);
        //jika folder tidak ada maka dibuat baru
        if (!folder.exists()) {
            folder.mkdir();
        }
        for (String filePath : FILE_PATH) {
            File file = new File("data", filePath);
            //jika file tidak ada maka dibuat baru
            if (!file.exists()) {
                try {
                    if (file.createNewFile()) {
                        System.out.println("File " + file + " berhasil dibuat.");
                    }
                    //mengisi data pegawai agar ada 1 data untuk login
                    if (filePath.equals("pegawai.txt")) {
                        setDefaultPegawai(file);
                    }
                } catch (IOException e) {
                    System.out.println("Gagal membuat file: " + filePath);
                }
            }
        }
    }

    //method untuk membuat pegawai default
    private void setDefaultPegawai(File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write("001|Hikmatullah|2000-10-11");
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Gagal membuat pegawai default, " + e.getMessage());
        }
    }

    //method untuk membuat kode 
    private String generateKodePeminjaman(String nis) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        return "TRX-" + nis + "-" + LocalDateTime.now().format(formatter);
    }

    //method untuk membuat data siswa
    public void saveSiswa(Siswa siswa) {
        File fileSiswa = new File(FOLDER, "siswa.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileSiswa, true))) {
            String data = String.join("|",
                    siswa.getNis(),
                    siswa.getName(),
                    siswa.getAlamat()
            );
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Gagal menambah data siswa, " + e.getMessage());
        }
    }

    //method untuk membuat data pegawai
    public void savePegawai(Pegawai pegawai) {
        File filePegawai = new File(FOLDER, "pegawai.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePegawai, true))) {
            String data = String.join("|",
                    pegawai.getNip(),
                    pegawai.getName(),
                    pegawai.getTglLahir().toString()
            );
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Gagal menambah data pegawai, " + e.getMessage());
        }
    }

    //method untuk membuat data buku
    public void saveBuku(Buku buku) {
        File fileBuku = new File(FOLDER, "buku.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileBuku, true))) {
            String data = String.join("|",
                    buku.getKode(),
                    buku.getJudul(),
                    buku.getJenis()
            );
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Gagal menambah data buku, " + e.getMessage());
        }
    }  

    //method untuk cek peminjaman, jika peminjaman < 2 maka boleh meminjam
    public boolean cekPeminjaman(String nis) {
        int count = 0;
        File file = new File(FOLDER, "peminjaman.txt");
        if (!file.exists()) {
            return true;
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data[1].equals(nis) && data[5].equals("0")) {
                    count++;
                }
            }
        } catch (IOException e) {
            System.out.println("Gagal mengecek data peminjaman: " + e.getMessage());
        }
        return count < 2;
    }

    public boolean cekSiswa(String nis) {
        for (Siswa s : getAllSiswa()) {
            if (s.getNis().equals(nis)) {
                return true; 
            }
        }
        return false; 
    }
    
    public boolean cekKetersediaanBuku(String kodeBuku) {
        for (Peminjaman p : getAllPeminjaman()) {
            if (p.getKodeBuku().equals(kodeBuku) && p.getStatus() == 0) {
                return true; 
            }
        }
        return false; 
    }
    
    public boolean cekBuku(String kodeBuku) {
        for (Buku b : getAllBuku()) {
            if (b.getKode().equals(kodeBuku)) {
                return true; 
            }
        }
        return false; 
    }
    
    //method untuk membuat data peminjaman
    public void savePeminjaman(Peminjaman pinjam) {
        if (!cekBuku(pinjam.getKodeBuku())) {
            System.out.println("Buku Tidak Ditemukan");
            return;
        }
        
        if (!cekSiswa(pinjam.getNis())) {
            System.out.println("Siswa Tidak Valid");
            return;
        }
        
        if (!cekPeminjaman(pinjam.getNis())) {
            System.out.println("Peminjaman sudah mencapai batas");
            return;
        }        
        
        if (cekKetersediaanBuku(pinjam.getKodeBuku())) {
            System.out.println("Buku Tidak Tersedia");
            return;
        }
        
        String kodePeminjaman = generateKodePeminjaman(pinjam.getNis());
        File filePeminjaman = new File(FOLDER, "peminjaman.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePeminjaman, true))) {
            String data = String.join("|",
                    kodePeminjaman,
                    pinjam.getNis(),
                    pinjam.getKodeBuku(),
                    pinjam.getTglPinjam().toString(),
                    pinjam.getTglKembali().toString(),
                    String.valueOf(pinjam.getStatus())
            );
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Gagal menambah data peminjaman, " + e.getMessage());
        }
    }

    //method untuk menampung semua data peminjaman
    public ArrayList<Peminjaman> getAllPeminjaman() {
        ArrayList<Peminjaman> list = new ArrayList<>();
        File file = new File(FOLDER, "peminjaman.txt");
        if (!file.exists()) {
            return list;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");
                Peminjaman p = new Peminjaman(data[0], data[1], data[2],
                        LocalDate.parse(data[3]), LocalDate.parse(data[4]),
                        Integer.parseInt(data[5]));
                list.add(p);
            }
        } catch (IOException e) {
            System.err.println("Gagal membaca data: " + e.getMessage());
        }
        return list;
    }

    public ArrayList<Siswa> getAllSiswa() {
        ArrayList<Siswa> list = new ArrayList<>();
        File file = new File(FOLDER, "siswa.txt");
        if (!file.exists()) {
            return list;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                // Urutan: NIS, Nama, Alamat [cite: 11]
                list.add(new Siswa(data[1], data[0], data[2]));
            }
        } catch (IOException e) {
            System.err.println("Gagal memuat data siswa: " + e.getMessage());
        }
        return list;
    }

    public ArrayList<Buku> getAllBuku() {
        ArrayList<Buku> list = new ArrayList<>();
        File file = new File(FOLDER, "buku.txt");
        if (!file.exists()) {
            return list;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                // Urutan: Kode, Judul, Jenis Buku [cite: 11]
                list.add(new Buku(data[0], data[1], data[2]));
            }
        } catch (IOException e) {
            System.err.println("Gagal memuat data buku: " + e.getMessage());
        }
        return list;
    }

    public ArrayList<Pegawai> getAllPegawai() {
        ArrayList<Pegawai> list = new ArrayList<>();
        File file = new File(FOLDER, "pegawai.txt");
        if (!file.exists()) {
            return list;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                // Urutan: NIP, Nama, Tanggal Lahir [cite: 12]
                list.add(new Pegawai(data[1], data[0], LocalDate.parse(data[2])));
            }
        } catch (IOException e) {
            System.err.println("Gagal memuat data pegawai: " + e.getMessage());
        }
        return list;
    }

    //method untuk pengembalian buku dan mengubah status peminjaman menjadi 1 (dikembalikan)
    public void pengembalianBuku(String kodeTransaksi) {
        ArrayList<Peminjaman> listPeminjaman = getAllPeminjaman();
        boolean found = false;

        for (Peminjaman p : listPeminjaman) {
            if (p.getKodeTransaksi().equals(kodeTransaksi) && p.getStatus() == 0) {
                p.setStatus(1);
                found = true;
                break;
            }
        }

        if (found) {
            File file = new File(FOLDER, "peminjaman.txt");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
                for (Peminjaman p : listPeminjaman) {
                    String data = String.join("|",
                            p.getKodeTransaksi(), p.getNis(), p.getKodeBuku(),
                            p.getTglPinjam().toString(), p.getTglKembali().toString(),
                            String.valueOf(p.getStatus())
                    );
                    writer.write(data);
                    writer.newLine();
                }
                System.out.println("Buku berhasil dikembalikan!");
            } catch (IOException e) {
                System.err.println("Gagal update file: " + e.getMessage());
            }
        } else {
            System.out.println("Kode transaksi tidak ditemukan atau buku sudah dikembalikan.");
        }
    }

    //method untuk laporann buku yang belum kembali 
    public void laporanBukuBelumKembali() {
        System.out.println("\n==== DAFTAR BUKU BELUM DIKEMBALIKAN ====");
        try {
            for (Peminjaman p : getAllPeminjaman()) {
                if (p.getStatus() == 0) {
                    System.out.println(p);
                    System.out.println("----------------------------------------");
                }
            }
        } catch (Exception e) {
            System.err.println("Gagal memuat laporan: " + e.getMessage());
        }
    }

    //method untuk laporan buku jatuh tempo 
    public void laporanBukuJatuhTempo() {
        System.out.println("\n====== DAFTAR BUKU JATUH TEMPO =========");
        try {
            for (Peminjaman p : getAllPeminjaman()) {
                long terlambat = ChronoUnit.DAYS.between(p.getTglKembali(), LocalDate.now());
                if ((terlambat > 0) && (p.getStatus() == 0)) {
                    System.out.println("Status keterlambatan: " + terlambat + " hari");
                    System.out.println(p);
                    System.out.println("----------------------------------------");
                }
            }
        } catch (Exception e) {
            System.err.println("Gagal memuat laporan: " + e.getMessage());
        }
    }
    
    //method untuk login
    public Pegawai login(String nip, String nama) {
        for (Pegawai p : getAllPegawai()) {
            if (p.getNip().equals(nip) && p.getName().equalsIgnoreCase(nama)) {
                return p;
            }
        }
        return null;
    }
    
    //method untuk update dan delete Siswa
    public void updateSiswa(String nis, String namaBaru, String alamatBaru) {
        ArrayList<Siswa> list = getAllSiswa();
        boolean found = false;
        for (Siswa s : list) {
            if (s.getNis().equals(nis)) {
                s.setName(namaBaru);
                s.setAlamat(alamatBaru);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Siswa tidak ditemukan.");
            return;
        }
        File file = new File(FOLDER, "siswa.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (Siswa s : list) {
                writer.write(String.join("|", s.getNis(), s.getName(), s.getAlamat()));
                writer.newLine();
            }
            System.out.println("Data siswa berhasil diperbarui.");
        } catch (IOException e) {
            System.out.println("Gagal memperbarui data siswa: " + e.getMessage());
        }
    }

    public void deleteSiswa(String nis) {
        // cegah penghapusan jika masih ada pinjaman aktif
        for (Peminjaman p : getAllPeminjaman()) {
            if (p.getNis().equals(nis) && p.getStatus() == 0) {
                System.out.println("Gagal! Siswa masih memiliki buku yang belum dikembalikan.");
                return;
            }
        }
        ArrayList<Siswa> list = getAllSiswa();
        boolean found = list.removeIf(s -> s.getNis().equals(nis));
        if (!found) {
            System.out.println("Siswa tidak ditemukan.");
            return; 
        }
        File file = new File(FOLDER, "siswa.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (Siswa s : list) {
                writer.write(String.join("|", s.getNis(), s.getName(), s.getAlamat()));
                writer.newLine();
            }
            System.out.println("Data siswa berhasil dihapus.");
        } catch (IOException e) {
            System.out.println("Gagal menghapus data siswa: " + e.getMessage());
        }
    }
    
    //method untuk update dan delete Buku
    public void updateBuku(String kode, String judulBaru, String jenisBaru) {
        ArrayList<Buku> list = getAllBuku();
        boolean found = false;
        for (Buku b : list) {
            if (b.getKode().equals(kode)) {
                b.setJudul(judulBaru);
                b.setJenis(jenisBaru);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Buku tidak ditemukan.");
            return;
        }
        File file = new File(FOLDER, "buku.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (Buku b : list) {
                writer.write(String.join("|", b.getKode(), b.getJudul(), b.getJenis()));
                writer.newLine();
            }
            System.out.println("Data buku berhasil diperbarui.");
        } catch (IOException e) {
            System.out.println("Gagal memperbarui data buku: " + e.getMessage());
        }
    }

    public void deleteBuku(String kode) {
        // cegah penghapusan jika sedang dipinjam
        for (Peminjaman p : getAllPeminjaman()) {
            if (p.getKodeBuku().equals(kode) && p.getStatus() == 0) {
                System.out.println("Gagal! Buku sedang dipinjam.");
                return;
            }
        }
        ArrayList<Buku> list = getAllBuku();
        boolean found = list.removeIf(b -> b.getKode().equals(kode));
        if (!found) {
            System.out.println("Buku tidak ditemukan.");
            return;
        }
        File file = new File(FOLDER, "buku.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (Buku b : list) {
                writer.write(String.join("|", b.getKode(), b.getJudul(), b.getJenis()));
                writer.newLine();
            }
            System.out.println("Data buku berhasil dihapus.");
        } catch (IOException e) {
            System.out.println("Gagal menghapus data buku: " + e.getMessage());
        }
    }
    
    //method untuk update dan delete Pegawai
    public void updatePegawai(String nip, String namaBaru, LocalDate tglLahirBaru) {
        ArrayList<Pegawai> list = getAllPegawai();
        boolean found = false;
        for (Pegawai p : list) {
            if (p.getNip().equals(nip)) {
                p.setName(namaBaru);
                p.setTglLahir(tglLahirBaru);
                found = true;
                break;
            }
        }
        if (!found) { 
            System.out.println("Pegawai tidak ditemukan.");
            return; 
        }
        File file = new File(FOLDER, "pegawai.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (Pegawai p : list) {
                writer.write(String.join("|", p.getNip(), p.getName(), p.getTglLahir().toString()));
                writer.newLine();
            }
            System.out.println("Data pegawai berhasil diperbarui.");
        } catch (IOException e) {
            System.out.println("Gagal memperbarui data pegawai: " + e.getMessage());
        }
    }
        
    public void deletePegawai(String nip) {
        ArrayList<Pegawai> list = getAllPegawai();
        if (list.size() <= 1) {
            System.out.println("Gagal! Minimal harus ada 1 pegawai.");
            return;
        }
        boolean found = list.removeIf(p -> p.getNip().equals(nip));
        if (!found) {
            System.out.println("Pegawai tidak ditemukan.");
            return;
        }
        File file = new File(FOLDER, "pegawai.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (Pegawai p : list) {
                writer.write(String.join("|", p.getNip(), p.getName(), p.getTglLahir().toString()));
                writer.newLine();
            }
            System.out.println("Data pegawai berhasil dihapus.");
        } catch (IOException e) {
            System.out.println("Gagal menghapus data pegawai: " + e.getMessage());
        }
    }
    
    //method untuk riwayat semua pinjaman
    public void laporanSemuaPeminjaman() {
        System.out.println("\n======== Riwayat Semua Pinjaman ========");
        ArrayList<Peminjaman> list = getAllPeminjaman();
        if (list.isEmpty()) {
            System.out.println("Belum ada data peminjaman");
            return; 
        }
        for (Peminjaman p : list) {
            System.out.println(p.toString());
            System.out.println("----------------------------------------");
        }
    }
}
