package com.example.g15_bugkiller;

import java.util.*;

public class Map {

    private Input info;   //eingelesene Input-Daten
    private Field[][] map;    //hier als endliche Map implementiert: außenrum nur noch default-Felder
    private int maxNumberOfTrials;   //maximale Zahl an Versuchen zur Kartengenerierung
    private Coordinate maxMapSize;  //speichert die Größe der endlichen, tatsächlich gespeicherten Teilkarte, auf der Kacheln liegen
    private boolean defaultMapIsValid;  //speichert, ob die Karte aus default-Kacheln valide ist
    private boolean mapGenerated;  //speichert, ob im Moment eine valide Karte generiert wurde
    private Field [][] connectedByMap; //speichert die schon besuchten Zellen in der Tiefensuche, die in valideConnectBy(...) durchgeführt wird


    public Map(Input info, int maxNumberOfTrials) {
        this.info = info;
        this.maxMapSize = info.getMaxMapSize();
        this.maxNumberOfTrials = maxNumberOfTrials;
        this.defaultMapIsValid = checkIfDefaultMapIsValid();
        this.map = generateMap();
    }

    public Field[][] getMap() {
        return map;
    }

    private Field[][] createEmptyMap (){
        return new Field[maxMapSize.getX()][maxMapSize.getY()];
    }

    public Field[][] generateMap(){   //Kartengenerierung
        mapGenerated = false;
        Field[][] newMap = generateMapWithDefaultTiles(); //erstelle Map mit nur default-Zellen

        outerloop: //cf. https://stackoverflow.com/questions/886955/how-do-i-break-out-of-nested-loops-in-java
         for(int trialCounter = 1; trialCounter <= maxNumberOfTrials; trialCounter++){ //Zähle Versuche

             List<TilesAt> tilesAts = info.getTilesAts();
             int numberOfTilesAts = tilesAts.size();

             String firstTilesAtKind = tilesAts.get(0).getKindName();  //Wähle für erstes TilesAt zufällig eine Version aus
             Random rand = new Random();    //cf. https://www.geeksforgeeks.org/randomly-select-items-from-a-list-in-java/
             int firstRandomIndex = rand.nextInt(info.getTileWithName(firstTilesAtKind).getVersions().size());
             TileVersion firstSelectedVersion = info.getTileWithName(firstTilesAtKind).getVersions().get(firstRandomIndex);

             boolean[][] indexMap = createIndexMapWithOneTile(tilesAts.get(0).getCoordinate(), firstSelectedVersion);
             Field[][] generatedMap = createMapWithOneTile(tilesAts.get(0).getCoordinate(), firstSelectedVersion);   //erstelle (Index-)Karte nur mit ausgewählter Version aus erstem tilesAt

             for(int tilesAtsIterator = 1; tilesAtsIterator < numberOfTilesAts; tilesAtsIterator++){ //für jede weitere TilesAt wähle zufällig geeignete Version aus und erstelle (Index-)Karte nur mit dieser Version
                 String currentTilesAtKind = tilesAts.get(tilesAtsIterator).getKindName();

                 int randomIndex = rand.nextInt(info.getTileWithName(currentTilesAtKind).getVersions().size());   //cf. https://www.geeksforgeeks.org/randomly-select-items-from-a-list-in-java/
                 TileVersion currentSelectedVersion = info.getTileWithName(currentTilesAtKind).getVersions().get(randomIndex);

                 boolean[][] currentIndexMap = createIndexMapWithOneTile(tilesAts.get(tilesAtsIterator).getCoordinate(), currentSelectedVersion);
                 Field[][] currentGeneratedMap = createMapWithOneTile(tilesAts.get(tilesAtsIterator).getCoordinate(), currentSelectedVersion);


                 List<Field[][]> mapList = new ArrayList<>();  //erstelle Liste, die bisher generierte Karte und Karte mit neu ausgewählter Kachel enthält...
                 mapList.add(generatedMap);
                 mapList.add(currentGeneratedMap);

                 List<boolean[][]> indexMapList  = new ArrayList<>(); //sowie die korrespondierende Liste aus Index-Karten
                 indexMapList.add(indexMap);
                 indexMapList.add(currentIndexMap);

                 if(checkIfMapsAgree(mapList, indexMapList)){   //prüfe: bisher generierte Karte und Karte mit neuer Kachel kompatibel?
                     generatedMap = uniteMaps(mapList, indexMapList); //wenn ja: vereinige sie

                     for(int rowIterator = 0; rowIterator < maxMapSize.getX(); rowIterator++){   //update indexMap
                         for(int columnIterator = 0; columnIterator < maxMapSize.getY(); columnIterator++){
                             indexMap[rowIterator][columnIterator] = indexMap[rowIterator][columnIterator] | currentIndexMap[rowIterator][columnIterator];
                         }
                     }

                 } else {
                     continue outerloop; //cf. https://stackoverflow.com/questions/886955/how-do-i-break-out-of-nested-loops-in-java
                 }
             }

           if(valideConnectBy(generatedMap)) {
                 newMap = generatedMap;
                 mapGenerated = true;
                 break;
           }
         }

        if(!defaultMapIsValid){
            newMap = null;
        }

        return newMap;

    }


    private boolean checkIfDefaultMapIsValid(){
        boolean defaultMapIsValid = false;

        int numberOfTilesAts = info.getTilesAts().size();

        TileVersion[] selectedTileversions = new TileVersion[numberOfTilesAts];
        List<Field[][]> mapsWithOneTile = new ArrayList<Field[][]>();
        List<boolean[][]> indexMapsWithOneTile = new ArrayList<boolean[][]>();

        for(int tilesAtIterator = 0; tilesAtIterator < numberOfTilesAts; tilesAtIterator++){   //wähle für jede tilesAt die default-Version aus und speichere diese in selectedTileversions
            TilesAt currentTilesAt = info.getTilesAts().get(tilesAtIterator);
            String kindName = currentTilesAt.getKindName();
            selectedTileversions[tilesAtIterator] = info.getTileWithName(kindName).getVersions().get(currentTilesAt.getDefaultValue());
            mapsWithOneTile.add(createMapWithOneTile(currentTilesAt.getCoordinate(), selectedTileversions[tilesAtIterator]));    //erstelle für jede ausgewählte tileversion aus selectedTileversions eine Karte, in der nur eine Kachel dargestellt wird und default sonst
            indexMapsWithOneTile.add(createIndexMapWithOneTile(currentTilesAt.getCoordinate(), selectedTileversions[tilesAtIterator])); //erstelle für jede ausgewählte tileversion aus selectedTileversions eine "Indexkarte", in der genau in den Zellen, in der die ausgewählte Kachel eingefügt wird, true steht
        }

        if(checkIfMapsAgree(mapsWithOneTile, indexMapsWithOneTile)){
            Field[][] defaultMap = uniteMaps(mapsWithOneTile, indexMapsWithOneTile);

            if(valideConnectBy(defaultMap)){
                defaultMapIsValid = true;
            }
        }

        return defaultMapIsValid;
    }

    private Field[][] generateMapWithDefaultTiles(){   //erstelle Map mit default-Tiles
        Field[][] defaultMap;

        int numberOfTilesAts = info.getTilesAts().size();

        TileVersion[] selectedTileversions = new TileVersion[numberOfTilesAts];
        List<Field[][]> mapsWithOneTile = new ArrayList<Field[][]>();
        List<boolean[][]> indexMapsWithOneTile = new ArrayList<boolean[][]>();

        for(int tilesAtIterator = 0; tilesAtIterator < numberOfTilesAts; tilesAtIterator++){                 //wähle für jede tilesAt die default-Version aus und speichere diese in selectedTileversions
            TilesAt currentTilesAt = info.getTilesAts().get(tilesAtIterator);
            String kindName = currentTilesAt.getKindName();
            selectedTileversions[tilesAtIterator] = info.getTileWithName(kindName).getVersions().get(currentTilesAt.getDefaultValue());
            mapsWithOneTile.add(createMapWithOneTile(currentTilesAt.getCoordinate(), selectedTileversions[tilesAtIterator]));    //erstelle für jede ausgewählte tileversion aus selectedTileversions eine Karte, in der nur eine Kachel dargestellt wird und default sonst
            indexMapsWithOneTile.add(createIndexMapWithOneTile(currentTilesAt.getCoordinate(), selectedTileversions[tilesAtIterator])); //erstelle für jede ausgewählte tileversion aus selectedTileversions eine "Indexkarte", in der genau in den Zellen, in der die ausgewählte Kachel eingefügt wird, true steht
        }


        defaultMap = uniteMaps(mapsWithOneTile, indexMapsWithOneTile);   //vereinige alle einzelnen default-Karten, ggf. passen Überlappungen nicht zusammen!

        return defaultMap;
    }

    private Field[][] createMapWithOneTile(Coordinate coordinate, TileVersion tileVersion){  //erstellt Karte, auf der nur eine Kachel ist und default-Zellen sonst
        Field[][] newMap = createEmptyMap();
        int x = coordinate.getX();
        int y = coordinate.getY();

        for(int rowIterator = 0; rowIterator < maxMapSize.getX(); rowIterator++){                      //erstelle map aus default-Werten
            for(int columnIterator = 0; columnIterator < maxMapSize.getY(); columnIterator++){
               newMap[rowIterator][columnIterator]= new Field(info.getDefaultField().getGegenstand());
            }
        }

        int numberOfRowsInTileVersion = tileVersion.getEntries().size();
        int biggestRowInTileVersion = tileVersion.getBiggestRowInTileVersion();

        for(int columnIterator = 0; columnIterator < numberOfRowsInTileVersion; columnIterator++){   //gehe alle Zeilen der Tileversion durch
            List<Field> currentRow = tileVersion.getEntries().get(columnIterator);
            int lengthOfCurrentRowInTileVersion = currentRow.size();

            for(int rowIterator = 0; rowIterator < biggestRowInTileVersion; rowIterator++){   //gehe in jeder Zeile alle Spalten durch
                if (rowIterator >= lengthOfCurrentRowInTileVersion){
                    //newMap[x + rowIterator][y + columnIterator].setInKachel();
                    continue;
                }
                if(!currentRow.get(rowIterator).getType().equals("-")) {
                    newMap[x + rowIterator][y + columnIterator] = currentRow.get(rowIterator);   //füge an der passenden Stelle ein
                } else {
                    newMap[x + rowIterator][y + columnIterator] = info.getDefaultField();   //"-" heißt: Loch in der Kachel --> Füge an dieser Stelle default-Zelle ein
                }
                //newMap[x + rowIterator][y + columnIterator].setInKachel();
            }
        }
        return newMap;
    }

    private boolean[][] createIndexMapWithOneTile(Coordinate coordinate, TileVersion tileVersion){   //gibt "Index-Karte" mit nur einer Kachel zurück: Zelle bekommt true genau dann wenn Kachel auf dieser Zelle ist
        boolean[][] indexMap = new boolean[maxMapSize.getX()][maxMapSize.getY()];
        int x = coordinate.getX();
        int y = coordinate.getY();

        for(int rowIterator = 0; rowIterator < maxMapSize.getX(); rowIterator++){                      //erstelle map aus false-Werten
            for(int columnIterator = 0; columnIterator < maxMapSize.getY(); columnIterator++){
                indexMap[rowIterator][columnIterator] = false;
            }
        }

        int numberOfRowsInTileVersion = tileVersion.getEntries().size();

        for(int columnIterator = 0; columnIterator < numberOfRowsInTileVersion; columnIterator++){  //gehe alle Tileversion-Zeilen durch

            List<Field> currentRow = tileVersion.getEntries().get(columnIterator);
            int lengthOfCurrentRowInTileVersion = currentRow.size();

            for(int rowIterator = 0; rowIterator < lengthOfCurrentRowInTileVersion; rowIterator++){  //gehe in jeder Zeile jede Spalte durch
                if(!currentRow.get(rowIterator).getType().equals("-")){
                    indexMap[x+rowIterator][y+columnIterator] = true;     //falls kein Loch: Kachel liegt darauf --> true
                }
            }
        }

        return indexMap;
    }

    private boolean checkIfMapsAgree(List<Field[][]> maps, List<boolean[][]> indexMaps){   //passen Karten aus der Liste maps zusammen?
        boolean mapsAgree = true;
        int numberOfMapsToBeMerged = maps.size();

        for(int mapIterator1 = 0; mapIterator1 < numberOfMapsToBeMerged; mapIterator1++){   //mapIterator1 und mapIterator2 greifen jeweils zwei Karten heraus
            outerloop:
            for(int mapIterator2 = 0; mapIterator2 < numberOfMapsToBeMerged; mapIterator2++){
                for(int rowIterator = 0; rowIterator < maxMapSize.getX(); rowIterator++){  //durchlaufe in beiden ausgewählten Karten jede Zeile
                    for(int columnIterator = 0; columnIterator < maxMapSize.getY(); columnIterator++){ //...und jede Spalte
                        if (indexMaps.get(mapIterator1)[rowIterator][columnIterator] && indexMaps.get(mapIterator2)[rowIterator][columnIterator] && !maps.get(mapIterator1)[rowIterator][columnIterator].getType().equals(maps.get(mapIterator2)[rowIterator][columnIterator].getType())) {
                            mapsAgree = false;   //falls Überlappung gegeben (ersichtlich aus zugehörigen Indexmaps), aber Zelle stimmt nicht überein --> Karten stimmen nicht überein
                            break outerloop;     //eine Nicht-Übereinstimmung reicht! --> Abbruch aller Schleifen und Rückgabe von false
                        }
                    }
                }
            }
        }
        return mapsAgree;
    }

    private Field[][] uniteMaps(List<Field[][]> maps, List<boolean[][]> indexMaps){   //hier wird bereits vorausgesetzt, dass überprüft wurde, dass die eingegebenen maps alle konsistent sind
        Field[][] resultMap = createEmptyMap();
        int numberOfMapsToBeMerged = maps.size();

        for(int rowIterator = 0; rowIterator < maxMapSize.getX(); rowIterator++){                //initialiere resultmap mit einer Kopie der ersten map aus der übergebenen Liste
            for(int columnIterator = 0; columnIterator < maxMapSize.getY(); columnIterator++){
                resultMap[rowIterator][columnIterator] = maps.get(0)[rowIterator][columnIterator];
            }
        }

        for(int mapIterator = 1; mapIterator < numberOfMapsToBeMerged; mapIterator++){      //für jede weitere map aus der Liste: füge Felder zu resultmap hinzu
            for(int rowIterator = 0; rowIterator < maxMapSize.getX(); rowIterator++){
                for(int columnIterator = 0; columnIterator < maxMapSize.getY(); columnIterator++){
                    if(indexMaps.get(mapIterator)[rowIterator][columnIterator]){
                        resultMap[rowIterator][columnIterator] = maps.get(mapIterator)[rowIterator][columnIterator];
                    }
                }
            }
        }
        return resultMap;
    }

    private Field[][] copyMap(Field[][] map){   //erstelle Kopie der Karte, bei der links, rechts, oben und unten noch eine Reihe mit default-Feldern angehängt ist (damit auch Wege "außenrum" geprüft werden)
        Field[][] copyOfMap = new Field[map.length+2][map[0].length+2];
        for (int i = 0; i < copyOfMap.length; i++) {
            for (int j = 0; j < copyOfMap[i].length; j++) {
                if(i>=1 && j>=1 && i<copyOfMap.length-1 && j<copyOfMap[i].length-1){
                    copyOfMap[i][j] = new Field(map[i-1][j-1].getGegenstand());   //"innerer Teil"
                } else {
                    copyOfMap[i][j] = new Field(info.getDefaultField().getGegenstand());  //"Rahmen aus default-Feldern"
                }
            }
        }
        return copyOfMap;
    }
    
    private boolean valideConnectBy(Field[][] valideMap) {
        List<ConnectBy> connectBys = info.getConnectBys();

        for (int i = 0; i < connectBys.size(); i++) {
            Coordinate connectByCoordinateFrom = new Coordinate(connectBys.get(i).getFrom().getX()+1, connectBys.get(i).getFrom().getY()+1);
            Coordinate connectByCoordinateTo = new Coordinate(connectBys.get(i).getTo().getX()+1, connectBys.get(i).getTo().getY()+1);
            connectedByMap = copyMap(valideMap);
            connectedByMap[connectBys.get(i).getFrom().getX()+1][connectBys.get(i).getFrom().getY()+1].setChecked();   //hier jeweils +1, da neu hinzugefügter "Rahmen" aus default-Feldern berücksichtigt werden muss!
            if(!checkDirections(connectByCoordinateFrom, connectByCoordinateTo, info.getConnectBys().get(i).getUses())){
                return false;
            }
        }
        return true;
    }

    private boolean checkDirections(Coordinate position, Coordinate toPosition, List<Field> uses) {

        TerminalMap.drawMap(connectedByMap);

        Coordinate [] possibleCoordinates = {
            new Coordinate(position.getX(), position.getY() + 1),   //bottom
            new Coordinate(position.getX() + 1, position.getY()),   //right
            new Coordinate(position.getX(), position.getY() - 1),   //top
            new Coordinate(position.getX()-1, position.getY())};    //left

        List<Coordinate> optionalPaths = new ArrayList<Coordinate>();

        for (int i=0; i<possibleCoordinates.length; i++) {

            //check if in one direction is 'to'
            if (possibleCoordinates[i].getX() == toPosition.getX() && possibleCoordinates[i].getY()+1 == toPosition.getY()+1){
               return true;
            }

            //check if one direction is out of the map
            if (possibleCoordinates[i].getX() < 0 | possibleCoordinates[i].getY() < 0 |
                    possibleCoordinates[i].getX()>=connectedByMap.length | possibleCoordinates[i].getY()>=connectedByMap[i].length){
                continue;
            }

            Field currentField = connectedByMap[possibleCoordinates[i].getX()][possibleCoordinates[i].getY()];

            /*
            //check if one direction is out of a Kachel
            if (!currentField.getInKachel()) {
                continue;
            }
            */

            //check if direction has already been checked
            if (currentField.getChecked()) {
                continue;
            }
                currentField.setChecked();

            //check if one direction is a used field
            for (int p=0; p<uses.size(); p++){
                if (currentField.getType().equals(uses.get(p).getType())) {
                    optionalPaths.add(possibleCoordinates[i]);
                    break;
                }
            }
        }
        for (int i=optionalPaths.size()-1; i>=0; i--){
           if (checkDirections(optionalPaths.get(i), toPosition, uses)){
               return true;
           }
        }
        return false;
    }

}