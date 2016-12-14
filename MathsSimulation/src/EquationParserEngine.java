public class EquationParserEngine {

	StringBuilder equation = new StringBuilder("exp(-x/100)");

	public static void main(String args[]) {
		EquationParserEngine epn = new EquationParserEngine();
		epn.computeEquation();
	}

	private void computeEquation() {
		// TODO Auto-generated method stub
		float yValue = 0;
		// prepare equation for sin , cos , tan ,log
		equation = new StringBuilder(equation.toString().replaceAll("sin", "s"));
		equation = new StringBuilder(equation.toString().replaceAll("cos", "c"));
		equation = new StringBuilder(equation.toString().replaceAll("tan", "t"));
		equation = new StringBuilder(equation.toString().replaceAll("log", "l"));
		equation = new StringBuilder(equation.toString().replaceAll("exp", "e"));
		// System.out.println("new equation " + equation);
		// System.exit(0);
		for (int x = -300; x <= 300; x++) {
			// System.out.println("X Value " + x);
			StringBuilder processEqu = new StringBuilder(equation);
			yValue = getYValue(processEqu, x);
			// System.out.println("Y Value " + yValue);
		}
	}

	public float getYValue(StringBuilder processEqu, int x) {
		// TODO Auto-generated method stub
		// System.out.println("Equation for getYValue " + processEqu);
		// processEqu.setLength(0);
		// System.out.println("Equation reset for getYValue " +
		// processEqu.length());
		int index = 0;
		float y = 0;

		if (processEqu.length() != 0) {
			index = processEqu.lastIndexOf("(");
			if (index != -1) {
				y = processBracket(processEqu, x, index);
			}

		}
		// on first entry the first sign is ignored because we are looking for
		// nonsign operator but here sin can come in begining unlike /,*,+ or -

		if (processEqu.length() != 0) {
			index = processEqu.indexOf("s");
			if (index != -1) {
				y = performSin(processEqu, x, index);

			}

		}
		if (processEqu.length() != 0) {
			index = processEqu.indexOf("c");
			if (index != -1) {
				y = performCos(processEqu, x, index);
			}

		}
		if (processEqu.length() != 0) {
			index = processEqu.indexOf("t");
			if (index != -1) {
				y = performTan(processEqu, x, index);
			}

		}
		if (processEqu.length() != 0) {
			index = processEqu.indexOf("l");
			if (index != -1) {
				y = performLog(processEqu, x, index);
			}

		}
		if (processEqu.length() != 0) {
			index = processEqu.indexOf("e");
			if (index != -1) {
				y = performExp(processEqu, x, index);
			}

		}
		if (processEqu.length() != 0) {
			index = processEqu.indexOf("/", 1);
			if (index != -1) {
				y = performDivision(processEqu, x, index);
			}

		}
		if (processEqu.length() != 0) {
			index = processEqu.indexOf("*", 1);
			if (index != -1) {
				y = performMultiplication(processEqu, x, index);
			}

		}
		if (processEqu.length() != 0) {
			index = processEqu.indexOf("+", 1);
			if (index != -1) {
				y = performAddition(processEqu, x, index);
			}

		}

		if (processEqu.length() != 0) {
			index = processEqu.indexOf("-", 1);
			if (index != -1) {
				y = performSubstraction(processEqu, x, index);
			}

		}
		if (processEqu.length() != 0) {
			y = performNoOperator(processEqu, x);
		}
		return y;
	}

	private float performExp(StringBuilder processEqu, int x, int index) {
		String rightNum = "";
		int leftCut = index;
		int rightCut = 0;
		// System.out.println("Equation to process " + processEqu);
		for (int right = index + 1; right < processEqu.length(); right++) {
			rightCut = right + 1;
			if (right != index + 1 && (processEqu.charAt(right) == '/' || processEqu.charAt(right) == '*'
					|| processEqu.charAt(right) == '+' || processEqu.charAt(right) == '-')) {
				rightCut = right;
				break;
			} else {
				if (processEqu.charAt(right) == 'x') {
					if (processEqu.charAt(index + 1) == '-') {
						rightNum = Integer.toString(-x);
					} else {
						rightNum = Integer.toString(x);
					}
				} else {
					rightNum = rightNum + processEqu.charAt(right);
				}
			}
		}
		float yvalue = (float) Math.exp((double) Float.valueOf(rightNum));
		// System.out.println("Yvalue " + yvalue);
		if (yvalue < 0.001 && yvalue > 0) {
			yvalue = 0;
		}
		if (yvalue > -0.001 && yvalue < 0) {
			yvalue = 0;
		}
		if (index != 0 && (processEqu.charAt(index - 1) == '-' || processEqu.charAt(index - 1) == '+')) {
			leftCut = index - 1;
			if (processEqu.charAt(index - 1) == '-') {
				yvalue = -yvalue;
			}
		}

		if (yvalue == 0) { // to handle -0
			yvalue = yvalue + 0;
		}
		// System.out.println("Equation perform sin " + processEqu);
		String insY = "";
		processEqu.delete(leftCut, rightCut);
		if (yvalue >= 0) {
			insY = "+" + Float.toString(yvalue);
		} else {
			insY = Float.toString(yvalue);
		}
		processEqu.insert(leftCut, insY);
		// System.out.println("Equation perform exp further processing " +
		// processEqu);
		return getYValue(processEqu, x);
	}

	private float performSin(StringBuilder processEqu, int x, int index) {
		String rightNum = "";
		int leftCut = index;
		int rightCut = 0;

		for (int right = index + 1; right < processEqu.length(); right++) {
			rightCut = right + 1;
			if (right != index + 1 && (processEqu.charAt(right) == '/' || processEqu.charAt(right) == '*'
					|| processEqu.charAt(right) == '+' || processEqu.charAt(right) == '-')) {
				rightCut = right;
				break;
			} else {
				if (processEqu.charAt(right) == 'x') {
					if (processEqu.charAt(index + 1) == '-') {
						rightNum = Integer.toString(-x);
					} else {
						rightNum = Integer.toString(x);
					}
				} else {
					rightNum = rightNum + processEqu.charAt(right);
				}
			}
		}
		float yvalue = (float) Math.sin((double) Float.valueOf(rightNum));
		if (yvalue < 0.001 && yvalue > 0) {
			yvalue = 0;
		}
		if (yvalue > -0.001 && yvalue < 0) {
			yvalue = 0;
		}
		if (index != 0 && (processEqu.charAt(index - 1) == '-' || processEqu.charAt(index - 1) == '+')) {
			leftCut = index - 1;
			if (processEqu.charAt(index - 1) == '-') {
				yvalue = -yvalue;
			}
		}

		if (yvalue == 0) { // to handle -0
			yvalue = yvalue + 0;
		}
		// System.out.println("Equation perform sin " + processEqu);
		String insY = "";
		processEqu.delete(leftCut, rightCut);
		if (yvalue >= 0) {
			insY = "+" + Float.toString(yvalue);
		} else {
			insY = Float.toString(yvalue);
		}
		processEqu.insert(leftCut, insY);
		// System.out.println("Equation perform sin further processing " +
		// processEqu);
		return getYValue(processEqu, x);
	}

	private float performCos(StringBuilder processEqu, int x, int index) {
		String rightNum = "";
		int leftCut = index;
		int rightCut = 0;
		for (int right = index + 1; right < processEqu.length(); right++) {
			rightCut = right + 1;
			if (right != index + 1 && (processEqu.charAt(right) == '/' || processEqu.charAt(right) == '*'
					|| processEqu.charAt(right) == '+' || processEqu.charAt(right) == '-')) {
				rightCut = right;
				break;
			} else {
				if (processEqu.charAt(right) == 'x') {
					if (processEqu.charAt(index + 1) == '-') {
						rightNum = Integer.toString(-x);
					} else {
						rightNum = Integer.toString(x);
					}
				} else {
					rightNum = rightNum + processEqu.charAt(right);
				}
			}
		}
		float yvalue = (float) Math.cos((double) Float.valueOf(rightNum));
		if (yvalue < 0.001 && yvalue > 0) {
			yvalue = 0;
		}
		if (yvalue > -0.001 && yvalue < 0) {
			yvalue = 0;
		}
		if (index != 0 && (processEqu.charAt(index - 1) == '-' || processEqu.charAt(index - 1) == '+')) {
			leftCut = index - 1;
			if (processEqu.charAt(index - 1) == '-') {
				yvalue = -yvalue;
			}
		}
		if (yvalue == 0) { // to handle -0
			yvalue = yvalue + 0;
		}
		// System.out.println("Equation perform sin " + processEqu);
		String insY = "";
		processEqu.delete(leftCut, rightCut);
		if (yvalue >= 0) {
			insY = "+" + Float.toString(yvalue);
		} else {
			insY = Float.toString(yvalue);
		}
		processEqu.insert(leftCut, insY);
		// System.out.println("Equation perform sin further processing " +
		// processEqu);
		return getYValue(processEqu, x);
	}

	private float performTan(StringBuilder processEqu, int x, int index) {
		String rightNum = "";
		int leftCut = index;
		int rightCut = 0;
		for (int right = index + 1; right < processEqu.length(); right++) {
			rightCut = right + 1;
			if (right != index + 1 && (processEqu.charAt(right) == '/' || processEqu.charAt(right) == '*'
					|| processEqu.charAt(right) == '+' || processEqu.charAt(right) == '-')) {
				rightCut = right;
				break;
			} else {
				if (processEqu.charAt(right) == 'x') {
					if (processEqu.charAt(index + 1) == '-') {
						rightNum = Integer.toString(-x);
					} else {
						rightNum = Integer.toString(x);
					}
				} else {
					rightNum = rightNum + processEqu.charAt(right);
				}
			}
		}
		float yvalue = (float) Math.tan((double) Float.valueOf(rightNum));
		if (yvalue < 0.001 && yvalue > 0) {
			yvalue = 0;
		}
		if (yvalue > -0.001 && yvalue < 0) {
			yvalue = 0;
		}
		if (index != 0 && (processEqu.charAt(index - 1) == '-' || processEqu.charAt(index - 1) == '+')) {
			leftCut = index - 1;
			if (processEqu.charAt(index - 1) == '-') {
				yvalue = -yvalue;
			}
		}
		if (yvalue == 0) { // to handle -0
			yvalue = yvalue + 0;
		}
		// System.out.println("Equation perform sin " + processEqu);
		String insY = "";
		processEqu.delete(leftCut, rightCut);
		if (yvalue >= 0) {
			insY = "+" + Float.toString(yvalue);
		} else {
			insY = Float.toString(yvalue);
		}
		processEqu.insert(leftCut, insY);
		// System.out.println("Equation perform sin further processing " +
		// processEqu);
		return getYValue(processEqu, x);
	}

	private float performLog(StringBuilder processEqu, int x, int index) {
		String rightNum = "";
		int leftCut = index;
		int rightCut = 0;
		for (int right = index + 1; right < processEqu.length(); right++) {
			rightCut = right + 1;
			if (right != index + 1 && (processEqu.charAt(right) == '/' || processEqu.charAt(right) == '*'
					|| processEqu.charAt(right) == '+' || processEqu.charAt(right) == '-')) {
				rightCut = right;
				break;
			} else {
				if (processEqu.charAt(right) == 'x') {
					if (processEqu.charAt(index + 1) == '-') {
						rightNum = Integer.toString(-x);
					} else {
						rightNum = Integer.toString(x);
					}
				} else {
					rightNum = rightNum + processEqu.charAt(right);
				}
			}
		}
		float yvalue = (float) Math.log((double) Float.valueOf(rightNum));
		if (yvalue < 0.001 && yvalue > 0) {
			yvalue = 0;
		}
		if (yvalue > -0.001 && yvalue < 0) {
			yvalue = 0;
		}
		if (index != 0 && (processEqu.charAt(index - 1) == '-' || processEqu.charAt(index - 1) == '+')) {
			leftCut = index - 1;
			if (processEqu.charAt(index - 1) == '-') {
				yvalue = -yvalue;
			}
		}
		if (yvalue == 0) { // to handle -0
			yvalue = yvalue + 0;
		}
		// System.out.println("Equation perform sin " + processEqu);
		String insY = "";
		processEqu.delete(leftCut, rightCut);
		if (yvalue >= 0) {
			insY = "+" + Float.toString(yvalue);
		} else {
			insY = Float.toString(yvalue);
		}
		processEqu.insert(leftCut, insY);
		// System.out.println("Equation perform sin further processing " +
		// processEqu);
		return getYValue(processEqu, x);
	}

	private float processBracket(StringBuilder processEqu, int x, int index) {
		String rightEqu = "";
		int leftCut = index;
		int rightCut = 0;
		for (int right = index + 1; right < processEqu.length(); right++) {
			if (processEqu.charAt(right) == ')') {
				rightCut = right + 1;
				break;
			}
			rightEqu = rightEqu + processEqu.charAt(right);
		}
		// System.out.println("Core Bracket to be replaced" + rightEqu);
		float yvalue = getYValue(new StringBuilder(rightEqu), x);
		String bIns = Float.toString(yvalue);
		processEqu.delete(leftCut, rightCut);
		processEqu.insert(leftCut, bIns);
		return getYValue(processEqu, x);
	}

	private float performDivision(StringBuilder processEqu, int x, int index) {
		// TODO Auto-generated method stub
		String rightNum = "";
		String leftNum = "";
		int leftCut = 0;
		int rightCut = 0;
		int sign = 1;
		float yvalue = 0;

		// get right to index value
		for (int right = index + 1; right < processEqu.length(); right++) {
			rightCut = right + 1;// if operator is not found on the right then
									// the rightCut will greater than max index
			// if on the right side operator is found then substring but ignore
			// if there is + or - immediately on right
			if (right != index + 1 && (processEqu.charAt(right) == '/' || processEqu.charAt(right) == '*'
					|| processEqu.charAt(right) == '+' || processEqu.charAt(right) == '-')) {
				rightCut = right;// this position will be used to delete the
									// substring;the operator will not be
									// deleted
				break;
			} else {
				if (processEqu.charAt(right) == 'x') {
					if (processEqu.charAt(index + 1) == '-') {
						rightNum = Integer.toString(-x);
					} else {
						rightNum = Integer.toString(x);
					}
				} else {
					rightNum = rightNum + processEqu.charAt(right);
				}
			}
		}
		// System.out.println("Postion of new operator right " + rightCut);
		// get left to index value
		for (int left = index - 1; left >= 0; left--) {
			leftCut = left;// make it out of bound if no operator on left
			if (processEqu.charAt(left) == '/' || processEqu.charAt(left) == '*' || processEqu.charAt(left) == '+'
					|| processEqu.charAt(left) == '-') {
				leftCut = left + 1;
				if (processEqu.charAt(left) == '-') {
					sign = -1;
					leftCut = left;
				}
				if (processEqu.charAt(left) == '+') {
					sign = 1;
					leftCut = left;
				}
				// for substring further
				break;
			} else {
				if (processEqu.charAt(left) == 'x') {
					leftNum = Integer.toString(x);
				} else {
					leftNum = processEqu.charAt(left) + leftNum;
				}
			}
		}
		// System.out.println("Postion of new operator left " + leftCut);
		// System.out.println("The equation is " + processEqu);
		// System.out.println("Left Number Found as " + sign + " " + leftNum);
		// System.out.println("Right Number Found as " + rightNum);

		// if operator index is not zero then it is not a sign example 10-3/4
		if (sign == 1) {
			yvalue = Float.valueOf(leftNum) / Float.valueOf(rightNum);
		} else {
			yvalue = -Float.valueOf(leftNum) / Float.valueOf(rightNum);
			sign = 1;
		}

		/*
		 * System.out.println("Value Calculated "+yvalue); System.out.println(
		 * "LeftCut "+leftCut); System.out.println("RightCut "+rightCut);
		 * System.out.println("SubString "+sb.substring(leftCut,rightCut));
		 */
		if (leftCut == 0 && rightCut == processEqu.length()) {
			// System.out.println("Number Found Final "+yvalue);
			processEqu.setLength(0);
			return yvalue;
		} else {
			// System.out.println("y inter Value " + yvalue);
			// System.out.println("Before Further Processing String " +
			// processEqu);
			processEqu.delete(leftCut, rightCut);
			// System.out.println("Deleted after " + processEqu);
			String insY = null;
			if (yvalue == 0) {
				yvalue = yvalue + 0;
			}
			if (yvalue >= 0) {
				insY = "+" + Float.toString(yvalue);
			} else {
				insY = Float.toString(yvalue);
			}
			// System.out.println("LeftCut before processing " + leftCut);
			processEqu.insert(leftCut, insY);
			// System.out.println("Further Processing String " + processEqu);
			return getYValue(processEqu, x);
		}
	}

	private float performMultiplication(StringBuilder processEqu, int x, int index) {
		// TODO Auto-generated method stub
		String rightNum = "";
		String leftNum = "";
		int leftCut = 0;
		int rightCut = 0;
		int sign = 1;
		float yvalue = 0;

		// get right to index value
		for (int right = index + 1; right < processEqu.length(); right++) {
			rightCut = right + 1;// if operator is not found on the right then
									// the rightCut will greater than max index
			// if on the right side operator is found then substring but ignore
			// if there is + or - immediately on right
			if (right != index + 1 && (processEqu.charAt(right) == '/' || processEqu.charAt(right) == '*'
					|| processEqu.charAt(right) == '+' || processEqu.charAt(right) == '-')) {
				rightCut = right;// this position will be used to delete the
									// substring;the operator will not be
									// deleted
				break;
			} else {
				if (processEqu.charAt(right) == 'x') {
					if (processEqu.charAt(index + 1) == '-') {
						rightNum = Integer.toString(-x);
					} else {
						rightNum = Integer.toString(x);
					}
				} else {
					rightNum = rightNum + processEqu.charAt(right);
				}
			}
		}
		// System.out.println("Postion of new operator right " + rightCut);
		// get left to index value
		for (int left = index - 1; left >= 0; left--) {
			leftCut = left;// make it out of bound if no operator on left
			if (processEqu.charAt(left) == '/' || processEqu.charAt(left) == '*' || processEqu.charAt(left) == '+'
					|| processEqu.charAt(left) == '-') {
				leftCut = left + 1;
				if (processEqu.charAt(left) == '-') {
					sign = -1;
					leftCut = left;
				}
				if (processEqu.charAt(left) == '+') {
					sign = 1;
					leftCut = left;
				}
				// for substring further
				break;
			} else {
				if (processEqu.charAt(left) == 'x') {
					leftNum = Integer.toString(x);
				} else {
					leftNum = processEqu.charAt(left) + leftNum;
				}
			}
		}
		// System.out.println("Postion of new operator left "+leftCut);
		// System.out.println("The equation is " + processEqu);
		// System.out.println("Left Number Found as " + sign + " " + leftNum);
		// System.out.println("Right Number Found as " + rightNum);

		// if operator index is not zero then it is not a sign
		if (sign == 1) {
			yvalue = Float.valueOf(leftNum) * Float.valueOf(rightNum);
		} else {
			yvalue = -Float.valueOf(leftNum) * Float.valueOf(rightNum);
			sign = 1;
		}
		// System.out.println("Value Calculated " + yvalue);
		/*
		 * System.out.println("Value Calculated "+yvalue); System.out.println(
		 * "LeftCut "+leftCut); System.out.println("RightCut "+rightCut);
		 * System.out.println("SubString "+sb.substring(leftCut,rightCut));
		 */
		if (leftCut == 0 && rightCut == processEqu.length()) {
			// System.out.println("Number Found Final "+yvalue);
			processEqu.setLength(0);
			return yvalue;
		} else {
			// System.out.println("Before Further Processing String "+sb);
			processEqu.delete(leftCut, rightCut);
			String insY = null;
			if (yvalue == 0) {
				yvalue = yvalue + 0;
			}
			if (yvalue >= 0) {
				insY = "+" + Float.toString(yvalue);
			} else {
				insY = Float.toString(yvalue);
			}
			processEqu.insert(leftCut, insY);
			// System.out.println("Further Processing String "+sb);
			return getYValue(processEqu, x);
		}
	}

	private float performAddition(StringBuilder processEqu, int x, int index) {
		// TODO Auto-generated method stub
		String rightNum = "";
		String leftNum = "";
		int leftCut = 0;
		int rightCut = 0;
		int sign = 1;
		float yvalue = 0;

		// get right to index value
		for (int right = index + 1; right < processEqu.length(); right++) {
			rightCut = right + 1;// if operator is not found on the right then
									// the rightCut will greater than max index
			// if on the right side operator is found then substring but ignore
			// if there is + or - immediately on right
			if (right != index + 1 && (processEqu.charAt(right) == '/' || processEqu.charAt(right) == '*'
					|| processEqu.charAt(right) == '+' || processEqu.charAt(right) == '-')) {
				rightCut = right;// this position will be used to delete the
									// substring;the operator will not be
									// deleted
				break;
			} else {
				if (processEqu.charAt(right) == 'x') {
					if (processEqu.charAt(index + 1) == '-') {
						rightNum = Integer.toString(-x);
					} else {
						rightNum = Integer.toString(x);
					}
				} else {
					rightNum = rightNum + processEqu.charAt(right);
				}
			}
		}
		// System.out.println("Postion of new operator right " + rightCut);
		// get left to index value
		for (int left = index - 1; left >= 0; left--) {
			leftCut = left;// make it out of bound if no operator on left
			if (processEqu.charAt(left) == '/' || processEqu.charAt(left) == '*' || processEqu.charAt(left) == '+'
					|| processEqu.charAt(left) == '-') {
				leftCut = left + 1;
				if (processEqu.charAt(left) == '-') {
					sign = -1;
					leftCut = left;
				}
				if (processEqu.charAt(left) == '+') {
					sign = 1;
					leftCut = left;
				}
				// for substring further
				break;
			} else {
				if (processEqu.charAt(left) == 'x') {
					leftNum = Integer.toString(x);
				} else {
					leftNum = processEqu.charAt(left) + leftNum;
				}
			}
		}
		// System.out.println("Postion of new operator left "+leftCut);
		// System.out.println("The equation is " + processEqu);
		// System.out.println("Left Number Found as " + sign + " " + leftNum);
		// System.out.println("Right Number Found as " + rightNum);

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
		if (leftCut == 0 && rightCut == processEqu.length()) {
			// System.out.println("Number Found Final "+yvalue);
			processEqu.setLength(0);
			return yvalue;
		} else {
			// System.out.println("Before Further Processing String "+sb);
			processEqu.delete(leftCut, rightCut);
			String insY = null;
			if (yvalue == 0) {
				yvalue = yvalue + 0;
			}
			if (yvalue >= 0) {
				insY = "+" + Float.toString(yvalue);
			} else {
				insY = Float.toString(yvalue);
			}
			processEqu.insert(leftCut, insY);
			// System.out.println("Further Processing String "+sb);
			return getYValue(processEqu, x);
		}

	}

	private float performSubstraction(StringBuilder processEqu, int x, int index) {
		// TODO Auto-generated method stub
		String rightNum = "";
		String leftNum = "";
		int leftCut = 0;
		int rightCut = 0;
		int sign = 1;
		float yvalue = 0;

		// get right to index value
		for (int right = index + 1; right < processEqu.length(); right++) {
			rightCut = right + 1;// if operator is not found on the right then
									// the rightCut will greater than max index
			// if on the right side operator is found then substring but ignore
			// if there is + or - immediately on right
			if (right != index + 1 && (processEqu.charAt(right) == '/' || processEqu.charAt(right) == '*'
					|| processEqu.charAt(right) == '+' || processEqu.charAt(right) == '-')) {
				rightCut = right;// this position will be used to delete the
									// substring;the operator will not be
									// deleted
				break;
			} else {
				if (processEqu.charAt(right) == 'x') {
					if (processEqu.charAt(index + 1) == '-') {
						rightNum = Integer.toString(-x);
					} else {
						rightNum = Integer.toString(x);
					}
				} else {
					rightNum = rightNum + processEqu.charAt(right);
				}
			}
		}
		// System.out.println("Postion of new operator right " + rightCut);
		// get left to index value
		for (int left = index - 1; left >= 0; left--) {
			leftCut = left;// make it out of bound if no operator on left
			if (processEqu.charAt(left) == '/' || processEqu.charAt(left) == '*' || processEqu.charAt(left) == '+'
					|| processEqu.charAt(left) == '-') {
				leftCut = left + 1;
				if (processEqu.charAt(left) == '-') {
					sign = -1;
					leftCut = left;
				}
				if (processEqu.charAt(left) == '+') {
					sign = 1;
					leftCut = left;
				}
				// for substring further
				break;
			} else {
				if (processEqu.charAt(left) == 'x') {
					leftNum = Integer.toString(x);
				} else {
					leftNum = processEqu.charAt(left) + leftNum;
				}
			}
		}
		// System.out.println("Postion of new operator left "+leftCut);
		// System.out.println("The equation is " + processEqu);
		// System.out.println("Left Number Found as " + sign + " " + leftNum);
		// System.out.println("Right Number Found as " + rightNum);

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
		if (leftCut == 0 && rightCut == processEqu.length()) {
			// System.out.println("Number Found Final "+yvalue);
			processEqu.setLength(0);
			return yvalue;
		} else {
			// System.out.println("Before Further Processing String "+sb);
			processEqu.delete(leftCut, rightCut);
			String insY = null;
			if (yvalue == 0) {
				yvalue = yvalue + 0;
			}
			if (yvalue >= 0) {
				insY = "+" + Float.toString(yvalue);
			} else {
				insY = Float.toString(yvalue);
			}
			processEqu.insert(leftCut, insY);
			// System.out.println("Further Processing String "+sb);
			return getYValue(processEqu, x);
		}

	}

	private float performNoOperator(StringBuilder processEqu, int x) {
		// System.out.println("Equation NoOperator "+sb);
		int sign = 1;
		int signpos = processEqu.indexOf("-");
		String rightNum = "";
		if (signpos == 0) {
			sign = -1;
		}
		if (signpos == 0 || processEqu.indexOf("+") == 0) {
			if (processEqu.indexOf("x") != -1) {
				processEqu.setLength(0);
				return sign * x;
			} else {
				for (int right = 1; right < processEqu.length(); right++) {
					rightNum = rightNum + processEqu.charAt(right);
				}
				processEqu.setLength(0);
				return sign * Float.valueOf(rightNum);
			}
		} else {
			if (processEqu.indexOf("x") != -1) {
				processEqu.setLength(0);
				return x;
			} else {
				for (int right = 0; right < processEqu.length(); right++) {
					rightNum = rightNum + processEqu.charAt(right);
				}
				processEqu.setLength(0);
				return Float.valueOf(rightNum);
			}
		}

	}

}
