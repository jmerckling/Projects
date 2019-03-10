#include <stdio.h>

int binarySum(int x, int y)
{
    int carry;
    while (y != 0)
    {
        //carry shifts all 1's to the left, where x and y are 1
        carry = (x & y) << 1;
        //find the sum by using XOR opperator
        x = x ^ y;
        y = carry;
    }
    //final result is stored in x
    return x;
}

int binarySub(int x, int y)
{
    int carry;
    
    //swap x and y values if y is greater
    if(y > x)
    {
        x ^= y;
        y ^= x;
        x ^= y;   
    }
    //get the twos compliment of y and set it to y
    y = binarySum(~y, 1);
    
    //now add x to the twos complimented y
    while (y != 0)
    {
       //shift shift all 1's to the left, where x and y are 1 
        carry = (x & y) << 1;
        //find the sum using XOR
        x = x ^ y;
        y = carry;
    }
    //final result in x
    return x;
}

//test in main

int main()
{
    int val1 = 0; 
    int val2 = 0;
    
    while(val1 < 1 || val1 > 999)
    {
        printf("Input the first value between 1 - 999: ");
        scanf("%d", &val1);
    }
    
    while(val2 < 1 || val2 > 999)
    {
        printf("Input the second value between 1 - 999: ");
        scanf("%d", &val2);
    }
    
    printf("Binary Addition result: %d\n", binarySum(val1, val2));
    printf("Binary Subtraction result: %d\n", binarySub(val1, val2));
}//end main