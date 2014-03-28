package code.auxis;

import javax.swing.JOptionPane;

public class Fraction {

	private int numerator, denominator, goodness, error;

	public static void main(String[] args) {
		try {
			double d = Double.parseDouble(JOptionPane
					.showInputDialog("input the decimal"));
			Fraction f = new Fraction(d);
			JOptionPane.showMessageDialog(null, "Result: " + f.getNumerator()
					+ "/" + f.getDenominator());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "failed");
		}
	}

	public Fraction(double d) {

		double decimal;
		double originalDecimal;
		int LIMIT = 12;
		int denominators[] = new int[LIMIT + 1];
		int numerator, denominator, temp;
		int MAX_GOODNESS = 100;

		// Get a number to be converted to a fraction
		decimal = Double.valueOf(d).doubleValue();

		originalDecimal = decimal;

		// Compute all the denominators
		int i = 0;
		while (i < LIMIT + 1) {
			denominators[i] = (int) decimal;
			decimal = 1.0 / (decimal - denominators[i]);
			i = i + 1;
		}

		// Compute the i-th approximation
		int last = 0;
		while (last < LIMIT) {

			// Initialize variables used in computation
			numerator = 1;
			denominator = 1;
			temp = 0;

			// Do the computation
			int current = last;
			while (current >= 0) {
				denominator = numerator;
				numerator = (numerator * denominators[current]) + temp;
				temp = denominator;
				current = current - 1;
			}
			last = last + 1;

			// Display results
			double value = (double) numerator / denominator;
			int goodness = denominators[last];
			double error = 100 * Math.abs(value - originalDecimal)
					/ originalDecimal;

			// Exit early if we have reached our goodness criterion
			if (Math.abs(goodness) > MAX_GOODNESS) {
				this.goodness = goodness;
				this.error = (int) error;

				this.numerator = numerator;
				this.denominator = denominator;
			}
		}
	}

	public int getNumerator() {
		return numerator;
	}

	public int getDenominator() {
		return denominator;
	}

	public int getGoodness() {
		return goodness;
	}

	public int getError() {
		return error;
	}
}
