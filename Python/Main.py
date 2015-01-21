#!/usr/local/bin/python3
from collections import deque
import sys

class Noeud():

    def __init__(self, i):
        self.number = i
        self.fils = list()
        self.couleur = True
        self.distance = 0
        self.chemin = list()

    def parcourSimple(self, queue):
        self.couleur = False
        queue.append(self)
        while queue:
            courant = queue.popleft()
            for succ in courant.fils:
                if succ.couleur:
                    succ.couleur = False
                    succ.distance = courant.distance + 1
                    queue.append(succ)
        return courant.distance

    def parcourLast(self, queue):
        first = self
        self.couleur = False
        queue.append(self)
        while queue:
            courant = queue.popleft()
            for succ in courant.fils:
                if succ.couleur:
                    succ.couleur = False
                    succ.distance = courant.distance + 1
                    queue.append(succ)
                    if succ.distance > first.distance:
                        first = succ
                    elif succ.number < first.number:
                        first = succ
        return first

    def parcourMiddle(self, queue):
        first = self
        self.couleur = False
        queue.append(self)
        while queue:
            courant = queue.popleft()
            for succ in courant.fils:
                if succ.couleur:
                    succ.couleur = False
                    succ.distance = courant.distance + 1
                    succ.chemin = courant.chemin + [courant]
                    queue.append(succ)
                    if succ.distance > first.distance:
                        first = succ
                    elif succ.number < first.number and succ.distance == first.distance:
                        first = succ
        return first.chemin[int(len(first.chemin) / 2)]


class Graph():

    def __init__(self, path):
        file = open(path, "r")
        if not file:
            print("erreur : le fichier n'existe pas")
            sys.exit(0)
        self.noeuds = [Noeud(i) for i in range(1, int(file.readline())+1)]
        self.diametre = 0
        self.queue = deque()

        # Quelques testes sur le fichier ?

        for line in file.readlines():
            arrete = line[0:-1].split(" ")
            # print(self.noeuds)
            self.noeuds[
                int(arrete[0]) - 1].fils.append(self.noeuds[int(arrete[1]) - 1])
            self.noeuds[
                int(arrete[1]) - 1].fils.append(self.noeuds[int(arrete[0]) - 1])
        file.close()

    def nParcours(self):
        for courant in self.noeuds:
            self.clean()
            self.diametre = max(
                self.diametre, courant.parcourSimple(self.queue))
        return self.diametre

    def habibParcours(self):
        self.clean()
        last = self.noeuds[0].parcourLast(self.queue)
        self.clean()
        middle = last.parcourMiddle(self.queue)
        self.clean()
        last = middle.parcourLast(self.queue)
        self.clean()
        return last.parcourSimple(self.queue)

    def clean(self):
        for n in self.noeuds:
            # increment couleur raz Distance sur init ?
            n.couleur = True
            n.distance = 0
            #n.chemin = []


def test(s1, s2):
    if len(sys.argv) != 3:
        print("erreur : usage = <e/h> <file.pfg>")
        sys.exit(0)
    if s1 == "e":
        graph = Graph(s2)
        print(graph.nParcours())
    elif s1 == "h":
        graph = Graph(s2)
        print(graph.habibParcours())
    else:
        print("erreur : usage = e ou h en premier argument")
