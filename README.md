# calculated

### My Calculator

My Calculator is a versatile Reverse Polish Notation (RPN) calculator implemented in Java. 
It provides a user-friendly interface for performing various mathematical calculations. This 
document will guide you on how to use My Calculator both from the command line and integrated 
development environments (IDEs).

## Main Features

- Shunting-Yard Algorithm and Reverse Polish Notation (RPN) support for intuitive mathematical expressions.

- Basic arithmetic operations: addition, subtraction, multiplication, division, 
and exponentiation.

- Trigonometric functions: sin, cos, tan, cot, arcsin, arccos, arctan, and arcctg.

- Logarithmic functions: ln, log10, and sqrt.

- Error handling for invalid expressions, order of operations, and division by zero.

- Support for both command-line and IDE-based usage.

## Usage

### IDE

To use My Calculator from an integrated development environment (IDE) like IntelliJ IDEA, Eclipse, or others, follow these steps:

1. IDE Setup: Open the calculator.java file within your chosen IDE.

2. Run Configuration: Set up a run configuration for the calculator class and execute it.

3. Interactive Menu: You will encounter the same interactive menu as in the command-line version.
	
I used the Java 21 version here on an IntelliJ IDE. 

### From the Command Line

To use My Calculator from the command line, follow these steps:

1. **Java Requirement:** Ensure that you have Java JDK/JRE installed on your system. 

2. **Compilation:** Compile the `calculator.java` file using the `javac` command: CLI enter: javac calculator.java

###Limitations

My Calculator is designed to handle a broad range of mathematical expressions. However, it is worth noting that 
the program is newly developed and only moderately tested at best due to time constraints. (17 days) With that, 
extremely large integers could cause an exception, which should be caught, or in the worst case crash the program.

###Contributing

Contributions to My Calculator are welcomed and encouraged. If you wish to contribute, please feel free to submit a pull request 	
or open an issue on the GitHub repository.
