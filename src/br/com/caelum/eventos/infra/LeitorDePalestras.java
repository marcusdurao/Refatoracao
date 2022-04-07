package br.com.caelum.eventos.infra;

import static br.com.caelum.eventos.dominio.TempoDeDuracao.LIGHTING_STRING;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import br.com.caelum.eventos.dominio.ListaDePalestras;
import br.com.caelum.eventos.dominio.Palestra;
import br.com.caelum.eventos.dominio.TempoDeDuracao;

public class LeitorDePalestras {

	private final File arquivoDePalestras;
	
	public LeitorDePalestras(File arquivoDePalestras) {
		this.arquivoDePalestras = arquivoDePalestras;
	}

	public ListaDePalestras lerPalestras() throws FileNotFoundException {
		Set<Palestra> ret = new HashSet<>();
		try(Scanner scanner = new Scanner(arquivoDePalestras)){
			while(scanner.hasNextLine()){
				Palestra palestra = lerPalestra(scanner);
				ret.add(palestra);
			}
		}
		return new ListaDePalestras(ret);
	}

	//UTILIZANDO A TECNICA DE REFATORACAO - Internalizar
	private Palestra lerPalestra(Scanner scanner) {
		String linha = scanner.nextLine();
		int indiceDaDivisaoEntreNomeETempo = linha.lastIndexOf(' ');

		//##################################################
		//VAMOS SUBISTUTIR AS VARIÁVEIS POR MÉTODOS
		//1) UTILIZAR EXTRAÇÃO DE MÉTODO (getNomePalestra, getDuracaoPalestra)
		//2) INTERNALIZAR AS VARIAVES (Nome e tempoString)
		//#################################################

		TempoDeDuracao duracao = LIGHTING_STRING.contains(getDuraoPalestra(linha, indiceDaDivisaoEntreNomeETempo))
				? TempoDeDuracao.LIGHTING
				: new TempoDeDuracao(Integer.valueOf(getDuraoPalestra(linha, indiceDaDivisaoEntreNomeETempo)));

		return new Palestra(getNomePalestra(linha, indiceDaDivisaoEntreNomeETempo), duracao);

		//		String nome = linha.substring(0, indiceDaDivisaoEntreNomeETempo);
		//		String tempoString = linha.substring(indiceDaDivisaoEntreNomeETempo + 1, linha.length() - 3);
		//		TempoDeDuracao duracao = LIGHTING_STRING.contains(tempoString)
		//				? TempoDeDuracao.LIGHTING
		//				: new TempoDeDuracao(Integer.valueOf(tempoString));
		//		return new Palestra(nome, duracao);
	}

	private String getDuraoPalestra(String linha, int indiceDaDivisaoEntreNomeETempo) {
		return linha.substring(indiceDaDivisaoEntreNomeETempo + 1, linha.length() - 3);
	}

	private String getNomePalestra(String linha, int indiceDaDivisaoEntreNomeETempo) {
		return linha.substring(0, indiceDaDivisaoEntreNomeETempo);
	}

	//########################################
	//METODO NÃO INTERNALIZADO
	//FOI CRIADODO 2 MÉTODOS PRIVADOS
	//LINHA 54 E 58
	//########################################
	//	private Palestra lerPalestra(Scanner scanner) {
	//		String linha = scanner.nextLine();
	//		return lerPalestra(linha);
	//	}

	//	private Palestra lerPalestra(String linha){
	//		int indiceDaDivisaoEntreNomeETempo = linha.lastIndexOf(' ');
	//
	//		String nome = linha.substring(0, indiceDaDivisaoEntreNomeETempo);
	//		String tempoString = linha.substring(indiceDaDivisaoEntreNomeETempo + 1, linha.length() - 3);
	//		TempoDeDuracao duracao = LIGHTING_STRING.contains(tempoString)
	//				? TempoDeDuracao.LIGHTING
	//				: new TempoDeDuracao(Integer.valueOf(tempoString));
	//
	//		return new Palestra(nome, duracao);
	//	}

}
