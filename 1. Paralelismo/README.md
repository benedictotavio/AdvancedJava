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


 - Tambem podemos sincronizar um bloco de código

 ```
 public static void run () {
   synchronized (this) {
   // Código
 }
 }
```