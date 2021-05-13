userinput = input()
argv = userinput.split(" ")

copyName = argv[1]
pasteName = argv[2]

copy = open(copyName, "r")
content=""

while(1):
    line = copy.readline()
    if(not line):
        break
    content += line
copy.close()

paste = open(pasteName, "w")
paste.write(content)
paste.close()
