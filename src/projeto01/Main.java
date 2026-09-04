package projeto01;

import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {

        GerenciadorCampi gerenciador = new GerenciadorCampi();
        String menu = "SISTEMA ACADÊMICO - UNICSUL\n\n"
                + "1 - Cadastrar novo aluno\n"
                + "2 - Localizar aluno\n"
                + "3 - Listar alunos do campus\n"
                + "4 - Sair\n\n"
                + "Escolha uma opção:";
        String[] campiOpcoes = {
                "Anália Franco", "Guarulhos", "Liberdade", "Paulista",
                "São Miguel", "Santo Amaro", "Villa Lobos"
        };

        while (true) {
            String opcaoStr = JOptionPane.showInputDialog(null, menu, "Menu Principal", JOptionPane.QUESTION_MESSAGE);

            if (opcaoStr == null) {
                break;
            }

            switch (opcaoStr) {
                case "1":
                    String campusCadastro = (String) JOptionPane.showInputDialog(
                            null,
                            "Selecione o Campus para matrícula:",
                            "Cadastro",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            campiOpcoes,
                            campiOpcoes[0]);
                    if (campusCadastro == null) break;

                    String alunoCadastro = JOptionPane.showInputDialog("Digite o nome do Aluno:");
                    if (alunoCadastro == null || alunoCadastro.trim().isEmpty()) break;

                    boolean sucesso = gerenciador.cadastrarAlunoGlobal(campusCadastro, alunoCadastro);

                    if (sucesso) {
                        JOptionPane.showMessageDialog(null,
                                "Aluno cadastrado com sucesso no campus " + campusCadastro);
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro: Aluno já existe em um dos campi ou campus inválido.",
                                "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case "2":
                    String alunoBusca = JOptionPane.showInputDialog("Digite o nome do Aluno para localizar:");
                    if (alunoBusca == null || alunoBusca.trim().isEmpty()) break;

                    String campusEncontrado = gerenciador.localizarAlunoGlobal(alunoBusca);
                    if (campusEncontrado != null) {
                        JOptionPane.showMessageDialog(null, "Aluno encontrado no campus: " + campusEncontrado, "Resultado da Busca", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Aluno não encontrado.", "Resultado da Busca", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;

                case "3":
                    String campusListagem = (String) JOptionPane.showInputDialog(
                            null,
                            "Selecione o Campus para listar alunos:",
                            "Listagem",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            campiOpcoes,
                            campiOpcoes[0]);
                    if (campusListagem == null) break;
                    
                    String listaAlunos = gerenciador.listarAlunosDoCampus(campusListagem);

                    if (listaAlunos != null) {
                        JOptionPane.showMessageDialog(null, "Alunos do campus " + campusListagem + ":\n" + listaAlunos, "Lista de Alunos", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Campus não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }

                    break;

                case "4":
                    JOptionPane.showMessageDialog(null, "Encerrando o sistema.");
                    System.exit(0);
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente.");
            }
        }
    }
}
