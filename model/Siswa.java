/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LK6.model;

/**
 *
 * @author FICKY
 */
public class Siswa extends Person {

    private String nis;
    private String alamat;

    public Siswa(String name, String nis, String alamat) {
        super(name);
        this.nis = nis;
        this.alamat = alamat;
    }

    @Override
    public String toString() {
        return super.toString() + getNis() + getAlamat();
    }

    public String getNis() {
        return nis;
    }

    public String getAlamat() {
        return alamat;
    }
    
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}

