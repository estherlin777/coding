package edu.illinois.cs.cs125.spring2020.mp.logic;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class AreaDivider {
    /**north.*/
    private double north;
    /**east.*/
    private double east;
    /**south.*/
    private double south;
    /**west.*/
    private double west;
    /**cell.*/
    private int cell;
    /** x cell size*/
    private double xCell;
    /** y cell size*/
    private double yCell;
    /** Creates an AreaDivider for an area.
     * @param setNorth - latitude of the north boundary
     * @param setEast - longitude of the east boundary
     * @param setSouth - latitude of the south boundary
     * @param setWest - longitude of the east boundary
     * @param setCellSize - the requested side length of each cell, in meters */
    public AreaDivider(final double setNorth, final double setEast, final double setSouth, final
        double setWest, final int setCellSize) {
        north = setNorth;
        east = setEast;
        south = setSouth;
        west = setWest;
        cell = setCellSize;
        xCell = LatLngUtils.distance(north, west, north, east) / getXCells();
        yCell = LatLngUtils.distance(north, west, south, west) / getYCells();

    }

    /** Gets the boundaries of the specified cell as a Google Maps LatLngBounds object.
     * @param x - the cell's X coordinate
     * @param y - the cell's Y coordinate
     * @return  the boundaries of the cell
     * */
    public com.google.android.gms.maps.model.LatLngBounds getCellBounds(final int x, final int y) {
        double a = (north - south) / getYCells();
        double b = (east - west) / getXCells();
        LatLng southwest = new LatLng(south + y * a, west + x * b);
        LatLng northeast = new LatLng(southwest.latitude + a, southwest.longitude + b);
        LatLngBounds test = new LatLngBounds(southwest, northeast);
        return test;
    }

    /** Gets the number of cells between the west and east boundaries.
     * @return the number of cells in the X direction
     * */
    public int getXCells() {
        double x = LatLngUtils.distance(south, east, south, west);
        double y = Math.ceil(x / cell);
        return (int) y;
    }

    /** Gets the X coordinate of the cell containing the specified location. The point is not
     * necessarily within the area. If it is not, the return value must not appear to be a valid
     * cell index. For example, returning 0 for a point even slightly west of the west boundary is
     * not allowed.
     * @param location - the location
     * @return the X coordinate of the cell containing the lat-long point*/
    public int getXIndex(final com.google.android.gms.maps.model.LatLng location) {
        double x = LatLngUtils.distance(location.latitude, west, location.latitude, location.longitude);
        for (int i = 0; i < getXCells(); i++) {
            if (x < (i + 1) * xCell) {
                return i;
            }
        }
        return -1;
    }
    /** Gets the number of cells between the south and north boundaries.
     * @return the number of cells in the Y directon */
    public int getYCells() {
        double x = LatLngUtils.distance(south, east, north, east);
        double y = Math.ceil(x / cell);
        return (int) y;
    }

    /** Gets the Y coordinate of the cell containing the specified location. The point is not
     *necessarily within the area. If it is not, the return value must not appear to be a valid cell
     *index. For example, returning 0 for a point even slightly south of the south boundary is not
     *allowed.
     * @param location - the location
     * @return the Y coordinate of the cell containing the lat-long point */
    public int getYIndex(final com.google.android.gms.maps.model.LatLng location) {
        double y = LatLngUtils.distance(south, location.longitude, location.latitude, location.longitude);
        for (int i = 0; i < getYCells(); i++) {
            if (y < (i + 1) * yCell) {
                return i;
            }
        }
        return -1;
    }
    /** Returns whether the configuration provided to the constructor is valid. The configuration
     * is valid if the cell size is positive the bounds delimit a region of positive area. That is,
     * the east boundary must be strictly further east than the west boundary and the north boundary
     * must be strictly further north than the south boundary. Due to floating-point strangeness,
     * you may find our LatLngUtils.same function helpful if equality comparison of double variables
     * does not work as expected.
     * @return whether this AreaDivider can divide a valid area */
    public boolean isValid() {
        if (cell > 0) {
            if (east > west && north > south) {
                return true;
            }
        }
        return false;
    }
    /** Draws the grid to a map using solid black polylines. There should be one line on each of the
     *four boundaries of the overall area and as many internal lines as necessary to divide the rows
     *and columns of the grid. Each line should span the whole width or height of the area rather
     *than the side of just one cell. For example, an area divided into a 2x3 grid would be drawn
     *with 7 lines total: 4 for the outer boundaries, 1 vertical line to divide the west half from
     *the east half (2 columns), and 2 horizontal lines to divide the area into 3 rows. See the
     *provided addLine function from GameActivity for how to add a line to the map. Since these
     *lines should be black, they should not be paired with any extra "border" lines. If equality
     *comparisons of double variables do not work as expected, consider taking advantage of our
     *LatLngUtils.same function. Draws the grid to a map using solid black polylines. There should
     *be one line on each of the four boundaries of the overall area and as many internal lines as
     *necessary to divide the rows and columns of the grid. Each line should span the whole width or
     *height of the area rather than the side of just one cell. For example, an area divided into a
     *2x3 grid would be drawn with 7 lines total: 4 for the outer boundaries, 1 vertical line to
     *divide the west half from the east half (2 columns), and 2 horizontal lines to divide the area
     *into 3 rows. See the provided addLine function from GameActivity for how to add a line to the
     *map. Since these lines should be black, they should not be paired with any extra "border"
     *lines. If equality comparisons of double variables do not work as expected, consider taking
     *advantage of our LatLngUtils.same function.
     *@param map - the Google map to draw on */
    public void renderGrid(final com.google.android.gms.maps.GoogleMap map) {
        System.out.println("0");
    }
}
