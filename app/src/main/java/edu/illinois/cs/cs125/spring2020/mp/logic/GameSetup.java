package edu.illinois.cs.cs125.spring2020.mp.logic;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

/**public class.*/
public class GameSetup {
    /**mode.*/
    private String mode;
    /**object array of invitees.*/
    private Object[] invitees;
    /**proximity threshold.*/
    private int proximityThreshold;
    /**object array of targets.*/
    private Object[] targets;
    /**cell size.*/
    private int cellSize;
    /**north.*/
    private double areaNorth;
    /**east.*/
    private double areaEast;
    /**south.*/
    private double areaSouth;
    /**west.*/
    private double areaWest;
    /**constructor for GameSetup.*/
    public GameSetup() {

    }
    /**Creates a JSON object representing the configuration of a multiplayer area mode game.
     * Refer to our API documentation for the structure of the output JSON.
     * The configuration is valid if there is at least one invitee and a positive (larger than zero) cell size.
     * @param invitees - all players involved in the game (never null)
     * @param area - the area boundaries
     * @param cellSize - the desired cell size in meters
     * @return a JSON object usable by the /games/create endpoint or null if the configuration is invalid
     * */
    public static JsonObject areaMode(final List<Invitee> invitees, final LatLngBounds area, final int cellSize) {
        JsonArray players = new JsonArray();
        if (invitees.size() < 1 || !(cellSize > 0)) {
            return null;
        }
        JsonObject areaMode = new JsonObject();
        areaMode.addProperty("mode", "area");
        areaMode.addProperty("cellSize", cellSize);
        areaMode.addProperty("areaNorth", area.northeast.latitude);
        areaMode.addProperty("areaEast", area.northeast.longitude);
        areaMode.addProperty("areaSouth", area.southwest.latitude);
        areaMode.addProperty("areaWest", area.southwest.longitude);
        for (Invitee person : invitees) {
            JsonObject invite = new JsonObject();
            invite.addProperty("email", person.getEmail());
            invite.addProperty("team", person.getTeamId());
            players.add(invite);
        }
        areaMode.add("invitees", players);
        return areaMode;
    }
    /** Creates a JSON object representing the configuration of a multiplayer target mode game.
     * Refer to our API documentation for the structure of the output JSON.
     * The configuration is valid if there is at least one invitee, at least one target, and a
     * positive (larger than zero) proximity threshold. If the configuration is invalid, this
     * function returns null.
     * @param invitees - all players involved in the game (never null)
     * @param targets - the positions of all targets (never null)
     * @param proximityThreshold - the proximity threshold in meters
     * @return a JSON object usable by the /games/create endpoint or null if the configuration is invalid
     */
    public static JsonObject targetMode(final List<Invitee> invitees, final List<LatLng> targets,
                                        final int proximityThreshold) {
        if (invitees.size() < 1 || targets.size() < 1 || proximityThreshold <= 1) {
            return null;
        }
        JsonObject target = new JsonObject();
        target.addProperty("mode", "target");
        target.addProperty("proximityThreshold", proximityThreshold);
        JsonArray targetArray = new JsonArray();
        for (LatLng a: targets) {
            JsonObject save = new JsonObject();
            save.addProperty("latitude", a.latitude);
            save.addProperty("longitude", a.longitude);
            targetArray.add(save);
        }
        target.add("targets", targetArray);
        JsonArray players = new JsonArray();
        for (Invitee person : invitees) {
            JsonObject invite = new JsonObject();
            invite.addProperty("email", person.getEmail());
            invite.addProperty("team", person.getTeamId());
            players.add(invite);
        }
        target.add("invitees", players);
        return target;
    }
}
