package calculadora;

// Calculadora basica do modulo de calculos da FinancApp
public class Calculadora {

    // Realiza a operacao op entre a e b
    public double calc(int a, int b, String op) {
        double r = 0;
        if (op.equals("+")) {
            r = (double) a + b;
        } else if (op.equals("-")) {
            r = (double) a - b;
        } else if (op.equals("*")) {
            r = (double) a * b;
        } else if (op.equals("/")) {
            r = (double) a / b;
        }
        return r;
    }
}
