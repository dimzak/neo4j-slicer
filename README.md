# Neo4j Spliter
Transfer part of your neo4j database with ease.

# Example run
```
java -jar neo4jslicer-1.0.jar -m=export -e=bolt://neo4j:neo4j@127.0.0.1:7687 -q="MATCH (a:Address) RETURN *" -f=export.cypher
```

# Args
```
java -jar neo4jslicer-1.0.jar
usage: neo4jslicer
    --e <export>   Export neo4j bolt url
    --f <file>     Cypher file location
    --i <import>   Import neo4j bolt url
    --m <mode>     Choose mode between import, export, all
    --q <query>    Cypher Match Query - assume return is *

```