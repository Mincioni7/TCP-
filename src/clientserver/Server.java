/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clientserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author M1K
 */
class Server {
    private ServerSocket server = null;
    private Socket client = null;
    private String stringaRicevuta = null;
    private String stringaModificata = null;
    private BufferedReader inDalClient;
    private DataOutputStream outVersoClient;
    
    /**
     * Quando questo metodo viene invocato il server crea un ServerSocket (connection socket) con una porta, 
     * dopodichè si mette in ascolto sul ServerSocket creato (accept()), quando viene stabilita una connessione viene creato il socket (data socket),
     * alla fine viene chiusa la connessione deistanziando il ServerSocket per permettere la connessione con altri client.
     *      * vengono inizializzate le variabili che permettono lo scambio di datri con il client.
     * @exception IOException se si verificia un errore di I/O quando si apre il socket.
     * @return client
     */
    public Socket attendi(){
        try{
            System.out.println("iniziato server");            
            server = new ServerSocket(9999);
            client = server.accept();
            server.close();
            
            outVersoClient= new DataOutputStream(client.getOutputStream());
            inDalClient = new BufferedReader(new InputStreamReader (client.getInputStream()));
        
        } catch(IOException e) {System.err.println(e.getMessage());}
        System.out.println("finito");
        return client;
    }
    
    /**
     * Questo metodo serve per scambiarsi dati con il client,
     * Riga 64 - viene inviato la stringa modificata al client
     * Riga 67 - viene chiusa la trasmissione
     * @exception IOException che può verificarsi quando si inserisce la frase.
     */
    public void comunica(){ 
        try {
            System.out.println("Scrivi una frase e la miglioro!!!");
            stringaRicevuta = inDalClient.readLine();
            System.out.println("Stringa ricevuta dal client: "+stringaRicevuta);
            
            stringaModificata = stringaRicevuta + "<3";
            System.out.println("Invio la string modificata al client...");
            outVersoClient.writeBytes(stringaModificata+'\n');
            
            System.out.println("Addio.");
            client.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void main(String args[]){
        Server servente = new Server();
        while(true){
            servente.attendi();
            servente.comunica();
        }
    }
    
}
