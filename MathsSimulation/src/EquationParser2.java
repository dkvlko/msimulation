
public class EquationParser2 {

	float yvalue = 0;
	StringBuilder sb = null;

	public static void main(String args[]) {
		EquationParser2 ep = new EquationParser2();
		StringBuilder sbo = new StringBuilder("2*x^2+x");
		ep.computeEquation(sbo);
	}

	private void computeEquation(StringBuilder sbo) {
		// TODO Auto-generated method stub

		float y = 0;

		for (int x = -2; x <= 1; x++) {
			System.out.println("X =" + x);
			// yvalue=0;
			sb = new StringBuilder(sbo);
			System.out.println("Equation " + sb);
			System.out.println("X Value " + x);
			y = getYValue(x);
			System.out.println("Y Value " + y);
		}
	}

	private float getYValue(int x) {
		// System.out.println("GetYValue "+sb);
		// TODO Auto-generated method stub
		float y = 0;
		int index = 0;
		// checkBrackets();
		// checkTrigFunctions();
		// if division is found enter the checkdivision subroutine
		if (sb != null) {
			index = sb.indexOf("^", 1);
			if (index != -1) {
				try {
					y = checkNRoot(x, index);
				} catch (Exception e) {
					System.out.println("Exception " + e.getMessage());
				}

			}
		}

		if (sb != null) {
			index = sb.indexOf("/", 1);
			if (index != -1) {
				try {
					y = checkDivision(x, index);
				} catch (Exception e) {
					System.out.println("Exception " + e.getMessage());
				}

			}
		}

		// if division is not found enter the checkMultiplication subroutine
		if (sb != null) {
			index = sb.indexOf("*", 1);
			if (index != -1) {
				y = checkMultiplication(x, index);
			}
		}

		// if division and multiplication is not found enter the checkAddition
		// subroutine
		// we do not deal with sign operator which is at index 0
		if (sb != null) {
			index = sb.indexOf("+", 1);
			if (index != -1) {
				y = checkAddition(x, index);
			}
		}

		// if division and multiplication and addition is not found enter the
		// checkSubstraction subroutine
		if (sb != null) {
			index = sb.indexOf("-", 1);
			if (index != -1) {
				y = checkSubstraction(x, index);
			}
		}
		if (sb != null) {
			y = checkNoOperator(x);
		}

		return y;
	}

	private float checkNoOperator(int x) {
		// System.out.println("Equation NoOperator "+sb);
		int sign = 1;
		int signpos = sb.indexOf("-");
		String rightNum = "";
		if (signpos == 0) {
			sign = -1;
		}
		if (signpos == 0 || sb.indexOf("+") == 0) {
			if (sb.indexOf("x") != -1) {
				sb = null;
				return sign * x;
			} else {
				for (int right = 1; right < sb.length(); right++) {
					rightNum = rightNum + sb.charAt(right);
				}
				sb = null;
				return sign * Float.valueOf(rightNum);
			}
		} else {
			if (sb.indexOf("x") != -1) {
				sb = null;
				return x;
			} else {
				for (int right = 0; right < sb.length(); right++) {
					rightNum = rightNum + sb.charAt(right);
				}
				sb = null;
				return Float.valueOf(rightNum);
			}
		}

	}

	private float checkSubstraction(int x, int index) {
		String rightNum = "";
		String leftNum = "";
		int leftCut = 0;
		int rightCut = 0;
		int sign = 1;
		// get right to index value
		for (int right = index + 1; right < sb.length(); right++) {
			rightCut = right + 1;// if operator is not found on the right then
									// the rightCut will greater than max index
			// if on the right side operator is found then substring but ignore
			// if there is + or - immediately on right
			if (right != index + 1 && (sb.charAt(right) == '/' || sb.charAt(right) == '*' || sb.charAt(right) == '+'
					|| sb.charAt(right) == '-')) {
				rightCut = right;// this position will be used to delete the
									// substring;the operator will not be
									// deleted
				break;
			} else {
				if (sb.charAt(right) == 'x') {
					if (sb.charAt(index + 1) == '-') {
						rightNum = Integer.toString(-x);
					} else {
						rightNum = Integer.toString(x);
					}
				} else {
					rightNum = rightNum + sb.charAt(right);
				}
			}
		}
		// System.out.println("Postion of new operator right "+rightCut);
		// get left to index value
		for (int left = index - 1; left >= 0; left--) {
			leftCut = left;// make it out of bound if no operator on left
			if (sb.charAt(left) == '/' || sb.charAt(left) == '*' || sb.charAt(left) == '+' || sb.charAt(left) == '-') {
				leftCut = left + 1;
				if (sb.charAt(left) == '-') {
					sign = -1;
					leftCut = left;
				}
				if (sb.charAt(left) == '+') {
					sign = 1;
					leftCut = left;
				}
				// for substring further
				break;
			} else {
				if (sb.charAt(left) == 'x') {
					leftNum = Integer.toString(x);
				} else {
					leftNum = sb.charAt(left) + leftNum;
				}
			}
		}
		// System.out.println("Postion of new operator left "+leftCut);
		// System.out.println("The equation is "+sb);
		// System.out.println("Left Number Found as "+sign +" "+leftNum);
		// System.out.println("Right Number Found as "+rightNum);

		// if operator index is not zero then it is not a sign
		if (sign == 1) {
			yvalue = Float.valueOf(leftNum) - Float.valueOf(rightNum);
		} else {
			yvalue = -Float.valueOf(leftNum) - Float.valueOf(rightNum);
			sign = 1;
		}

		/*
		 * System.out.println("Value Calculated "+yvalue); System.out.println(
		 * "LeftCut "+leftCut); System.out.println("RightCut "+rightCut);
		 * System.out.println("SubString "+sb.substring(leftCut,rightCut));
		 */
		if (leftCut == 0 && rightCut == sb.length()) {
			// System.out.println("Number Found Final "+yvalue);
			sb = null;
			return yvalue;
		} else {
			// System.out.println("Before Further Processing String "+sb);
			sb.delete(leftCut, rightCut);
			String insY = null;
			if (yvalue > 0) {
				insY = "+" + Float.toString(yvalue);
			} else {
				insY = Float.toString(yvalue);
			}
			sb.insert(leftCut, insY);
			// System.out.println("Further Processing String "+sb);
			return getYValue(x);
		}

		// return 0;
		// TODO Auto-generated method stub

	}

	private float checkAddition(int x, int index) {
		String rightNum = "";
		String leftNum = "";
		int leftCut = 0;
		int rightCut = 0;
		int sign = 1;
		// get right to index value
		for (int right = index + 1; right < sb.length(); right++) {
			rightCut = right + 1;// if operator is not found on the right then
									// the rightCut will greater than max index
			// if on the right side operator is found then substring but ignore
			// if there is + or - immediately on right
			if (right != index + 1 && (sb.charAt(right) == '/' || sb.charAt(right) == '*' || sb.charAt(right) == '+'
					|| sb.charAt(right) == '-')) {
				rightCut = right;// this position will be used to delete the
									// substring;the operator will not be
									// deleted
				break;
			} else {
				if (sb.charAt(right) == 'x') {
					if (sb.charAt(index + 1) == '-') {
						rightNum = Integer.toString(-x);
					} else {
						rightNum = Integer.toString(x);
					}
				} else {
					rightNum = rightNum + sb.charAt(right);
				}
			}
		}
		// System.out.println("Postion of new operator right "+rightCut);
		// get left to index value
		for (int left = index - 1; left >= 0; left--) {
			leftCut = left;// make it out of bound if no operator on left
			if (sb.charAt(left) == '/' || sb.charAt(left) == '*' || sb.charAt(left) == '+' || sb.charAt(left) == '-') {
				leftCut = left + 1;
				if (sb.charAt(left) == '+') {
					sign = 1;
					leftCut = left;
				}
				if (sb.charAt(left) == '-') {
					sign = -1;
					leftCut = left;
				}
				// for substring further
				break;
			} else {
				if (sb.charAt(left) == 'x') {
					leftNum = Integer.toString(x);
				} else {
					leftNum = sb.charAt(left) + leftNum;
				}
			}
		}
		// System.out.println("Postion of + operator left "+leftCut);
		// System.out.println("The equation is "+sb);
		// System.out.println("Left Number Found as "+sign +" "+leftNum);
		// System.out.println("Right Number Found as "+rightNum);

		// if operator index is not zero then it is not a sign
		if (sign == 1) {
			yvalue = Float.valueOf(leftNum) + Float.valueOf(rightNum);
		} else {
			yvalue = -Float.valueOf(leftNum) + Float.valueOf(rightNum);
			sign = 1;
		}

		/*
		 * System.out.println("Value Calculated "+yvalue); System.out.println(
		 * "LeftCut "+leftCut); System.out.println("RightCut "+rightCut);
		 * System.out.println("SubString "+sb.substring(leftCut,rightCut));
		 */
		if (leftCut == 0 && rightCut == sb.length()) {
			// System.out.println("Number Found Final "+yvalue);
			sb = null;
			return yvalue;
		} else {
			// System.out.println("Before Further Processing String "+sb);
			sb.delete(leftCut, rightCut);
			String insY = null;
			if (yvalue > 0) {
				insY = "+" + Float.toString(yvalue);
			} else {
				insY = Float.toString(yvalue);
			}
			sb.insert(leftCut, insY);
			// System.out.println("Further Processing String "+sb);
			return getYValue(x);
		}

		// return 0;
		// TODO Auto-generated method stub

	}

	private float checkMultiplication(int x, int index) {
		String rightNum = "";
		String leftNum = "";
		int leftCut = 0;
		int rightCut = 0;
		int sign = 1;
		// get right to index value
		for (int right = index + 1; right < sb.length(); right++) {
			rightCut = right + 1;// if operator is not found on the right then
									// the rightCut will greater than max index
			// if on the right side operator is found then substring but ignore
			// if there is + or - immediately on right
			if (right != index + 1 && (sb.charAt(right) == '/' || sb.charAt(right) == '*' || sb.charAt(right) == '+'
					|| sb.charAt(right) == '-')) {
				rightCut = right;// this position will be used to delete the
									// substring;the operator will not be
									// deleted
				break;
			} else {
				if (sb.charAt(right) == 'x') {
					if (sb.charAt(index + 1) == '-') {
						rightNum = Integer.toString(-x);
					} else {
						rightNum = Integer.toString(x);
					}

				} else {
					rightNum = rightNum + sb.charAt(right);
				}
			}
		}
		// System.out.println("Postion of new operator right "+rightCut);
		// get left to index value
		for (int left = index - 1; left >= 0; left--) {
			leftCut = left;// make it out of bound if no operator on left
			if (sb.charAt(left) == '/' || sb.charAt(left) == '*' || sb.charAt(left) == '+' || sb.charAt(left) == '-') {
				leftCut = left + 1;
				if (sb.charAt(left) == '+') {
					sign = 1;
					leftCut = left;
				}
				if (sb.charAt(left) == '-') {
					sign = -1;
					leftCut = left;
				}
				// for substring further
				break;
			} else {
				if (sb.charAt(left) == 'x') {
					leftNum = Integer.toString(x);
				} else {
					leftNum = sb.charAt(left) + leftNum;
				}
			}
		}
		// System.out.println("Postion of * operator left "+leftCut);
		// System.out.println("The equation is "+sb);
		// System.out.println("Left Number Found as "+sign +" "+leftNum);
		// System.out.println("Right Number Found as "+rightNum);

		// if operator index is not zero then it is not a sign
		if (sign == 1) {
			yvalue = Float.valueOf(leftNum) * Float.valueOf(rightNum);
		} else {
			yvalue = -Float.valueOf(leftNum) * Float.valueOf(rightNum);
			sign = 1;
		}

		/*
		 * System.out.println("Value Calculated "+yvalue); System.out.println(
		 * "LeftCut "+leftCut); System.out.println("RightCut "+rightCut);
		 * System.out.println("SubString "+sb.substring(leftCut,rightCut));
		 */
		if (leftCut == 0 && rightCut == sb.length()) {
			// System.out.println("Number Found Final "+yvalue);
			sb = null;
			return yvalue;
		} else {
			// System.out.println("Before Further Processing String "+sb);
			sb.delete(leftCut, rightCut);
			String insY = null;
			if (yvalue > 0) {
				insY = "+" + Float.toString(yvalue);
			} else {
				insY = Float.toString(yvalue);
			}
			sb.insert(leftCut, insY);
			// System.out.println("Further Processing String "+sb);
			return getYValue(x);
		}

		// return 0;
		// TODO Auto-generated method stub

	}

	private float checkDivision(int x, int index) throws IllegalArgumentException {
		String rightNum = "";
		String leftNum = "";
		int leftCut = 0;
		int rightCut = 0;
		int sign = 1;
		// get right to index value
		for (int right = index + 1; right < sb.length(); right++) {
			rightCut = right + 1;// if operator is not found on the right then
									// the rightCut will greater than max index
			// if on the right side operator is found then substring but ignore
			// if there is + or - immediately on right
			if (right != index + 1 && (sb.charAt(right) == '/' || sb.charAt(right) == '*' || sb.charAt(right) == '+'
					|| sb.charAt(right) == '-')) {
				rightCut = right;// this position will be used to delete the
									// substring;the operator will not be
									// deleted
				break;
			} else {
				if (sb.charAt(right) == 'x') {
					if (sb.charAt(index + 1) == '-') {
						rightNum = Integer.toString(-x);
					} else {
						rightNum = Integer.toString(x);
					}
				} else {
					rightNum = rightNum + sb.charAt(right);
				}
			}
		}
		// System.out.println("Postion of new operator right "+rightCut);
		// get left to index value
		for (int left = index - 1; left >= 0; left--) {
			leftCut = left;// make it out of bound if no operator on left
			if (sb.charAt(left) == '/' || sb.charAt(left) == '*' || sb.charAt(left) == '+' || sb.charAt(left) == '-') {
				leftCut = left + 1;
				if (sb.charAt(left) == '+') {
					sign = 1;
					leftCut = left;
				}
				if (sb.charAt(left) == '-') {
					sign = -1;
					leftCut = left;
				}
				// for substring further
				break;
			} else {
				if (sb.charAt(left) == 'x') {
					leftNum = Integer.toString(x);
				} else {
					leftNum = sb.charAt(left) + leftNum;
				}
			}
		}
		// System.out.println("Postion of div operator left "+leftCut);
		// System.out.println("The equation is "+sb);
		// System.out.println("Left Number Found as "+sign +" "+leftNum);
		// System.out.println("Right Number Found as "+rightNum);

		// if operator index is not zero then it is not a sign
		float divisor = Float.valueOf(rightNum);
		if (divisor == 0) {
			throw new IllegalArgumentException("Argument 'divisor' is zero.");
		}

		if (sign == 1) {
			yvalue = Float.valueOf(leftNum) / divisor;
		} else {
			yvalue = -Float.valueOf(leftNum) / divisor;
			sign = 1;
		}

		/*
		 * System.out.println("Value Calculated "+yvalue); System.out.println(
		 * "LeftCut "+leftCut); System.out.println("RightCut "+rightCut);
		 * System.out.println("SubString "+sb.substring(leftCut,rightCut));
		 */
		if (leftCut == 0 && rightCut == sb.length()) {
			// System.out.println("Number Found Final "+yvalue);
			sb = null;
			return yvalue;
		} else {
			// System.out.println("Before Further Processing String "+sb);
			sb.delete(leftCut, rightCut);
			String insY = null;
			if (yvalue > 0) {
				insY = "+" + Float.toString(yvalue);
			} else {
				insY = Float.toString(yvalue);
			}
			sb.insert(leftCut, insY);
			// System.out.println("Further Processing String "+sb);
			return getYValue(x);
		}

		// return 0;
		// TODO Auto-generated method stub
	}

	private float checkNRoot(int x, int index) throws IllegalArgumentException {
		String rightNum = "";
		String leftNum = "";
		int leftCut = 0;
		int rightCut = 0;
		int sign = 1;

		// get right to index value
		for (int right = index + 1; right < sb.length(); right++) {
			rightCut = right + 1;// if operator is not found on the right then
									// the rightCut will greater than max index
			// if on the right side operator is found then substring but ignore
			// if there is + or - immediately on right
			if (right != index + 1 && (sb.charAt(right) == '/' || sb.charAt(right) == '*' || sb.charAt(right) == '+'
					|| sb.charAt(right) == '-')) {
				rightCut = right;// this position will be used to delete the
									// substring;the operator will not be
									// deleted
				break;
			} else {
				if (sb.charAt(right) == 'x') {
					/*
					 * if(sb.charAt(index+1)=='-'){
					 * rightNum=Integer.toString(-x); }else{
					 * rightNum=Integer.toString(x); }
					 */

					rightNum = Integer.toString(x);
				} else {
					rightNum = rightNum + sb.charAt(right);
				}
			}
		}
		// System.out.println("Postion of new operator right "+rightCut);
		// get left to index value
		for (int left = index - 1; left >= 0; left--) {
			leftCut = left;// make it out of bound if no operator on left
			if (sb.charAt(left) == '/' || sb.charAt(left) == '*' || sb.charAt(left) == '+' || sb.charAt(left) == '-') {
				leftCut = left + 1;
				if (sb.charAt(left) == '+') {
					sign = 1;
					leftCut = left;
				}
				if (sb.charAt(left) == '-') {
					sign = -1;
					leftCut = left;
				}
				// for substring further
				break;
			} else {
				if (sb.charAt(left) == 'x') {
					leftNum = Integer.toString(x);
				} else {
					leftNum = sb.charAt(left) + leftNum;
				}
			}
		}
		// System.out.println("Postion of ^ operator left "+leftCut);
		// System.out.println("The equation is "+sb);
		// System.out.println("Left Number Found as "+sign +" "+leftNum);
		// System.out.println("Right Number Found as "+rightNum);

		// if operator index is not zero then it is not a sign
		double root = Float.valueOf(rightNum);
		double num = Float.valueOf(leftNum);
		yvalue = (float) Math.pow(num, root);
		if (sign == -1) {
			yvalue = -yvalue;
		}

		/*
		 * System.out.println("Value Calculated "+yvalue); System.out.println(
		 * "LeftCut "+leftCut); System.out.println("RightCut "+rightCut);
		 * System.out.println("SubString "+sb.substring(leftCut,rightCut));
		 */
		if (leftCut == 0 && rightCut == sb.length()) {
			// System.out.println("Number Found Final "+yvalue);
			sb = null;
			return yvalue;
		} else {
			// System.out.println("Before Further Processing String "+sb);
			sb.delete(leftCut, rightCut);
			String insY = null;
			if (yvalue > 0) {
				insY = "+" + Float.toString(yvalue);
			} else {
				insY = Float.toString(yvalue);
			}
			sb.insert(leftCut, insY);
			// System.out.println("Further Processing String "+sb);
			return getYValue(x);
		}

		// return 0;
		// TODO Auto-generated method stub
	}

}
