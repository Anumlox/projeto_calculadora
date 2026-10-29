package calculadora;

public class Calculadora {

    public static final String MSG_DIVISAO_POR_ZERO =
            "Erro: não é possível dividir por zero.";

    public static final String MSG_OPERACAO_INVALIDA =
            "Erro: operação inválida. Use +, -, * ou /.";

    public Calculadora() {
        // Nenhuma inicialização necessária
    }

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

    public String calcSeguro(int a, int b, String op) {
        try {
            double r = calc(a, b, op);
            return a + " " + op + " " + b + " = " + formatar(r);
        } catch (ArithmeticException | IllegalArgumentException e) {
            // Captura o erro e devolve a mensagem em vez de interromper o programa
            return e.getMessage();
        }
    }

    public static String formatar(double valor) {
        if (valor == Math.rint(valor) && !Double.isInfinite(valor)) {
            return String.valueOf((long) valor);
        }
        return String.valueOf(valor);
    }
}
