# Passerelle Connectivitée mobile

## Applications externes

  Afin de fonctionner, notre application utilise un parser de fichier shapefile. Celui-ci est disponible à l'adresse suivante sous licence publique générale limitée GNU :
```
https://github.com/delight-im/Java-Shapefile-Parser
```

## Utilisation du logiciel

  Le but du logiciel est d'extraire des fichiers shapefiles des données qui seront par la suite mises en base de données. Les fichiers shapefiles sont disponibles en open dta sur :
```
https://www.data.gouv.fr/fr/datasets/monreseaumobile/
```
  Il est important de noter qu'il faut utiliser les fichiers shapefiles et non d'autres afin de faire fonctionner la passerelle. Un exemple de fichiers contenant des informations utilisable par la passerelle est :
```
https://www.data.gouv.fr/s/resources/monreseaumobile/20180228-173701/Cartes_4G_2018_01_01.7z
```

  Une fois le fichier téléchargé il suffit d'extraire l'archive présente à l'intérieur et de lancer l'application passerelle. Vos identifiants de connexion vous seront demandés afin de pouvoir utiliser le service. Une fois ceux-ci renseignés et correct vous aurez accès à la fenêtre d'administration. 
  Celle-ci vous permettra de choisir un fichier dont l'extension est ".shp" afin d'en extraire et insérer en base de données les informations en cliquant sur "Parcourir" puis sur "Valider". Si votre choix de fichier a bien été retenu par l'application vous devriez voir le chemin du fichier écrit au-dessus du bouton "Parcourir".

## Fichier jar
  
  L'application java a déjà été compilé sous forme de jar sous intellij IDEA dans le dossier `\classes\artifacts\Projet_connectivitee_jar`.

## Problème de compatibilitée

  Ce logiciel utilise un parser pour récupérer les informations des fichiers ".shp". Malheureusement la version de la bibliothèque geotools utilisée par le parser ne dispose pas du [fix](https://github.com/geotools/geotools/wiki/FactoryRegistry-Refactoring-for-Java-9-Compatibility) permettant son usage sur les versions de la jdk 9 et au-delà. 
  
  Si l'utilisateur dispose d'une version de java 9 ou 10 installée sur son ordinateur il ne lui sera pas possible de faire fonctionner la passerelle sans manipulations.
  
  ### Windows
  
  Il  est possible de résoudre ce problème de deux façons différentes :
  
  #### Manipulation nécessaire à chaque lancement
  
  Utilisez les commandes suivantes dans l'ordre  :
```
  Utilisez en un premier temps la commande "cd" pour vous déplacez dans le dossier contenant le fichier ".jar".
  
  set path="C:\Program Files\Java\jre1.8.0_172\bin";%path%
  
  java -jar nomduJar.jar
```
  La valeur de path="" doit être celle du chemin vers un dossier `\bin` d'une jre 1.8. Dans cet exemple c'est le chemin du dossier `\bin` de la jre 1.8 patch 172.
  > Pensez donc à mettre un chemin correct pour votre machine !
  
  ##### Utilisation d'un fichier batch 
 
   Il existe sous windows un type de fichier, le ".bat" qui permet de créer des scripts qui seront exécutés. Il se trouve qu'il est possible de les utiliser afin d'exécuter des lignes de commandes. Si vous souhaitez vous facilitez la vie vous pouvez utiliser la solution expliquée au-dessus et mettre les 2 lignes :
    
```  
  set path="C:\Program Files\Java\jre1.8.0_172\bin";%path%
  
  java -jar nomduJar.jar
```
  > En mettant le fichier ".bat" dans le même dossier que le fichier ".jar" vous n'aurez plus qu'à cliquer sur le fichier .bat pour lancer votre application !
  
  #### Manipulation unique
  
  L'autre solution permet d'utiliser la passerelle beaucoup plus facilement, elle consiste à dire à votre ordinateur quelle version de java il doit utiliser lorsque vous tentez de lancer l'application. 
  Cette manipulation possède cependant un problème, elle fait en sorte que java 8 devienne la version de java utilisée par défaut. Cela a pour conséquence de vous forcer à faire la manipulation décrite au-dessus avec java 10 si vous souhaitez lancer une application avec spécifiquement la version 10 de java. Nous conseillons donc l'utilisation de la méthode décrite au-dessus.
  
  Cette méthode consiste à modifier la variable d'environnement "Path". Pour cela utiliser le [guide officiel de java.](https://www.java.com/fr/download/help/path.xml) 
  > Pensez toutefois que windows utilisera le premier chemin valide qu'il trouvera pour utiliser java, si le chemin de java 8 est placée plus loin que le chemin d'une version 9 ou 10 dans la liste cette méthode ne fonctionnera pas, il faut placer le chemin de java 1.8 **AVANT**.
  
  ### Linux
  
  Il  est possible de résoudre ce problème de deux façons différentes :
  
   #### Manipulation nécessaire à chaque lancement
  
   Il est possible de lancer java 1.8 en utilisant son chemin. Si l'on considère que la jre 1.8 a été installée dans "/usr/lib/jvm/jdk1.8.0_172" alors la commande, en se plaçant dans le dossier où le jar se trouve, serait :
  
  ```
  /usr/lib/jvm/jdk1.8.0_172/bin/java -jar nomduJar.jar
  ```
    
  > Encore une fois pensez à bien vérifier quels sont les chemins pour votre machine !
  
  #### Manipulation unique
  
   Les problèmes sous linux sont les mêmes que pour windows et la manière de faire se trouve sur la même page. Je vous renvoie donc vers la section windows qui contient les informations nécessaires.
    
