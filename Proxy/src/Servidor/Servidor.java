package Servidor;

import Base.Anotacao;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Usuario
 */
public class Servidor {
    private static Cache cache;
    private static PostgresDatabase database;

    public static void main(String[] args) {
        // Cache dura 60 segundos e pode conter até 100 anotações
        long cacheDuration = 60000;
        int limiteCache = 100;

        cache = new Cache(cacheDuration, limiteCache);
        database = new PostgresDatabase("jdbc:postgresql://localhost:5432/facul", "postgres", "postgres");

        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            // Obter o IP do servidor
            InetAddress ipAddress = InetAddress.getLocalHost();
            int porta = serverSocket.getLocalPort();
            System.out.println("Servidor iniciado na porta " + porta + ".");
            System.out.println("IP do servidor: " + ipAddress.getHostAddress());

            while (true) {
                System.out.println("Aguardando conexao do cliente...");
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Cliente conectado.");
                    new TrataCliente(clientSocket, cache, database).start();
                } catch (IOException e) {
                    System.out.println("Erro ao aceitar conexão do cliente.");
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao iniciar o servidor.");
        }
    }
}
