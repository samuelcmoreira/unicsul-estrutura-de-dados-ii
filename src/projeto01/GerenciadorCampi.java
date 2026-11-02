package projeto01;

import java.util.HashMap;
import java.util.Map;

public class GerenciadorCampi {

    private final Map<String, ArvoreBinariaCampus> campi;

    public GerenciadorCampi() {
        campi = new HashMap<>();
        campi.put("Anália Franco", new ArvoreBinariaCampus());
        campi.put("Guarulhos", new ArvoreBinariaCampus());
        campi.put("Liberdade", new ArvoreBinariaCampus());
        campi.put("Paulista", new ArvoreBinariaCampus());
        campi.put("São Miguel", new ArvoreBinariaCampus());
        campi.put("Santo Amaro", new ArvoreBinariaCampus());
        campi.put("Villa Lobos", new ArvoreBinariaCampus());
    }

    public boolean cadastrarAlunoGlobal(String nomeCampus, String nomeAluno) {
        ArvoreBinariaCampus arvore = campi.get(nomeCampus);
        if (arvore != null && nomeAluno != null && !nomeAluno.trim().isEmpty()) {
            for (ArvoreBinariaCampus arv : campi.values()) {
                if (arv.buscar(nomeAluno)) {
                    return false;
                }
            }
            return arvore.inserir(nomeAluno);
        }
        return false;
    }

    public String localizarAlunoGlobal(String nomeAluno) {
        if (nomeAluno == null || nomeAluno.trim().isEmpty()) {
            return null;
        }
        for (Map.Entry<String, ArvoreBinariaCampus> par : campi.entrySet()) {
            String nomeCampus = par.getKey();
            ArvoreBinariaCampus arvore = par.getValue();
            if (arvore.buscar(nomeAluno)) {
                return nomeCampus;
            }
        }
        return null;
    }

    public String listarAlunosDoCampus(String nomeCampus) {
        ArvoreBinariaCampus arvore = campi.get(nomeCampus);
        if (arvore != null) {
            return arvore.listarEmOrdem();
        }
        return null;
    }
}
