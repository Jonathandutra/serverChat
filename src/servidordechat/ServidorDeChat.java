/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidordechat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Ronaldo
 */
public class ServidorDeChat extends Thread {
      FrmServidor frm;      
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        try {
            
            FrmServidor frm = new FrmServidor();
            frm.setVisible(true);
            
            frm.escrever("Inicializando Servidor...\n");
            ServerSocket servidor = new ServerSocket(12345);    
            
            while (true) {
                frm.escrever("Esperando alguma conexão...\n");
                Socket cliente = servidor.accept();            
                
                // cria uma nova thread para tratar essa conexão
                ServidorDeChat t = new ServidorDeChat(cliente);
                t.frm=frm;
                t.start();
                frm.escrever("Criou a thread...\n");
                frm.escrever("Cliente conectado:" + cliente.getInetAddress().getHostAddress()+"\n");
            }
            
        } catch (IOException e) {
            e.getMessage();
        } 
    }
    
    private Socket cliente;
    
    public ServidorDeChat(Socket cliente){
        this.cliente=cliente;
    }
        
    public void run(){
        
        try {
            
            String mensagem = "";
            
            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            PrintStream saida = new PrintStream(cliente.getOutputStream());
            
            while (true) {
                mensagem = entrada.readLine();   
                
                //..
                frm.escrever("Cliente: " + mensagem + "\n");
                                
                if ("FIM".equals(mensagem)) {
                    frm.escrever("Encerrando servidor...");
                    break;                    
                }
            }
                        
            entrada.close();
            saida.close();
            cliente.close();
            //servidor.close();
            
        } catch (IOException e) {
            e.getMessage();
        }
            
    }
    
    
}
