package aula02;

public class ArvoreBinaria {

    static class No{
        String nome;
        No esquerda, direita;

        public No(String nome) {
            this.nome = nome;
            this.esquerda = this.direita = null;
        }
    }

    No raiz;


    void inserir(String nome) {
        raiz = inserirRecursivo(raiz, nome);
    }

    protected No inserirRecursivo(No atual, String nome) {
        if (atual == null) {
            return new No(nome);
        }

        if (nome.compareToIgnoreCase(atual.nome) < 0) {
            atual.esquerda = inserirRecursivo(atual.esquerda, nome);
        } else if (nome.compareToIgnoreCase(atual.nome) > 0) {
            atual.direita = inserirRecursivo(atual.direita, nome);
        }

        return atual;
    }

    public static void main(String[] args) {
        ArvoreBinaria arvore = new ArvoreBinaria();

        arvore.inserir("Lucas");
        arvore.inserir("Amanda");
        System.out.println("Conteúdo da raiz: " + arvore.raiz.nome);
        System.out.println("No esquerda da raiz: " + arvore.raiz.esquerda.nome);


    }
}