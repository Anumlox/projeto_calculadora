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
 * {@link #calcularComTratamento(int, int, String)} devolve sempre um texto,
 * garantindo que o programa nunca seja interrompido.
 * </p>
 *
 * @author Nicolas Gomes Ferreira
 * @version 2.0 (refatorada)
 */
public class Calculadora {

    // ===================== Operações suportadas =====================

    /** Símbolo da operação de soma. */
    public static final String SOMA = "+";

    /** Símbolo da operação de subtração. */
    public static final String SUBTRACAO = "-";

    /** Símbolo da operação de multiplicação. */
    public static final String MULTIPLICACAO = "*";

    /** Símbolo da operação de divisão. */
    public static final String DIVISAO = "/";

    // ===================== Mensagens de erro =====================

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

    // ===================== API pública =====================

    /**
     * Realiza a operação matemática informada entre dois números inteiros.
     *
     * @param primeiroNumero primeiro número (operando da esquerda)
     * @param segundoNumero  segundo número (operando da direita)
     * @param operacao       operação desejada: {@link #SOMA}, {@link #SUBTRACAO},
     *                       {@link #MULTIPLICACAO} ou {@link #DIVISAO}
     * @return resultado da operação como {@code double}
     *         (a divisão pode gerar casas decimais, ex.: 7 / 2 = 3.5)
     * @throws ArithmeticException      se a operação for divisão e
     *                                  {@code segundoNumero} for zero
     * @throws IllegalArgumentException se {@code operacao} for nula ou não
     *                                  for uma das operações suportadas
     */
    public double calc(int primeiroNumero, int segundoNumero, String operacao) {
        validarOperacao(operacao);

        // switch substitui a cadeia de if/else da versão anterior
        return switch (operacao) {
            case SOMA -> somar(primeiroNumero, segundoNumero);
            case SUBTRACAO -> subtrair(primeiroNumero, segundoNumero);
            case MULTIPLICACAO -> multiplicar(primeiroNumero, segundoNumero);
            case DIVISAO -> dividir(primeiroNumero, segundoNumero);
            default -> throw new IllegalArgumentException(MSG_OPERACAO_INVALIDA);
        };
    }

    /**
     * Versão com tratamento de erros do cálculo: nunca lança exceção.
     * <p>
     * Em caso de sucesso devolve a conta formatada (ex.: {@code "10 + 5 = 15"});
     * em caso de erro devolve a mensagem correspondente
     * ({@link #MSG_DIVISAO_POR_ZERO} ou {@link #MSG_OPERACAO_INVALIDA}).
     * </p>
     *
     * @param primeiroNumero primeiro número
     * @param segundoNumero  segundo número
     * @param operacao       operação desejada ({@code "+"}, {@code "-"}, {@code "*"} ou {@code "/"})
     * @return texto com o resultado ou com a mensagem de erro
     */
    public String calcularComTratamento(int primeiroNumero, int segundoNumero, String operacao) {
        try {
            double resultado = calc(primeiroNumero, segundoNumero, operacao);
            return montarExpressao(primeiroNumero, segundoNumero, operacao, resultado);
        } catch (ArithmeticException | IllegalArgumentException erro) {
            // Devolve a mensagem em vez de interromper o programa
            return erro.getMessage();
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
        boolean ehInteiro = valor == Math.rint(valor) && !Double.isInfinite(valor);
        return ehInteiro ? String.valueOf((long) valor) : String.valueOf(valor);
    }

    // ===================== Operações (métodos extraídos) =====================

    /**
     * Soma dois números.
     *
     * @param primeiroNumero primeira parcela
     * @param segundoNumero  segunda parcela
     * @return a soma, calculada em {@code double} para evitar estouro de {@code int}
     */
    private double somar(int primeiroNumero, int segundoNumero) {
        return (double) primeiroNumero + segundoNumero;
    }

    /**
     * Subtrai o segundo número do primeiro.
     *
     * @param primeiroNumero minuendo
     * @param segundoNumero  subtraendo
     * @return a diferença
     */
    private double subtrair(int primeiroNumero, int segundoNumero) {
        return (double) primeiroNumero - segundoNumero;
    }

    /**
     * Multiplica dois números.
     *
     * @param primeiroNumero primeiro fator
     * @param segundoNumero  segundo fator
     * @return o produto
     */
    private double multiplicar(int primeiroNumero, int segundoNumero) {
        return (double) primeiroNumero * segundoNumero;
    }

    /**
     * Divide o primeiro número pelo segundo, impedindo a divisão por zero.
     *
     * @param dividendo número a ser dividido
     * @param divisor   número pelo qual dividir
     * @return o quociente, com casas decimais
     * @throws ArithmeticException se {@code divisor} for zero
     */
    private double dividir(int dividendo, int divisor) {
        validarDivisor(divisor);
        return (double) dividendo / divisor;
    }

    // ===================== Validações =====================

    /**
     * Garante que a operação não seja nula.
     * Símbolos desconhecidos são rejeitados no {@code default} do switch.
     *
     * @param operacao operação a validar
     * @throws IllegalArgumentException se {@code operacao} for nula
     */
    private void validarOperacao(String operacao) {
        if (operacao == null) {
            throw new IllegalArgumentException(MSG_OPERACAO_INVALIDA);
        }
    }

    /**
     * Garante que o divisor não seja zero.
     *
     * @param divisor divisor a validar
     * @throws ArithmeticException se {@code divisor} for zero
     */
    private void validarDivisor(int divisor) {
        if (divisor == 0) {
            throw new ArithmeticException(MSG_DIVISAO_POR_ZERO);
        }
    }

    /**
     * Monta o texto da conta realizada (ex.: {@code "7 / 2 = 3.5"}).
     *
     * @param primeiroNumero primeiro número
     * @param segundoNumero  segundo número
     * @param operacao       operação realizada
     * @param resultado      resultado obtido
     * @return expressão formatada
     */
    private String montarExpressao(int primeiroNumero, int segundoNumero,
                                   String operacao, double resultado) {
        return primeiroNumero + " " + operacao + " " + segundoNumero + " = " + formatar(resultado);
    }
}
