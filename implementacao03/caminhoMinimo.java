/*
 * Teoria do Grafos e Computabilidade
 * Aluna: Kênia Teixeira de Paula
 * Implementação N.03 - Caminhos mínimos
 * 
*/

import java.io.*;
import java.util.*;

public class caminhoMinimo {
    static class Aresta {
        int destino;
        int peso;

        public Aresta(int destino, int peso) {
            this.destino = destino;
            this.peso = peso;
        }
    }
    static Map<Integer, List<Aresta>> grafo = new HashMap<>();
    
    private static void lerGrafoDeArquivo(String nomeArquivo) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(nomeArquivo));
        String linha;

        while ((linha = br.readLine()) != null) {
            String[] partes = linha.trim().split("\\s+");
            if (partes.length != 3) continue;

            int origem = Integer.parseInt(partes[0]);
            int destino = Integer.parseInt(partes[1]);
            int peso = Integer.parseInt(partes[2]);

            grafo.putIfAbsent(origem, new ArrayList<>());
            grafo.get(origem).add(new Aresta(destino, peso));
        }

        br.close();
    }

    private static void encontrarCaminhoMinimo(int origem, int destino) {
        Map<Integer, Integer> pai = new HashMap<>();
        Map<Integer, Integer> distancia = new HashMap<>();

        Queue<Integer> fila = new LinkedList<>();
        fila.offer(origem);
        distancia.put(origem, 0);

        while (!fila.isEmpty()) {
            int atual = fila.poll();

            if (atual == destino) {
                break;
            }

            for (Aresta aresta : grafo.getOrDefault(atual, new ArrayList<>())) {
                if (!distancia.containsKey(aresta.destino)) {
                    distancia.put(aresta.destino, distancia.get(atual) + 1);
                    pai.put(aresta.destino, atual);
                    fila.offer(aresta.destino);
                }
            }
        }

        if (!pai.containsKey(destino) && origem != destino) {
            System.out.println("Não existe caminho entre " + origem + " e " + destino);
            return;
        }
        List<Integer> caminho = new ArrayList<>();
        int atual = destino;
        caminho.add(atual);

        while (pai.containsKey(atual)) {
            atual = pai.get(atual);
            caminho.add(atual);
        }

        Collections.reverse(caminho);

        int pesoTotal = 0;
        for (int i = 0; i < caminho.size() - 1; i++) {
            int u = caminho.get(i);
            int v = caminho.get(i + 1);
            for (Aresta aresta : grafo.getOrDefault(u, new ArrayList<>())) {
                if (aresta.destino == v) {
                    pesoTotal += aresta.peso;
                    break;
                }
            }
        }

        System.out.println("\nCaminho encontrado:");
        for (int v : caminho) {
            System.out.print(v + " ");
        }
        System.out.println();
        System.out.println("Número de arestas: " + (caminho.size() - 1));
        System.out.println("Comprimento total (soma dos pesos): " + pesoTotal);
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
    
        System.out.print("Digite o nome do arquivo contendo o grafo: ");
        String nomeArquivo = sc.nextLine();
    
        lerGrafoDeArquivo(nomeArquivo);
    
        System.out.print("Digite o vértice de origem: ");
        int origem = sc.nextInt();
    
        System.out.print("Digite o vértice de destino: ");
        int destino = sc.nextInt();

        long inicio = System.currentTimeMillis();
    
        encontrarCaminhoMinimo(origem, destino);

        long fim = System.currentTimeMillis();

        System.out.println("Tempo de execução: " + (fim - inicio) + " ms");
        sc.close();
    }
}
