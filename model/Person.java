/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LK6.model;

/**
 *
 * @author FICKY
 */
public abstract class Person {
    private String name;
    
    public Person(String name){
    this.name = name;
    }
    
    @Override
    public String toString(){
        return String.format("Nama: " + getName());
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}

