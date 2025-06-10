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