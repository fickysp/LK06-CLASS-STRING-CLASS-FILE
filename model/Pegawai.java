/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LK6.model;

/**
 *
 * @author FICKY
 */
import java.time.*;
public class Pegawai extends Person{
    private String nip;
    private LocalDate tglLahir;
    public Pegawai(String name, String nip, LocalDate tgl) {
        super(name);
        this.nip = nip;
        this.tglLahir = tgl;
    }

    @Override
    public String toString() {
        return super.toString() + getNip() + getTglLahir(); 
    }      

    public String getNip() {
        return nip;
    }

    public LocalDate getTglLahir() {
        return tglLahir;
    } 

    public void setTglLahir(LocalDate tglLahir) {
        this.tglLahir = tglLahir;
    }
}
