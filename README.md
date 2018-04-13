# DevOpsProject

## Utilisation de maven
Pour cette commande on peut specifier un parametre -Tx

mvn [cycle de vie]


Les cycle de vie : 
------------------
L'execution d'une de ces etapes va executer toute etape d'avant`

validate :  valide que le projet est correct et que toutes les infos necessaires sont disponibles

compile : compile

test : lance les test

package :  package les sources compil ́ees dans un format distribuable (par ex JAR)

integration-test : test d'integration

verify :  Lance les tests pour verifier la qualite du package

install :  Installe le package dans le depot local

deploy :  Copie le package final dans un depot distant pour le partager

(Repris depuis le cours de DevOps de Thomas Ropars sur l'integration continue)


MAVEN SITE :
------------
Pour la couverture de code, utiliser la commande "mvn site" qui va faire un site dans "target/site/index.html"