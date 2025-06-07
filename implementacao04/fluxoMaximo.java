/*
 * Teoria do Grafos e Computabilidade
 * Aluna: Kênia Teixeira de Paula
 * Implementação N.04 - Fluxo Máximo
 * 
*/
import java.io.*;
import java.util.*;

public class fluxoMaximo {

    static class Grafo {
        int numVertices;
        int[][] capacidade;
        List<Integer>[] adj;

        public Grafo(int n) {
            this.numVertices = n;
            capacidade = new int[n][n];
            adj = new List[n];
            for (int i = 0; i < n; i++) {
                adj[i] = new ArrayList<>();
            }
        }

        public void adicionarAresta(int u, int v, int c) {
            capacidade[u][v] = c;
            adj[u].add(v);
            adj[v].add(u);
        }

        //criar um grafo separado com a capacidade de 1 
        //para que nenhum caminho encontrado reutilize a mesma aresta na mesma direção.
        public Grafo copiarComCapacidadeUm() {
            Grafo copia = new Grafo(numVertices);
            for (int u = 0; u < numVertices; u++) {
                for (int v : adj[u]) {
                    if (capacidade[u][v] > 0) {
                        copia.adicionarAresta(u, v, 1);
                    }
                }
            }
            return copia;
        }
    }

    //busca em largura usado no metodo do Edmonds KArp
    public static int bfs(int[] pai, Grafo grafo, int origem, int destino) {
        Arrays.fill(pai, -1);
        pai[origem] = -2;
        Queue<int[]> fila = new LinkedList<>();
        fila.add(new int[]{origem, Integer.MAX_VALUE});

        while (!fila.isEmpty()) {
            int[] atual = fila.poll();
            int nodo = atual[0];
            int fluxo = atual[1];

            for (int prox : grafo.adj[nodo]) {
                if (pai[prox] == -1 && grafo.capacidade[nodo][prox] > 0) {
                    pai[prox] = nodo;
                    int novoFluxo = Math.min(fluxo, grafo.capacidade[nodo][prox]);
                    if (prox == destino)
                        return novoFluxo;
                    fila.add(new int[]{prox, novoFluxo});
                }
            }
        }
        return 0;
    }

    public static int edmondsKarp(Grafo grafo, int origem, int destino, List<List<Integer>> caminhos) {
        int fluxo = 0;
        int[] pai = new int[grafo.numVertices];

        while (true) {
            int fluxoAtual = bfs(pai, grafo, origem, destino);
            if (fluxoAtual == 0)
                break;

            fluxo += fluxoAtual;
            List<Integer> caminho = new ArrayList<>();
            int nodo = destino;
            while (nodo != origem) {
                caminho.add(nodo);
                int anterior = pai[nodo];
                grafo.capacidade[anterior][nodo] -= fluxoAtual;
                grafo.capacidade[nodo][anterior] += fluxoAtual;
                nodo = anterior;
            }
            caminho.add(origem);
            Collections.reverse(caminho);
            caminhos.add(caminho);
        }
        return fluxo;
    }

    //copia o grafo sem mudar as capacidades originais
    public static Grafo copiarGrafo(Grafo original) {
        Grafo copia = new Grafo(original.numVertices);
        for (int u = 0; u < original.numVertices; u++) {
            for (int v : original.adj[u]) {
                if (original.capacidade[u][v] > 0) {
                    copia.adicionarAresta(u, v, original.capacidade[u][v]);
                }
            }
        }
        return copia;
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o arquivo de entrada:");
        String caminhoArquivo = scanner.nextLine();

        BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo));
        String[] primeiraLinha = br.readLine().split(" ");
        int N = Integer.parseInt(primeiraLinha[0]);
        int M = Integer.parseInt(primeiraLinha[1]);

        Grafo grafoOriginal = new Grafo(N);

        for (int i = 0; i < M; i++) {
            String[] linha = br.readLine().split(" ");
            int u = Integer.parseInt(linha[0]) - 1;
            int v = Integer.parseInt(linha[1]) - 1;
            int c = Integer.parseInt(linha[2]);
            grafoOriginal.adicionarAresta(u, v, c);
        }

        br.close();

        System.out.println("Digite o vértice de origem:");
        int origem = Integer.parseInt(scanner.nextLine()) - 1;

        System.out.println("Digite o vértice de destino:");
        int destino = Integer.parseInt(scanner.nextLine()) - 1;

        long inicio = System.nanoTime(); //marcar tempo

        //fluxo máximo real
        Grafo grafoCapacidadeReal = copiarGrafo(grafoOriginal);
        List<List<Integer>> caminhosFluxoReal = new ArrayList<>();
        int fluxoMaximo = edmondsKarp(grafoCapacidadeReal, origem, destino, caminhosFluxoReal);

        //caminhos disjuntos em arestas (capacidade 1)
        Grafo grafoCapacidadeUm = grafoOriginal.copiarComCapacidadeUm();
        List<List<Integer>> caminhosDisjuntos = new ArrayList<>();
        int quantidadeCaminhosDisjuntos = edmondsKarp(grafoCapacidadeUm, origem, destino, caminhosDisjuntos);

        //marcar o tempo
        long fim = System.nanoTime();
        double tempoExecucao = (fim - inicio) / 1e6; // milissegundos


        System.out.println("\n===================================");
        System.out.println("Fluxo máximo: " + fluxoMaximo);
        System.out.println("Quantidade de caminhos disjuntos em arestas: " + quantidadeCaminhosDisjuntos);
        System.out.println("Caminhos encontrados:");
        for (List<Integer> caminho : caminhosDisjuntos) {
            System.out.print("Caminho: ");
            for (int i = 0; i < caminho.size(); i++) {
                System.out.print((caminho.get(i) + 1));
                if (i < caminho.size() - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println();
        }

        System.out.printf("Tempo de execução: %.4f ms\n", tempoExecucao);
    }
}
