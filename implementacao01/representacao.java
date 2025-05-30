/*
 * Teoria do Grafos e Computabilidade
 * Aluna: Kênia Teixeira de Paula
 * Implementação N.01 - Representação de Grafos 
 * 
*/
import java.io.*;
import java.util.*;

public class representacao {
    static class Grafo {
        private int numVertices;
        private List<List<Integer>> listaAdjacencia;
        private int[] grauEntrada;

        public Grafo(int numVertices) {
            this.numVertices = numVertices;
            listaAdjacencia = new ArrayList<>();
            grauEntrada = new int[numVertices];

                for (int i = 0; i < numVertices; i++) {
                    listaAdjacencia.add(new ArrayList<>());
                }
        }

        public void adicionarAresta(int origem, int destino) {
            listaAdjacencia.get(origem - 1).add(destino - 1); // indexa em zero
            grauEntrada[destino - 1]++; // atualiza o grau de entrada
        }
        
        private List<Integer> convertParaBase1(List<Integer> lista) {
            List<Integer> listaCorrigida = new ArrayList<>();
                for (int i = 0; i < lista.size(); i++) { 
                    listaCorrigida.add(lista.get(i) + 1); //volta para base 1
                }
            return listaCorrigida;
        }
        
        public void exibirInformacoesVertice(int vertice) {
            int indiceVertice = vertice - 1; // indice começando de zero
            List<Integer> sucessores = listaAdjacencia.get(indiceVertice);

            // Conjunto de predecessores 
            Set<Integer> predecessores = new HashSet<>();
            for (int i = 0; i < numVertices; i++) {
                if (listaAdjacencia.get(i).contains(indiceVertice)) {
                    predecessores.add(i + 1); // Convertendo de volta para indice começando de 1
                }
            }

            System.out.println("\n\n------------------------------------- \nInformações sobre o vértice " + vertice + ": \n-------------------------------------");
            System.out.println("Grau de saída: " + sucessores.size());
            System.out.println("Grau de entrada: " + grauEntrada[indiceVertice]);
            System.out.println("Conjunto de sucessores: " + formatarLista(convertParaBase1(sucessores)));
            System.out.println("Conjunto de predecessores: " + formatarLista(predecessores));
            System.out.println("-------------------------------------");
        }

        private String formatarLista(Collection<Integer> lista) {
            if (lista.isEmpty()) {
                return "Vazio";
            }
            return lista.toString().replaceAll("[\\[\\],]", ""); //retorna a lista so com os valores  (sem virgula ecolchete)
        }
    }

    public static Grafo ArquivoComGrafo(String nomeArquivo) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo));
        
        String[] primeiraLinha = reader.readLine().trim().split("\\s+");
        int numVertices = Integer.parseInt(primeiraLinha[0]); //pega o numero de vertices
        int numArestas = Integer.parseInt(primeiraLinha[1]); //pega o numero de arestas

        Grafo grafo = new Grafo(numVertices); //cria um grafo com o numero de vertices
        
            for (int i = 0; i < numArestas; i++) { 
                String[] partes = reader.readLine().trim().split("\\s+");
                int origem = Integer.parseInt(partes[0]);
                int destino = Integer.parseInt(partes[1]);
                grafo.adicionarAresta(origem, destino); //adiciona as arestas
            }

        reader.close();
        return grafo;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Digite o nome do arquivo (com o formato): ");
            String nomeArquivo = scanner.nextLine();

            System.out.print("Digite o número do vértice: ");
            int vertice = scanner.nextInt();

            Grafo grafo = ArquivoComGrafo(nomeArquivo);

            if (vertice < 1 || vertice > grafo.numVertices) {
                System.out.println("Erro: O vértice informado está fora do intervalo válido");
            } else {
                grafo.exibirInformacoesVertice(vertice);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida! insira um número inteiro para o vértice.");
        } finally {
            scanner.close();
        }
    }
}
