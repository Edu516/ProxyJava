package Base;
import java.io.Serializable;
/**
 *
 * @author Usuario
 */
public class Anotacao implements Serializable{

    private int id;
    private String conteudo;

    public Anotacao(int id, String conteudo) {
        this.id = id;
        this.conteudo = conteudo;
    }

    public int getId() {
        return id;
    }

    public String getConteudo() {
        return conteudo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Anotacao{");
        sb.append(" id = \n").append(id);
        sb.append(" conteudo = \n").append(conteudo);
        sb.append('}');
        return sb.toString();
    }
}
