/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clientserver;

/**
 *
 * @author M1K
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    private String nomeServer = "localhost";
    private int portaServer = 9999;
    private Socket mySocket;
    private BufferedReader tastiera;
    private String stringaUtente = null;
    private String stringaRicevutaDalServer;
    private DataOutputStream outVersoServer;
    private BufferedReader inDalServer;

    /*public Client(String nS, int pS) {
        nS = nomeServer;
        pS = portaServer;
    }*/
    
    
    /**
     * Quando questo metodo viene invocato il client cercherà di connettersi al server nomeServer alla portaServer creando un socket (data socket),
     * vengono inizializzate le variabili che permettono lo scambio di datri con il server.
     * @exception UnknownHostEsception si verifica se non riconosce il nomeServer quando cercando di connettersi.
     * @exception IOException se si verificia un errore di I/O quando si apre il socket.
     * @return mySocket
     */
    public Socket connetti() {
        try {
            tastiera = new BufferedReader(new InputStreamReader(System.in));
            
            mySocket = new Socket(nomeServer, portaServer); //data socket non connection socket
            System.out.println("Connessione effettuata!");
            
            outVersoServer = new DataOutputStream(mySocket.getOutputStream());
            inDalServer = new BufferedReader(new InputStreamReader (mySocket.getInputStream()));
        } catch(ConnectException e){System.err.println("Server non disponibile");
        } catch (UnknownHostException e) { //se non riconosce nomeServer
            System.err.println(e);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la connessione!");
            System.exit(1);
        }
        return mySocket;
    }
    
    
    /**
     * Questo metodo serve per scambiarsi dati con il server,
     * Riga 74 - viene inviato la stringa al server, viene spostato il messaggio(stringaUtente) nella parte dell'output stream
     * Riga 77 - viene immagazzinata la stringa ricevuta dal server, nell'input stream
     * Riga 82 - viene chiusa la trasmissione
     * @exception IOException che può verificarsi quando si inserisce la frase.
     * usiamo bufferedreader perchè mè più efficiente perchè invece che controllare ogni carattere controlliamo un unico buffer (riga, line, readLine())
     */
    public void comunica(){ 
        
        try {
            System.out.println("Inserisci la stringa da trasmettere al server:\n");
            stringaUtente = tastiera.readLine();
            
            System.out.println("Invio... ");
            outVersoServer.writeBytes(stringaUtente+'\n');
            
            stringaRicevutaDalServer = inDalServer.readLine();
            System.out.println("Risposta ricevuta  \n"+stringaRicevutaDalServer);
            
            System.out.println("Chiusura connessione!");
            mySocket.close();
            
        } catch (IOException ex) {
            Logger.getLogger(ex.getMessage());
        }
    }
            
    
    
    public static void main(String args[]){
        Client cliente = new Client();
        cliente.connetti();
        if(cliente.mySocket != null){
            cliente.comunica();
        }
    }
}