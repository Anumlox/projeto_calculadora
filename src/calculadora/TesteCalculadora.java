package calculadora;

public class TesteCalculadora {

    private static final Calculadora calc = new Calculadora();

    private static int total = 0;

    private static int passou = 0;

    private TesteCalculadora() {
    }

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
        System.out.println("\n==================================================");
        System.out.println("  RESUMO: " + passou + " de " + total + " testes passaram"
                + (passou == total ? "  ✔" : "  ✘"));
        System.out.println("==================================================");

        if (passou != total) {
            System.exit(1);
        }
    }

    private static void secao(String titulo) {
        System.out.println("\n--- " + titulo + " ---");
    }

    private static void testarResultado(String descricao, int a, int b, String op, double esperado) {
        String obtido;
        boolean ok;
        try {
            double r = calc.calc(a, b, op);
            obtido = String.valueOf(r);
            ok = Double.compare(r, esperado) == 0;
        } catch (RuntimeException e) {
            // Uma exceção aqui significa que o teste falhou
            obtido = e.getClass().getSimpleName() + ": " + e.getMessage();
            ok = false;
        }
        registrar(descricao, a, b, op, String.valueOf(esperado), obtido, ok);
    }

    private static void testarErro(String descricao, int a, int b, String op,
                                   Class<? extends RuntimeException> tipoEsperado,
                                   String mensagemEsperada) {
        String esperado = tipoEsperado.getSimpleName() + ": " + mensagemEsperada;
        String obtido;
        boolean ok;
        try {
            double r = calc.calc(a, b, op);
            obtido = "nenhuma exceção (retornou " + r + ")";
            ok = false;
        } catch (RuntimeException e) {
            obtido = e.getClass().getSimpleName() + ": " + e.getMessage();
            ok = tipoEsperado.isInstance(e) && mensagemEsperada.equals(e.getMessage());
        }
        registrar(descricao, a, b, op, esperado, obtido, ok);
    }

    private static void testarMensagemSegura(String descricao, int a, int b, String op,
                                             String mensagemEsperada) {
        String obtido;
        boolean ok;
        try {
            obtido = calc.calcSeguro(a, b, op);
            ok = mensagemEsperada.equals(obtido);
        } catch (RuntimeException e) {
            // Se chegou aqui, o programa teria sido interrompido
            obtido = "exceção lançada: " + e;
            ok = false;
        }
        registrar(descricao, a, b, op, mensagemEsperada, obtido, ok);
    }

    private static void registrar(String descricao, int a, int b, String op,
                                  String esperado, String obtido, boolean ok) {
        total++;
        if (ok) {
            passou++;
        }
        String opTexto = (op == null) ? "null" : "\"" + op + "\"";
        System.out.printf("[%02d] %s%n", total, descricao);
        System.out.println("     Entradas ......... a = " + a + ", b = " + b + ", op = " + opTexto);
        System.out.println("     Resultado esperado " + esperado);
        System.out.println("     Resultado obtido . " + obtido);
        System.out.println("     Status ........... " + (ok ? "PASSOU ✔" : "FALHOU ✘"));
    }
}
