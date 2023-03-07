package med.voll.api.domain.validacoes;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.dto.DadosAgendamentoConsulta;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsulta{

	public void validar(DadosAgendamentoConsulta dados) {
		LocalDateTime dataConsulta = dados.data();
		
		Boolean domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
		Boolean antesDaAberturaDaClinica = dataConsulta.getHour() < 7;
		Boolean depoisDoEncerramentoDaClinica = dataConsulta.getHour() > 18;
		
		if (domingo || antesDaAberturaDaClinica || depoisDoEncerramentoDaClinica) {
			throw new ValidacaoException("Consulta fora de horário de funcionamento da clínica! Segunda a sábado das 8 as 18.");
		}
	}
}
