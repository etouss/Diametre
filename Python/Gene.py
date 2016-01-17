import random

file = open("graphEtienne.pfg", "w")
random.seed()
nb_noeud = 1000000
file.write(str(nb_noeud)+"\n")

nb1 = 0
nb2 = 1
while nb1 < nb_noeud:
    nb1 = nb1+1
    nb_arrete = 20
    nb2 = random.randint(nb1, nb_noeud)
    a = 0
    while a < nb_arrete and nb2 < nb_noeud:
        nb2 = random.randint(nb2, nb_noeud)
        a = a+1
        file.write(str(nb1)+" "+str(nb2)+"\n")
file.close()
