import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class App {

    private static final String ARQUIVO = "alunos.xml";

    static class Aluno {
        private String nome;
        private double nota;

        public Aluno(String nome, double nota) {
            this.nome = nome;
            this.nota = nota;
        }

        public String getNome() { return nome; }
        public double getNota() { return nota; }
    }

    private static void criarArquivo() {
        File file = new File(ARQUIVO);
        if (!file.exists()) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
                pw.println("<alunos>");
                pw.println("</alunos>");
            } catch (IOException e) {
                System.out.println("Erro ao criar o arquivo: " + e.getMessage());
            }
        }
    }

    private static List<Aluno> lerAlunos() {
        List<Aluno> alunos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String line;
            String nome = null;
            double nota = 0;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("<nome>")) nome = line.replace("<nome>", "").replace("</nome>", "");
                if (line.startsWith("<nota>")) nota = Double.parseDouble(line.replace("<nota>", "").replace("</nota>", ""));
                if (line.equals("</aluno>") && nome != null) {
                    alunos.add(new Aluno(nome, nota));
                    nome = null;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return alunos;
    }

    private static void salvarAluno(Aluno aluno) {
        List<String> linhas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String line;
            while ((line = br.readLine()) != null) linhas.add(line);
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            return;
        }

        if (!linhas.isEmpty()) linhas.remove(linhas.size() - 1); // remove </alunos>

        linhas.add("  <aluno>");
        linhas.add("    <nome>" + aluno.getNome() + "</nome>");
        linhas.add("    <nota>" + aluno.getNota() + "</nota>");
        linhas.add("  </aluno>");
        linhas.add("</alunos>");

        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO))) {
            for (String l : linhas) pw.println(l);
        } catch (IOException e) {
            System.out.println("Erro ao salvar o aluno: " + e.getMessage());
        }
    }

    private static void cadastrarAluno(Scanner sc) {
        System.out.print("Digite o nome do aluno: ");
        String nome = sc.nextLine();

        double nota;
        while (true) {
            System.out.print("Digite a nota do aluno (0-10): ");
            try {
                nota = Double.parseDouble(sc.nextLine());
                if (nota >= 0 && nota <= 10) break;
                System.out.println("Nota inválida! Deve estar entre 0 e 10.");
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }

        salvarAluno(new Aluno(nome, nota));
        System.out.println("Aluno cadastrado!\n");
    }

    private static void listarAlunos() {
        List<Aluno> alunos = lerAlunos();
        if (alunos.isEmpty()) System.out.println("Nenhum aluno cadastrado.\n");
        else {
            System.out.println("Lista de alunos:");
            for (Aluno a : alunos) {
                System.out.println("- " + a.getNome() + ": " + a.getNota());
            }
            System.out.println();
        }
    }

    // Menu: Média das notas
    private static void mediaNotas() {
        List<Aluno> alunos = lerAlunos();
        if (alunos.isEmpty()) System.out.println("Nenhum aluno cadastrado.\n");
        else {
            double soma = 0;
            for (Aluno a : alunos) soma += a.getNota();
            double media = soma / alunos.size();
            System.out.printf("Média das notas: %.2f\n\n", media);
        }
    }

    private static void maiorNota() {
        List<Aluno> alunos = lerAlunos();
        if (alunos.isEmpty()) System.out.println("Nenhum aluno cadastrado.\n");
        else {
            Aluno maior = Collections.max(alunos, Comparator.comparingDouble(Aluno::getNota));
            System.out.println("Aluno com maior nota: " + maior.getNome() + " (" + maior.getNota() + ")\n");
        }
    }

    // Menu: Contar alunos com nota >= 7
    private static void contarAprovados() {
        List<Aluno> alunos = lerAlunos();
        if (alunos.isEmpty()) System.out.println("Nenhum aluno cadastrado.\n");
        else {
            int count = 0;
            for (Aluno a : alunos) if (a.getNota() >= 7) count++;
            System.out.println("Alunos com nota >= 7: " + count + "\n");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        criarArquivo();

        while (true) {
            System.out.println("----- MENU -----");
            System.out.println("1 - Cadastrar aluno");
            System.out.println("2 - Listar alunos");
            System.out.println("3 - Média das notas");
            System.out.println("4 - Aluno com maior nota");
            System.out.println("5 - Quantos alunos possuem nota >= 7");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            String opcao = sc.nextLine();
            switch (opcao) {
                case "1": cadastrarAluno(sc); break;
                case "2": listarAlunos(); break;
                case "3": mediaNotas(); break;
                case "4": maiorNota(); break;
                case "5": contarAprovados(); break;
                case "0": System.out.println("Saindo..."); return;
                default: System.out.println("Opção inválida!\n");
            }
        }
    }
}