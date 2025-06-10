# Paraleslimo
- São excuções feitas em paralelo

## Monothreaded
- São excuções feitas em sequencia
-  Exemplo:
   - 1º excução
   - 2º excução
   - 3º excução
   - 4º excução
- Apresentam menor desempenho

## Multithreaded
- São excuções feitas em paralelo, de forma que varias excuções são feitas ao mesmo tempo
- Exemplo:
   - 1º excução, 2º excução, 3º excução, 4º excução

### Paralelismo x Concorrência
- Paralelismo:
  - Excuções são feitas ao mesmo tempo compartilhando recursos
- Concorrência:
  - Excuções são feitas ao mesmo tempo, disputando o mesmo recurso


#### Thread
- Adiciona uma nova thread a aplicação

#### Runable
- Interface que define um método run()


#### Nova Thread
- Uma nova thread é criada ao executar o método start()

```
var runnable = new MyRunnable();
var thread = new Thread(runnable);

thread.start(); // Ele vai iniciar uma nova thread
```

> OBS: Uma thread depois de iniciada não pode ser iniciada novamente, caso isso ocorra, uma exceção será lançada. é necessário criar uma nova thread ou para a thread atual.

```
Thread t1 = new Thread(new MyRunnable());

// Errado: A thread já foi iniciada
t1.start(); // Inicia a thread
t1.start(); // Lança uma exceção


// Certo: Cria uma nova thread
t1.stop(); // Para a thread

t1.start(); // Inicia a thread novamente
```



## Synchronized
- Palavra reservada que permite que apenas uma thread execute um bloco de código

 ```
 // Ele ira executar o bloco de código apenas um de cada vez
 public static synchronized void run () {
   // Código
 }
 ```

 > OBS: O método run() é sincronizado, ou seja, apenas uma thread executa esse método por vez

> OBS: Por sua vez isso acaba com o paralelismo, pois apenas uma thread executa o método run()


 - Tambem podemos sincronizar um bloco de código

 ```
 public static void run () {
   synchronized (this) {
   // Código
 }
 }
```

> O Sincronizado é uma forma de sincronizar o acesso a um recurso compartilhado por várias threads.


## Não use o Synchronized
- Não use o Synchronized em coleções como List, Set, Map, etc.
  - Pois isso pode causar problemas de concorrência
  - Elas não são thread-safe, ou seja, não são preparadas para serem usadas em ambientes concorrentes
  - Em caso de lista podemos usar o syncronizedList

  ```
  List<String> list = Collections.synchronizedList(new ArrayList<>()); // Cria uma lista sincronizada(thread-safe)
  // Para adicionar um elemento na lista
  synchronized (list) {
    list.add("Elemento"); // o método add() é sincronizado
  }
  ```

  - Tambem existem as classes ConcurrentHashMap, ConcurrentSkipListMap, ConcurrentSkipListSet, etc.


### Collections para Threads
- CopyOnWriteArrayList
  - É uma lista thread-safe que permite a adição de elementos
  - É uma classe bem pesada, pois cria uma cópia da lista para adicionar um elemento
  - É uma boa opção para listas que não são muito grandes

- ConcurrentHashMap
  - É uma classe thread-safe que permite a adição e remoção de elementos.
  - Alternativa thread-safe para HashMap
  - Assm como todas as classes thread-safe, é menos perfomatica por precisar sincronizar o acesso aos dados

- LinkedBlockingQueue
  - É uma fila thread-safe que permite a adição e remoção de elementos
  - É uma boa opção para filas que não são muito grandes
  - é possivel definir o tamanho máximo da fila

  ``java
  LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(10);
  queue.add("Elemento"); // Adiciona um elemento na fila
  queue.remove(); // Remove um elemento da fila
  ```

- LinkedBlockingDeque
  - É uma fila thread-safe que permite a adição e remoção de elementos no inicio e no final da fila
  - É uma boa opção para filas que não são muito grandes

## Operações Atômicas
- Operações que são executadas de forma atômica, ou seja, não podem ser interrompidas

#### AtomicInteger
- É uma classe que permite a criação de um inteiro atômico
- É uma classe thread-safe

#### AtomicLong
- É uma classe que permite a criação de um long atômico
- É uma classe thread-safe

#### AtomicBoolean
- É uma classe que permite a criação de um boolean atômico
- É uma classe thread-safe


### CyclicBarrier
- É uma classe que permite a criação de uma barreira de sincronização
- É uma classe thread-safe
- É uma boa opção para sincronizar threads
- Uma thread ira experar pela outra thread
- É uma boa opção para sincronizar threads que precisam esperar por outras threads

```java
 Runnable r1 = () -> {
            System.out.println(
                    4323d * 3d);
            await(cyclicBarrier); // Aguarda a outra thread
        };

        Runnable r2 = () -> {
            System.out.println(
                    Math.pow(
                            3d, 20d));
            await(cyclicBarrier);
        };

        Runnable r3 = () -> {
            System.out.println(
                    4323d * 3d);
            await(cyclicBarrier);
        };
```


# Tambem consguimos colocar a execução principal em uma thread separada
```java
 Runnable finish = () -> {
        System.out.println("All threads finished");
        double sum = 0;
        sum += deque.poll();
        sum += deque.poll();
        sum += deque.poll();
        System.out.println("Sum is " + sum);
};

CyclicBarrier cyclicBarrier = new CyclicBarrier(3, finish); // Cria uma barreira de sincronização

// Assim podemos colocar a execução principal em uma thread separada

// Essa thread só sera executada quando todas as threads forem executadas
```
> Pode ser usado para gerar relatórios, apos todo o processamento do produto ele ira gerar um relatório.

### CountDownLatch
- É uma classe que permite a criação de uma barreira de sincronização


### Semáforo
- É uma classe que permite a criação de um semáforo!
- Limita a quantidade de Threads em uma execução!
- Exemplo:
  - Se tiver apenas 3 vagas
  - ⁠as 3 primeiras Threads que chegarem ao ponto do semana irão ser executadas, as outras irá esperar serem executadas
  - ⁠Conforme vai surgindo vagas, nossas Threads são liberadas.
  - ⁠Essa é uma forma de limitar a quantidade de Threads em uma execução

### Locks
- Similar ao synchronized
- Evita concorrencias entre Threads
- É uma forma de sincronizar o acesso a um recurso compartilhado por várias threads


### Syncronous Queue
- Cria uma lista syncrona
- Funcionamento: 
  - Podemos adicionar elementos na lista
  - POdemos pegar elementos da lista
  - mas só podemos adicionar ou pegar um elemento por vez
- Utilizado para executar tarefas de forma sincronizada
- Utlizado como evento.

### Exchange
- É uma classe que permite a troca de valores entre threads
- Diferente do Syncronous Queue, o Exchange ira retornar o valor trocado para producer.
- É uma boa opção para trocar valores entre threads

### CompletableFuture
- Similar ao async/await do javascript
- Executar tarefas em segundo plano
- Execução não bloqueadora
- É uma boa opção para executar tarefas em segundo plano
- Procesamento assíncrono
- Exemplo:
  - Busca em um banco de dados
  - Busca em um arquivo
  - Busca em uma API externa
- Podemos realizar outras tarefas enquanto a tarefa em segundo plano é executada
- Adiantar a execução de uma tarefa
- Adicionada na versão 8 do Java

```java
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");

// Podemos realizar outras tarefas enquanto a tarefa em segundo plano é executada

String result = future.get(); // Bloqueia até que a tarefa em segundo plano seja executada
```

- é possivel fazer uma cadeia de tarefas

```java
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello")
        .thenApply(s -> s + " World")
        .thenApply(s -> s + "!");
String result = future.get(); // Bloqueia até que a tarefa em segundo plano seja executada
```

#### CompletableFuture x Threads
- Deadlock
  - Thread 1:
  - Thread 1: Aguarda Thread 2
  - Thread 2:
  - Thread 2: Aguarda Thread 1


### Paralelismo JavaEE
- 