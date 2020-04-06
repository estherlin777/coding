package edu.illinois.cs.cs125.spring2020.mp.logic;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.neovisionaries.ws.client.WebSocket;

import java.util.HashMap;
import java.util.Map;

import edu.illinois.cs.cs125.spring2020.mp.R;

/**
 * Represents an area mode game. Keeps track of cells and the player's most recent capture.
 * <p>
 * All these functions are stubs that you need to implement.
 * Feel free to add any private helper functions that would be useful.
 * See {@link TargetGame} for an example of how multiplayer games are handled.
 */
public final class AreaGame extends Game {

    // You will probably want some instance variables to keep track of the game state
    // (similar to the area mode gameplay logic you previously wrote in GameActivity)
    /**Map.*/
    private Map<String, Cell> capturedCells = new HashMap<>();
    /**emailID.*/
    private String emailID;
    /**google map.*/
    private GoogleMap map;
    /**areaNorth.*/
    private double areaNorth;
    /**areaEast.*/
    private double areaEast;
    /**areaSouth.*/
    private double areaSouth;
    /**areaWest.*/
    private double areaWest;
    /**cellSize.*/
    private int cellSize;
    /**area divider.*/
    private AreaDivider divider;
    /**
     * Creates a game in area mode.
     * <p>
     * Loads the current game state from JSON into instance variables and populates the map
     * to show existing cell captures.
     * @param email the user's email
     * @param map the Google Maps control to render to
     * @param webSocket the websocket to send updates to
     * @param fullState the "full" update from the server
     * @param context the Android UI context
     */
    public AreaGame(final String email, final GoogleMap map, final WebSocket webSocket,
                    final JsonObject fullState, final Context context) {
        super(email, map, webSocket, fullState, context);
        this.map = map;
        emailID = email;
        areaNorth = fullState.get("areaNorth").getAsDouble();
        areaEast = fullState.get("areaEast").getAsDouble();
        areaSouth = fullState.get("areaSouth").getAsDouble();
        areaWest = fullState.get("areaWest").getAsDouble();
        cellSize = fullState.get("cellSize").getAsInt();
        divider = new AreaDivider(areaNorth, areaEast, areaSouth, areaWest, cellSize);
        divider.renderGrid(map);
        for (JsonElement cell : fullState.getAsJsonArray("cells")) {
            JsonObject cellObject = cell.getAsJsonObject();
            Cell currentCell = new Cell(map, cellObject.get("x").getAsInt(), cellObject.get("y").getAsInt(),
                    cellObject.get("email").getAsString(), cellObject.get("team").getAsInt());
            capturedCells.put(email, currentCell);
            polygonAdd(map, currentCell);
        }
    }

    /** Create a private cell class.*/
    private class Cell {
        /**x.*/
        private int x;
        /**y.*/
        private int y;
        /**email.*/
        private String email;
        /**teamId.*/
        private int teamId;
        /**map.*/
        private GoogleMap map;

        /**contructor.
         * @param setMap setMap.
         * @param setX set x
         * @param setY set y
         * @param setEmail set email
         * @param setId setId
         */
        Cell(final GoogleMap setMap, final int setX, final int setY, final String setEmail, final int setId) {
            x = setX;
            y = setY;
            email = setEmail;
            teamId = setId;
            map = setMap;
        }
        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }
        public String getCellEmail() {
            return email;
        }
        public int getCellTeamId() {
            return teamId;
        }
    }

    /** polygon add.
     * @param setMap set map
     * @param currentCell current cell
     */
    private void polygonAdd(final GoogleMap setMap, final Cell currentCell) {
        PolygonOptions polygon = new PolygonOptions();
        LatLngBounds cellBounds = divider.getCellBounds(currentCell.getX(), currentCell.getY());
        LatLng northWest = new LatLng(cellBounds.northeast.latitude, cellBounds.southwest.longitude);
        LatLng southEast = new LatLng(cellBounds.southwest.latitude, cellBounds.northeast.longitude);
        polygon.add(cellBounds.northeast, northWest, cellBounds.southwest, southEast);
        if (currentCell.getCellTeamId() == TeamID.TEAM_BLUE) {
            polygon.fillColor(getContext().getColor(R.color.blue));
        } else if (currentCell.getCellTeamId() == TeamID.TEAM_RED) {
            polygon.fillColor(getContext().getColor(R.color.red));
        } else if (currentCell.getCellTeamId() == TeamID.TEAM_GREEN) {
            polygon.fillColor(getContext().getColor(R.color.green));
        } else if (currentCell.getCellTeamId() == TeamID.TEAM_YELLOW) {
            polygon.fillColor(getContext().getColor(R.color.yellow));
        }
        setMap.addPolygon(polygon);
    }

    /**
     * Called when the user's location changes.
     * <p>
     * Area mode games detect whether the player is in an uncaptured cell. Capture is possible if
     * the player has no captures yet or if the cell shares a side with the previous cell captured by
     * the player. If capture occurs, a polygon with the team color is added to the cell on the map
     * and a cellCapture update is sent to the server.
     * @param location the player's most recently known location
     */
    @Override
    public void locationUpdated(final LatLng location) { }

    /**
     * Processes an update from the server.
     * <p>
     * Since playerCellCapture events are specific to area mode games, this function handles those
     * by placing a polygon of the capturing player's team color on the newly captured cell and
     * recording the cell's new owning team.
     * All other message types are delegated to the superclass.
     * @param message JSON from the server (the "type" property indicates the update type)
     * @return whether the message type was recognized
     */
    @Override
    public boolean handleMessage(final JsonObject message) {
        return false;
    }

    /**
     * Gets a team's score in this area mode game.
     * @param teamId the team ID
     * @return the number of cells owned by the team
     */
    @Override
    public int getTeamScore(final int teamId) {
        return 0;
    }

}
