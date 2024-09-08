import java.util.ArrayList;
/**
 * The DecorateManager class manages the decoration of classrooms.
 */
public class DecorateManager {
    /**
     * Decorates the classrooms based on the given decoration data and writes the result to an output file.
     *
     * @param decorateDatas An array of strings containing decoration data.
     * @param classes An ArrayList containing ClassBuilder objects representing classrooms to be decorated.
     * @param decorations An ArrayList containing Decoration objects representing available decorations.
     * @param pathOfOutput The path to the output file where the decorated classrooms will be written.
     */
    public void decorate(String[] decorateDatas, ArrayList<ClassBuilder> classes, ArrayList<Decoration> decorations, String pathOfOutput) {
        int totalCost = 0;
        for(String line: decorateDatas) {
            // we identify the features of decorations. While calculating the area of the wall,
            // if we cover the surface with tile, we divide the area of the surface to the area of
            // the tile. We did this for finding the number of tiles that will be used.
            // if it is not tile, we divide the area of the surface to 1 automatically.
            // with this calculations, if it is not tile, the area of the surface remained the same.
            Double wallPrice = 0.0;
            Double wallUnitArea = 0.0 ;
            Double floorPrice = 0.0;
            Double floorUnitArea = 0.0;
            String wallDecoreType = "";
            String[] parts = line.split("\t");
            // we find the decor details by determining the decor features.
            for (Decoration decor: decorations) {
                if(decor.getName().equals(parts[1])){
                    wallPrice = decor.getPrice();
                    wallUnitArea = decor.getUnitArea();
                    wallDecoreType = decor.getType();
                }if(decor.getName().equals(parts[2])) {
                    floorPrice = decor.getPrice();
                    floorUnitArea = decor.getUnitArea();
                }
            }

            for (ClassBuilder cb : classes) {
                if(cb.getName().equals(parts[0])) {
                    if (cb.getRadius() != 0) {

                        // if we cover the surface with tiles, areaOfWall or areaOfFloor represents the number of tiles.
                        // the ClassBuilder class have methods for calculating the areas of surface according to class's shape.
                        double areaOfWall = Math.ceil(cb.calculateWallsArea(cb.getRadius(), cb.getHeight()))/wallUnitArea;
                        double priceOfWall = Math.ceil( cb.calculateWallsArea(cb.getRadius(), cb.getHeight())/wallUnitArea* wallPrice);

                        double areaOfFloor = Math.ceil(cb.calculateFloorArea(cb.getRadius())/floorUnitArea);
                        double priceOfFloor = areaOfFloor * floorPrice;

                        int total = (int) (priceOfFloor + priceOfWall);
                        totalCost += total;
                        // we override the toString method of Object class for preparing the string message of output file.
                        String message = toString(cb.getName(), wallDecoreType, (int) areaOfWall, (int) areaOfFloor, total);
                        FileOutput.writeToFile(pathOfOutput, message, true, true);
                    }else{
                        double areaOfWall2 = Math.ceil(cb.calculateWallsArea(cb.getWidth(), cb.getLength(), cb.getHeight())/wallUnitArea);
                        double priceOfWall2 = Math.ceil(areaOfWall2 * wallPrice);

                        double areaOfFloor2 = Math.ceil(cb.calculateFloorArea(cb.getWidth(), cb.getLength())/floorUnitArea);
                        double priceOfFloor2 = Math.ceil(areaOfFloor2 * floorPrice);

                        int total = (int) (priceOfFloor2 + priceOfWall2);
                        totalCost += total;
                        String message = toString(cb.getName(), wallDecoreType, (int) areaOfWall2, (int) areaOfFloor2, total);
                        FileOutput.writeToFile(pathOfOutput, message, true, true);
                    }
                }
            }
        }
        FileOutput.writeToFile(pathOfOutput, "Total price is: " + totalCost + "TL.", true, false);

    }
    /**
     * Generates a string message representing the decoration details of a classroom.
     *
     * @param className The name of the classroom.
     * @param decoreTypeOfWalls The type of decoration used for walls.
     * @param decoreFieldOfWalls The area or number of units of decoration used for walls.
     * @param decoreFieldOfFloor The area or number of units of decoration used for flooring.
     * @param total The total cost of decorations for the classroom.
     * @return A string message representing the decoration details.
     */
    public String toString(String className, String decoreTypeOfWalls, int decoreFieldOfWalls, int decoreFieldOfFloor, int total) {

        if( decoreTypeOfWalls.equals("Tile")) {
            String message = "Classroom " + className + " used " + decoreFieldOfWalls + " Tiles for walls and used " +
                    decoreFieldOfFloor + " Tiles for flooring, these costed " + total + "TL.";
            return message;
        }else{
            String message = "Classroom " + className + " used " + decoreFieldOfWalls + "m2 of " + decoreTypeOfWalls +
                    " for walls and used " + decoreFieldOfFloor + " Tiles for flooring, these costed " + total + "TL.";
            return message;
        }
    }
}
