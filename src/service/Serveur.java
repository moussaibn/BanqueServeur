package service;

import java.net.*;
import java.util.*;

import javax.swing.*;
import entites.*;
import dao.*;

import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class Serveur extends JFrame implements ActionListener
{
	 private JTextArea zonerecep;
	 private JButton qt;
	 private JPanel pan1,pan2;
	 private Calendar date_jour = Calendar.getInstance();
	 int jour = date_jour.get(Calendar.DATE);
     int mois = date_jour.get(Calendar.MONTH)+1;
     int annee = date_jour.get(Calendar.YEAR);
     int heure = date_jour.get(Calendar.HOUR_OF_DAY);
     int minute = date_jour.get(Calendar.MINUTE);

	 public Serveur()
	 {
	     zonerecep=new JTextArea(25,35);
	     zonerecep.setFont(new Font("Arial", 6, 16));
	    qt=new JButton("Quitter");
	    setTitle(" Serveur TCP Multiclients");
	    pan1=new JPanel();
	    pan2=new JPanel();
	    pan1.add(new JScrollPane(zonerecep));
	    qt.addActionListener(this);
	    pan2.add(qt);
	    add(pan1,BorderLayout.CENTER);
	    add(pan2,BorderLayout.SOUTH);
	    setSize(500,600);
	    setLocationRelativeTo(null);
	    setVisible(true);


	     try
	    {
	        ServerSocket serv = new ServerSocket(8000);
	        zonerecep.append("Le Serveur a demarre "+"\n");
	        int numclient=1;
	        while(true)
	        {
	            Socket socket=serv.accept();
	            InetAddress adr = socket.getInetAddress();
	            String ipclient = adr.getHostAddress();
	            String nomclient= adr.getHostName();
	            zonerecep.append("client n�:"+numclient+" adresse ip:"+ipclient+"\n");
	            zonerecep.append("nom machine cliente: "+nomclient+"\n");
	            zonerecep.append("Connexion le "+jour+"/"+mois+"/"+annee+" � "+heure+":"+minute+"\n");
	            Service s = new Service(socket);
	            s.start();
	            numclient++;
	        }

	    }
	    catch(IOException ex)
	    {
	        System.out.println("Blem1 "+ex.getMessage());
	    }
	 }

	 //class interne
	    class Service extends Thread
	    {
	       private  Socket socket;
	       private  AccesBD bd;
	        public Service(Socket socket)
	        {
	        	bd = new AccesBD();
	            this.socket=socket;
	            	   
	        }
	        public void run()
	        {
	            try
	            {
	           ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
	           ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
	           DataOutputStream out = new DataOutputStream(socket.getOutputStream());
	           DataInputStream in = new DataInputStream(socket.getInputStream());
	       	
	           String mode;
	           do
	           {
	        	    
	             mode =(String)ois.readObject();
	             zonerecep.append("Mode en cours d'exploitation "+mode+"\n");
	             if (mode.equals("Creeragence"))
	             {
	                  System.out.println("Creation dagence");
	                  Agence a = (Agence) ois.readObject();
                      bd.creeragence(a);                     
	             }
	            
	             //{
	            	 if(mode.equals("AjoutClient"))
	            	 {
	            		System.out.println("Creation de client"); 
	            		Client cl= (Client) ois.readObject();
	            		bd.creerclient(cl);
	            	 }
	            	 
	            	 if(mode.equals("Creercompte"))
	            	 {
	            		System.out.println("Creation de compte client");
	            		Compte cp= (Compte) ois.readObject();
	            		bd.creercompteclient(cp);
	            	 }
	            	
	            	 if(mode.equals("Passeroperation"))
	            	 {
	            		System.out.println("Passer Op�ration");
	            		Operations op= (Operations) ois.readObject();
	            		bd.passeroperation(op);
	            	 }
	            	
	                 if (mode.equals("listerAgences"))
	                 {

	               	 ArrayList<Agence> liste = new ArrayList<Agence>();
	               	   liste=bd.listerAgences(); 
	                    oos.writeObject(liste);
	                    oos.flush();
	                 }
	                
	                 if (mode.equals("afficheCompteCli"))
	                 {

	               	   ArrayList<Compte> liste = new ArrayList<Compte>();
	               	   Client cl= (Client)ois.readObject();
	               	   liste=bd.listerComptes(cl); 
	                    oos.writeObject(liste);
	                    oos.flush();
	                 }
	               
	                 if (mode.equals("Afficherelevebancaire"))
	                 {

	               	 ArrayList<Operations> liste = new ArrayList<Operations>();
	               	 Compte cp= (Compte)ois.readObject();
	               	 liste=bd.afficherreleve(cp); 
	                 oos.writeObject(liste);
	                 oos.flush();
	                 }

	                 if(mode.equals("remplir")){
		                	System.out.println("entre");
		                	ArrayList<Object> o = new ArrayList<Object>();
			               	o=bd.remplir(); 
			                oos.writeObject(o);
			                oos.flush();
		                 } 
		                 
	                 
	                 else
	                 if (mode.equals("fin"))
	                 {
	                      zonerecep.append("Connexion terminee!!! pour un client"+"\n");
	                      oos.flush();
	                     
	                 }
	             //}
	           }
	             while(true);
	           
	            }
	            catch(Exception ex)
	            {
	                System.out.println("**** ICI: "+ex.getMessage());
	            }
	          
	            

	    }
	 }//fin classe interne

	  public void actionPerformed(ActionEvent e)
	    {
	        dispose();
	        System.exit(0);
	    }



	    public static void main(String args[])
	    {
	        new Serveur();
	    }
	
}
