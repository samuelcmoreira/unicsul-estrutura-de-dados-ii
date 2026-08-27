package exercicio02;

public class Exercicio2 {

    static class No {
        Agenda contato;
        No esquerda, direita;

        public No(Agenda contato) {
            this.contato = contato;
            this.esquerda = this.direita = null;
        }
    }

    No raiz;

    void inserir(Agenda contato) {
        raiz = inserirRecursivo(raiz, contato);
    }

    private No inserirRecursivo(No atual, Agenda contato) {
        if (atual == null) {
            return new No(contato);
        }

        if (contato.getNome().compareToIgnoreCase(atual.contato.getNome()) < 0) {
            atual.esquerda = inserirRecursivo(atual.esquerda, contato);
        } else if (contato.getNome().compareToIgnoreCase(atual.contato.getNome()) > 0) {
            atual.direita = inserirRecursivo(atual.direita, contato);
        }

        return atual;
    }

    public Agenda buscarPorNome(String nome) {
        return buscarRecursivo(raiz, nome);
    }

    private Agenda buscarRecursivo(No atual, String nomeBusca) {
        if (atual == null) {
            return null;
        }

        int comparacao = nomeBusca.compareToIgnoreCase(atual.contato.getNome());

        if (comparacao == 0) {
            return atual.contato;
        } else if (comparacao < 0) {
            return buscarRecursivo(atual.esquerda, nomeBusca);
        } else {
            return buscarRecursivo(atual.direita, nomeBusca);
        }
    }

    void emOrdem() {
        emOrdemRecursivo(raiz);
        System.out.println();
    }

    private void emOrdemRecursivo(No atual) {
        if (atual != null) {
            emOrdemRecursivo(atual.esquerda);
            System.out.println(atual.contato);
            emOrdemRecursivo(atual.direita);
        }
    }

    public static void main(String[] args) {

        Exercicio2 arvoreAgenda = new Exercicio2();

        Agenda[] contatos = {
            new Agenda("Lucas", "Rua A, 123", "1111-1111"),
            new Agenda("Amanda", "Rua B, 456", "2222-2222"),
            new Agenda("Bruno", "Rua C, 789", "3333-3333"),
            new Agenda("Carla", "Rua D, 101", "4444-4444"),
            new Agenda("Eduardo", "Rua E, 202", "5555-5555"),
            new Agenda("Fernanda", "Rua F, 303", "6666-6666"),
            new Agenda("Gustavo", "Rua G, 404", "7777-7777"),
            new Agenda("Helena", "Rua H, 505", "8888-8888"),
            new Agenda("Igor", "Rua I, 606", "9999-9999"),
            new Agenda("Beatriz", "Rua J, 707", "0000-0000")

        };

        for (Agenda contato : contatos) {
            arvoreAgenda.inserir(contato);
        }

        System.out.println("=== Contatos na Agenda ===");

        arvoreAgenda.emOrdem();

        // Buscar pessoa
        String nomeBuscaAgenda = "Helena";

        Object resultadoAgenda = arvoreAgenda.buscarPorNome(nomeBuscaAgenda);

        System.out.println("\nBusca na agenda: " + resultadoAgenda);

    }
}
