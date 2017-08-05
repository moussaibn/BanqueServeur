package entites;

import java.io.*;

public class Compte implements Serializable{
	
	private String numcompte;
	private String libcompte;
	private Double solde;
	private String sens;
	private String numcli;
	
	public String getNumcli() {
		return numcli;
	}
	public void setNumcli(String numcli) {
		this.numcli = numcli;
	}
	public String getNumcompte() {
		return numcompte;
	}
	public void setNumcompte(String numcompte) {
		this.numcompte = numcompte;
	}
	public String getLibcompte() {
		return libcompte;
	}
	public void setLibcompte(String libcompte) {
		this.libcompte = libcompte;
	}
	public Double getSolde() {
		return solde;
	}
	public void setSolde(Double solde) {
		this.solde = solde;
	}
	public String getSens() {
		return sens;
	}
	public void setSens(String sens) {
		this.sens = sens;
	}
	
	

}
