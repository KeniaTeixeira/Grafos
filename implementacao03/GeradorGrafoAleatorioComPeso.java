import java.io.*;
import java.util.*;

public class GeradorGrafoAleatorioComPeso {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("GERANDO GRAFO ALEATORIO");
        System.out.print("Digite o número de vértices: ");
        int numVertices = sc.nextInt();

        System.out.print("Digite o número de arestas: ");
        int numArestas = sc.nextInt();

        System.out.print("Digite o nome do arquivo (com formato): ");
        String nomeArquivo = sc.next();

        gerarGrafoAleatorio(numVertices, numArestas, nomeArquivo);
        System.out.println("Grafo aleatório gerado com sucesso!!");

        sc.close();
    }

    private static void gerarGrafoAleatorio(int numVertices, int numArestas, String nomeArquivo) throws IOException {
        Random random = new Random();
        Set<String> arestasExistentes = new HashSet<>();
        BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo));

        int arestasCriadas = 0;
        while (arestasCriadas < numArestas) {
            int origem = random.nextInt(numVertices);
            int destino = random.nextInt(numVertices);

            if (origem != destino) { 
                String chave = origem + "-" + destino;
                if (!arestasExistentes.contains(chave)) {
                    arestasExistentes.add(chave);

                    int peso = random.nextInt(10) + 1; 
                    writer.write(origem + " " + destino + " " + peso);
                    writer.newLine();
                    arestasCriadas++;
                }
            }
        }

        writer.close();
    }
}
