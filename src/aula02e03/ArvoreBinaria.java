package aula02e03;

public class ArvoreBinaria {

    static class No {

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

    void preOrdem() {
        System.out.println("Nomes em pré-ordem:");
        preOrdemRecursivo(raiz);
        System.out.println();
    }

    void preOrdemRecursivo(No atual) {
        if (atual != null) {
            System.out.print(atual.nome + " ");
            preOrdemRecursivo(atual.esquerda);
            preOrdemRecursivo(atual.direita);
        }
    }

    void emOrdem() {
        System.out.println("Nomes em ordem alfabética:");
        emOrdemRecursivo(raiz);
        System.out.println();
    }

    void emOrdemRecursivo(No atual) {
        if (atual != null) {
            emOrdemRecursivo(atual.esquerda);
            System.out.print(atual.nome + " ");
            emOrdemRecursivo(atual.direita);
        }
    }

    void exibirArvore() {
        System.out.println("\nÁrvore binária:");

        if (raiz == null) {
            System.out.println("(Arvore Vazia)");
            return;
        }

        System.out.println(raiz.nome);

        // Exibir subárvore esquerda
        if (raiz.esquerda != null) {
            exibirArvoreRecursivo(
                    raiz.esquerda,
                    "",
                    raiz.direita == null,
                    "E: "
            );
        }

        // Exibir subárvore direita
        if (raiz.direita != null) {
            exibirArvoreRecursivo(
                    raiz.direita,
                    "",
                    true,
                    "D: "
            );
        }
    }

    void exibirArvoreRecursivo(
            No atual,
            String prefixo,
            boolean ultimo,
            String lado) {

        if (atual == null) {
            return;
        }

        // Exibe o nó atual
        System.out.println(
                prefixo
                + (ultimo ? "└── " : "├── ")
                + lado
                + atual.nome
        );

        String novoPrefixo;

        if (ultimo) {
            novoPrefixo = prefixo + "    ";
        } else {
            novoPrefixo = prefixo + "│   ";
        }

        // Possui filho esquerdo
        if (atual.esquerda != null) {
            exibirArvoreRecursivo(
                    atual.esquerda,
                    novoPrefixo,
                    atual.direita == null,
                    "E: "
            );
        }

        // Possui filho direito
        if (atual.direita != null) {
            exibirArvoreRecursivo(
                    atual.direita,
                    novoPrefixo,
                    true,
                    "D: "
            );
        }
    }

    void excluir(String nome) {
        raiz = excluirRecursivo(raiz, nome);
    }

    No excluirRecursivo(No atual, String nome) {
        // Nome não encontrado
        if (atual == null) {
            return null;
        }

        // Procurar na subárvore esquerda
        if (nome.compareTo(atual.nome) < 0) {
            atual.esquerda = excluirRecursivo(atual.esquerda, nome);
        } // Procurar na subárvore direita
        else if (nome.compareTo(atual.nome) > 0) {
            atual.direita = excluirRecursivo(atual.direita, nome);
        } // Encontrou o nó
        else {
            // CASO 1: Nó folha
            if (atual.esquerda == null && atual.direita == null) {
                return null;
            }

            // CASO 2: Nó com um filho
            if (atual.esquerda == null) {
                return atual.direita;
            } else if (atual.direita == null) {
                return atual.esquerda;
            }

            // CASO 3: Nó com dois filhos
            // Maior valor da subárvore esquerda
            No predecessor = maiorNo(atual.esquerda);

            // Substitui o valor
            atual.nome = predecessor.nome;

            // Remove o predecessor original
            atual.esquerda = 
                    excluirRecursivo(
                            atual.esquerda,
                            predecessor.nome
                    );
        }
        
        return atual;
    }

    // Retorna o maior nó da subárvore
    No maiorNo(No atual) {
        while (atual.direita != null) {
            atual = atual.direita;
        }
        return atual;
    }

    public static void main(String[] args) {
        ArvoreBinaria arvore = new ArvoreBinaria();

        // arvore.inserir("Lucas");
        // arvore.inserir("Amanda");
        // System.out.println("Conteúdo da raiz: " + arvore.raiz.nome);
        // System.out.println("No esquerda da raiz: " + arvore.raiz.esquerda.nome);
        String[] nomes = {
            "Lucas", "Amanda", "Bruno", "Carla", "Eduardo",
            "Fernanda", "Gustavo", "Helena", "Igor", "Beatriz", "Ana"
        };

        for (String nome : nomes) {
            arvore.inserir(nome);
        }

        arvore.preOrdem();

        arvore.emOrdem();

        arvore.exibirArvore();

        arvore.excluir("Amanda");
        arvore.exibirArvore();
    }
}
