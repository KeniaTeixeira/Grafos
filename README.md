# Teoria dos Grafos e computabilidade – Implementações
Este repositório reúne quatro implementações fundamentais em Teoria dos Grafos, cada uma abordando um conceito central da disciplina.

Cada implementação está contida em um diretório específico, nomeado como `implementacao0N`.

## Índice de Implementações
### 📁 `implementacao01` – Representação de Grafos
Implementa a estrutura de representação de um grafo direcionado a partir de um arquivo de entrada. O programa constrói a estrutura em memória e realiza as seguintes operações para um vértice informado:
- Cálculo do grau de entrada e grau de saída.
- Identificação dos sucessores (vértices alcançáveis diretamente).
- Identificação dos predecessores (vértices que apontam para o vértice dado).

### 📁 `implementacao02` – Busca em Profundidade (DFS) com Classificação de Arestas
Executa uma busca em profundidade (DFS) lexicográfica sobre um grafo direcionado. A partir de um vértice inicial informado pelo usuário, o programa:
- Lista todas as arestas de árvore encontradas durante a DFS.
- Classifica todas as arestas que partem do vértice inicial (em árvore, de retorno, de avanço e cruzadas).

### 📁 `implementacao03` – Caminho Mínimo
Implementa um algoritmo para encontrar o caminho mínimo entre dois vértices em um grafo direcionado e ponderado (com pesos positivos). O algoritmo retorna:
- O comprimento total do menor caminho.
- O número de arestas no trajeto.
- A sequência completa de vértices percorridos.

### 📁 `implementacao04` – Fluxo Máximo com Caminhos Disjuntos
Utiliza o algoritmo de **Edmonds-Karp** (uma implementação em largura do método de Ford-Fulkerson) para encontrar o número máximo de caminhos disjuntos em arestas entre dois vértices de um grafo direcionado. O programa:
- Calcula o fluxo máximo entre os dois vértices.
- Lista todos os caminhos disjuntos encontrados (sem sobreposição de arestas).

---

## Entrada dos Dados
Todas as implementações utilizam arquivos de entrada com o seguinte formato:
- `n`: número de vértices (rotulados de 1 a n)
- `m`: número de arestas
- As pastas de cada implementação incluem exemplos de arquivos `.txt` contendo grafos para testes.

## Execução
Cada implementação pode ser executada por linha de comando, recebendo como argumentos:
1. O caminho do arquivo de entrada contendo o grafo.
2. Os vértices específicos necessários à tarefa (ex: origem, destino, ou apenas um vértice alvo).

---

**Desenvolvido como parte dos estudos em Teoria dos Grafos e Computabilidade - PUC Minas** 

