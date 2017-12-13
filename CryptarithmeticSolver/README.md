This is program that can solve a cryptarithmetic problem. 
Each letter must be assigned a unique digit from zero to nine, and no term is allowed to have leading zeros.
Exampe problem:

F O U R - T W O = T W O

The program allows a user to enter the subtrahend, the minuend, and the difference. 
For simplicity, the program limits the subtrahend term to four letters and the minuend and difference both to three letters. 
The user can input any combination of letters (i.e., not only FOUR-TWO=TWO). The program outputs all valid assignments of the variables and digits, or outputs that no valid assignment exists.
Uses the Most Constrained Variable, Most Constraining Variable, and Least Constraining Value heuristics in backtracking to solve the problem.
