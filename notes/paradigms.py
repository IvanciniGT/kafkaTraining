
# ask for a number... print every number from 0 to that number

# Imperative style
max=int(input("Give me a number: "))
for counter in range(0,max):
    print(counter)

# Procedural style
def count():
    max=int(input("Give me a number: "))
    for counter in range(0,max):
        print(counter)
    
count()

# OOP 
class Counter:
    
    def __init__(self):
        self.max=int(input("Give me a number: "))

    def count(self):
        for counter in range(0,self.max):
            print(counter)

my_counter=Counter()
my_counter.count()

# Functional programming
print_function=print # Since I can do this, I can supply a function as an argument to anotherfunction
                     # I can return a function from a function
                     

def triple(number):
    return number * 3




# Lambda expressión = Anonymous function
my_list=[1,2,3,4] 
#triple_values = list(map(triple, my_list))
triple_values = list(map(lambda number: number * 3, my_list))
print(triple_values)

my_list=[1,2,3,4] 
map(
    lambda number: number * 2,
    map(lambda number: number * 3, my_list)
    )

# Language with it compiler or interpreter
# The language is not java.exe nor javac.exe
"""
.java ---> javac ---> .class    ---> java
JAVA                    BYTECODE

LANGUAGE: 
    Syntax
    Grammar
    Semanthics
"""