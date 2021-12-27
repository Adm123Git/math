package ru.adm123.math;

import org.openjdk.jmh.annotations.Benchmark;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Dmitry Ushakov on 23.08.21
 */
public class Main {

	public static void main(String[] args) {
		String str = "3 * 2 / 6.7 + 4 - 1 / 5";
		System.out.println(str + " = " + calculate(str));
	}

	@Benchmark
	public static float calculate(String str) {
		List<String> digits = new ArrayList<>(Arrays.asList(str.split("\\s*\\*|/|\\+|-\\s*")));
		List<String> signs = new ArrayList<>(Arrays.asList(str.split("\\s*\\d+(\\.\\d+)?\\s*")));
		for (int i = digits.size() - 1; i > 0; i--) {
			if (isDivisionOrMultiplication(signs.get(i))) {
				calcProcess(digits, signs, i);
			}
		}
		for (int i = digits.size() - 1; i > 0; i--) {
			if (isComplexityOrSubtraction(signs.get(i))) {
				calcProcess(digits, signs, i);
			}
		}
		return Float.parseFloat(digits.get(0));
	}

	private static String calcPair(List<String> digits,
								   String sign,
								   int i) {
		if (sign.equals("*")) {
			return String.valueOf(Float.parseFloat(digits.get(i - 1)) * Float.parseFloat(digits.get(i)));
		}
		if (sign.equals("/")) {
			return String.valueOf(Float.parseFloat(digits.get(i - 1)) / Float.parseFloat(digits.get(i)));
		}
		if (sign.equals("+")) {
			return String.valueOf(Float.parseFloat(digits.get(i - 1)) + Float.parseFloat(digits.get(i)));
		}
		if (sign.equals("-")) {
			return String.valueOf(Float.parseFloat(digits.get(i - 1)) - Float.parseFloat(digits.get(i)));
		}
		return null;
	}

	private static void calcProcess(List<String> digits,
									List<String> signs,
									int iterationIndex) {
		digits.set(iterationIndex - 1, calcPair(digits, signs.get(iterationIndex), iterationIndex));
		digits.remove(iterationIndex);
		signs.remove(iterationIndex);
	}

	private static boolean isDivisionOrMultiplication(String sign) {
		return sign.equals("*") || sign.equals("/");
	}

	private static boolean isComplexityOrSubtraction(String sign) {
		return sign.equals("+") || sign.equals("-");
	}

}
