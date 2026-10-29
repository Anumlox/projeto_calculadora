package calculadora;

import java.util.Scanner;

/**
 * Programa interativo (modo console) para usar a {@link Calculadora}.
 * <p>
 * O usuário informa dois números inteiros e a operação. Entradas
 * inválidas (texto no lugar de número, divisão por zero, operação
 * desconhecida) geram uma mensagem e o programa continua rodando.
 * Para sair, digite {@code s} quando for pedido o primeiro número.
 * </p>
 *
 * @author Nicolas Gomes Ferreira
 * @version 1.0
 */
public class Main {

    /** Construtor privado: classe utilitária, não deve ser instanciada. */
    private Main() {
    }

    /**
     * Ponto de entrada do programa.
     *
     * @param args argumentos de linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        Calculadora calculadora = new Calculadora();
        Scanner sc = new Scanner(System.in);

        System.out.println("=== FinançApp - Calculadora ===");
        System.out.println("Operações: +  -  *  /   (digite 's' para sair)");

        while (true) {
            System.out.print("\nPrimeiro número: ");
            if (!sc.hasNextLine()) {
                break;
            }
            String entradaA = sc.nextLine().trim();
            if (entradaA.equalsIgnoreCase("s")) {
                break;
            }

            System.out.print("Operação (+ - * /): ");
            if (!sc.hasNextLine()) {
                break;
            }
            String op = sc.nextLine().trim();

            System.out.print("Segundo número: ");
            if (!sc.hasNextLine()) {
                break;
            }
            String entradaB = sc.nextLine().trim();

            try {
                int a = Integer.parseInt(entradaA);
                int b = Integer.parseInt(entradaB);
                // calcSeguro nunca lança exceção: devolve resultado ou mensagem de erro
                System.out.println(">> " + calculadora.calcSeguro(a, b, op));
            } catch (NumberFormatException e) {
                // Entrada não numérica: avisa e segue o loop sem encerrar o programa
                System.out.println(">> Erro: digite apenas números inteiros.");
            }
        }

        System.out.println("Calculadora encerrada.");
        sc.close();
    }
}
