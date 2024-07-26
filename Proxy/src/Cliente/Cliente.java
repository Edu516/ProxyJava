package Cliente;

import java.net.Socket;
import Base.Anotacao;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 *
 * @author Usuario
 */

public class Cliente {
    public static void main(String[] args) {
        String ip = "localhost";
//        String ip = "";
        
        try (Socket socket = new Socket(ip, 5000);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            System.out.println(in.readUTF());
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            int id = Integer.parseInt(reader.readLine());

            out.writeInt(id);
            out.flush();

            Anotacao anotacao = (Anotacao) in.readObject();
            System.out.println("Recebido do servidor: \n" + anotacao);

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro na comunicação com o servidor.");
        }
    }
}

