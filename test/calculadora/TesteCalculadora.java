package calculadora;

/**
 * Testes unitários da classe {@link Calculadora}.
 * <p>
 * Cada teste mostra no console as <b>entradas utilizadas</b>, o
 * <b>resultado esperado</b> e o <b>resultado obtido</b>, além do status
 * (PASSOU / FALHOU). Ao final é exibido um resumo. Se algum teste falhar,
 * o programa termina com código de saída 1.
 * </p>
 * <p>
 * Os testes não dependem de bibliotecas externas: basta compilar e
 * executar o método {@link #main(String[])}.
 * </p>
 *
 * @author Nicolas Gomes Ferreira
 * @version 2.0 (refatorada)
 */
public class TesteCalculadora {

    /** Instância da calculadora que está sendo testada. */
    private static final Calculadora calculadora = new Calculadora();

    /** Quantidade de testes executados. */
    private static int testesExecutados = 0;

    /** Quantidade de testes que passaram. */
    private static int testesAprovados = 0;

    /** Construtor privado: classe de testes, não deve ser instanciada. */
    private TesteCalculadora() {
    }

    /**
     * Executa todos os testes e exibe o resumo.
     *
     * @param args argumentos de linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  TESTES UNITÁRIOS - Calculadora (FinançApp)");
        System.out.println("==================================================");

        // ---------- SOMA ----------
        secao("SOMA");
        testarResultado("Soma de dois positivos", 10, 5, "+", 15);
        testarResultado("Soma de dois negativos", -4, -6, "+", -10);
        testarResultado("Soma com zero", 7, 0, "+", 7);

        // ---------- SUBTRAÇÃO ----------
        secao("SUBTRAÇÃO");
        testarResultado("Subtração simples", 10, 5, "-", 5);
        testarResultado("Subtração com resultado negativo", 3, 8, "-", -5);

        // ---------- MULTIPLICAÇÃO ----------
        secao("MULTIPLICAÇÃO");
        testarResultado("Multiplicação de positivos", 6, 7, "*", 42);
        testarResultado("Multiplicação com negativo", -3, 4, "*", -12);
        testarResultado("Multiplicação por zero", 99, 0, "*", 0);

        // ---------- DIVISÃO ----------
        secao("DIVISÃO");
        testarResultado("Divisão exata", 10, 2, "/", 5);
        testarResultado("Divisão com casas decimais", 7, 2, "/", 3.5);
        testarResultado("Divisão com negativo", -9, 3, "/", -3);

        // ---------- DIVISÃO POR ZERO ----------
        secao("DIVISÃO POR ZERO");
        testarErro("Divisão por zero lança ArithmeticException", 10, 0, "/",
                ArithmeticException.class, Calculadora.MSG_DIVISAO_POR_ZERO);
        testarMensagemSegura("Divisão por zero não interrompe o programa", 10, 0, "/",
                Calculadora.MSG_DIVISAO_POR_ZERO);

        // ---------- OPERAÇÕES INVÁLIDAS ----------
        secao("OPERAÇÕES INVÁLIDAS");
        testarErro("Operação '%' é inválida", 10, 5, "%",
                IllegalArgumentException.class, Calculadora.MSG_OPERACAO_INVALIDA);
        testarErro("Operação vazia é inválida", 10, 5, "",
                IllegalArgumentException.class, Calculadora.MSG_OPERACAO_INVALIDA);
        testarErro("Operação nula é inválida", 10, 5, null,
                IllegalArgumentException.class, Calculadora.MSG_OPERACAO_INVALIDA);
        testarMensagemSegura("Operação inválida não interrompe o programa", 10, 5, "x",
                Calculadora.MSG_OPERACAO_INVALIDA);

        // ---------- RESUMO ----------
        imprimirResumo();

        if (!todosAprovados()) {
            System.exit(1);
        }
    }

    /**
     * Exibe quantos testes foram executados e quantos passaram.
     */
    private static void imprimirResumo() {
        System.out.println("\n==================================================");
        System.out.println("  RESUMO: " + testesAprovados + " de " + testesExecutados + " testes passaram"
                + (todosAprovados() ? "  ✔" : "  ✘"));
        System.out.println("==================================================");
    }

    /**
     * Indica se todos os testes executados foram aprovados.
     *
     * @return {@code true} se nenhum teste falhou
     */
    private static boolean todosAprovados() {
        return testesAprovados == testesExecutados;
    }

    /**
     * Imprime o título de um grupo de testes.
     *
     * @param titulo nome do grupo (ex.: "SOMA")
     */
    private static void secao(String titulo) {
        System.out.println("\n--- " + titulo + " ---");
    }

    /**
     * Testa uma operação que deve retornar um valor numérico.
     *
     * @param descricao descrição do teste
     * @param a         primeiro número
     * @param b         segundo número
     * @param op        operação
     * @param esperado  resultado esperado
     */
    private static void testarResultado(String descricao, int a, int b, String op, double esperado) {
        String obtido;
        boolean aprovado;
        try {
            double resultado = calculadora.calc(a, b, op);
            obtido = String.valueOf(resultado);
            aprovado = Double.compare(resultado, esperado) == 0;
        } catch (RuntimeException e) {
            // Uma exceção aqui significa que o teste falhou
            obtido = e.getClass().getSimpleName() + ": " + e.getMessage();
            aprovado = false;
        }
        registrar(descricao, a, b, op, String.valueOf(esperado), obtido, aprovado);
    }

    /**
     * Testa uma operação que deve lançar uma exceção com a mensagem informada.
     *
     * @param descricao       descrição do teste
     * @param a               primeiro número
     * @param b               segundo número
     * @param op              operação
     * @param tipoEsperado    classe da exceção esperada
     * @param mensagemEsperada mensagem esperada na exceção
     */
    private static void testarErro(String descricao, int a, int b, String op,
                                   Class<? extends RuntimeException> tipoEsperado,
                                   String mensagemEsperada) {
        String esperado = tipoEsperado.getSimpleName() + ": " + mensagemEsperada;
        String obtido;
        boolean aprovado;
        try {
            double resultado = calculadora.calc(a, b, op);
            obtido = "nenhuma exceção (retornou " + resultado + ")";
            aprovado = false;
        } catch (RuntimeException e) {
            obtido = e.getClass().getSimpleName() + ": " + e.getMessage();
            aprovado = tipoEsperado.isInstance(e) && mensagemEsperada.equals(e.getMessage());
        }
        registrar(descricao, a, b, op, esperado, obtido, aprovado);
    }

    /**
     * Testa o método {@link Calculadora#calcularComTratamento(int, int, String)}, que deve
     * devolver a mensagem de erro sem lançar exceção.
     *
     * @param descricao        descrição do teste
     * @param a                primeiro número
     * @param b                segundo número
     * @param op               operação
     * @param mensagemEsperada mensagem que deve ser devolvida
     */
    private static void testarMensagemSegura(String descricao, int a, int b, String op,
                                             String mensagemEsperada) {
        String obtido;
        boolean aprovado;
        try {
            obtido = calculadora.calcularComTratamento(a, b, op);
            aprovado = mensagemEsperada.equals(obtido);
        } catch (RuntimeException e) {
            // Se chegou aqui, o programa teria sido interrompido
            obtido = "exceção lançada: " + e;
            aprovado = false;
        }
        registrar(descricao, a, b, op, mensagemEsperada, obtido, aprovado);
    }

    /**
     * Imprime o resultado de um teste e atualiza os contadores.
     *
     * @param descricao descrição do teste
     * @param a         primeiro número
     * @param b         segundo número
     * @param op        operação
     * @param esperado  resultado esperado (texto)
     * @param obtido    resultado obtido (texto)
     * @param aprovado  {@code true} se o teste passou
     */
    private static void registrar(String descricao, int a, int b, String op,
                                  String esperado, String obtido, boolean aprovado) {
        testesExecutados++;
        if (aprovado) {
            testesAprovados++;
        }
        String opTexto = (op == null) ? "null" : "\"" + op + "\"";
        System.out.printf("[%02d] %s%n", testesExecutados, descricao);
        System.out.println("     Entradas ......... a = " + a + ", b = " + b + ", op = " + opTexto);
        System.out.println("     Resultado esperado " + esperado);
        System.out.println("     Resultado obtido . " + obtido);
        System.out.println("     Status ........... " + (aprovado ? "PASSOU ✔" : "FALHOU ✘"));
    }
}
