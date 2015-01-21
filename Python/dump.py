import pstats
pstats.Stats('out').strip_dirs().sort_stats("cumulative").print_stats()
