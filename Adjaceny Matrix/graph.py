# -*- coding: utf-8 -*-
"""
Created on Sun Nov 27 19:51:24 2016

@author: matti
"""
# var to hold string values represented at each vertex
A="A"
B="B"
C="C"
D="D"
E="E"
F="F"

# vertex and edge mappings represented in a 'YUCCA' graph
V = set([A,B,C,D,E,F])
E = set([(A,B),(A,C),(A,D),(A,E),(A,F),(B,C),(C,B),(C,D),(D,E),(F,E)])


in_degree_count = {}
out_degree_count = {}

# instantiates each dictionary to map zero, O(N) 
for u in V:
  in_degree_count[u] = 0
  out_degree_count[u] = 0

# iterates each edge and increments for each u,v mapping, O(M)
for u,v in E:
  out_degree_count[u] += 1
  in_degree_count[v] += 1

if((out_degree_count[u]==5)&(in_degree_count[u]==0)):
    print "Adjacent matrix represents graph of YUCCA classification"
    print 'out_degree({0}):'.format(u), out_degree_count[u]
    print 'in_degree({0}):'.format(u), in_degree_count[u]

else:
    print "Adjacent matrix is not a graph representation of YUCCA classification"