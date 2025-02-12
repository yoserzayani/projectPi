/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilisateur;

import Connection.MyConnection;
import InterfaceCrud.MyCrud;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Utilisateur 2
 */
public class UtilisateurService implements MyCrud<Utilisateur> {

    public UtilisateurService() {
    }
    
    
    
    MyConnection conx= MyConnection.getInstance();
    Connection myConx=conx.getConnection();

    @Override
    public int ajouter(Utilisateur u) {
        if(this.chercher(u)!=null)
            return -1; 
        
        String req="INSERT INTO `utilisateur` ( `cin`, `nom`, `prenom`, `date_naissance`, `age`, `pic`, `username`, `password`)"
                    + " VALUES ( ?,?, ?,?,?, ?, ?, ?);";
        
        try {
            PreparedStatement prepStat = myConx.prepareStatement(req);
            
            prepStat.setString(1, u.getCIN());
            prepStat.setString(2, u.getNom());
            prepStat.setString(3, u.getPrenom());
            prepStat.setString(4, u.getDateNaissance());
            prepStat.setInt(5, u.getAge());
            prepStat.setString(6, u.getPic());
            prepStat.setString(7, u.getUserName());
            prepStat.setString(8, u.getPassword());
            int rowsAffected =  prepStat.executeUpdate();
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        
        return 0;
    }

    @Override
    public Utilisateur chercher(Utilisateur u) {
        String req="SELECT * FROM `utilisateur` WHERE `cin` LIKE ? AND `nom` LIKE ? AND `prenom` LIKE ? AND "
                + "`date_naissance` = ? AND `age` = ? AND  `username` LIKE ? ;";
        Utilisateur found = new Utilisateur();
        try {
            PreparedStatement prepStat = myConx.prepareStatement(req);
            
            prepStat.setString(1, u.getCIN());
            prepStat.setString(2, u.getNom());
            prepStat.setString(3, u.getPrenom());
            prepStat.setString(4, u.getDateNaissance());
            prepStat.setInt(5, u.getAge());
           
            prepStat.setString(6, u.getUserName());
            
            ResultSet rS= prepStat.executeQuery();
            if(!rS.next())
                return null;
            found.setId(rS.getLong("id"));
            found.setCIN(rS.getString("cin"));
            found.setNom(rS.getString("nom"));
            found.setPrenom(rS.getString("Prenom"));
            found.setDateNaissance(rS.getString("date_naissance"));
            found.setAge(rS.getInt("age"));
            found.setUserName(rS.getString("username"));
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
       
        
        return found;
    }

    @Override
    public int supprimer(Utilisateur u) {
        
        String req="DELETE FROM utilisateur WHERE `utilisateur`.`id` = ?;";
        try {
            PreparedStatement prepStat = myConx.prepareStatement(req);
            prepStat.setLong(1, u.getId());
            int rowsAffected =  prepStat.executeUpdate();
            if(rowsAffected==0)
                return -1;
            
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return 0;
    }

    @Override
    public List<Utilisateur> retournerTout() {
        List<Utilisateur> retour= new ArrayList();
        String req ="select * from `utilisateur` ";
        
        
        try {
            PreparedStatement prepStat = myConx.prepareStatement(req);
            ResultSet rS= prepStat.executeQuery();
            
            while(rS.next())
            {
            Utilisateur found= new Utilisateur();
            found.setCIN(rS.getString("cin"));
            found.setNom(rS.getString("nom"));
            found.setPrenom(rS.getString("Prenom"));
            found.setDateNaissance(rS.getString("date_naissance"));
            found.setAge(rS.getInt("age"));
            found.setUserName(rS.getString("username"));
            retour.add(found);
                
            }
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return retour;
    }

    @Override
    public Utilisateur modifier(Utilisateur u, Utilisateur n) {
       
            
            String req ="UPDATE `utilisateur` SET `cin` = ?, `nom` = ?, `prenom` = ?, "
                    + "`date_naissance` = ?, `age` = ?, `pic` = ?, `username` = ?, `password` = ? WHERE `utilisateur`.`id` = 5;";
        try {
            
            PreparedStatement prepStat = myConx.prepareStatement(req);
            prepStat.setString(1, n.getCIN());
            prepStat.setString(2, n.getNom());
            prepStat.setString(3, n.getPrenom());
            prepStat.setString(4, n.getDateNaissance());
            prepStat.setInt(5, n.getAge());
            prepStat.setString(6, n.getPic());
            prepStat.setString(7, n.getUserName());
            prepStat.setString(8, n.getPassword());
           // prepStat.setLong(9, u.getId());
            int rowsAffected =  prepStat.executeUpdate();
           
            
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return n;
    }
    
    
    
    public int login (String username , String password){
        String req="select password from utilisateur where username=?;";
        
        try {
            PreparedStatement prepStat = myConx.prepareStatement(req);
            
            prepStat.setString(1, username);
            
            ResultSet rS= prepStat.executeQuery();
            if(!rS.next())
                return -1;
            if(!password.equals(rS.getString("password")))
                return -2;
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    
       
        
        
        return 0;
    }
    
    
}
