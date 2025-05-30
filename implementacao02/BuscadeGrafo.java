/*
 * Teoria do Grafos e Computabilidade
 * Aluna: Kênia Teixeira de Paula
 * Implementação N.02 - Busca em Grafos 
 * 
*/
import java.io.*;
import java.util.*;

class Grafo {
    private int numVertices;
    private List<List<Integer>> listaAdjacencia;
    private boolean[] visitado;
    private int[] descoberto, terminado;
    private int tempo;
    private List<String> arestasArvore;
    private List<String> arestasDivergentes;
    private int verticeEscolhido;
    
    public Grafo(int numVertices) {
        this.numVertices = numVertices;
        listaAdjacencia = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            listaAdjacencia.add(new ArrayList<>());
        }
    }
    
    public void adicionarAresta(int origem, int destino) {
        listaAdjacencia.get(origem - 1).add(destino - 1); // indexa em zero
    }
    
    public void buscaProfundidade(int vertice) {
        visitado = new boolean[numVertices];
        descoberto = new int[numVertices];
        terminado = new int[numVertices];
        tempo = 0;
        arestasArvore = new ArrayList<>();
        arestasDivergentes = new ArrayList<>();
        verticeEscolhido = vertice - 1;

        for (int i = 0; i < numVertices; i++) {
            Collections.sort(listaAdjacencia.get(i)); // Ordem lexicográfica
        }
        busca(verticeEscolhido);
    }
    
    private void busca(int v) {
        visitado[v] = true;
        descoberto[v] = ++tempo;
    
        for (int vizinho : listaAdjacencia.get(v)) {
            if (!visitado[vizinho]) {
                arestasArvore.add((v + 1) + " -> " + (vizinho + 1));
                if (v == verticeEscolhido) {
                    arestasDivergentes.add((v + 1) + " -> " + (vizinho + 1) + " (Árvore)");
                }
                busca(vizinho);
            } else {
                if (v == verticeEscolhido) {
                    if (terminado[vizinho] == 0) { 
                        arestasDivergentes.add((v + 1) + " -> " + (vizinho + 1) + " (Retorno)");
                    } else if (descoberto[vizinho] > descoberto[v]) { 
                        arestasDivergentes.add((v + 1) + " -> " + (vizinho + 1) + " (Avanço)");
                    } else { 
                        arestasDivergentes.add((v + 1) + " -> " + (vizinho + 1) + " (Cruzamento)");
                    }
                }
            }
        }
    
        terminado[v] = ++tempo;
    }

    public void imprimirArestas() {
        System.out.println("----- Arestas de árvore ------ ");
        for (String aresta : arestasArvore) {
            System.out.println(aresta);
        }
        System.out.println("\n----- Arestas do vértice escolhido ------- ");
        for (String aresta : arestasDivergentes) {
            System.out.println(aresta);
        }
    }
    
    public static Grafo lerArquivo(String nomeArquivo) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo));
        String[] primeiraLinha = reader.readLine().trim().split("\\s+");
        int numVertices = Integer.parseInt(primeiraLinha[0]);
        int numArestas = Integer.parseInt(primeiraLinha[1]);

        Grafo grafo = new Grafo(numVertices);
        for (int i = 0; i < numArestas; i++) {
            String[] partes = reader.readLine().trim().split("\\s+");
            int origem = Integer.parseInt(partes[0]);
            int destino = Integer.parseInt(partes[1]);
            grafo.adicionarAresta(origem, destino);
        }
        reader.close();
        return grafo;
    }
}

public class BuscadeGrafo{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Digite o nome do arquivo (com o formato):");
            String nomeArquivo = scanner.nextLine();
            
            System.out.print("Digite o número do vértice: ");
            int vertice = scanner.nextInt();
            
            Grafo grafo = Grafo.lerArquivo(nomeArquivo);
            grafo.buscaProfundidade(vertice);
            grafo.imprimirArestas();
            
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida!");
        } finally {
            scanner.close();
        }
    }
}
