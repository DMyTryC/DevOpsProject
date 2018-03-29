# DevOpsProject

## Utilisation de maven
Pour cette commande on peut specifier un parametre -Tx (x*4 etant le nombre de threads a utiliser)

mvn [cycle de vie]https://github.com/DMyTryC/DevOpsProject/blob/master/README.md#9


Les cycle de vie : 
------------------
`L'execution d'une de ces etapes va executer toute etape d'avant`

validate :  valide que le projet est correct et que toutes les infos necessaires sont disponibles

compile : compile

test : lance les test

package :  package les sources compil ́ees dans un format distribuable (par ex JAR)

integration-test : test d'integration

verify :  Lance les tests pour verifier la qualite du package

install :  Installe le package dans le depot local

deploy :  Copie le package final dans un depot distant pour le partager

(Repris depuis le cours de DevOps de Thomas Ropars sur l'integration continue)
