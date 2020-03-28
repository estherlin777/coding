package edu.illinois.cs.cs125.spring2020.mp.logic;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.gson.JsonObject;

import java.util.List;

public class GameSetup {
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
        JsonObject json = new JsonObject();
        return json;
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
        JsonObject json = new JsonObject();
        return json;
    }
}
