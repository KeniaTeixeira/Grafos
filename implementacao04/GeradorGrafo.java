import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GeradorGrafo {

    public static void gerarGrafo(String nomeArquivo, int numVertices, int numArestas, int capacidadeMin, int capacidadeMax) throws IOException {
        FileWriter fw = new FileWriter(nomeArquivo);
        Random rand = new Random();

        fw.write(numVertices + " " + numArestas + "\n");

        for (int i = 0; i < numArestas; i++) {
            int u = rand.nextInt(numVertices) + 1; 
            int v = rand.nextInt(numVertices) + 1; 

            while (u == v) { 
                v = rand.nextInt(numVertices) + 1;
            }

            int capacidade = rand.nextInt(capacidadeMax - capacidadeMin + 1) + capacidadeMin;

            fw.write(u + " " + v + " " + capacidade + "\n");
        }

        fw.close();
        System.out.println("Grafo gerado e salvo em " + nomeArquivo);
    }

    public static void main(String[] args) throws IOException {
        gerarGrafo("grafo10000.txt", 10000, 120000, 1, 20);
    }
}