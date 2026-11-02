package exercicio03aula;

import java.util.LinkedList;               // importa a implementação LinkedList (usada como fila)
import java.util.Queue;                    // importa a interface Queue (contrato para filas)

class AVLTree3 {                            // início da classe que implementa a árvore AVL

    // Nó da árvore
    static class Node {                     // classe interna que representa um nó da árvore
        String valor;                          // campo que guarda o valor inteiro armazenado no nó
        Node esquerda, direita;             // referências para os filhos esquerdo e direito
        int altura;                         // altura do nó (necessária para manter o balanceamento)

        Node(String v) {                       // construtor do nó
            valor = v;                      // inicializa o campo valor com o parâmetro v
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
    Node inserir(Node node, String valor) {    // método recursivo que insere um valor em um nó/subárvore
        // Inserção normal de BST
        if (node == null) return new Node(valor);
        // se node é null, alcançamos o local de inserção: cria e retorna novo nó

        if (valor.compareToIgnoreCase(node.valor) < 0)
            node.esquerda = inserir(node.esquerda, valor);
        // se valor é menor, insere recursivamente na subárvore esquerda
        else if (valor.compareToIgnoreCase(node.valor) > 0)
            node.direita = inserir(node.direita, valor);
        // se valor é maior, insere recursivamente na subárvore direita
        else
            return node; // valores iguais não permitidos — retorna o nó sem alterar

        // Atualiza altura
        node.altura = 1 + Math.max(altura(node.esquerda), altura(node.direita));
        // após inserir nos filhos, atualiza a altura do nó atual (1 + maior altura dos filhos)

        // Verifica balanceamento
        int balance = fatorBalanceamento(node);
        // calcula o fator de balanceamento para decidir se precisa rotacionar

        // 4 casos de desbalanceamento

        if (balance > 1 && valor.compareToIgnoreCase(node.esquerda.valor) < 0)
            return rotacaoDireita(node);
        // Caso Left-Left (esquerda pesada e o valor foi inserido na subárvore esquerda-esquerda)
        // solução: rotação direita simples

        if (balance < -1 && valor.compareToIgnoreCase(node.direita.valor) > 0)
            return rotacaoEsquerda(node);
        // Caso Right-Right (direita pesada e valor inserido à direita-direita)
        // solução: rotação esquerda simples

        if (balance > 1 && valor.compareToIgnoreCase(node.esquerda.valor) > 0) {
            node.esquerda = rotacaoEsquerda(node.esquerda);
            return rotacaoDireita(node);
        }
        // Caso Left-Right (esquerda pesada, mas valor inserido na subárvore direita do filho esquerdo)
        // solução: dupla rotação (esquerda no filho esquerdo, depois direita no nó)

        if (balance < -1 && valor.compareToIgnoreCase(node.direita.valor) < 0) {
            node.direita = rotacaoDireita(node.direita);
            return rotacaoEsquerda(node);
        }
        // Caso Right-Left (direita pesada, valor inserido na subárvore esquerda do filho direito)
        // solução: dupla rotação (direita no filho direito, depois esquerda no nó)

        return node; // se nenhum caso de desbalanceamento requer rotação, retorna o nó atualizado
    }

    // Percurso em ordem (imprime ordenado)
    void emOrdem(Node node) {                // visita recursiva: esquerda -> raiz -> direita
        if (node != null) {
            emOrdem(node.esquerda);          // percorre a subárvore esquerda
            System.out.print(node.valor + " "); // processa (imprime) o valor do nó
            emOrdem(node.direita);           // percorre a subárvore direita
        }
    }

    // 🔹 Percurso em nível (BFS)
    void emNivel() {                         // imprime a árvore por níveis (nível por nível, da esquerda para a direita)
        if (raiz == null) return;            // se árvore vazia, não faz nada

        Queue<Node> fila = new LinkedList<>(); // cria uma fila para controlar a ordem de visita
        fila.add(raiz);                      // começa colocando a raiz na fila

        while (!fila.isEmpty()) {            // enquanto houver nós na fila
            Node atual = fila.poll();        // remove o próximo nó da fila (FIFO)
            System.out.print(atual.valor + " "); // imprime o valor do nó atual

            if (atual.esquerda != null)
                fila.add(atual.esquerda);   // adiciona filho esquerdo na fila (se existir)

            if (atual.direita != null)
                fila.add(atual.direita);    // adiciona filho direito na fila (se existir)
        }
        System.out.println();                // quebra de linha ao final da impressão
    }    

    // Método público para inserir
    public void inserir(String valor) {
        System.out.println("Inserção cores: " + valor);
        raiz = inserir(raiz, valor);
        // expõe um método simples para o usuário: atualiza a raiz com o resultado da inserção recursiva
        emNivel();
        System.out.println();                 // quebra de linha para leitura        
    }

    // Método público para mostrar a árvore
    public void mostrar() {
        emOrdem(raiz);                        // faz o percurso em ordem a partir da raiz
        System.out.println();
        emNivel();
        System.out.println();                 // quebra de linha para leitura
    }

    // Buscar valor
    public boolean buscar(Node node, String valor) { // busca recursiva de valor (começa geralmente pela raiz)
        if (node == null) return false;       // se nó é null, não encontrou
        if (valor.compareToIgnoreCase(node.valor) == 0) return true; // se valor igual ao do nó, encontrou
        if (valor.compareToIgnoreCase(node.valor) < 0) return buscar(node.esquerda, valor);
        // se valor menor, continua busca na subárvore esquerda
        else return buscar(node.direita, valor);
        // se valor maior, continua busca na subárvore direita
    }
}

// Teste
public class AVLCores {               // classe com método main para testar a AVLTree2
    public static void main(String[] args) {
        AVLTree3 arvore = new AVLTree3();     // cria uma nova árvore AVL vazia

        String[] cores = {"Verde", "Azul", "Bege", "Cinza", "Laranja", "Vermelho", "Branco", "Amarelo"}; 
        
        // vetor de valores a serem inseridos
        for (String c : cores) {
            arvore.inserir(c);                // insere cada valor na árvore
        }

        System.out.print("Em ordem: ");
        arvore.mostrar();                      // imprime os valores em ordem crescente (emOrdem)

        System.out.print("Em nível: ");
        arvore.emNivel();                      // imprime os valores nível a nível (BFS)

        System.out.println("\nPesquisa.....................");
        // Testando busca
        System.out.println("Buscar Azul? " + arvore.buscar(arvore.raiz, "Azul"));
        // imprime true se encontrou Azul na árvore

        System.out.println("Buscar Roxo? " + arvore.buscar(arvore.raiz, "Roxo"));
        // imprime false porque 15 não foi inserido
    }
}
