package modelo;
public class Pareja {
    private int primerElemento;
    private int segundoElemento;

    public Pareja(int primerElemento, int segundoElemento) {
        this.primerElemento = primerElemento;
        this.segundoElemento = segundoElemento;
    }

    public int getPrimerElemento() {
        return primerElemento;
    }

    public int getSegundoElemento() {
        return segundoElemento;
    }

    @Override
    public String toString() {
        return "(" + primerElemento + ", " + segundoElemento + ")";
    }
}
