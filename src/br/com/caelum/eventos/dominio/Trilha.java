package br.com.caelum.eventos.dominio;


import static br.com.caelum.eventos.dominio.Atividade.ALMOCO;
import static br.com.caelum.eventos.dominio.Atividade.NETWORKING;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Trilha {
	
	private final String nome;
	private final List<Atividade> atividadesAgendadas;

	//##############################################
	//UTILIZANDO A TECNICA DE REFATORACAO - EXTRACT METHOD
	//##############################################
	public Trilha(String nome, SessaoDaManha sessaoDaManha, SessaoDaTarde sessaoDaTarde){
		this.nome = nome;
		atividadesAgendadas = new ArrayList<>();

		LocalTime horarioCalculado = sessaoDaManha.comecaAs();
		for(Palestra palestra : sessaoDaManha){
			agendarPalestra(horarioCalculado, palestra);
			horarioCalculado = horarioCalculado.plusMinutes(palestra.lerTempoDeDuracao().toInt());
		}
		agendarAlmoco();
		
		horarioCalculado = sessaoDaTarde.comecaAs();
		for(Palestra palestra : sessaoDaTarde){
			agendarPalestra(horarioCalculado, palestra);
			//######################################
			//Internalizar variável temporária
			//######################################
			//int tempoDeDuracaoEmMinutos = palestra.lerTempoDeDuracao().toInt();
			//horarioCalculado = horarioCalculado.plusMinutes(tempoDeDuracaoEmMinutos);

			horarioCalculado = horarioCalculado.plusMinutes(palestra.lerTempoDeDuracao().toInt());
		}
		agendarNetworking();
	}

	private void agendarPalestra(LocalTime horarioCalculado, Palestra palestra) {
		palestra.agendarPara(horarioCalculado);
		atividadesAgendadas.add(palestra);
	}

	//##############################################
	//Método não extraido
	//##############################################
	//	public Trilha(String nome, SessaoDaManha sessaoDaManha, SessaoDaTarde sessaoDaTarde){
	//		this.nome = nome;
	//		atividadesAgendadas = new ArrayList<>();

	//#############################################
	// Código repindo nas linhas 54,55 e 62,63
	//#############################################
	//		LocalTime horarioCalculado = sessaoDaManha.comecaAs();
	//		for(Palestra palestra : sessaoDaManha){
	//			palestra.agendarPara(horarioCalculado);
	//			atividadesAgendadas.add(palestra);
	//			horarioCalculado = horarioCalculado.plusMinutes(palestra.lerTempoDeDuracao().toInt());
	//		}
	//		agendarAlmoco();

	//		horarioCalculado = sessaoDaTarde.comecaAs();
	//		for(Palestra palestra : sessaoDaTarde){
	//			palestra.agendarPara(horarioCalculado);
	//			atividadesAgendadas.add(palestra);
	//			int tempoDeDuracaoEmMinutos = palestra.lerTempoDeDuracao().toInt();
	//			horarioCalculado = horarioCalculado.plusMinutes(tempoDeDuracaoEmMinutos);
	//		}
	//		agendarNetworking();
	//	}


	private void agendarNetworking() {
		atividadesAgendadas.add(NETWORKING);
	}

	private void agendarAlmoco(){
		atividadesAgendadas.add(ALMOCO);
	}
	
	public String nome(){
		return "Trilha " + nome;
	}

	public List<Atividade> lerAtividadesAgendadasEmOrdem() {
		return atividadesAgendadas;
	}
}
