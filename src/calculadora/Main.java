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
 * @version 2.0 (refatorada)
 */
public class Main {

    /** Comando digitado pelo usuário para encerrar o programa. */
    private static final String COMANDO_SAIR = "s";

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

        try (Scanner leitor = new Scanner(System.in)) {
            exibirCabecalho();

            while (true) {
                String primeiroNumero = lerEntrada(leitor, "\nPrimeiro número: ");
                if (primeiroNumero == null || primeiroNumero.equalsIgnoreCase(COMANDO_SAIR)) {
                    break;
                }

                String operacao = lerEntrada(leitor, "Operação (+ - * /): ");
                String segundoNumero = lerEntrada(leitor, "Segundo número: ");
                if (operacao == null || segundoNumero == null) {
                    break;
                }

                System.out.println(">> " + processar(calculadora, primeiroNumero, segundoNumero, operacao));
            }
        }

        System.out.println("Calculadora encerrada.");
    }

    /**
     * Mostra o título e as instruções de uso.
     */
    private static void exibirCabecalho() {
        System.out.println("=== FinançApp - Calculadora ===");
        System.out.println("Operações: +  -  *  /   (digite 's' para sair)");
    }

    /**
     * Exibe uma mensagem e lê uma linha digitada pelo usuário.
     *
     * @param leitor   scanner de onde a linha será lida
     * @param mensagem texto exibido antes da leitura
     * @return a linha digitada sem espaços nas pontas, ou {@code null}
     *         se não houver mais entrada disponível
     */
    private static String lerEntrada(Scanner leitor, String mensagem) {
        System.out.print(mensagem);
        return leitor.hasNextLine() ? leitor.nextLine().trim() : null;
    }

    /**
     * Converte as entradas em números e realiza o cálculo, sem nunca
     * interromper o programa.
     *
     * @param calculadora    calculadora usada no cálculo
     * @param primeiroNumero texto do primeiro número
     * @param segundoNumero  texto do segundo número
     * @param operacao       operação desejada
     * @return o resultado formatado ou uma mensagem de erro
     */
    private static String processar(Calculadora calculadora, String primeiroNumero,
                                    String segundoNumero, String operacao) {
        try {
            int a = Integer.parseInt(primeiroNumero);
            int b = Integer.parseInt(segundoNumero);
            return calculadora.calcularComTratamento(a, b, operacao);
        } catch (NumberFormatException e) {
            // Entrada não numérica: avisa sem encerrar o programa
            return "Erro: digite apenas números inteiros.";
        }
    }
}
