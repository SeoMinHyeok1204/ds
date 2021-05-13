userinput = input()
argv = userinput.split(" ")

input = argv[1]

f = open(input, "r")
lNum = 0
wNum = 0

for line in f:
    line = line.replace("\n","")
    lNum+=1
    words = line.split(" ")
    for word in words:
        wNum+=1

print("# of line : "+str(lNum))
print("# of word : "+str(wNum))
        
