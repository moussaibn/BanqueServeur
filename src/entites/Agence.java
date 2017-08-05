package entites;

import java.io.*;

public class Agence  implements Serializable{
    private String nomagence;
    private int numagence=0;
    
    
    public int getNumagence() {
		return numagence;
	}

	public void setNumagence(int numagence) {
		this.numagence = numagence;
	}

	public String getNomagence() {
        return nomagence;
    }

    public void setNomagence(String nomagence) {
        this.nomagence = nomagence;
    }
}