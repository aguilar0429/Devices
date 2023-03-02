/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.kayak;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Eduardo Aguilar
 */
public class Kayak {

    private static List<Socket> clients = new ArrayList<>();
    static String url = "jdbc:sqlserver://EDUARDO_AGUILAR\\SQLEXPRESS;databaseName=SpiritAirlines;encrypt=true;trustServerCertificate=true";
    static public String username = "edu";
    static public String password = "123";

    public static void connectSQL() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to SQL Server.");
            connection.close();
        } catch (SQLException ex) {
            System.out.println("Fallo en Finalizar");
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        connectSQL();
        ServerSocket serverSocket = new ServerSocket(2222);
        System.out.println("Server is listening on port 2222");

        while (true) {
            String query = "";
            Socket socket = serverSocket.accept();
            System.out.println("New client connected");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String question = in.readLine();
            System.out.println(question);
            System.out.println("Client's question: " + question);

            try {
                System.out.println("hi2");
                Connection connection = DriverManager.getConnection(url, username, password);
                System.out.println("Connected to SQL Server.");
                Statement statement = connection.createStatement();
                ResultSet rs;

                rs = statement.executeQuery(question);
                
                int y = 10;
                String airline = "";
                
                while (rs.next()) {
                    String Vuelo1 = rs.getString("Vuelo1");
                    System.out.println(Vuelo1);
                    String Vuelo2 = rs.getString("Vuelo2");
                    System.out.println(Vuelo2);
                    String Origen = rs.getString("Origen");
                    System.out.println(Origen);
                    String Escala = rs.getString("Escala");
                    System.out.println(Escala);
                    String Destino = rs.getString("Destino");
                    System.out.println(Destino);
                    String Salida1 = rs.getString("Salida1");
                    String Salida2 = rs.getString("Salida2");
                    System.out.println(Salida1);
                    String LlegadaEscala = rs.getString("LlegadaEscala");
                    System.out.println(LlegadaEscala);
                    String LlegadaDestino = rs.getString("LlegadaDestino");
                    query+= Vuelo1 + " " + Vuelo2 + " " + Origen + " " + Escala + " " + Destino + " " + Salida1 + " " + LlegadaEscala + " " + Salida2 + " " + LlegadaDestino;
                    
                }

                connection.close();

            } catch (SQLException ex) {
                //Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error al Updatear Tabla Proyectos");
            }

            String answer = ""; // replace with your own logic to generate an answer

            out.println(query);
            

            socket.close();
            System.out.println("Connection with client closed");
        }
    }

    public static ArrayList<String> RSeats = new ArrayList();
    public static ArrayList<String> RUsers = new ArrayList();
}
