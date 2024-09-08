import java.util.ArrayList;

/**
 * The ItemsManager class manages the creation of objects related to items and decorations.
 */
public class ItemsManager {
    /**
     * Constructs item objects and decoration objects based on the provided data.
     *
     * @param datasOfItems An array of strings containing data for items and decorations.
     * @return An ArrayList containing two ArrayLists: the first ArrayList contains ClassBuilder objects
     * representing classrooms, and the second ArrayList contains Decoration objects.
     */
    public ArrayList<ArrayList> makeItemObjects(String[] datasOfItems) {

        ArrayList<ClassBuilder> classObjects = new ArrayList<>();
        ArrayList<Decoration> decorationObjects = new ArrayList<>();
        ArrayList<ArrayList> returnList = new ArrayList<>();

        for(String line: datasOfItems) {
            String[] parts = line.split("\t");
            // Check if the data represents a classroom or a decoration.
            if (parts[0].equals("CLASSROOM")) {
                String nameC = parts[1];
                String shapeC = parts[2];
                Double widthC = Double.parseDouble(parts[3]);
                Double lengthC = Double.parseDouble(parts[4]);
                Double heightC = Double.parseDouble(parts[5]);

                // Create a ClassBuilder object for circle or rectangle shaped classrooms.
                if(shapeC.equals("Circle")) {
                    ClassBuilder cb = new ClassBuilder.
                            Builder(nameC, heightC).
                            radius(widthC/2).build();
                    classObjects.add(cb);
                }
                else if (shapeC.equals("Rectangle")) {
                    ClassBuilder cb2 = new ClassBuilder.
                            Builder(nameC, heightC).
                            width(widthC).
                            length(lengthC).build();
                    classObjects.add(cb2);
                }
            }
            // Create a Decoration object for decorations.
            else if (parts[0].equals("DECORATION")) {
                String nameD = parts[1];
                String type = parts[2];
                double unitArea = (parts.length == 4) ? 1 : Double.parseDouble(parts[4]);
                double price = Double.parseDouble(parts[3]);
                Decoration dObject = new Decoration();
                dObject.setName(nameD);
                dObject.setUnitArea(unitArea);
                dObject.setPrice(price);
                dObject.setType(type);
                decorationObjects.add(dObject);
            }
        }
        // Add the ArrayLists containing class objects and decoration objects to the return list.
        returnList.add(classObjects);
        returnList.add(decorationObjects);
        return returnList;

    }




}
