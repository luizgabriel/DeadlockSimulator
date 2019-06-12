package br.edu.ifce.deadlock.models;

public class ResourceInfo {
    private String name;
    private int totalQtd;
    private int currentQtd;

    public ResourceInfo(String name, int qtd) {
        this.name = name;
        this.totalQtd = qtd;
        this.currentQtd = qtd;
    }

    public String getName() {
        return name;
    }

    public int getTotalQtd() {
        return totalQtd;
    }
    public int getQtd() {
        return currentQtd;
    }

    public String getQtdText() {
        return getQtd() + "/" + getTotalQtd();
    }

    public void subtractQtd() {
        currentQtd--;
    }

    public void incrementQtd() {
        currentQtd++;
    }
}

