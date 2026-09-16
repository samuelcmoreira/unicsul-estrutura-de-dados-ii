package exercicio04;

import exercicio02.Agenda;               // importa a implementação LinkedList (usada como fila)
import java.util.LinkedList;                    // importa a interface Queue (contrato para filas)
import java.util.Queue;

class AVLTree3 {                            // início da classe que implementa a árvore AVL

    // Nó da árvore
    static class Node {                     // classe interna que representa um nó da árvore

        Agenda contato;                          // campo que guarda o valor inteiro armazenado no nó
        Node esquerda, direita;             // referências para os filhos esquerdo e direito
        int altura;                         // altura do nó (necessária para manter o balanceamento)

        Node(Agenda c) {                       // construtor do nó
            contato = c;                      // inicializa o campo contato com o parâmetro c
            altura = 1;                     // nó recém-criado tem altura 1 (por convenção)
        }
    }

    Node raiz;                              // referência para o nó raiz da árvore (inicia como null)

    // Função para obter altura de um nó
    int altura(Node n) {                    // método auxiliar que retorna a altura de um nó
        return (n == null) ? 0 : n.altura;  // se o nó for nulo retorna 0, caso contrário retorna a altura armazenada
    }

    // Fator de balanceamento
    int fatorBalanceamento(Node n) {        // calcula o fator de balanceamento de um nó
        return (n == null) ? 0 : altura(n.esquerda) - altura(n.direita);
        // fator = altura(subárvore esquerda) - altura(subárvore direita)
        // fator > 0 => lado esquerdo é mais alto; fator < 0 => lado direito é mais alto
    }

    // Rotação simples para a direita
    Node rotacaoDireita(Node y) {           // rotação à direita com y como raiz do subárvore desbalanceada
        Node x = y.esquerda;                // x aponta para o filho esquerdo de y (será nova raiz do subárvore)
        Node T2 = x.direita;                // T2 guarda a subárvore que vai mudar de posição (direita de x)

        // Rotação
        x.direita = y;                      // x passa a ter y como filho direito
        y.esquerda = T2;                    // y recebe T2 como filho esquerdo (restauração de subárvore)

        // Atualizar alturas
        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;
        // nova altura de y = 1 + maior altura entre seus filhos
        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;
        // nova altura de x = 1 + maior altura entre seus filhos

        return x;                           // retorna x como nova raiz do subárvore após rotação
    }

    // Rotação simples para a esquerda
    Node rotacaoEsquerda(Node x) {          // rotação à esquerda com x como raiz do subárvore desbalanceada
        Node y = x.direita;                 // y aponta para o filho direito de x (será nova raiz do subárvore)
        Node T2 = y.esquerda;               // T2 guarda a subárvore que vai mudar de posição (esquerda de y)

        // Rotação
        y.esquerda = x;                     // y passa a ter x como filho esquerdo
        x.direita = T2;                     // x recebe T2 como filho direito

        // Atualizar alturas
        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;
        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;

        return y;                           // retorna y como nova raiz do subárvore após rotação
    }

    // Inserir valor e balancear
    Node inserir(Node node, Agenda contato) {    // método recursivo que insere um valor em um nó/subárvore
        // Inserção normal de BST
        if (node == null) {
            return new Node(contato);
        }
        // se node é null, alcançamos o local de inserção: cria e retorna novo nó

        if (contato.getNome().compareTo(node.contato.getNome()) < 0) {
            node.esquerda = inserir(node.esquerda, contato); 
        }// se valor é menor, insere recursivamente na subárvore esquerda
        else if (contato.getNome().compareTo(node.contato.getNome()) > 0) {
            node.direita = inserir(node.direita, contato); 
        }// se valor é maior, insere recursivamente na subárvore direita
        else {
            return node; // valores iguais não permitidos — retorna o nó sem alterar
        }
        // Atualiza altura
        node.altura = 1 + Math.max(altura(node.esquerda), altura(node.direita));
        // após inserir nos filhos, atualiza a altura do nó atual (1 + maior altura dos filhos)

        // Verifica balanceamento
        int balance = fatorBalanceamento(node);
        // calcula o fator de balanceamento para decidir se precisa rotacionar

        // 4 casos de desbalanceamento
        if (balance > 1 && contato.getNome().compareTo(node.contato.getNome()) < 0) {
            return rotacaoDireita(node);
        }
        // Caso Left-Left (esquerda pesada e o valor foi inserido na subárvore esquerda-esquerda)
        // solução: rotação direita simples

        if (balance < -1 && contato.getNome().compareTo(node.contato.getNome()) > 0) {
            return rotacaoEsquerda(node);
        }
        // Caso Right-Right (direita pesada e valor inserido à direita-direita)
        // solução: rotação esquerda simples

        if (balance > 1 && contato.getNome().compareTo(node.contato.getNome()) > 0) {
            node.esquerda = rotacaoEsquerda(node.esquerda);
            return rotacaoDireita(node);
        }
        // Caso Left-Right (esquerda pesada, mas valor inserido na subárvore direita do filho esquerdo)
        // solução: dupla rotação (esquerda no filho esquerdo, depois direita no nó)

        if (balance < -1 && contato.getNome().compareTo(node.contato.getNome()) < 0) {
            node.direita = rotacaoDireita(node.direita);
            return rotacaoEsquerda(node);
        }
        // Caso Right-Left (direita pesada, valor inserido na subárvore esquerda do filho direito)
        // solução: dupla rotação (direita no filho direito, depois esquerda no nó)

        return node; // se nenhum caso de desbalanceamento requer rotação, retorna o nó atualizado
    }

    // Percurso em ordem (imprime ordenado)
    void emOrdem() {                // visita recursiva: esquerda -> raiz -> direita
        emOrdemRecursivo(raiz);
        System.out.println();
    }

    private void emOrdemRecursivo(Node atual) {
        if (atual != null) {
            emOrdemRecursivo(atual.esquerda);
            System.out.println(atual.contato);
            emOrdemRecursivo(atual.direita);
        }
    }

    // 🔹 Percurso em nível (BFS)
    void emNivel() {                         // imprime a árvore por níveis (nível por nível, da esquerda para a direita)
        if (raiz == null) {
            return;            // se árvore vazia, não faz nada
        }
        Queue<Node> fila = new LinkedList<>(); // cria uma fila para controlar a ordem de visita
        fila.add(raiz);                      // começa colocando a raiz na fila

        while (!fila.isEmpty()) {            // enquanto houver nós na fila
            Node atual = fila.poll();        // remove o próximo nó da fila (FIFO)
            System.out.println(atual.contato); // imprime o valor do nó atual

            if (atual.esquerda != null) {
                fila.add(atual.esquerda);   // adiciona filho esquerdo na fila (se existir)
            }
            if (atual.direita != null) {
                fila.add(atual.direita);    // adiciona filho direito na fila (se existir)
            }
        }
        System.out.println();                // quebra de linha ao final da impressão
    }

    // Método público para inserir
    public void inserir(Agenda contato) {
        System.out.println("Inserção contato: " + contato.getNome());
        raiz = inserir(raiz, contato);
        // expõe um método simples para o usuário: atualiza a raiz com o resultado da inserção recursiva
        emNivel();
        System.out.println();                 // quebra de linha para leitura        
    }

    // Método público para mostrar a árvore
    public void mostrar() {
        emOrdem();                        // faz o percurso em ordem a partir da raiz
        System.out.println();
        emNivel();
        System.out.println();                 // quebra de linha para leitura
    }

    public Agenda buscarPorNome(String nome) {
        return buscarRecursivo(raiz, nome);
    }

    private Agenda buscarRecursivo(Node atual, String nomeBusca) {
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
}

// Teste
public class Exercicio4 {               // classe com método main para testar a AVLTree2

    public static void main(String[] args) {
        AVLTree3 arvoreAgenda = new AVLTree3();     // cria uma nova árvore AVL vazia

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
