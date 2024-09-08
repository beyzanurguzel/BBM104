/**
 * The ClassBuilder class is used to construct classroom objects with various shapes and dimensions.
 */
public class ClassBuilder {
    private String name;        // The name of the classroom.
    private double height;      // The height of the classroom.
    private double radius;      // The radius of the circular classroom (if applicable).
    private double width;       // The width of the rectangular classroom (if applicable).
    private double length;      // The length of the rectangular classroom (if applicable).
    /**
     * Constructs a ClassBuilder object using the builder pattern.
     *
     * @param builder The builder object containing the necessary parameters for constructing the classroom.
     */
    ClassBuilder(Builder builder) {
        name = builder.getName();
        height = builder.getHeight();
        radius = builder.getRadius();;
        width = builder.getWidth();
        length = builder.getLength();
    }
    /**
     * The Builder class provides methods to set the parameters for constructing a classroom.
     */
    public static class Builder {
        private String name;       // The name of the classroom.
        private double height;     // The height of the classroom.
        private double radius;     // The radius of the circular classroom.
        private double width;      // The width of the rectangular classroom.
        private double length;     // The length of the rectangular classroom.

        /**
         * Constructs a Builder object with the specified name and height.
         *
         * @param name   The name of the classroom.
         * @param height The height of the classroom.
         */
        public Builder(String name, double height) {
            this.height = height;
            this.name = name;
        }
        public Builder radius(double radius) {
            this.radius = radius;
            return this;
        }
        public Builder width(double width) {
            this.width = width;
            return this;
        }
        public Builder length(double length) {
            this.length = length;
            return this;
        }
        public ClassBuilder build() {
            return new ClassBuilder(this);
        }
        public String getName() {
            return this.name;
        }

        public double getHeight() {
            return this.height;
        }

        public double getRadius() {
            return this.radius;
        }

        public double getWidth() {
            return this.width;
        }

        public double getLength() {
            return this.length;
        }


    }
    public String getName() {
        return this.name;
    }

    public double getHeight() {
        return this.height;
    }

    public double getRadius() {
        return this.radius;
    }

    public double getWidth() {
        return this.width;
    }

    public double getLength() {
        return this.length;
    }

    /**
     * Calculates the area of the floor of the rectangular classroom.
     *
     * @param width  The width of the classroom.
     * @param length The length of the classroom.
     * @return The area of the floor.
     */
    public double calculateFloorArea(double width, double length) {
        return width * length;
    }
    /**
     * Calculates the area of the floor of the circular classroom.
     *
     * @param radius The radius of the classroom.
     * @return The area of the floor.
     */
    public double calculateFloorArea(double radius) {
        return Math.PI * radius * radius;
    }
    /**
     * Calculates the total surface area of the walls of the rectangular classroom.
     *
     * @param width  The width of the classroom.
     * @param length The length of the classroom.
     * @param height The height of the classroom.
     * @return The total surface area of the walls.
     */
    public double calculateWallsArea(double width, double length, double height) {
        return  2 * (length + width) * height;
    }
    /**
     * Calculates the total surface area of the walls of the circular classroom.
     *
     * @param radius The radius of the classroom.
     * @param height The height of the classroom.
     * @return The total surface area of the walls.
     */
    public double calculateWallsArea(double radius, double height) {
        return 2 * Math.PI * radius * height;
    }
}
