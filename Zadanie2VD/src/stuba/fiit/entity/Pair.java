package stuba.fiit.entity;

public class Pair<X,Y> {
    private X xElement;
    private Y yElement;

    public Pair(X xElement, Y yElement) {
        this.xElement = xElement;
        this.yElement = yElement;
    }

    public X getxElement() {
        return xElement;
    }

    public void setxElement(X xElement) {
        this.xElement = xElement;
    }

    public Y getyElement() {
        return yElement;
    }

    public void setyElement(Y yElement) {
        this.yElement = yElement;
    }
}
