package Servidor;

import Base.Anotacao;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class Cache {
    private List<Anotacao> cache;
    private long limite;
    private int limiteCache;

    public Cache(long limite, int limiteCache) {
        this.cache = new ArrayList<>();
        this.limite = System.currentTimeMillis() + limite;
        this.limiteCache = limiteCache;
    }

    public Anotacao get(int id) {
        if (System.currentTimeMillis() > limite) {
            cache.clear();
        }
        for (Anotacao anotacao : cache) {
            if (anotacao.getId() == id) {
                return anotacao;
            }
        }
        return null;
    }

    public void put(Anotacao anotacao) {
        if (cache.size() >= limiteCache) {
            cache.remove(0); // Remove a anotação mais antiga
        }
        cache.add(anotacao);
    }

    public boolean contains(int id) {
        if (System.currentTimeMillis() > limite) {
            cache.clear();
            System.out.println("Limpando o Cache");
        }
        for (Anotacao anotacao : cache) {
            if (anotacao.getId() == id) {
                return true;
            }
        }
        return false;
    }
}
