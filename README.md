# Rapport Btree
Nous avons créé deux structures de données basées sur le nœud Btree ayant m pair et impair, dans les paquets tp_pair et tp_impair, respectivement.

# m est impaire
Dans le cas où m est impaire, qui est également le cas décrit dans le cours, nous avons également testé les exemples dans slides, et les tests ont tous été réussis.
Voici une brève analyse du code
### Classes incluses dans le code
il y a 3 classe:
- BTree<br>
- BTreeNode<br>
- ParentEntry<br>
ParentEntry,est utilisée pour représenter la relation entre un nœud et son nœud parent,il y a deux élément :
  - BTreeNode représente le nœud parent du nœud 
  - l'index représente la position dans le nœud parent)

### L'idée principale de la conception est d'utiliser 
- d'utiliser une liste(children) dans chaque BtreeNode pour contenir tous ses enfants 
- d'ajouter l'attribut parentEntry pour représenter sa relation avec le nœud parent.

Nous connaissons la relation entre un nœud et son parent (attribut parentEntry), et nous connaissons également la relation entre un nœud et ses enfants (attribut chlidren). Lorsque le nœud est plein et que nous devons le diviser, nous devons réajuster ces deux attributs. C'est pourquoi, chaque fois que nous divisons, nous générons deux nœuds enfants, un gauche et un droit, qui héritent du nœud parent du nœud original, le nœud gauche héritant du nœud enfant de gauche et le nœud droit héritant du nœud enfant de droite.

# m est paire
La situation est complètement différente lorsque m est paire, et nous pouvons nous passer de l'entrée parentale car nous n'avons pas besoin du nœud temporaire
Par exemple, lorsque m = 4, un nœud complet est /2 4 6/, et peu importe que nous insérions 1, ou 3, ou 7, ou 5, nous pouvons faire remonter 4 au parent sans violer les principes d'un arbre B, au lieu de la possibilité de faire remonter un clé qui vient d'être inséré, comme cela serait possible lorsque m est impair.

### Classes incluses dans le code
il y a 3 classe:
- BTree<br>
- BTreeNode<br>
- ResearchOutCome<br>
  ResearchOutCome ：Recherche la valeur clé donnée dans le nœud de l'arbre B.
  * Ce résultat se compose de deux parties. La première partie indique si la recherche a abouti ou non.
  * Si la recherche a réussi, la deuxième partie indique la position de la valeur clé donnée dans le nœud de l'arbre B.
  * Si la recherche a échoué, la deuxième partie indique l'endroit où la valeur clé donnée doit être insérée.


# Attention
Pour plus d'informations, veuillez vous référer à la JavaDoc.
veuillez exécuter MainImpairTest et MainPairTest pour tester 

(note : pour m en tant qu'arbre B impair, nous ne pouvons pas insérer le même nombre, pour m en tant qu'arbre B pair, nous pouvons insérer le même nombre, dans l'insertion du même nombre, les deux types d'arbre B seront le même nombre dans l'impression de l'invite).