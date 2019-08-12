package com.carlos.gestorfinancas.utils;

import java.util.Random;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 12/08/2019
 */
public class StringUtils {

	/**
	 * Gera uma sequência de caracteres aleatórios
	 * @param strength
	 * @return
	 */
	public static String generateRandomString(int strength) {
		Random random = new Random();
		String hash = Long.toString(System.currentTimeMillis());
		char[] chars = { 'A', 'B', 'C', 'D', 'E', 'F',
				'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
				'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
				'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		
		for(int c = 0; c < strength; c++) {
			hash += chars[random.nextInt(chars.length)];
		}
		
		return hash;
	}
}
