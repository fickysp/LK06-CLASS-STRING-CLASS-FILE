/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LK6.model;

/**
 *
 * @author AL KUBRANI
 */
import java.time.*;

public class Peminjaman {

    private String kodeTransaksi;
    private String nis;
    private String kodeBuku;
    private LocalDate tglPinjam;
    private LocalDate tglKembali;
    private int status;

    public Peminjaman(String kt, String n, String kb, LocalDate tp, LocalDate tk, int s) {
        this.kodeTransaksi = kt;
        this.nis = n;
        this.kodeBuku = kb;
        this.tglPinjam = tp;
        this.tglKembali = tk;
        this.status = s;
    }

    @Override
    public String toString() {
        String statusNow = "belum dikembalikan";
        if (this.getStatus() == 1) {
            statusNow = "dikembalikan";
        }

        return String.format("=========== Detail Peminjaman ==========\n"
                + "Kode Transaksi: " + getKodeTransaksi()
                + "\nKode Buku: " + getKodeBuku()
                + "\nNIS: " + getNis()
                + "\nTanggal Peminjaman: " + getTglPinjam()
                + "\nTanggal Pengembalian: " + getTglKembali()
                + "\nStatus Peminjaman: " + statusNow
        );
    }

    public String getKodeTransaksi() {
        return kodeTransaksi;
    }

    public String getNis() {
        return nis;
    }

    public String getKodeBuku() {
        return kodeBuku;
    }

    public LocalDate getTglPinjam() {
        return tglPinjam;
    }

    public LocalDate getTglKembali() {
        return tglKembali;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int s) {
        this.status = s;
    }   

    public void setNis(String nis) {
        this.nis = nis;
    }

    public void setKodeBuku(String kodeBuku) {
        this.kodeBuku = kodeBuku;
    }

    public void setTglPinjam(LocalDate tglPinjam) {
        this.tglPinjam = tglPinjam;
    }

    public void setTglKembali(LocalDate tglKembali) {
        this.tglKembali = tglKembali;
    }
}