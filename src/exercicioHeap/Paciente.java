package exercicioHeap;

public class Paciente implements Comparable<Paciente> {
    private static long contadorChegada = 0;

    private String nome;
    private int id;
    private int prioridade;
    private long ordemChegada;

    public Paciente(String nome, int id, int prioridade) {
        this.nome = nome;
        this.id = id;
        this.prioridade = prioridade;
        this.ordemChegada = contadorChegada++;
    }

    public String getNome() { return nome; }
    public int getId() { return id; }
    public int getPrioridade() { return prioridade; }
    public long getOrdemChegada() { return ordemChegada; }

    @Override
    public int compareTo(Paciente outro) {
        int cmp = Integer.compare(this.prioridade, outro.prioridade);
        if (cmp != 0) {
            return cmp;
        }
        return Long.compare(this.ordemChegada, outro.ordemChegada);
    }

    @Override
    public String toString() {
        return nome + " (ID: " + id + ", Prioridade: " + prioridade + ")";
    }
}