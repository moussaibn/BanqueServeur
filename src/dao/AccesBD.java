package dao;
import entites.*;

import java.sql.*;
import java.util.ArrayList;





public class AccesBD 
{
	private Connection con=null;
    private PreparedStatement st=null;
    private PreparedStatement st1=null;
    private PreparedStatement st2=null;
    private PreparedStatement st3=null;
    private ResultSet rs=null;
    private ResultSet rs2=null;
    private ResultSet rs3=null;
    
   public AccesBD()
   {
	   try
       {
           Class.forName("com.mysql.jdbc.Driver");
           con = DriverManager.getConnection("jdbc:mysql://localhost:8889/banquedb", "root", "root");
           //System.out.println("Connexion OK !");
       }
       catch(Exception ex)
       {
           //System.out.println("!!!!"+ex.getMessage());
       }

   }
   
   public void creeragence(Agence a){
	   try{
		   st = con.prepareStatement("insert into agence (numagence,nomagence) values(NULL ,?)");
		   
           st.setString(1,a.getNomagence());
           st.executeUpdate();
	   }
	   catch(Exception ex)
       {
           System.out.println("!!!! ERREUR DE CREATION "+ex.getMessage());
       }
	   
   }
   
   public void creerclient(Client cl){
	   try{
		   st= con.prepareStatement("insert into client (numcli,nomcli,prenomcli,numagence) values(?,?,?,?)");
           st.setString(1,cl.getNumcli());
           st.setString(2,cl.getNomcli());
           st.setString(3,cl.getPrenomcli());
           st.setInt(4,cl.getNumagence());
           st.executeUpdate(); 
	   }
	   catch(Exception ex)
       {
           System.out.println("Probl�me cr�ation client: "+ex.getMessage());
       }
   }
   
   public void creercompteclient(Compte cp)
   {
	   try{
		   
		   st= con.prepareStatement("insert into compte (numcompte,libcompte,solde,sens,numcli) values(?,?,?,?,?)");
		   
		    System.out.println(" "+cp.getNumcompte()); 
		   System.out.println(" "+cp.getLibcompte()); 
		   System.out.println(" "+cp.getSolde()); 
		   System.out.println(" "+cp.getSens()); 
		   System.out.println(" "+cp.getNumcli());
           
           st.setString(1, cp.getNumcompte());
           st.setString(2, cp.getLibcompte());
           st.setDouble(3, cp.getSolde());
           st.setString(4, cp.getSens());
           st.setString(5, cp.getNumcli());
           st.executeUpdate();
		   
	   }
	   catch(Exception ex)
	   {
		   System.out.println("!!!!"+ex.getMessage()); 
	   }
	   
   }
   
   public void passeroperation(Operations op){
	   try{
		   double solde=0.0, soldenouveau=0.0;
		   String numcpt=op.getNumcompte();
		   String sensnouveau="";
		   
		   st= con.prepareStatement("insert into Operations (libop,sensop,dateop,montant,numcompte) values(?,?,?,?,?)");
		   
		   st2 = con.prepareStatement("select solde from compte cp where cp.numcompte='"+numcpt+"'");
		   
		   st.setString(1,op.getLibop());
		   st.setString(2, op.getSensop());
		   st.setString(3, op.getDateop());
		   if(op.getSensop().equals("CR")){
			   st.setDouble(4, op.getMontant());
		   }
		   if(op.getSensop().equals("DB")){
			   st.setDouble(4, -1*op.getMontant());
		   }
		   
		   
		   
		   st.setString(5, op.getNumcompte());
		   
		   rs=st2.executeQuery();
		   while(rs.next())
           {
             solde=rs.getDouble("solde");
           }
          
           st.executeUpdate();
           
           if(op.getSensop().equals("CR")){
        	   System.out.println("CREDIT");
        	   soldenouveau=solde + op.getMontant();
        	   
           }
           if(op.getSensop().equals("DB")){
        	   System.out.println("DEBIT");
        	   soldenouveau = solde - op.getMontant();
           }
           st1= con.prepareStatement("update compte set solde="+soldenouveau+"where numcompte='"+numcpt+"'");
           st1.executeUpdate();
           
           st2=con.prepareStatement("select sum(montant) as SC from operations where sensop='CR' and numcompte='"+op.getNumcompte()+"'");
           System.out.println("select sum(montant) as SC from operations where sensop='CR' and numcompte='"+op.getNumcompte()+"'");
           st3=con.prepareStatement("select sum(montant) as SD from operations where sensop='DB' and numcompte='"+op.getNumcompte()+"'");
           System.out.println("select sum(montant) as SD from operations where sensop='DB' and numcompte='"+op.getNumcompte()+"'");
           
           rs2=st2.executeQuery();
           double SC=0.0, SD=0.0;
           while(rs2.next()){
        	    SC = rs2.getDouble("SC");
           }
           rs3=st3.executeQuery();
           while(rs3.next()){
        	   SD = rs3.getDouble("SD");
           }
           
           if(SC>=SD){
           	sensnouveau="CR";
           }
           else{
        	sensnouveau="DB";
           }
           
           st2= con.prepareStatement("update compte set sens='"+sensnouveau+"'where numcompte='"+numcpt+"'");
           st2.executeUpdate();
           
           
              
		   
	   }
	   catch(Exception ex)
	   {
		   System.out.println("!!!!"+ex.getMessage()); 
	   }
   }
   
   
   public ArrayList<Agence> listerAgences()
   {
	   ArrayList <Agence> liste= new ArrayList <Agence>();
	   try
	   {
		   st =con.prepareStatement("select * from agence");
           rs=st.executeQuery();
           while(rs.next())
           {
            Agence a = new Agence();
            a.setNumagence(rs.getInt("numagence"));
            a.setNomagence(rs.getString("nomagence"));
            liste.add(a);
            }
          
	   }
	   catch(Exception ex)
       {
           System.out.println("!!!!"+ex.getMessage());
       }
	   return liste;
   }
   
   public ArrayList<Object> remplir()
   {
	   ArrayList<Object> o = new ArrayList<Object>();
       try
	   {
		  
		   st = con.prepareStatement("select nomagence from agence");
           rs=st.executeQuery();
           int i=0;
           while(rs.next())
           {
        	   Object ob=new Object();
        	   ob=(rs.getString("nomagence"));
        	   o.add(ob);
            }
          
	   }
	   catch(Exception ex)
       {
           System.out.println("!!!!"+ex.getMessage());
       }
       return o;
}
   public ArrayList<Compte> listerComptes(Client cl)
   {
	   System.out.println("Nulero "+cl.getNumcli());
	   ArrayList <Compte> liste= new ArrayList <Compte>();
	   try
	   {
		   
		   st =con.prepareStatement("select * from compte where numcli='"+cl.getNumcli()+"'");
		   System.out.println("select * from compte where numcli='"+cl.getNumcli()+"'");
           rs=st.executeQuery();
           while(rs.next())
           {
            Compte cp = new Compte();
            cp.setNumcompte(rs.getString("numcompte"));
            cp.setLibcompte(rs.getString("libcompte"));
            cp.setSolde(rs.getDouble("solde"));
            cp.setSens(rs.getString("sens"));  
            liste.add(cp);
            }
          
	   }
	   catch(Exception ex)
       {
           System.out.println("!!!!"+ex.getMessage());
       }
	   return liste;
   }
   
   public ArrayList<Compte> sensetsolde(Compte cp){
	   
	   ArrayList <Compte> liste= new ArrayList <Compte>();
	   
	   
	   try
	   {
		   
	   st = con.prepareStatement("select libcompte,solde,sens from compte cp where cp.numcompte='"+cp.getNumcompte()+"'");
       rs=st.executeQuery();
       
       while(rs.next())
       {
        Compte cp2 = new Compte();
        cp2.setNumcompte(rs.getString("numcompte"));
        cp2.setLibcompte(rs.getString("libcompte"));
        cp2.setSolde(rs.getDouble("solde"));
        cp2.setSens(rs.getString("sens"));  
        liste.add(cp2);
        }
	   }
	   catch(Exception ex)
       {
           System.out.println("!!!!"+ex.getMessage());
       }
	   return liste;

	   
   }
   
   public ArrayList<Operations> afficherreleve(Compte cp)
   {
	   ArrayList <Operations> liste= new ArrayList <Operations>();
	   try
	   {
		  
		   st = con.prepareStatement("select numop,libop,sensop,dateop,solde,montant from operations op,compte cp, client cl where cl.numcli=cp.numcli and op.numcompte='"+cp.getNumcompte()+"'");

           rs=st.executeQuery();
           while(rs.next())
           {
            Operations op = new Operations();
            op.setLibop(rs.getString("libop"));
            op.setSensop(rs.getString("sensop"));
            op.setDateop(rs.getString("dateop"));
            op.setMontant(rs.getDouble("montant"));
            op.solde= rs.getDouble("solde");

               liste.add(op);
            }
          
	   }
	   catch(Exception ex)
       {
           System.out.println("!!!!"+ex.getMessage());
       }
	   return liste; //il faudra egalement appeler la methode  sensetsolde pour connaitre le sens et le solde global du compte
   }
   
   
}
