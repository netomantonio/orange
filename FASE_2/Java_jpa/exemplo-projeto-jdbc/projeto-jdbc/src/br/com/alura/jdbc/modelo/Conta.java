package br.com.alura.jdbc.modelo;

public class Conta {
	private Long id;
	private Long agencia;
	private Long numero;
	private String titular;

	public Conta(Long agencia, Long numero, String titular) {
		this.agencia = agencia;
		this.numero = numero;
		this.titular = titular;
	}

	public Long getId() {
		return id;
	}

	public Long getAgencia() {
		return agencia;
	}

	public Long getNumero() {
		return numero;
	}

	public String getTitular() {
		return titular;
	}

}
