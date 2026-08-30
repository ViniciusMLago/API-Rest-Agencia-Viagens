package com.example.demo.model;

public class Destino {
    private Long id;
    private String nome;
    private String localizacao;
    private String descricao;
    private double mediaAvaliacoes;
    private int quantidadeAvaliacoes;

    public Destino() {
        this.mediaAvaliacoes = 0.0;
        this.quantidadeAvaliacoes = 0;
    }

    public Destino(Long id, String nome, String localizacao, String descricao) {
        this.id = id;
        this.nome = nome;
        this.localizacao = localizacao;
        this.descricao = descricao;
        this.mediaAvaliacoes = 0.0;
        this.quantidadeAvaliacoes = 0;
    }

    // Método para recalcular a média ao receber nova nota
    public void adicionarAvaliacao(double nota) {
        double somaTotal = (this.mediaAvaliacoes * this.quantidadeAvaliacoes) + nota;
        this.quantidadeAvaliacoes++;
        this.mediaAvaliacoes = somaTotal / this.quantidadeAvaliacoes;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public double getMediaAvaliacoes() { return mediaAvaliacoes; }
    public int getQuantidadeAvaliacoes() { return quantidadeAvaliacoes; }
}