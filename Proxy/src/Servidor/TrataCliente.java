package Servidor;

import Base.Anotacao;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author eduardo
 */
public class TrataCliente extends Thread {
    private Socket clientSocket;
    private Cache cache;
    private PostgresDatabase database;

    public TrataCliente(Socket clientSocket, Cache cache, PostgresDatabase database) {
        this.clientSocket = clientSocket;
        this.cache = cache;
        this.database = database;
    }

    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {

            out.writeUTF("Informe o número da anotacao que gostaria de visualizar:");
            out.flush();

            int id = in.readInt();
            Anotacao anotacao = null;

            if (cache.contains(id)) {
                anotacao = cache.get(id);
                System.out.println("Anotacao recuperada via cache");
            } else {
                anotacao = database.getAnotacao(id);
                if (anotacao != null) {
                    System.out.println("Anotacao recuperada via banco");
                    cache.put(anotacao);
                }
            }

            if (anotacao != null) {
                out.writeObject(anotacao);
            } else {
                out.writeObject(new Anotacao(id, "Nao foi encontrada informacao no sistema."));
            }
            out.flush();
        } catch (IOException e) {
            System.out.println("Erro na comunicacao com o cliente.");
        }
    }
}