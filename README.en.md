# Currency 

#### Description
Each payment includes a currency and an amount. The program should output a list of all the currency and amounts to the console once per minute. The input can be typed into the command line, and optionally also be loaded from a file when starting up.

Sample input: 
USD 1000
HKD 100
USD -100
CNY 2000
HKD 200

Sample output:
USD 900
CNY 2000
HKD 300



When your Java program is run, a filename can be optionally specified. The format of the file will be one or more lines with Currency Code Amount like in the Sample Input above, where the currency may be any uppercase 3 letter code, such as USD, HKD, CNY, NZD, GBP etc. The user can then enter more lines into the console by typing a currency and amount and pressing enter. Once per minute, the output showing the net amounts of each currency should be displayed. If the net amount is 0, that currency should not be displayed. When the user types "quit", the program should exit. 

#### Instructions

1. set your initial document with filepath in "*\src\main\resources\initial.txt".

2. set your exchange rate with filepath in "*\src\main\resources\exchangeRate.properties".

   

