# Memoria
- Memoria é o espaço de armazenamento de dados
- GBRam

![imagem_memoria](https://unicminds.com/wp-content/uploads/2022/09/StackvsHeap-Expalined-for-Kids-1024x576.png)

## Memoria Stack
- memoria menor e mais rápida
- Memoria Stack é a memória que armazena os dados de uma aplicação
- É uma memória que é criada quando a aplicação é iniciada e é destruída quando a aplicação é finalizada
- Pemite que uma funnção seja executada e depois destruída

### Processo de alocação de memória

#### Código

```java
public class Main {
    public static void main(String[] args) {
    printHelloWorld();
    }

    private static void printHelloWorld() {
        System.out.println("Hello world!");
    }
}
```
1. sempre que uma função é chamada, um frame de ativação é criado na memória Stack.
2. o frame de ativação é responsável por armazenar os dados da função
3. quando a função é finalizada, o frame de ativação é destruído

> Todos os dados que são criados dentro de uma função são criados na pilha de execução(Stack)

## Memoria Heap
- memoria maior e mais lenta
- Memoria Heap é a memória que armazena os dados de uma aplicação
- aloca objetos que vão permanecer vivos na aplicaçõa por mais tempo
- Garbagge Collector é o responsável por liberar a memória Heap
- Desalocação deterministicas
- Armazena objetos que vão permanecer vivos na aplicação por mais tempo


```java
public class Main {
    public static void main(String[] args) {
        Object obj = new Object(); // Alocação de memória Heap
        System.out.println(obj);
    }
}
```

> Todos os dados que são criados fora de uma função são criados na memória Heap

Claro! Abaixo está a **mesma explicação dos principais Garbage Collectors em Java**, escrita em **formato Markdown**, focando no uso a partir do **Java 17**.

---

# 🧹 Tipos de Garbage Collector no Java (Java 17+)

> Java 17 é uma versão LTS (Long-Term Support), e os principais Garbage Collectors disponíveis são: **Serial**, **Parallel**, **G1**, **ZGC**, e **Shenandoah**.

---

## 1. 🔸 Serial GC (`UseSerialGC`)

### 📘 Descrição:

Garbage Collector simples e monothread, ideal para ambientes com pouca memória ou aplicações simples.

### ⚙️ Funcionamento:

* Utiliza **uma única thread**.
* Coleta toda a memória com pausas completas (**Stop-The-World**).

### 🚀 Como usar:

```bash
java -XX:+UseSerialGC -jar app.jar
```

### ✅ Vantagens:

* Simples e previsível.
* Útil para aplicações pequenas ou embarcadas.

### ⚠️ Desvantagens:

* **Ineficiente** em múltiplos núcleos.
* Longas pausas com heaps maiores.

---

## 2. 🔸 Parallel GC (`UseParallelGC`)

### 📘 Descrição:

Coletor paralelo projetado para **alta taxa de transferência** (throughput).

### ⚙️ Funcionamento:

* Utiliza **múltiplas threads** para coleta.
* Pausas `Stop-The-World`, mas mais rápidas.

### 🚀 Como usar:

```bash
java -XX:+UseParallelGC -jar app.jar
```

### ✅ Vantagens:

* Alta performance em ambientes com múltiplos núcleos.
* Bom para tarefas em lote (batch).

### ⚠️ Desvantagens:

* Pausas ainda podem ser perceptíveis.
* Não é focado em latência.

---

## 3. 🔸 G1 GC (`UseG1GC`) - **Padrão desde Java 9**

### 📘 Descrição:

Coletor moderno que busca **equilíbrio entre throughput e baixa latência**.

### ⚙️ Funcionamento:

* Divide o heap em **pequenas regiões**.
* Coleta incremental e paralela.
* Configurável por pausa máxima.

### 🚀 Como usar:

```bash
java -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -jar app.jar
```

### ✅ Vantagens:

* Baixa latência com bom desempenho.
* Ideal para aplicações web, APIs REST.

### ⚠️ Desvantagens:

* Mais complexo.
* Overhead um pouco maior que ParallelGC.

---

## 4. 🔸 ZGC (`UseZGC`) - Java 17+

### 📘 Descrição:

Garbage Collector de **latência ultra-baixa**, ideal para heaps grandes (até 16 TB).

### ⚙️ Funcionamento:

* Quase toda coleta é **concorrente**.
* Pausas sempre <10ms.
* Usa **barreiras de leitura/gravação**.

### 🚀 Como usar:

```bash
java -XX:+UseZGC -jar app.jar
```

### ✅ Vantagens:

* Pausas mínimas.
* Escala muito bem com memória e núcleos.

### ⚠️ Desvantagens:

* Pode consumir mais CPU.
* Ainda evoluindo; não compactava até o Java 15.

---

## 5. 🔸 Shenandoah GC (`UseShenandoahGC`) - Java 17 OpenJDK (Red Hat)

### 📘 Descrição:

Coletor de baixa latência da Red Hat, similar ao ZGC, mas com **compactação concorrente**.

### ⚙️ Funcionamento:

* Quase toda a coleta é feita sem pausas.
* Compactação feita **sem stop-the-world**.

### 🚀 Como usar:

```bash
java -XX:+UseShenandoahGC -jar app.jar
```

> ❗ Nem todas as distribuições de Java 17 vêm com Shenandoah habilitado.

### ✅ Vantagens:

* Latência muito baixa.
* Compactação sem pausa.

### ⚠️ Desvantagens:

* Requer versão específica do OpenJDK.
* Mais sensível à configuração.

---

## 🔚 Resumo Comparativo

| GC             | Melhor para                | Pausas curtas | Escalável | Java 17     |
| -------------- | -------------------------- | ------------- | --------- | ----------- |
| `SerialGC`     | Apps simples ou embarcados | ❌             | ❌         | ✅           |
| `ParallelGC`   | Lote/batch                 | ❌             | ✅         | ✅           |
| `G1GC`         | Web/API/geral              | ✅ (mod.)      | ✅         | ✅ (padrão)  |
| `ZGC`          | Tempo real, heaps grandes  | ✅✅            | ✅✅        | ✅           |
| `ShenandoahGC` | Interativo, baixa latência | ✅✅            | ✅✅        | ✅ (OpenJDK) |

---
