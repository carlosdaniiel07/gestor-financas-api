package com.carlos.gestorfinancas.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 11/07/2019
 */
public class DateUtils {
	/**
	 * Retorna a data e hora atual
	 * @return
	 */
	public static Date getDataAtual() {
		return new Date(System.currentTimeMillis());
	}
	
	/**
	 * Retorna a data atual no formato de string 'dd/MM/yyyy HH:mm:ss'
	 * @return
	 */
	public static String getDataAtualAsBrFormat() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return dateFormat.format(getDataAtual()); 
	}
	
	/**
	 * Retorna a data e hora atual no formato de nome de arquivo (ddMMyyyyhhmmss)
	 * @return
	 */
	public static String getDataAtualAsFileName() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyyhhmmss");
		return dateFormat.format(getDataAtual());
	}
	
	/**
	 * Retorna um objeto Calendar baseado em uma data específica
	 * @return
	 */
	public static Calendar buildCalendarWithLocalDate(LocalDate localDate) {
		Calendar calendar = Calendar.getInstance();
		
		calendar.set(Calendar.DAY_OF_MONTH, localDate.getDayOfMonth());
		calendar.set(Calendar.MONTH, localDate.getMonthValue() - 1);
		calendar.set(Calendar.YEAR, localDate.getYear());
		
		return calendar;
	}
}
