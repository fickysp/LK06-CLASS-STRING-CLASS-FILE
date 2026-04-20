/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LK6.model;

/**
 *
 * @author FICKY
 */
public class Buku {
    private String kode;
    private String judul;
    private String jenis;

    public Buku(String kode, String judul, String jenis) {        
        this.kode = kode;
        this.judul = judul;
        this.jenis = jenis;
    }

    @Override
    public String toString() {
        return String.format("Kode Buku: " + kode
                +"\n Judul: "+ judul
                +"\n Jenis: " + jenis
        );
    }

    public String getKode() {
        return kode;
    }

    public String getJudul() {
        return judul;
    }
    
    public String getJenis() {
        return jenis;
    }   

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }
}
