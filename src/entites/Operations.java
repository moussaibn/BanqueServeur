package entites;

import java.io.*;

public class Operations implements Serializable{

	private int numop;
    private String libop;
    private String sensop;
    private String dateop;
    private double montant;
    private String numcompte; 
    
	public int getNumop() {
		return numop;
	}
	public void setNumop(int numop) {
		this.numop = numop;
	}
	public String getLibop() {
		return libop;
	}
	public void setLibop(String libop) {
		this.libop = libop;
	}
	public String getSensop() {
		return sensop;
	}
	public void setSensop(String sensop) {
		this.sensop = sensop;
	}
	public String getDateop() {
		return dateop;
	}
	public Double solde;
	public void setDateop(String datop) {
		this.dateop = datop;
	}
	public double getMontant() {
		return montant;
	}
	public void setMontant(double montant) {
		this.montant = montant;
	}
	public String getNumcompte() {
		return numcompte;
	}
	public void setNumcompte(String numcompte) {
		this.numcompte = numcompte;
	}
    
    


}