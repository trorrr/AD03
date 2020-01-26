/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.ad03;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import java.net.URL;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 * @author trorrr
 */
public class AD03 {
    public static String db = "Franquicia.db";
    public static void main(String args[]) 
    { 
        
        String opcion = "";
        String param1 = "";
        String param2 = "";
        String param3 = "";
        String param4 = "";
        String tenda_elixida = "";
        CrearBBDD(db);
        cargarJSONprovincias();
        Scanner input = new Scanner(System.in);
        while(true) {
        System.out.println(" "); 
        System.out.println("Menú xestión tendas"); 
        System.out.println(" 1. Engadir unha tenda"); 
        System.out.println(" 2. Mostrar as tendas");
        System.out.println(" 3. Eliminar unha tenda");
        System.out.println(" 4. Engadir un producto");
        System.out.println(" 5. Mostrar os productos da franquicia");
        System.out.println(" 6. Mostrar os productos dunha tenda");
        System.out.println(" 7. Engadir un producto a unha tenda");
        System.out.println(" 8. Actualizar o stock dun producto nunha tenda");
        System.out.println(" 9. Mostrar o stock dun producto nunha tenda");
        System.out.println(" 10. Eliminar un producto dunha determinada tenda");
        System.out.println(" 11. Eliminar un producto");
        System.out.println(" 12. Engadir un empregado");
        System.out.println(" 13. Mostrar os empregados");
        System.out.println(" 14. Eliminar un empregado");
        System.out.println(" 15. Engadir un cliente");
        System.out.println(" 16. Mostrar os clientes");
        System.out.println(" 17. Eliminar un cliente");
        System.out.println(" E. Ler os titulares de El País"); 
        System.out.println(" F. Ler os titulares online de El País"); 
        System.out.println(" S. Saír"); 

        opcion = input.next();
        
        switch(opcion) {
            case "1":
                System.out.print("  Nome da tenda: ");
                param1 = input.next();
                System.out.print("  Provincia: ");
                param2 = input.next();
                System.out.print("  Cidade: ");
                param3 = input.next();
                EjecutarBBDD("INSERT INTO tendas(nome, id_provincia, cidade) VALUES ('"+param1+"',(select id_provincia from provincias where provincia='"+param2+"'),'"+param3+"')");
                break;
            case "2":
                LineasConsultaBBDD("select * from tendas");
                break;
            case "3":
                System.out.print("  Nome da tenda a eliminar: ");
                param1 = input.next();
                EjecutarBBDD("DELETE FROM tendas WHERE Nome='"+param1+"'");
                break;
            case "4":    
                System.out.print("  Nome do producto: ");
                param1 = input.next();
                System.out.print("  Descripción: ");
                param2 = input.next();
                System.out.print("  Precio: ");
                param3 = input.next();
                EjecutarBBDD("INSERT INTO productos(nome, descripcion, precio) VALUES ('"+param1+"','"+param2+"',"+param3+")");                
                break;
            case "5":
                LineasConsultaBBDD("select * from productos");
                break;
            case "6":
                System.out.print("  Nome da tenda da que consultar o stock: ");
                param1 = input.next();
                LineasConsultaBBDD("select * from stock join tendas on stock.id_tenda=tendas.id_tenda where tendas.nome='"+param1+"'");
                break;
            case "7":
                System.out.print("Tenda á que engadir o producto: ");
                tenda_elixida = input.next();
                System.out.print("  Nome do producto: ");
                param1 = input.next();
                System.out.print("  Cantidade inicial: ");
                param2 = input.next();
                EjecutarBBDD("INSERT INTO stocks (id_tenda,id_producto,unids) VALUES ((select(id_tenda) from tendas where nome='"+tenda_elixida+"'),(select(id_producto) from productos where nome='"+param1+"'),"+param2+")");
                break;
            case "8":
                System.out.print("Tenda da que actualizar o stock: ");
                tenda_elixida = input.next();
                System.out.print("  Nome do producto: ");
                param1 = input.next();
                System.out.print("  Nova cantidade: ");
                param2 = input.next();
                EjecutarBBDD("UPDATE stocks SET unids="+param2+" where id_tenda=(select(id_tenda) from tendas where nome='"+tenda_elixida+"') and id_producto=(select(id_producto) from productos where nome='"+param1+"')");
                //EjecutarBBDD("INSERT INTO stocks (id_tenda,id_producto,unids) VALUES (select(id_tenda) from tendas where )");
                break;               
            case "9":
                System.out.print("Tenda da que mostrar o stock: ");
                tenda_elixida = input.next();
                System.out.print("  Nome do producto: ");
                param1 = input.next();               
                LineasConsultaBBDD("select '"+tenda_elixida+"','"+param1+"',unids from stocks where id_tenda=(select(id_tenda) from tendas where nome='"+tenda_elixida+"') and id_producto=(select(id_producto) from productos where nome='"+param1+"')");
                break;
            case "10":
                System.out.print("Tenda da que eliminar o producto: ");
                tenda_elixida = input.next();
                System.out.print("  Nome do producto: ");
                param1 = input.next();
                EjecutarBBDD("DELETE FROM stocks WHERE id_tenda=(select(id_tenda) from tendas where nome='"+tenda_elixida+"') and id_producto=(select(id_producto) from productos where nome='"+param1+"')");
                break;
            case "11":
                System.out.print("Nome do producto a eliminar: ");
                param1 = input.next();
                EjecutarBBDD("DELETE FROM productos WHERE Nome='"+param1+"'");
            case "12":
                System.out.print("  Nome do empregado: ");
                param1 = input.next();
                System.out.print("  Apelidos: ");
                param2 = input.next();
                EjecutarBBDD("INSERT INTO empregados(nome, apelidos) VALUES ('"+param1+"','"+param2+"')");
                break;
            case "13":
                LineasConsultaBBDD("select * from empregados");
                break;
            case "14":
                System.out.print("  Nome do empregado a eliminar: ");
                param1 = input.next();
                System.out.print("  Apelidos: ");
                param2 = input.next();
                EjecutarBBDD("DELETE FROM empregados WHERE nome='"+param1+"' and apelidos='"+param2+"'");
                break;   
            case "15":
                System.out.print("  Nome do cliente: ");
                param1 = input.next();
                System.out.print("  Apelidos: ");
                param2 = input.next();
                System.out.print("  Email: ");
                param3 = input.next();
                EjecutarBBDD("INSERT INTO clientes(nome, apelidos, email) VALUES ('"+param1+"','"+param2+"','"+param3+"')");
                break;
            case "16":
                LineasConsultaBBDD("select * from clientes");
                break;            
            case "17":
                System.out.print("  Nome do cliente a eliminar: ");
                param1 = input.next();
                System.out.print("  Apelidos: ");
                param2 = input.next();
                EjecutarBBDD("DELETE FROM clientes WHERE nome='"+param1+"' and apelidos='"+param2+"'");             
                break;                
            case "E":
                XMLReader procesadorXML = null;
                try {
                    procesadorXML = XMLReaderFactory.createXMLReader();
                    ChannelsXML channelsXML = new ChannelsXML();
                    procesadorXML.setContentHandler(channelsXML);
                    InputSource arquivo = new InputSource("portada.xml");
                    procesadorXML.parse(arquivo);
                } catch (SAXException e) {
                    System.out.println("Ocurriu un erro ao ler o arquivo XML");
                } catch (IOException e) {
                    System.out.println("Ocurriu un erro ao ler o arquivo XML");
                } 
                break; 
            case "F":
                XMLReader procesadorXML_online = null;
                try {
                    procesadorXML_online = XMLReaderFactory.createXMLReader();
                    ChannelsXML channelsXML = new ChannelsXML();
                    procesadorXML_online.setContentHandler(channelsXML);
                    //InputSource arquivo = new InputSource("portada.xml");
                    procesadorXML_online.parse(new InputSource(new URL("http://ep00.epimg.net/rss/elpais/portada.xml").openStream()));
                } catch (SAXException e) {
                    System.out.println("Ocurriu un erro ao ler o arquivo XML");
                } catch (IOException e) {
                    System.out.println("Ocurriu un erro ao ler o arquivo XML");
                } 
                break; 

            case "S":
                System.out.println("Saíndo");
                System.exit(0);
                break;  
            default:
                System.out.println("Opción non recoñecida");
        }
        }
    } 
    
        private static void CrearBBDD(String filename){
        String databaseFile = "jdbc:sqlite:" + filename;
        
        try{
            Connection connection = DriverManager.getConnection(databaseFile);
            if(connection != null){
                DatabaseMetaData meta = connection.getMetaData();
                System.out.println("A base de datos foi creada");
                try{
                    Statement stmt = connection.createStatement();
                    stmt.execute("CREATE TABLE IF NOT EXISTS provincias (id_provincia integer PRIMARY KEY,provincia text NOT NULL);");
                    System.out.println("Táboa provincias creada con éxito");
                    stmt.execute("CREATE TABLE IF NOT EXISTS tendas (id_tenda integer PRIMARY KEY AUTOINCREMENT,nome text NOT NULL,id_provincia integer NOT NULL,cidade text NOT NULL);");
                    System.out.println("Táboa tendas creada con éxito");
                    stmt.execute("CREATE TABLE IF NOT EXISTS productos (id_producto integer PRIMARY KEY AUTOINCREMENT,nome text NOT NULL,descripcion text NOT NULL,precio decimal(4,2) NOT NULL);");
                    System.out.println("Táboa productos creada con éxito");
                    stmt.execute("CREATE TABLE IF NOT EXISTS stocks (id_stock integer PRIMARY KEY AUTOINCREMENT,id_tenda text NOT NULL,id_producto integer NOT NULL,unids integer NOT NULL);");
                    System.out.println("Táboa stocks creada con éxito");
                    stmt.execute("CREATE TABLE IF NOT EXISTS empregados (id_empregado integer PRIMARY KEY AUTOINCREMENT,nome text NOT NULL,apelidos text NOT NULL);");
                    System.out.println("Táboa empregados creada con éxito");
                    stmt.execute("CREATE TABLE IF NOT EXISTS horas_traballadas (id_horas_traballadas integer PRIMARY KEY AUTOINCREMENT,id_empregado integer,id_tenda integer NOT NULL,horas integer NOT NULL);");
                    System.out.println("Táboa horas_traballadas creada con éxito");
                    stmt.execute("CREATE TABLE IF NOT EXISTS clientes (id_cliente integer PRIMARY KEY AUTOINCREMENT,nome text NOT NULL,apelidos text NOT NULL,email text NOT NULL);");
                    System.out.println("Táboa clientes creada con éxito");
                }
                catch(SQLException e){
                    System.out.println(e.getMessage());
                }
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
    }
   
    private static Connection ConectaBBDD(String filename){
        Connection connection = null;
        try
        {
            //Creamos a conexión a base de datos
            connection = DriverManager.getConnection("jdbc:sqlite:" + filename);
            System.out.println("Conexión realizada con éxito");
            return connection;
             
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
            return null;
        }
    }
    
    
    private static void DesconectaBBDD(Connection connection){
        try{
            if(connection != null){
                connection.close();
                System.out.println("Desconexión realizada con éxito");
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    private static void EjecutarBBDD(String sql) {
        try{
            Connection con = ConectaBBDD(db);
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.executeUpdate();
            System.out.println("Execución correcta");
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }    
    }
    private static void LineasConsultaBBDD(String sql){
        try
        {
            Connection con = ConectaBBDD(db);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            for (int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++) {
                System.out.print("  " + rs.getMetaData().getColumnName(i));
            }
            System.out.println();
            while(rs.next()){
                //System.out.println("ID = " + rs.getString("id_tendas") + ", Nome = " + rs.getString("nome")+" Provincia = " + rs.getString("provincia") + " cidade = " + rs.getString("cidade"));
                for (int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++) {
                    System.out.print("  " + rs.getObject(i));
                    
                }
                System.out.println();
            }
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    
    public static void cargarJSONprovincias() {
        File arquivo = new File("provincias.json");
        Provincias provincias = new Provincias();
        try {
            FileReader fluxoDatos;
            fluxoDatos = new FileReader(arquivo);        
            BufferedReader buferEntrada = new BufferedReader(fluxoDatos);
            
            StringBuilder jsonBuilder = new StringBuilder();
            String linea;
            while ((linea=buferEntrada.readLine()) != null) {
                jsonBuilder.append(linea).append("\n");
            }
            
            buferEntrada.close();
            String json = jsonBuilder.toString();
            Gson gson = new Gson();
            provincias = gson.fromJson(json, Provincias.class);
            try{
                Connection con = ConectaBBDD(db);
                System.out.println("Limpiando provincias");
                PreparedStatement pstmt_delete = con.prepareStatement("DELETE FROM provincias");
                pstmt_delete.executeUpdate();                
                System.out.println("Insertando provincias");
                for (Provincia provincia : provincias.provincias) {
                    //System.out.println("INSERT INTO provincias(id_provincia,provincia) VALUES ("+provincia.getID()+",'"+provincia.getNome()+"')");
                    PreparedStatement pstmt = con.prepareStatement("INSERT INTO provincias(id_provincia,provincia) VALUES ("+provincia.getID()+",'"+provincia.getNome()+"')");
                    pstmt.executeUpdate();
                }
                DesconectaBBDD(con);
            }
            catch(SQLException e){
            System.out.println(e.getMessage());
            }    
            
        } catch (FileNotFoundException e) {
            System.out.println("Non se encontra o arquivo");
        } catch (IOException e) {
            System.out.println("Erro de entrada saída");        
        }
    }
}
