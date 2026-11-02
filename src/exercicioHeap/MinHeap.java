package exercicioHeap;

public class MinHeap<T extends Comparable<T>> {
    private Object[] heap;
    public int tamanho;
    private int capacidade;

    public MinHeap(int capacidade) {
        this.capacidade = capacidade;
        this.heap = new Object[capacidade];
        this.tamanho = 0;
    }

    private int pai(int i) { return (i - 1) / 2; }
    private int esquerdo(int i) { return 2 * i + 1; }
    private int direito(int i) { return 2 * i + 2; }

    @SuppressWarnings("unchecked")
    private T get(int i) {
        return (T) heap[i];
    }

    private void trocar(int i, int j) {
        Object temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    public void inserir(T valor) {
        if (tamanho >= capacidade) {
            return;
        }
        heap[tamanho] = valor;
        int atual = tamanho;
        tamanho++;

        while (atual > 0 && get(atual).compareTo(get(pai(atual))) < 0) {
            trocar(atual, pai(atual));
            atual = pai(atual);
        }
    }

    public T removerMin() {
        if (tamanho <= 0) {
            return null;
        }
        T raiz = get(0);
        heap[0] = heap[tamanho - 1];
        heap[tamanho - 1] = null;
        tamanho--;
        heapify(0);
        return raiz;
    }

    private void heapify(int i) {
        int menor = i;
        int esq = esquerdo(i);
        int dir = direito(i);

        if (esq < tamanho && get(esq).compareTo(get(menor)) < 0)
            menor = esq;
        if (dir < tamanho && get(dir).compareTo(get(menor)) < 0)
            menor = dir;

        if (menor != i) {
            trocar(i, menor);
            heapify(menor);
        }
    }

    public T obter(int i) {
        if (i < 0 || i >= tamanho) return null;
        return get(i);
    }
}