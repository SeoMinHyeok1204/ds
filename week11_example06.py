import sys

copyName = sys.argv[1]
pasteName = sys.argv[2]

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
