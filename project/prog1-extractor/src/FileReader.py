#Imported packages
import codecs

print("Write which file to read :)") #Prompts user to enter Unity or Tupperware
print("Enter 1: Unity 10K")
print("Enter 2: Tupperware 10K")

file_name = ""
company10K = ''
input = input("Please input now\n")
if(input == '1'):
    file_name = 'UNITY - December 31, 2022.txt'
    company10K = 'UNITY10K'
elif(input == '2'):
    file_name = 'TUPPERWARE - December 31, 2022.txt'
    company10K = 'TUPPERWARE10K'
else: #None of the valid inputs were given
    print("Invalid input given, showing Unity data")
    file_name = 'UNITY - December 31, 2022.txt'
    company10K = 'UNITY10K'

#Different variables for stats
num_lines = 0
num_words = 0
num_chars = 0
num_parts = 0
parts = []

with codecs.open('data/'+file_name, 'r','utf8') as f: #Open file to read
    for line in f: #Reads each line in the file
        if(line.__contains__("PART ")): #Counts all parts
            parts.append(line)
        
        words = line.split()

        num_lines += 1
        num_words += len(words)
        num_chars += len(line)

print("words: "+num_words.__str__()) #Prints all stats to console
print("chars: " + num_chars.__str__())
print("lines: " + num_lines.__str__())
print("parts: " + len(set(parts)).__str__())

file1 = codecs.open('data/'+company10K.lower()+'stats.txt','w', 'utf8') #Opens file to write to

file1.write(company10K + ' STATS:\n') #Prints 10K stats to file
file1.write("words: "+num_words.__str__()+"\n")
file1.write("chars: "+num_chars.__str__()+"\n")
file1.write("lines: "+num_lines.__str__()+"\n")
file1.write("parts: "+len(set(parts)).__str__()+"\n")

file1.close()