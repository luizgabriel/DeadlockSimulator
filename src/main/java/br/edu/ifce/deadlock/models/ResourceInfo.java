package br.edu.ifce.deadlock.models;

public class ResourceInfo {
    private String name;
    private int qtd;

    public ResourceInfo(String name, int qtd) {
        this.name = name;
        this.qtd = qtd;
    }

    public String getName() {
        return name;
    }

    public int getQtd() {
        return qtd;
    }

    public void subtractQtd() {
        qtd--;
    }

    public void incrementQtd() {
        qtd++;
    }
}

