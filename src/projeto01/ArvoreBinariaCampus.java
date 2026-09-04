package projeto01;

public class ArvoreBinariaCampus {

    private static class No {
        String nomeAluno;
        No esquerda;
        No direita;

        No(String nomeAluno) {
            this.nomeAluno = nomeAluno;
        }
    }

    private No raiz;

    public boolean inserir(String nomeAluno) {
        if (nomeAluno == null || nomeAluno.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome inválido.");
        }

        if (raiz == null) {
            raiz = new No(nomeAluno);
            return true;
        }

        return inserirRecursivo(raiz, nomeAluno);
    }

    private boolean inserirRecursivo(No atual, String nomeAluno) {
        if (nomeAluno.compareToIgnoreCase(atual.nomeAluno) < 0) {
            if (atual.esquerda == null) {
                atual.esquerda = new No(nomeAluno);
                return true;
            } else {
                return inserirRecursivo(atual.esquerda, nomeAluno);
            }
        } else if (nomeAluno.compareToIgnoreCase(atual.nomeAluno) > 0) {
            if (atual.direita == null) {
                atual.direita = new No(nomeAluno);
                return true;
            } else {
                return inserirRecursivo(atual.direita, nomeAluno);
            }
        } else {
            return false;
        }
    }

    public boolean buscar(String nomeAluno) {
        if (nomeAluno == null || nomeAluno.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome inválido.");
        }

        return buscarRecursivo(raiz, nomeAluno);
    }

    private boolean buscarRecursivo(No atual, String nomeAluno) {
        if (atual == null) {
            return false;
        }

        if (nomeAluno.compareToIgnoreCase(atual.nomeAluno) < 0) {
            return buscarRecursivo(atual.esquerda, nomeAluno);
        } else if (nomeAluno.compareToIgnoreCase(atual.nomeAluno) > 0) {
            return buscarRecursivo(atual.direita, nomeAluno);
        } else {
            return true;
        }
    }

    public String listarEmOrdem() {
        StringBuilder sb = new StringBuilder();
        listarEmOrdemRecursivo(raiz, sb);

        if (sb.length() == 0) {
            return "Nenhum aluno cadastrado neste campus.";
        }
        return sb.toString();
    }

    private void listarEmOrdemRecursivo(No atual, StringBuilder sb) {
        if (atual != null) {
            listarEmOrdemRecursivo(atual.esquerda, sb);
            sb.append(atual.nomeAluno).append("\n");
            listarEmOrdemRecursivo(atual.direita, sb);
        }
    }
}
