package calculadora;

/**
 * Módulo básico de cálculos matemáticos da FinançApp.
 * <p>
 * Esta classe é usada por diferentes funcionalidades do aplicativo
 * (orçamento mensal, divisão de despesas, projeção de economia etc.)
 * e realiza as quatro operações básicas: soma, subtração,
 * multiplicação e divisão.
 * </p>
 * <p>
 * Situações de erro (divisão por zero e operação inválida) são
 * sinalizadas por exceções no método {@link #calc(int, int, String)}.
 * Para quem não quer lidar com exceções, o método
 * {@link #calcSeguro(int, int, String)} devolve sempre um texto,
 * garantindo que o programa nunca seja interrompido.
 * </p>
 *
 * @author Nicolas Gomes Ferreira
 * @version 1.0
 */
public class Calculadora {

    /** Mensagem exibida quando há tentativa de divisão por zero. */
    public static final String MSG_DIVISAO_POR_ZERO =
            "Erro: não é possível dividir por zero.";

    /** Mensagem exibida quando a operação informada não é reconhecida. */
    public static final String MSG_OPERACAO_INVALIDA =
            "Erro: operação inválida. Use +, -, * ou /.";

    /**
     * Cria uma nova calculadora. A classe não guarda estado entre
     * os cálculos, então uma mesma instância pode ser reutilizada.
     */
    public Calculadora() {
        // Nenhuma inicialização necessária
    }

    /**
     * Realiza a operação matemática informada entre dois números inteiros.
     *
     * @param a  primeiro número (operando da esquerda)
     * @param b  segundo número (operando da direita)
     * @param op operação desejada: {@code "+"}, {@code "-"},
     *           {@code "*"} ou {@code "/"}
     * @return resultado da operação como {@code double}
     *         (a divisão pode gerar casas decimais, ex.: 7 / 2 = 3.5)
     * @throws ArithmeticException      se {@code op} for {@code "/"} e
     *                                  {@code b} for zero
     * @throws IllegalArgumentException se {@code op} for nula ou não for
     *                                  uma das operações suportadas
     */
    public double calc(int a, int b, String op) {
        double r = 0;

        // Operação nula não pode ser comparada: trata como inválida
        if (op == null) {
            throw new IllegalArgumentException(MSG_OPERACAO_INVALIDA);
        }

        // Converte para double antes de operar para evitar estouro de int
        if (op.equals("+")) {
            r = (double) a + b;
        } else if (op.equals("-")) {
            r = (double) a - b;
        } else if (op.equals("*")) {
            r = (double) a * b;
        } else if (op.equals("/")) {
            // Impede a divisão por zero
            if (b == 0) {
                throw new ArithmeticException(MSG_DIVISAO_POR_ZERO);
            } else {
                r = (double) a / b;
            }
        } else {
            // Qualquer símbolo diferente de + - * / é rejeitado
            throw new IllegalArgumentException(MSG_OPERACAO_INVALIDA);
        }
        return r;
    }

    /**
     * Versão "segura" do cálculo: nunca lança exceção.
     * <p>
     * Em caso de sucesso devolve a conta formatada (ex.: {@code "10 + 5 = 15"});
     * em caso de erro devolve a mensagem correspondente
     * ({@link #MSG_DIVISAO_POR_ZERO} ou {@link #MSG_OPERACAO_INVALIDA}).
     * </p>
     *
     * @param a  primeiro número
     * @param b  segundo número
     * @param op operação desejada ({@code "+"}, {@code "-"}, {@code "*"} ou {@code "/"})
     * @return texto com o resultado ou com a mensagem de erro
     */
    public String calcSeguro(int a, int b, String op) {
        try {
            double r = calc(a, b, op);
            return a + " " + op + " " + b + " = " + formatar(r);
        } catch (ArithmeticException | IllegalArgumentException e) {
            // Captura o erro e devolve a mensagem em vez de interromper o programa
            return e.getMessage();
        }
    }

    /**
     * Formata um número removendo o ".0" de resultados inteiros
     * (ex.: 15.0 vira "15", mas 3.5 continua "3.5").
     *
     * @param valor número a ser formatado
     * @return o número formatado como texto
     */
    public static String formatar(double valor) {
        if (valor == Math.rint(valor) && !Double.isInfinite(valor)) {
            return String.valueOf((long) valor);
        }
        return String.valueOf(valor);
    }
}
