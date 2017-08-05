package entites;


import java.io.*;


public class Client  implements Serializable{

    private String numcli;
    private String nomcli;
    private String prenomcli;
    private int numagence;

    public String getNumcli() {
        return numcli;
    }

    public void setNumcli(String numcli) {
        this.numcli = numcli;
    }

    public String getNomcli() {
        return nomcli;
    }

    public void setNomcli(String nomcli) {
        this.nomcli = nomcli;
    }

    public String getPrenomcli() {
        return prenomcli;
    }

    public void setPrenomcli(String prenomcli) {
        this.prenomcli = prenomcli;
    }

    public int getNumagence() {
        return numagence;
    }

    public void setNumagence(int numagence) {
        this.numagence = numagence;
    }
}