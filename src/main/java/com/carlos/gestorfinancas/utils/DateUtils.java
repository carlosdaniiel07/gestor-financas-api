package com.carlos.gestorfinancas.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 11/07/2019
 */
public class DateUtils {
	/**
	 * Retorna a data atual no formato dd/MM/yyyy
	 * @return
	 */
	public static Date getCurrentDate() {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			return formato.parse(new Date().toString());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
