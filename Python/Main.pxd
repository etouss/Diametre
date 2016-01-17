from cpython cimport bool
from cpython cimport list
#from cpython cimport queue
cimport cqueue


#from libcpp.deque cimport deque

cpdef int test(str x, str y)

cdef class Noeud:
    cdef public int number,distance
    cdef public bool couleur
    cdef public list fils,chemin

    cpdef int parcourSimple(self,Queue* queue)
