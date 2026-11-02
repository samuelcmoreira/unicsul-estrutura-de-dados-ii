package exercicioHeap;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SistemaHospitalarGUI extends JFrame {

    private MinHeap<Paciente> filaP1;
    private MinHeap<Paciente> filaP2;
    private MinHeap<Paciente> filaP3;

    private int contadorAtendimentosP2;

    private JTable tabelaPacientes;
    private DefaultTableModel modeloTabela;
    private JTextArea txtLog;
    private JTextField txtNome, txtId;
    private JComboBox<Integer> cbPrioridade;
    
    private List<Paciente> listaTeste;

    public SistemaHospitalarGUI() {
        filaP1 = new MinHeap<>(50);
        filaP2 = new MinHeap<>(50);
        filaP3 = new MinHeap<>(50);
        contadorAtendimentosP2 = 0;

        listaTeste = new ArrayList<>();
        
        setTitle("Sistema de Triagem Hospitalar - Min-Heap Modularizado");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        
        JPanel painelSuperior = new JPanel(new GridLayout(2, 1, 5, 5));
        
        JPanel painelCadastro = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        painelCadastro.setBorder(BorderFactory.createTitledBorder("Cadastrar Paciente Individual"));

        painelCadastro.add(new JLabel("Nome:"));
        txtNome = new JTextField(8);
        painelCadastro.add(txtNome);

        painelCadastro.add(new JLabel("ID:"));
        txtId = new JTextField(4);
        painelCadastro.add(txtId);

        painelCadastro.add(new JLabel("Prioridade:"));
        cbPrioridade = new JComboBox<>(new Integer[]{1, 2, 3});
        painelCadastro.add(cbPrioridade);

        JButton btnInserir = new JButton("Inserir na Fila");
        painelCadastro.add(btnInserir);

        JButton btnAtender = new JButton("Atender Próximo");
        painelCadastro.add(btnAtender);
        
        painelSuperior.add(painelCadastro);

        
        JPanel painelTestes = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        painelTestes.setBorder(BorderFactory.createTitledBorder("Gerenciamento de Testes"));

        JButton btnAcrescentarTeste = new JButton("Adicionar à Lista de Teste");
        JButton btnRodarTeste = new JButton("Rodar Teste Automático");
        JButton btnLimparTeste = new JButton("Limpar Lista de Teste");
        
        painelTestes.add(btnAcrescentarTeste);
        painelTestes.add(btnRodarTeste);
        painelTestes.add(btnLimparTeste);

        painelSuperior.add(painelTestes);
        add(painelSuperior, BorderLayout.NORTH);

        
        modeloTabela = new DefaultTableModel(new Object[]{"Posição", "Nome", "ID", "Prioridade", "Ordem Chegada"}, 0);
        tabelaPacientes = new JTable(modeloTabela);
        JScrollPane scrollTabela = new JScrollPane(tabelaPacientes);
        scrollTabela.setBorder(BorderFactory.createTitledBorder("Estado Atual da Fila (Heap Mínima)"));

        txtLog = new JTextArea(8, 20);
        txtLog.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(txtLog);
        scrollLog.setBorder(BorderFactory.createTitledBorder("Log de Atendimento"));

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollTabela, scrollLog);
        splitPane.setDividerLocation(220);
        add(splitPane, BorderLayout.CENTER);

        btnInserir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nome = txtNome.getText().trim();
                    if (nome.isEmpty()) {
                        JOptionPane.showMessageDialog(SistemaHospitalarGUI.this, "Informe o nome.");
                        return;
                    }
                    int id = Integer.parseInt(txtId.getText().trim());
                    int prioridade = (Integer) cbPrioridade.getSelectedItem();

                    Paciente p = new Paciente(nome, id, prioridade);

                    if (prioridade == 1) filaP1.inserir(p);
                    else if (prioridade == 2) filaP2.inserir(p);
                    else filaP3.inserir(p);

                    adicionarLog("Inserido manualmente: " + p);
                    atualizarTabela();

                    txtNome.setText("");
                    txtId.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SistemaHospitalarGUI.this, "ID numérico inválido.");
                }
            }
        });

        btnAtender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Paciente p = atenderProximoPaciente();
                if (p != null) {
                    adicionarLog("ATENDIDO: " + p);
                    atualizarTabela();
                } else {
                    JOptionPane.showMessageDialog(SistemaHospitalarGUI.this, "Todas as filas estão vazias!");
                }
            }
        });

        btnAcrescentarTeste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nome = txtNome.getText().trim();
                    if (nome.isEmpty()) {
                        JOptionPane.showMessageDialog(SistemaHospitalarGUI.this, "Informe o nome para adicionar à lista de teste.");
                        return;
                    }
                    int id = Integer.parseInt(txtId.getText().trim());
                    int prioridade = (Integer) cbPrioridade.getSelectedItem();

                    Paciente p = new Paciente(nome, id, prioridade);
                    listaTeste.add(p);
                    adicionarLog("Adicionado à lista de teste: " + p);

                    txtNome.setText("");
                    txtId.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SistemaHospitalarGUI.this, "ID numérico inválido.");
                }
            }
        });

        btnLimparTeste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaTeste.clear();
                adicionarLog("Lista de teste limpa.");
            }
        });

        btnRodarTeste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Paciente> pacientesParaRodar = new ArrayList<>(listaTeste);
                if (pacientesParaRodar.isEmpty()) {
                    pacientesParaRodar.add(new Paciente("Carlos", 103, 1));
                    pacientesParaRodar.add(new Paciente("Ana", 101, 2));
                    pacientesParaRodar.add(new Paciente("Bruno", 102, 3));
                    pacientesParaRodar.add(new Paciente("Daniela", 104, 2));
                    pacientesParaRodar.add(new Paciente("Eduardo", 105, 3));
                    pacientesParaRodar.add(new Paciente("Fernanda", 106, 1));
                    pacientesParaRodar.add(new Paciente("Gabriel", 107, 2));
                    pacientesParaRodar.add(new Paciente("Helena", 108, 3));
                    pacientesParaRodar.add(new Paciente("Igor", 109, 1));
                    pacientesParaRodar.add(new Paciente("Julia", 110, 2));
                }
                executarTesteAutomatico(pacientesParaRodar);
            }
        });
    }

    private Paciente atenderProximoPaciente() {
        if (filaP1.tamanho > 0) {
            return filaP1.removerMin();
        }
        else if (contadorAtendimentosP2 >= 2 && filaP3.tamanho > 0) {
            contadorAtendimentosP2 = 0;
            return filaP3.removerMin();
        }
        else if (filaP2.tamanho > 0) {
            contadorAtendimentosP2++;
            return filaP2.removerMin();
        } 
        else if (filaP3.tamanho > 0) {
            contadorAtendimentosP2 = 0;
            return filaP3.removerMin();
        }
        
        return null;
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        int posicaoGeral = 0;

        for (int i = 0; i < filaP1.tamanho; i++) {
            Paciente p = filaP1.obter(i);
            if (p != null) {
                modeloTabela.addRow(new Object[]{posicaoGeral++, p.getNome(), p.getId(), p.getPrioridade(), p.getOrdemChegada()});
            }
        }

        for (int i = 0; i < filaP2.tamanho; i++) {
            Paciente p = filaP2.obter(i);
            if (p != null) {
                modeloTabela.addRow(new Object[]{posicaoGeral++, p.getNome(), p.getId(), p.getPrioridade(), p.getOrdemChegada()});
            }
        }

        for (int i = 0; i < filaP3.tamanho; i++) {
            Paciente p = filaP3.obter(i);
            if (p != null) {
                modeloTabela.addRow(new Object[]{posicaoGeral++, p.getNome(), p.getId(), p.getPrioridade(), p.getOrdemChegada()});
            }
        }
    }

    private void adicionarLog(String texto) {
        txtLog.append(texto + "\n");
        txtLog.setCaretPosition(txtLog.getDocument().getLength());
    }

    private void executarTesteAutomatico(List<Paciente> pacientes) {
        filaP1 = new MinHeap<>(50);
        filaP2 = new MinHeap<>(50);
        filaP3 = new MinHeap<>(50);
        contadorAtendimentosP2 = 0;

        modeloTabela.setRowCount(0);
        txtLog.setText("");

        adicionarLog("=== INICIANDO TESTE DO HOSPITAL ===");

        new Thread(() -> {
            try {
                adicionarLog("\n--- INSERINDO PACIENTES ---");
                for (Paciente p : pacientes) {
                    SwingUtilities.invokeLater(() -> {

                        if (p.getPrioridade() == 1) filaP1.inserir(p);
                        else if (p.getPrioridade() == 2) filaP2.inserir(p);
                        else filaP3.inserir(p);

                        adicionarLog("Inserido: " + p);
                        atualizarTabela();
                    });
                    Thread.sleep(400);
                }

                adicionarLog("\n--- REMOVENDO / ATENDENDO PACIENTES ---");
                while (filaP1.tamanho > 0 || filaP2.tamanho > 0 || filaP3.tamanho > 0) {
                    SwingUtilities.invokeLater(() -> {
                        Paciente p = atenderProximoPaciente();
                        if (p != null) {
                            adicionarLog("Removido/Atendido: " + p);
                            atualizarTabela();
                        }
                    });
                    Thread.sleep(600);
                }

                SwingUtilities.invokeLater(() -> {
                    adicionarLog("\nTodos os pacientes foram atendidos!");
                    JOptionPane.showMessageDialog(this, "Teste automático finalizado com sucesso!");
                });
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SistemaHospitalarGUI().setVisible(true);
        });
    }
}