class stack:
    def __init__(self):
        self.arr = []
    def push(self, obj):
        self.arr.append(obj)
    def pop(self):
        obj = self.arr[len(self.arr)-1]
        del self.arr[len(self.arr)-1]
        return obj
    def isEmpty(self):
        return len(self.arr)==0
    def top(self):
        return self.arr[len(self.arr)-1]
    
def calc(op, a, b):
    if(op == '+'):
        return b+a
    elif(op == '-'):
        return b-a
    elif(op == '*'):
        return b*a
    elif(op == '/'):
        return b/a
        

input = input("계산식 입력 : ")
content = input.split(" ")
num = stack()
operator = stack()
result = 0
text = ""

for word in content:
    if(word == '+' or word == '-' or word == '*' or word == '/'):
        if(operator.isEmpty()):
            operator.push(word)
        else:
            if(word == '+' or word == '-'):
                result = calc(operator.pop(), int(num.pop()), int(num.pop()))
                num.push(result)
                operator.push(word)
            else:
                operator.push(word)
    else:
        if(not operator.isEmpty()):
            if(operator.top() == '*' or operator.top() == '/'):
                result = calc(operator.pop(), int(num.pop()), int(word))
                num.push(result)            
            else:
                num.push(word)
        else:
            num.push(word)
    text += str(word)


print("계산식 출력 : "+text+" = "+str(calc(operator.pop(), int(num.pop()), int(num.pop()))))
