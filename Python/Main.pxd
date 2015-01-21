cpdef int test(str x, str y)

cdef class Noeud:
    cdef public int number,distance
    cdef public list fils,chemin
    cdef public bool couleur

    def __init__(self, i):
        self.number = i
        self.fils = list()
        self.couleur = True
        self.distance = 0
        self.chemin = list()
