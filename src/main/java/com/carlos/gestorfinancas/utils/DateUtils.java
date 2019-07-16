package com.carlos.gestorfinancas.utils;

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
}
